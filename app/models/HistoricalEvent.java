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

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class HistoricalEvent extends AbstractModel
{
    @Column(nullable = false)
    public Date creationDate;

    @Column(nullable = false)
    public String category;

    @Column(length = 1000,
            nullable = false)
    public String message;

    public static final Finder<Long, HistoricalEvent> FIND = new Finder<Long, HistoricalEvent>(Long.class,
                                                                                               HistoricalEvent.class);

    public static List<HistoricalEvent> findMostRecent(int count)
    {
        return FIND.where().orderBy("creationDate DESC").setMaxRows(count).findList();
    }
}
