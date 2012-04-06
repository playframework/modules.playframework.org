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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class Account extends Model
{
    @Id
    public Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    public User user;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Module> modules;

    public static final Finder<Long, Account> FIND = new Finder<Long, Account>(Long.class,
                                                                               Account.class);

    public static Account getByUser(User user)
    {
        return FIND.where()
                   .eq("user", user)
                   .findUnique();
    }
}
