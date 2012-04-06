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
package utils;

import java.util.Collection;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class CollectionUtils
{
    private CollectionUtils()
    {
        // no-op
    }

    /**
     * Gets the first element of the list.
     *
     * @param list the list
     * @param <T> the list's generic type
     * @return the first element of the list, or null if the list is null or empty.  The returned value will also be null
     * if the first element of the list is null.
     */
    public static <T> T first(List<T> list)
    {
        return isEmpty(list) ? null : list.get(0);
    }

    /**
     * Gets the last element of the list.
     *
     * @param list the list
     * @param <T> the list's generic type
     * @return the last element of the list, or null if the list is null or empty.  The returned value will also be null
     * if the last element of the list is null.
     */
    public static <T> T last(List<T> list)
    {
        return isEmpty(list) ? null : list.get(list.size() - 1);
    }

    /**
     * Checks if a collection is null or empty.
     *
     * @param c the collection
     * @return true if the collection is null or has a length of 0. A collection containing null entries is considered
     * to be non-empty.
     */
    public static boolean isEmpty(Collection c)
    {
        return c == null || c.isEmpty();
    }
}
