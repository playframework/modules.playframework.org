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
package forms.login;

import models.User;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class Login
{
    public String userName;
    public String password;

    public String validate()
    {
        String result = null;
        if (User.authenticate(userName,
                              password) == null)
        {
            result = "Invalid user or password";
        }
        return result;
    }
}
