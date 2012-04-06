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

/**
 * Test cases for {@link StringUtils}.
 *
 * @author Steve Chaloner
 */
public class StringUtilsTest
{
    @Test
    public void isEmpty_null()
    {
        Assert.assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    public void isEmpty_empty()
    {
        Assert.assertTrue(StringUtils.isEmpty(""));
    }

    @Test
    public void isEmpty_padded()
    {
        Assert.assertTrue(StringUtils.isEmpty("   "));
    }

    @Test
    public void isEmpty_newLine()
    {
        Assert.assertTrue(StringUtils.isEmpty("\n"));
    }

    @Test
    public void isEmpty_cr()
    {
        Assert.assertTrue(StringUtils.isEmpty("\r"));
    }

    @Test
    public void isEmpty_tab()
    {
        Assert.assertTrue(StringUtils.isEmpty("\t"));
    }

    @Test
    public void isEmpty_combo()
    {
        Assert.assertTrue(StringUtils.isEmpty("  \t \n   \r   \t"));
    }

    @Test
    public void isEmpty_nonEmptyString()
    {
        Assert.assertFalse(StringUtils.isEmpty("foo"));
    }

    @Test
    public void isEmpty_paddedNonEmptyString()
    {
        Assert.assertFalse(StringUtils.isEmpty("    foo    "));
    }
}
