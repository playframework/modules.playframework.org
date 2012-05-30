/*
 * Copyright 2012 The Play! Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package models;

import play.data.validation.Constraints;
import utils.CollectionUtils;
import utils.Transformer;

import javax.persistence.*;
import java.util.*;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class ModuleVersion extends AbstractModel {

    @ManyToOne
    public Module playModule;

    // this will be taken from Build.scala
    @Column(nullable = false)
    @Constraints.Required
    public String versionCode;

    @Column(nullable = false)
    @Constraints.Required
    public String releaseNotes;

    @Column(nullable = false)
    public Date releaseDate;

    @ManyToMany
    public List<PlayVersion> compatibility;

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public BinaryContent binaryFile;

    @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public BinaryContent sourceFile;

    @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public BinaryContent documentFile;

    public static final Finder<Long, ModuleVersion> FIND = new Finder<Long, ModuleVersion>(Long.class,
            ModuleVersion.class);

    public static int count() {
        return FIND.findRowCount();
    }

    public Set<PlayVersion.MajorVersion> getMajorVersions()
    {
        Set<PlayVersion.MajorVersion> majorVersions = new HashSet<PlayVersion.MajorVersion>();
        Transformer<PlayVersion, PlayVersion.MajorVersion> transformer = new MajorVersionExtractor();
        majorVersions.addAll(CollectionUtils.transform(compatibility,
                                                       transformer));
        return majorVersions;
    }

    public static List<ModuleVersion> findByModule(Module module) {
        return FIND.where()
                .eq("playModule", module)
                .order("versionCode ASC")
                .findList();
    }

    public static List<Module> findModulesByPlayVersion(List<PlayVersion> playVersions) {
        List<ModuleVersion> matches = FIND.where()
                .in("compatibility", playVersions)
                .query().fetch("playModule")
                .findList();
        Set<Module> modules = new HashSet<Module>();
        for (ModuleVersion match : matches) {
            modules.add(match.playModule);
        }

        return new ArrayList<Module>(modules);
    }

    private class MajorVersionExtractor implements Transformer<PlayVersion, PlayVersion.MajorVersion>
    {
        @Override
        public PlayVersion.MajorVersion transform(PlayVersion playVersion)
        {
            return playVersion.majorVersion;
        }
    }
}
