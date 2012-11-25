import com.google.appengine.api.datastore.DatastoreServiceFactory
import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.EntityNotFoundException
import com.google.appengine.api.datastore.Entity
import org.forfuncoding.Crawler
import org.forfuncoding.entities.SchedulerSettings
import org.forfuncoding.entities.RealEstatesFilter

/**
 * Scheduler based on the settings from google Big Table
 */
log.info("Getting settings from storage...")
def datastore = DatastoreServiceFactory.getDatastoreService();
def key = KeyFactory.createKey("settings", "crawlerSetting");
def entity
try {
    entity = datastore.get(key)
} catch (EntityNotFoundException e) {
    entity = new Entity(key)
    entity << SchedulerSettings.getDefaultSettings().properties
    entity.save()
}

log.info("Scheduler settings are ${entity}")

def settings = new SchedulerSettings()
settings.dayTime = entity.dayTime
settings.emails = entity.emails
settings.subject = entity.subject

log.info("Parameter startParsing=${params.startParsing}")
log.info("Current hour=${Calendar.instance.get(Calendar.HOUR_OF_DAY)}")
if(params.startParsing != null || Calendar.getInstance(TimeZone.getTimeZone("Pacific/Auckland")).get(Calendar.HOUR_OF_DAY) == settings.dayTime) {
    log.info("Start crawling...")
    def crawler = Crawler.createCrawler(new RealEstatesFilter(), settings)
    crawler.process()
    log.info("Crawling has finished")
}
