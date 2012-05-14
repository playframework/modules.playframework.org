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
package services;

import models.Rate;
import models.User;
import models.UserRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class UserServices
{

    public User createUser(String userName,
                           String displayName,
                           String password)
    {
        return createUser(userName,
                          displayName,
                          password,
                          Collections.<UserRole>emptyList());
    }

    public User createUser(String userName,
                           String displayName,
                           String password,
                           List<UserRole> roles)
    {
        User user = new User();
        user.userName = userName;
        user.displayName = displayName;
        user.password = password;
        user.accountActive = true;
        user.roles = new ArrayList<UserRole>(roles);
        user.rates = new ArrayList<Rate>();
        user.save();
        user.saveManyToManyAssociations("roles");
        return user;
    }
}
