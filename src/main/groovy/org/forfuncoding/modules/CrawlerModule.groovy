package org.forfuncoding.modules

import com.google.inject.AbstractModule
import org.forfuncoding.*
import com.google.inject.name.Names
import org.forfuncoding.entities.RealEstatesFilter
import org.forfuncoding.entities.SchedulerSettings

/**
 * Module to define injected classes
 */
class CrawlerModule extends AbstractModule {

    RealEstatesFilter filter
    SchedulerSettings settings

    CrawlerModule(RealEstatesFilter filter, SchedulerSettings settings) {
        this.filter = filter
        this.settings = settings
    }

    @Override
    protected void configure() {
        bind(TrademeParser.class)
        bind(MailGenerator.class)
        bind(String.class).annotatedWith(Names.named("templatePath")).toInstance("templates/mail_message.html.template")
        bind(MailSender.class)
        bind(PageDownloader.class)
        bind(RealEstatesFilter.class).toInstance(filter)
        bind(SchedulerSettings.class).toInstance(settings)
        bind(Crawler.class)
    }
}
