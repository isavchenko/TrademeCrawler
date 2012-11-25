package org.forfuncoding

import org.forfuncoding.entities.RealEstatesFilter
import com.google.inject.Inject
import com.google.inject.Guice
import org.forfuncoding.modules.CrawlerModule
import org.forfuncoding.entities.SchedulerSettings

/**
 * Trademe crawler
 */
class Crawler {

    MailGenerator mailGenerator
    MailSender mailSender
    TrademeParser parser
    PageDownloader downloader
    RealEstatesFilter filter
    SchedulerSettings settings

    @Inject
    Crawler(MailGenerator mailGenerator, MailSender mailSender, TrademeParser parser, PageDownloader downloader,
            RealEstatesFilter filter, SchedulerSettings settings) {
        this.mailGenerator = mailGenerator
        this.mailSender = mailSender
        this.parser = parser
        this.downloader = downloader
        this.filter = filter
        this.settings = settings
    }

    void process() {
        def pages = downloader.downloadPages(filter)
        def newDailyRealEstates = [:]
        def realEstates = [:]
        for(page in pages) {
            def pageInfo = parser.parse(page)
            def realEstateList = []
            for(realEstateLink in pageInfo.links) {
                def realEstatePage = downloader.downloadPage(realEstateLink)
                def realEstate = parser.parseRealEstate(realEstatePage)
                realEstate.link = realEstateLink
                realEstate.name = "${pageInfo.suburb}, ${realEstate.price}, ${realEstate.available}"
                realEstateList.add(realEstate)
            }
            def realEstateListFiltered = realEstateList.findAll {it.isListedToday}
            if(!realEstateListFiltered.empty) {
                newDailyRealEstates.put(pageInfo.suburb, realEstateListFiltered)
            }

            realEstateListFiltered = realEstateList.findAll {!it.isListedToday}
            if(!realEstateListFiltered.empty) {
                realEstates.put(pageInfo.suburb, realEstateListFiltered)
            }
        }
        def mailText = mailGenerator.generateMailText(newDailyRealEstates, realEstates)
        println("Message content:\n$mailText")
        mailSender.sendMail(settings.emails, settings.subject, mailText)
    }

    static createCrawler(RealEstatesFilter filter, SchedulerSettings settings) {
        def injector = Guice.createInjector(new CrawlerModule(filter, settings))
        return injector.getInstance(Crawler.class)
    }
}
