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

import models.security.UserRole;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Iterator;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class User extends Model
{
    @Id
    public Long id;

    @Column(nullable = false, length = 40, unique = true)
    public String userName;

    @Column(nullable = false, length = 100)
    public String displayName;

    @Column(nullable = false, length = 64)
    public String password;

    @Column(nullable = true, length = 2500)
    public String avatarUrl;

    @Column(nullable = false)
    public Boolean accountActive;

    @Column(nullable = true, length = 36)
    public String confirmationCode;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    public List<UserRole> roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Vote> votes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Rate> rates;

    public static final Finder<Long, User> FIND = new Finder<Long, User>(Long.class,
                                                                         User.class);

    public static User findByUserName(String userName)
    {
        return FIND.where()
                   .eq("userName", userName)
                   .findUnique();
    }

    public boolean hasVoted(Module module)
    {
        boolean hasVoted = false;

        for (Iterator<Vote> iterator = votes.iterator(); !hasVoted && iterator.hasNext(); )
        {
            Vote vote = iterator.next();
            hasVoted = module.equals(vote.module);
        }

        return hasVoted;
    }

    public boolean hasRated(Module module)
    {
        boolean hasRated = false;

        for (Iterator<Rate> iterator = rates.iterator(); !hasRated && iterator.hasNext(); )
        {
            Rate rate = iterator.next();
            hasRated = module.equals(rate.module);
        }

        return hasRated;
    }
}
