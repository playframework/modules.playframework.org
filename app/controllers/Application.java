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
package controllers;

import actions.CurrentUser;
import forms.login.Login;
import forms.login.Register;
import models.FeaturedModule;
import models.Module;
import models.PlayVersion;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.UserServices;
import views.html.index;
import views.html.login;
import views.html.register;

import java.util.Collections;
import java.util.List;

import static actions.CurrentUser.currentUser;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@With(CurrentUser.class)
public class Application extends AbstractController
{
    public static Result index()
    {
        final List<Module> mostRecentModules = Module.findMostRecent(5);
        final List<Module> highestRatedModules = Collections.emptyList(); // best way to use the rating algorithm for the db call?  pre-calculate before storing?
        final List<FeaturedModule> featuredModules = FeaturedModule.getAll();
        final List<PlayVersion> playVersions = PlayVersion.getAll();

        return ok(index.render(currentUser(),
                               mostRecentModules,
                               highestRatedModules,
                               featuredModules,
                               playVersions,
                               Module.count()));
    }

    public static Result login()
    {
        return ok(login.render(form(Login.class)));
    }

    public static Result authenticate()
    {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        Result result;
        if (loginForm.hasErrors())
        {
            result = badRequest(login.render(loginForm));
        }
        else
        {
            session("userName", loginForm.get().userName);
            result = redirect(routes.Application.index());
        }

        return result;
    }

    public static Result register()
    {
        return ok(register.render(form(Register.class)));
    }

    public static Result createAccount()
    {
        Form<Register> registerForm = form(Register.class).bindFromRequest();
        Result result;
        if (registerForm.hasErrors())
        {
            result = badRequest(register.render(registerForm));
        }
        else
        {
            Register register = registerForm.get();
            new UserServices().createUser(register.userName, register.displayName, register.password);
            session("userName", register.userName);
            result = redirect(routes.Application.index());
        }
        return result;
    }

    public static Result logout()
    {
        session().clear();
        return redirect(routes.Application.index());
    }
}
