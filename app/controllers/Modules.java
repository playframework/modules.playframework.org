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
import models.Module;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.modules.moduleRegistrationForm;
import views.html.modules.myModules;

import static actions.CurrentUser.currentUser;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@With(CurrentUser.class)
public class Modules extends Controller {

	public static Result myModules() {
		return ok(myModules.render(currentUser(), currentUser().modules));
	}

	public static Result showModuleRegistrationForm() {
		return ok(moduleRegistrationForm.render(currentUser(), form(Module.class)));
	}

	public static Result submitModuleRegistrationForm() {
		Form<Module> form = form(Module.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(moduleRegistrationForm.render(currentUser(), form));
		} else {
			Module module = form.get();
			User user = currentUser();
			User user1 = user.addModule(module);
			user1.save();
			return myModules();
		}
	}
    
    public static Result getModules(String ofType) {
        return TODO;
    }

    // e.g. /modules/play-1.2.4
    public static Result getModulesByPlayVersion(String version) {
        return TODO;
    }
    
    public static Result getModulesByCategory(String version,
                                              String category) {
        return TODO;
    }
    
    public static Result details(String moduleKey) {
        return TODO;
    }
    
    // this will have a deadbolt dynamic restriction to ensure the user hasn't voted twice
    public static Result vote(String moduleKey) {
        return TODO;
    }

    // If a user has already rated, then change the rate, don't add a new one
    public static Result rate(String moduleKey,
                              int rate) {
        return TODO;
    }
}
