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
package security.dynamic;

import be.objectify.deadbolt.AbstractDynamicResourceHandler;
import be.objectify.deadbolt.DeadboltHandler;
import models.User;
import models.Vote;
import play.Logger;
import play.mvc.Http;
import utils.StringUtils;

import java.util.Iterator;

/**
 * @author Steve Chaloner
 */
public class AllowToVote extends AbstractDynamicResourceHandler
{
    public static final String KEY = "allowToVote";

    @Override
    public boolean isAllowed(String name,
                             String meta,
                             DeadboltHandler deadboltHandler,
                             Http.Context ctx)
    {
        boolean allowed = true;
        if (StringUtils.isEmpty(meta))
        {
            Logger.error("HasVoted required a moduleKey in the meta data");
        }
        else
        {
            User user = (User)deadboltHandler.getRoleHolder(ctx);
            for (Iterator<Vote> iterator = user.votes.iterator(); allowed && iterator.hasNext(); )
            {
                Vote vote = iterator.next();
                allowed = !meta.equals(vote.playModule.key);
            }
        }
        return allowed;
    }
}
