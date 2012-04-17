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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class PlayVersion extends AbstractModel {

    public enum MajorVersion {
        ONE("1"),
        TWO("2");

        private MajorVersion(String numeric) {
            this.numeric = numeric;
        }

        public String numeric;
    }


    @Column(nullable = false, unique = true)
    @Constraints.Required
    public String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public MajorVersion majorVersion;

    @Column(nullable = false)
    @Constraints.Required
    public String documentationUrl;
    
    public static final Finder<Long, PlayVersion> FIND = new Finder<Long, PlayVersion>(Long.class,
            PlayVersion.class);

    public static int count() {
        return FIND.findRowCount();
    }

    public static List<PlayVersion> getAll() {
        return FIND.where()
                .order("name ASC")
                .findList();
    }

    public static PlayVersion findByName(String name) {
        return FIND.where()
                .eq("name", name)
                .findUnique();
    }

    /**
     * Allow 1.2 to match 1.2, 1.2.1, 1.2.2, etc
     *
     * @param name
     * @return
     */
    public static List<PlayVersion> findByLooseName(String name) {
        return FIND.where()
                .startsWith("name", name)
                .findList();
    }

    public static List<PlayVersion> findByMajorVersion(MajorVersion majorVersion) {
        return FIND.where()
                .eq("majorVersion", majorVersion)
                .findList();
    }
}
