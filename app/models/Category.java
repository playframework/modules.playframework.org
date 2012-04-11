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
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class Category extends Model
{
    @Id
    public Long id;

    @Column(nullable = false, unique = true)
    @Constraints.Required
    public String name;

    public static Map<String, String> options()
    {
        Map<String, String> options = new LinkedHashMap<String, String>();

        for (Category category : getAll())
        {
            options.put(category.id.toString(),
                        category.name);
        }

        return options;
    }

    public static final Finder<Long, Category> FIND = new Finder<Long, Category>(Long.class,
                                                                                 Category.class);

    public static List<Category> getAll()
    {
        return FIND.all();
    }

    public static Category findByName(String name)
    {
        return FIND.where()
                   .eq("name", name)
                   .findUnique();
    }
}
