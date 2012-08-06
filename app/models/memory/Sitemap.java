package models.memory;

import java.util.Date;

/**
 * @author Pere Villega (pere.villega@gmail.com)
 */
public class Sitemap {

    public String url;
    public String changeFreq;
    public String priority;

    /**
     * Stores information on a link for a sitemap
     * Change Frequency is set to monthly
     * Priority is set to "0.5"
     * @param url the link to add to the sitemap
     */
    public Sitemap(String url) {
        this(url, "monthly", "0.5");
    }

    /**
     * Stores information on a link for a sitemap
     * Priority is set to "0.5"
     * @param url the link to add to the sitemap
     * @param changeFreq how often is the link updated. Default: monthly
     */
    public Sitemap(String url, String changeFreq) {
        this(url, changeFreq, "0.5");
    }

    /**
     * Stores information on a link for a sitemap
     * @param url the link to add to the sitemap
     * @param changeFreq how often is the link updated. Default: Monthly
     * @param priority priority of the link (relevance). Default "0.5"
     */
    public Sitemap(String url, String changeFreq, String priority) {
        this.url = url;
        this.changeFreq = changeFreq;
        this.priority = priority;
    }

}
