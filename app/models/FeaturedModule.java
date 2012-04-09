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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class FeaturedModule extends Model implements ModuleAccessor
{
    @Id
    public Long id;

    @OneToOne(optional = false)
    public Module playModule;

    @Column(nullable = true, length = 1000)
    public String description;

    @Column(nullable = false)
    public Date creationDate;

    @Column(nullable = false)
    public Boolean sticky;
    
    public static final Finder<Long, FeaturedModule> FIND = new Finder<Long, FeaturedModule>(Long.class,
                                                                                             FeaturedModule.class);

    public static List<FeaturedModule> getAll()
    {
        return FIND.all();
    }
    
    public static List<FeaturedModule> getStickyModules()
    {
        return FIND.where()
                   .eq("sticky", Boolean.TRUE)
                   .findList();
    }

    public static List<FeaturedModule> getEphemeralModules()
    {
        return FIND.where()
                   .eq("sticky", Boolean.FALSE)
                   .findList();
    }
    
    @Override
    public Module getModule()
    {
        return playModule;
    }
}
