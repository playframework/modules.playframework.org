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
import models.PlayVersion;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import security.RoleDefinitions;
import views.html.admin.playVersions;

import static actions.CurrentUser.currentUser;

/**
 * @author Steve Chaloner (steve@obectify.be)
 */
@With(CurrentUser.class)
@Restrict(RoleDefinitions.ADMIN)
public class PlayVersions extends Controller
{
    public static Result showPlayVersions()
    {
        return ok(playVersions.render(CurrentUser.currentUser(),
                                      PlayVersion.findByMajorVersion(PlayVersion.MajorVersion.ONE),
                                      PlayVersion.findByMajorVersion(PlayVersion.MajorVersion.TWO),
                                      form(PlayVersion.class)));
    }

    public static Result addNewPlayVersion()
    {
        Result result;
        Form<PlayVersion> form = form(PlayVersion.class).bindFromRequest();
        if (form.hasErrors())
        {
            result = badRequest(playVersions.render(currentUser(),
                                                    PlayVersion.findByMajorVersion(PlayVersion.MajorVersion.ONE),
                                                    PlayVersion.findByMajorVersion(PlayVersion.MajorVersion.TWO),
                                                    form));
        }
        else
        {
            PlayVersion playVersion = form.get();
            playVersion.majorVersion = playVersion.name.startsWith("1") ? PlayVersion.MajorVersion.ONE
                                                                        : PlayVersion.MajorVersion.TWO;
            playVersion.save();
            result = showPlayVersions();
        }
        return result;
    }
    
    public static Result update()
    {
        Result result;
        Form<PlayVersion> form = form(PlayVersion.class).bindFromRequest();
        if (form.hasErrors())
        {
            result = badRequest(form.errorsAsJson());
        }
        else
        {
            PlayVersion incoming = form.get();
            PlayVersion storedVersion = PlayVersion.FIND.byId(incoming.id);
            storedVersion.name = incoming.name;
            storedVersion.documentationUrl = incoming.documentationUrl;
            storedVersion.majorVersion = storedVersion.name.startsWith("1") ? PlayVersion.MajorVersion.ONE
                                                                            : PlayVersion.MajorVersion.TWO;
            storedVersion.save();
            result = ok(Json.toJson(storedVersion));
        }
        return result;

    }
}
