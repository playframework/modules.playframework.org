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
     * Get the first item from a list, or null if the list is null or empty.
     *
     * @param list the list
     * @param <T> the generic type of the list
     * @return the first item, or null
     */
    public static <T> T first(List<T> list)
    {
        return isEmpty(list) ? null : list.get(0);
    }

    /**
     * Get the last item from a list, or null if the list is null or empty.
     *
     * @param list the list
     * @param <T> the generic type of the list
     * @return the last item, or null
     */
    public static <T> T last(List<T> list)
    {
        return isEmpty(list) ? null : list.get(list.size() - 1);
    }

    /**
     * Checks if a collection is null or empty.
     *
     * @param c the collection
     * @return true if the list is null or empty, otherwise false
     */
    public static boolean isEmpty(Collection c)
    {
        return c == null || c.isEmpty();
    }
}
