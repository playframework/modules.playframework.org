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

import play.db.ebean.Model;
import utils.CollectionUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;
import static play.data.validation.Constraints.Required;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
@Table(name = "MPO_MODULE")
public class Module extends Model implements ModuleAccessor {
	@Id
	public Long id;

    @ManyToOne(optional = false)
    public User owner;
    
	@Column(name = "module_key", nullable = false, unique = true)
	@Required
	public String key;

	@Column(nullable = false)
	@Required
	public String name;

	@Column(nullable = false, length = 500)
	@Required
	public String summary;

	@Column(nullable = false, length = 4000)
	@Required
	public String description;

	@ManyToOne(optional = true, cascade = {DETACH, REFRESH})
	public Category category;

	// there's no length limit for a URL according to RFC 2616, but 2500 should be enough
	@Column(nullable = true, length = 2500)
	public String projectUrl;

	@Column(nullable = true, length = 2500)
	public String demoUrl;

	@Column(nullable = true, length = 2500)
	public String avatarUrl;

	@Column(nullable = false)
	@Required
	public String licenseType;

	@Column(nullable = true)
	public String licenseUrl;

	@Column(nullable = false)
	public Long downloadCount = 0L;

	@Column(nullable = false)
	public Long upVoteCount = 0L;

	@Column(nullable = false)
	public Date createdOn = new Date();

	@Column(nullable = false)
	public Date updatedOn = new Date();

	@OneToOne(optional = true)
	public Rating rating;

	@OneToMany(fetch = LAZY, cascade = ALL, orphanRemoval = true)
	public List<Comment> comments;

	@ManyToMany(cascade = {DETACH, PERSIST, REFRESH})
	public List<Tag> tags;

	public static final Finder<Long, Module> FIND = new Finder<Long, Module>(Long.class, Module.class);

    public List<ModuleVersion> getVersions()
    {
        return ModuleVersion.findByModule(this);
    }

	public ModuleVersion getMostRecentVersion() {
		return CollectionUtils.last(getVersions());
	}

	public static Module findByModuleKey(String moduleKey) {
		return FIND.where().eq("key", moduleKey).findUnique();
	}

	@Override
	public Module getModule() {
		return this;
	}

	public static List<Module> findByTag(Tag tag) {
		return FIND.where().in("tags", tag).findList();
	}

	public static List<Module> findByCategory(Category category) {
		return FIND.where().eq("category", category).findList();
	}

	public static List<Module> findMostRecent(int count) {
		return FIND.where().orderBy("updatedOn DESC").setMaxRows(count).findList();
	}

    public static List<Module> ownedBy(User user)
    {
        return FIND.where()
                   .eq("owner", user)
                   .findList();
    }

    public static Module findById(Long id)
    {
        return FIND.where()
                   .eq("id", id)
                   .findUnique();
    }
}
