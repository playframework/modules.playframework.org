/*
 * Copyright 2012 Steve Chaloner
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

import models.PlayVersion;
import play.Application;
import play.GlobalSettings;

/**
 * 
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
    }
}
