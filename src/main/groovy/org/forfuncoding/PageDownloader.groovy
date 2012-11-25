package org.forfuncoding

import org.forfuncoding.entities.RealEstatesFilter

/**
 * Download html pages
 */
class PageDownloader {

    def timeout = 10000

    List<String> downloadPages(RealEstatesFilter filter) {
        def pages = []
        for(suburb in filter.suburbs) {
            def url = "http://www.trademe.co.nz/Browse/CategoryAttributeSearchResults.aspx?sort_order=price_asc&search=1&cid=5748&sidebar=1&rptpath=0350-&132=FLAT&selected135=7" +
                    "&selected136=122&134=${filter.city}&135=${filter.area}&136=${suburb.value}&153=&29=${filter.propertyType}&122=${filter.bedsCount}&122=${filter.bedsCount}" +
                    "&59=0&59=${filter.maxPrice}&178=0&178=0&sidebarSearch_keypresses=0&sidebarSearch_suggested=0";
            def page = downloadPage(url)
            pages.add(page)
        }
        return pages
    }

    String downloadPage(String link) {
        return new URL(link).getText(connectTimeout : timeout, readTimeout : timeout)
    }

    static main(args) {
        def filter = new RealEstatesFilter()
        List pages = new PageDownloader().downloadPages(filter)
        println(pages)
    }
}
