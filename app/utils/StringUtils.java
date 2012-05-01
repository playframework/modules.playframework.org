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

import static org.apache.commons.lang.StringUtils.replace;

/**
 * A few handy string helpers.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public class StringUtils
{
    /**
     * Check if a string has non-empty content.
     *
     * @param s the string
     * @return true if the string is null, empty or comprised only of empty characters spaces, tabs, \n, etc),
     * otherwise false
     */
    public static boolean isEmpty(String s)
    {
        return s == null || s.trim().length() == 0;
    }
    
    public static String summarize(String s)
    {
        String result = "";
        if (!isEmpty(s))
        {
            result = s.length() <= 100 ? s : s.substring(0, 100) + "...";
        }
        return result;
    }
    
    public static String nl2br(String s)
    {
        return replace(s, "\r\n", "<br/>");
    }
}
