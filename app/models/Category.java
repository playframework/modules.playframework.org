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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class Category extends Model
{
    @Id
    public Long id;

    @Column(nullable = false, unique = true)
    public String name;

    public static final Finder<Long, Category> FIND = new Finder<Long, Category>(Long.class,
                                                                                 Category.class);

    public static Category findByName(String name)
    {
        return FIND.where()
                   .eq("name", name)
                   .findUnique();
    }
}
