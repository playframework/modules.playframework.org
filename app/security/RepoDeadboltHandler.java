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
package security;

import actions.CurrentUser;
import be.objectify.deadbolt.DeadboltHandler;
import be.objectify.deadbolt.DynamicResourceHandler;
import be.objectify.deadbolt.models.RoleHolder;
import controllers.Application;
import controllers.routes;
import play.core.Router;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

/**
 * @author Steve Chaloner
 */
public class RepoDeadboltHandler extends Results implements DeadboltHandler
{
    @Override
    public Result beforeRoleCheck(Http.Context context)
    {
        return null;
    }

    @Override
    public RoleHolder getRoleHolder(Http.Context context)
    {
        return CurrentUser.currentUser(context);
    }

    @Override
    public Result onAccessFailure(Http.Context context,
                                  String s)
    {
        Http.Context.current.set(context);
        return redirect(routes.Application.index());
    }

    @Override
    public DynamicResourceHandler getDynamicResourceHandler(Http.Context context)
    {
        return new RepoDynamicResourceHandler();
    }
}
