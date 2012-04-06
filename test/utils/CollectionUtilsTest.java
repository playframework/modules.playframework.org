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

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Steve Chaloner
 */
public class CollectionUtilsTest
{
    @Test
    public void isEmpty_null()
    {
        Assert.assertTrue(CollectionUtils.isEmpty(null));
    }

    @Test
    public void isEmpty_emptyList()
    {
        Assert.assertTrue(CollectionUtils.isEmpty(Collections.emptyList()));
    }

    @Test
    public void isEmpty_list_nullEntry()
    {
        Collection<String> list = new ArrayList<String>();
        list.add(null);
        Assert.assertFalse(CollectionUtils.isEmpty(list));
    }

    @Test
    public void isEmpty_emptySet()
    {
        Assert.assertTrue(CollectionUtils.isEmpty(Collections.emptySet()));
    }

    @Test
    public void isEmpty_set_nullEntry()
    {
        Collection<String> set = new ArrayList<String>();
        set.add(null);
        Assert.assertFalse(CollectionUtils.isEmpty(set));
    }

    @Test
    public void isEmpty_nonEmptyList()
    {
        Collection<String> list = new ArrayList<String>();
        list.add("foo");
        Assert.assertFalse(CollectionUtils.isEmpty(list));
    }

    @Test
    public void isEmpty_nonEmptySet()
    {
        Collection<String> set = new HashSet<String>();
        set.add("foo");
        Assert.assertFalse(CollectionUtils.isEmpty(set));
    }

    @Test
    public void first_nullList()
    {
        Assert.assertNull(CollectionUtils.first(null));
    }

    @Test
    public void first_nullEntry()
    {
        List<String> list = new ArrayList<String>();
        list.add(null);
        Assert.assertNull(CollectionUtils.first(list));
    }

    @Test
    public void first_oneEntry()
    {
        List<String> list = new ArrayList<String>();
        list.add("foo");

        String first = CollectionUtils.first(list);
        Assert.assertEquals("foo",
                            first);
    }

    @Test
    public void first_twoEntries()
    {
        List<String> list = new ArrayList<String>();
        list.add("foo");
        list.add("bar");

        String first = CollectionUtils.first(list);
        Assert.assertEquals("foo",
                            first);
    }

    @Test
    public void last_nullList()
    {
        Assert.assertNull(CollectionUtils.last(null));
    }

    @Test
    public void last_nullEntry()
    {
        List<String> list = new ArrayList<String>();
        list.add(null);
        Assert.assertNull(CollectionUtils.last(list));
    }

    @Test
    public void last_oneEntry()
    {
        List<String> list = new ArrayList<String>();
        list.add("foo");

        String first = CollectionUtils.last(list);
        Assert.assertEquals("foo",
                            first);
    }

    @Test
    public void last_twoEntries()
    {
        List<String> list = new ArrayList<String>();
        list.add("foo");
        list.add("bar");

        String first = CollectionUtils.last(list);
        Assert.assertEquals("bar",
                            first);
    }
}
