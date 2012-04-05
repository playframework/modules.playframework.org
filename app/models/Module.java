/*
 * Copyright 2012 Steve Chaloner
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

import play.db.ebean.Model;
import utils.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import java.util.Date;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class Module extends Model
{
    @Id
    public Long id;

    @Column(nullable = false, unique = true)
    public String moduleKey;

    @Column(nullable = false)
    public String moduleName;

    @Column(nullable = false, length = 500)
    public String summary;

    @Column(nullable = false, length = 4000)
    public String description;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    public Category category;

    // there's no length limit for a URL according to RFC 2616, but 2500 should be enough
    @Column(nullable = true, length = 2500)
    public String projectUrl;

    @Column(nullable = true, length = 2500)
    public String demoUrl;

    @Column(nullable = true, length = 2500)
    public String avatarUrl;

    @Column(nullable = false)
    public String licenseType;

    @Column(nullable = true)
    public String licenseUrl;

    @Column(nullable = false)
    public Long downloadCount;

    @Column(nullable = false)
    public Long upVoteCount;

    @Column(nullable = false)
    public Date createdOn;

    @Column(nullable = false)
    public Date updatedOn;

    @OneToOne(optional = false)
    public Rating rating;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("versionCode ASC")
    public List<ModuleVersion> versions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Comment> comments;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    public List<Tag> tags;

    public static final Finder<Long, Module> FIND = new Finder<Long, Module>(Long.class,
                                                                             Module.class);

    public ModuleVersion getMostRecentVersion()
    {
        return CollectionUtils.last(versions);
    }

    public static Module findByModuleKey(String moduleKey)
    {
        return FIND.where()
                   .eq("moduleKey", moduleKey)
                   .findUnique();
    }

    public static List<Module> findByTag(Tag tag)
    {
        return FIND.where()
                   .in("tags", tag)
                   .findList();
    }

    public static List<Module> findByCategory(Category category)
    {
        return FIND.where()
                   .eq("category", category)
                   .findList();
    }

    public static List<Module> findMostRecent(int count)
    {
        return FIND.where()
                   .orderBy("updatedOn DESC")
                   .setMaxRows(count)
                   .findList();
    }
}
