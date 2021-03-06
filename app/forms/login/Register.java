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

import static play.data.validation.Constraints.Required;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class Register
{
    @Required
    public String userName;

    @Required
    public String password;

    @Required
    public String displayName;

    public String validate()
    {
        String result = null;

        if (User.findByUserName(userName) != null)
        {
            result = "That user name is already in use";
        }
        return result;
    }
}
