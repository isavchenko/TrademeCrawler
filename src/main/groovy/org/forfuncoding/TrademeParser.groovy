package org.forfuncoding

import java.text.SimpleDateFormat
import org.forfuncoding.entities.PropertyListPageInfo
import org.forfuncoding.entities.RealEstate

/**
 * Parse trademe.co.nz htmls page
 */
class TrademeParser {

    private def slurper;

    TrademeParser() {
        def tagSoupParser = new org.ccil.cowan.tagsoup.Parser();
        slurper = new XmlSlurper(tagSoupParser)
    }

    PropertyListPageInfo parse(String htmlPage) {
        def pageInfo = new PropertyListPageInfo()
        def htmlParser = slurper.parseText(htmlPage)
        pageInfo.suburb = htmlParser.'**'.find {it.@class == 'attSearchResultList'}.toString().split(",")[0]
        def links = []
        htmlParser.'**'.findAll {it.@class == 'pTH'}.a.each {
            def link = "http://trademe.co.nz" + it.@href
            links.add(link)
        }
        pageInfo.links = links
        return pageInfo
    }

    RealEstate parseRealEstate(String htmlPage) {
        def realEstate = new RealEstate()
        def htmlParser = slurper.parseText(htmlPage.replace("<br />", " "))
        def rowWithDateTime = htmlParser.'**'.find{it.@id == "ListingTitle_titleTime"}.toString().substring(8)
        def dateTime = new SimpleDateFormat("EEE dd MMM, h:mm a").parse(rowWithDateTime)
        dateTime.setYear(new Date().getYear())
        realEstate.isListedToday = dateTime.clearTime() == new Date().clearTime()

        def parentTag = htmlParser.'**'.find {it.@id == 'ListingAttributes'}
        realEstate.address = parentTag.'*'.find {it.th == 'Location:'}.parent().td.toString().trim()

        realEstate.price = htmlParser.'**'.find {it.@id == "ListingTitle_classifiedTitlePrice"}.toString()

        realEstate.available = parentTag.'**'.find {it.@id == 'ListingAttributes_AttributesRepeater_ctl02_ltHeaderRow'}.parent().td.toString().trim()

        return realEstate
    }
}
