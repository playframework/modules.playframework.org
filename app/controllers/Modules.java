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
import models.BinaryContent;
import models.Module;
import models.ModuleVersion;
import models.PlayVersion;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import utils.RequestUtils;
import views.html.modules.moduleRegistrationForm;
import views.html.modules.manageVersionsForm;
import views.html.modules.moduleDetails;
import views.html.modules.myModules;
import views.html.modules.genericModuleList;

import java.util.Date;
import java.util.List;

import static actions.CurrentUser.currentUser;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@With(CurrentUser.class)
public class Modules extends Controller {

	public static Result myModules() {
        User currentUser = currentUser();
        return ok(myModules.render(currentUser,
                                   Module.ownedBy(currentUser)));
	}

    public static Result showModuleRegistrationForm() {
		return ok(moduleRegistrationForm.render(currentUser(),
                                                form(Module.class)));
	}

	public static Result submitModuleRegistrationForm() {
		Form<Module> form = form(Module.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(moduleRegistrationForm.render(currentUser(), form));
		} else {
			Module module = form.get();
            module.owner = currentUser();
			module.save();
			return myModules();
		}
	}
    
    public static Result showVersionManagement(String moduleKey) {
        Form<ModuleVersion> form = form(ModuleVersion.class);
        Module module = Module.findByModuleKey(moduleKey);
        return ok(manageVersionsForm.render(currentUser(),
                                            module,
                                            PlayVersion.getAll(),
                                            form));
    }

	public static Result uploadNewVersion() {
        Form<ModuleVersion> form = form(ModuleVersion.class).bindFromRequest();
        ModuleVersion moduleVersion = form.get();
        moduleVersion.playModule = Module.findById(moduleVersion.playModule.id);
        moduleVersion.releaseDate = new Date();

        moduleVersion.compatibility.addAll(RequestUtils.getListFromRequest(request(),
                                                                           "compatibility.id",
                                                                           PlayVersion.FIND));

        // everything below here needs to be implemented
        moduleVersion.binaryFile = new BinaryContent();
        moduleVersion.binaryFile.content = new byte[]{1};
        moduleVersion.binaryFile.contentLength = 1;

        moduleVersion.save();
        moduleVersion.saveManyToManyAssociations("compatibility");

        // or just cascade from the module?
        // looks like an issue with ebean here - see http://www.avaje.org/bugdetail-260.html
        // should be fixed in this version, but it's not
        // for now, I'm changing the model to accomodate this
        // module.versions.add(moduleVersion);
        // module.save();
        // Ebean.saveAssociation(module, "versions");

        return TODO;
	}
    
    public static Result getModules(String ofType) {
        return TODO;
    }

    // e.g. /modules/play-1.2.4
    public static Result getModulesByPlayVersion(String version) {
        List<PlayVersion> playVersions = PlayVersion.findByLooseName(version);
        List<Module> modulesByVersion = ModuleVersion.findModulesByPlayVersion(playVersions);
        return ok(genericModuleList.render(currentUser(),
                                           "Modules for Play " + version,
                                           modulesByVersion));
    }
    
    public static Result getModulesByCategory(String version,
                                              String category) {
        return TODO;
    }
    
    public static Result details(String moduleKey) {
        Module module = Module.findByModuleKey(moduleKey);
        List<ModuleVersion> moduleVersions = ModuleVersion.findByModule(module);
        return ok(moduleDetails.render(currentUser(),
                                       module,
                                       moduleVersions));
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
