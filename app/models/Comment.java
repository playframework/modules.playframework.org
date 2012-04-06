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
public class Comment extends Model
{
    @Id
    public Long id;

    @OneToOne(optional = false)
    public User author;

    @Column(nullable = false)
    public Date postDate;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    public Comment inReplyTo;

    @Column(length = 1000, nullable = false)
    public String commentText;

    @Column(nullable = false)
    public Boolean visible;

    public static final Finder<Long, Comment> FIND = new Finder<Long, Comment>(Long.class,
                                                                               Comment.class);

    public static List<Comment> findByAuthor(User author)
    {
        return FIND.where()
                   .eq("author", author)
                   .findList();
    }
}
