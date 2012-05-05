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

import actors.FeedCreationActor;
import actors.HistoricalEventActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Duration;
import com.avaje.ebean.Ebean;
import models.Module;
import models.PlayVersion;
import models.User;
import models.UserRole;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import play.libs.Yaml;
import security.RoleDefinitions;
import services.UserServices;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class Global extends GlobalSettings
{
    @Override
    public void onStart(Application application)
    {
        // Add code or TODOs here for startup behaviour
        // todo - add a scheduled job that selects a given number - maybe 2? - of modules and makes them the featured modules of the day
        // the previous day's featured modules are removed, if they are not sticky
        // the description of the featured module will be taken from the module summary, but may be overwritten from the admin console to something specific
        // featured modules created in this way are not sticky.
        // featured modules can be made sticky to have them stick around for more than 24 hours via admin console - featured modules can also be created from there
        // I'll add this to the wiki later
        // this space for rent

        // TODO remove this!  It's a development convenience
        Logger.info("Adding admin user...");
        if (UserRole.findByRoleName(RoleDefinitions.ADMIN) == null)
        {
            UserRole role = new UserRole();
            role.roleName = RoleDefinitions.ADMIN;
            role.description = "MPO administrator";
            role.save();
        }

        if (User.findByUserName("admin") == null)
        {
            new UserServices().createUser("admin",
                                          "MPO Admin",
                                          "password",
                                          Arrays.asList(UserRole.findByRoleName(RoleDefinitions.ADMIN)));
        }

        loadInitialData();

        scheduleJobs();
    }

    public void scheduleJobs()
    {
        ActorSystem actorSystem = Akka.system();
        ActorRef feedCreationActor = actorSystem.actorOf(new Props(FeedCreationActor.class));
        actorSystem.scheduler().schedule(Duration.create(0, TimeUnit.MILLISECONDS),
                                           Duration.create(1, TimeUnit.MINUTES),
                                           feedCreationActor,
                                           "generate");
    }

    /**
     * Simplistic loading of YAML initial data file.
     */
    public void loadInitialData()
    {
        Logger.info("Loading initial data...");
        Map<String, List<Object>> data = (Map<String, List<Object>>) Yaml.load("initial-data.yml");

        if (PlayVersion.count() == 0)
        {
            final List<Object> versions = data.get("playVersions");
            Logger.debug(String.format("PlayVersion: %d loaded", versions.size()));
            Ebean.save(versions);
        }

        // The 'admin' user is already loaded.
        if (User.count() <= 1)
        {
            final List<Object> users = data.get("users");
            Logger.debug(String.format("User: %d loaded", users.size()));
            Ebean.save(users);
        }

        if (Module.count() == 0)
        {
            final List<Object> modules = data.get("modules");
            Logger.debug(String.format("Module: %d loaded", modules.size()));
            Ebean.save(modules);
        }
    }
}
