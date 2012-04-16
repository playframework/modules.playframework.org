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
import be.objectify.deadbolt.models.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class UserRole extends Model implements Role
{
    @Id
    public Long id;

    @Column(nullable = false, unique = true)
    public String roleName;

    @Column(nullable = false, length = 1000)
    public String description;

    public static final Finder<Long, UserRole> FIND = new Finder<Long, UserRole>(Long.class,
                                                                                 UserRole.class);

    @Override
    public String getRoleName()
    {
        return roleName;
    }

    public static UserRole findByRoleName(String roleName)
    {
        return FIND.where()
                   .eq("roleName",
                       roleName)
                   .findUnique();

    }
}
