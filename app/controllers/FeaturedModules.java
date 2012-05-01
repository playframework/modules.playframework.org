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
import be.objectify.deadbolt.actions.Restrict;
import com.avaje.ebean.Ebean;
import models.FeaturedModule;
import models.Module;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import security.RoleDefinitions;
import utils.StringUtils;
import views.html.admin.featuredModules;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static actions.CurrentUser.currentUser;

/**
 * @author Steve Chaloner (steve@obectify.be)
 */
@With(CurrentUser.class)
@Restrict(RoleDefinitions.ADMIN)
public class FeaturedModules extends Controller
{
    public static Result showFeaturedModules()
    {
        // if we don't do this, some fields are missing from playModule. Any ideas?
        List<FeaturedModule> featured = FeaturedModule.getAll();
        for (FeaturedModule featuredModule : featured)
        {
            Ebean.refresh(featuredModule.playModule);
        }
        return ok(featuredModules.render(CurrentUser.currentUser(),
                                         featured,
                                         form(FeaturedModule.class)));
    }

    public static Result addNewFeaturedModule()
    {
        Result result;
        Form<FeaturedModule> form = form(FeaturedModule.class).bindFromRequest();
        if (form.hasErrors())
        {
            result = badRequest(featuredModules.render(currentUser(),
                                                       FeaturedModule.getAll(),
                                                       form));
        }
        else
        {
            FeaturedModule featuredModule = form.get();
            Ebean.refresh(featuredModule.playModule);
            if (StringUtils.isEmpty(featuredModule.description))
            {
                featuredModule.description = featuredModule.playModule.description;
            }
            featuredModule.creationDate = new Date();
            featuredModule.save();
            result = redirect(routes.FeaturedModules.showFeaturedModules());
        }
        return result;
    }
    
    public static Result update()
    {
        Result result;
        Form<FeaturedModule> form = form(FeaturedModule.class).bindFromRequest();
        if (form.hasErrors())
        {
            result = badRequest(form.errorsAsJson());
        }
        else
        {
            FeaturedModule incoming = form.get();
            FeaturedModule storedVersion = FeaturedModule.FIND.byId(incoming.id);
            storedVersion.description = StringUtils.isEmpty(incoming.description) ? storedVersion.playModule.description
                                                                                  : incoming.description;
            storedVersion.sticky = incoming.sticky;
            storedVersion.save();

            // Annoying - converting storedVersion directly to JSON results in a cycle according to Jackson
            // Buggered if I can see why though
            ObjectNode node = Json.newObject();
            node.put("id", storedVersion.id);
            node.put("description", storedVersion.description);
            node.put("sticky", storedVersion.sticky);
            result = ok(node);
        }
        return result;
    }

    public static Result delete()
    {
        Map<String,String[]> postParams = request().body().asFormUrlEncoded();
        String[] ids = postParams.get("id");

        Result result;
        if (ids.length != 1)
        {
            result = badRequest("A featured module must be defined");
        }
        else
        {
            FeaturedModule featuredModule = FeaturedModule.FIND.byId(Long.parseLong(ids[0]));
            featuredModule.delete();

            ObjectNode node = Json.newObject();
            node.put("id", featuredModule.id);
            result = ok(node);
        }
        return result;
    }
}
