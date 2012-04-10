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

import play.db.ebean.Model;
import play.mvc.Http;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steve Chaloner (steve@obectify.be)
 */
public class RequestUtils
{
    private RequestUtils()
    {
        // no-op
    }

    // todo this needs some serious rework!
    public static <T> List<T> getListFromRequest(Http.Request request,
                                                 String name,
                                                 Model.Finder<Long, T> finder)
    {
        String[] values = request.body().asFormUrlEncoded().get(name);
        List<T> results = new ArrayList<T>();

        for (String value : values)
        {
            T item = finder.where()
                           .eq("id", Long.parseLong(value))
                           .findUnique();
            results.add(item);
        }

        return results;
    }
}
