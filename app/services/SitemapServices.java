package services;

import controllers.routes;
import models.Module;
import models.memory.Sitemap;
import play.api.mvc.RequestHeader;
import play.mvc.Http;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Pere Villega (pere.villega@gmail.com)
 */
public class SitemapServices {

    /**
     * Generates the sitemaps entries for the application
     * @param request the current request
     * @return a list of sitemap entries
     */
    public static List<Sitemap> generateSitemap(Http.Request request){
        List<Sitemap> list = new ArrayList<Sitemap>();

        // home
        list.add(new Sitemap(routes.Application.index().absoluteURL(request)));

        // modules lists
        list.add(new Sitemap(routes.Modules.getModulesByPlayVersion("1").absoluteURL(request), "daily", "0.8" ));
        list.add(new Sitemap(routes.Modules.getModulesByPlayVersion("2").absoluteURL(request), "daily", "0.8" ));

        // modules details
        List<Module> modules = Module.all();
        for(Module mod: modules) {
            list.add(new Sitemap(routes.Modules.details(mod.key).absoluteURL(request), "daily", "1" ));
        }

        return list;
    }
}
