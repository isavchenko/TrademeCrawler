import com.google.appengine.api.datastore.*
import org.forfuncoding.entities.SchedulerSettings

log.info("Store settings")
log.info("Parameters are $params")
log.info("Get settings from datastore")

def datastore = DatastoreServiceFactory.getDatastoreService();
def key = KeyFactory.createKey("settings", "crawlerSetting");
def entity;
try {
    entity = datastore.get(key)
} catch (EntityNotFoundException e) {
    entity = null
}

log.info("Entity is $entity")

def defaultSettings = SchedulerSettings.getDefaultSettings()
def areSettingsSaved = false
if(params.size() > 0) {
    log.info("Params found")

    def dayTime = params.dayTime?.toString()?.toInteger()
    def emails = params.emails
    def subject = params.subject

    //save settings
    if(entity == null) {
        entity = new Entity(key)
    }
    entity.dayTime = dayTime != null ? dayTime : defaultSettings.dayTime
    entity.emails = emails?:defaultSettings.emails
    entity.subject = subject?:defaultSettings.subject
    entity.save()
    areSettingsSaved = true
} else {
    log.info("No parameters")
    if(entity == null) {
        log.info("Entity is null")
        entity = new Entity(key)
        entity.dayTime = defaultSettings.dayTime
        entity.emails = defaultSettings.emails
        entity.subject = defaultSettings.subject
        entity.save()
        areSettingsSaved = true
    }
}

def settings = new SchedulerSettings()
settings.dayTime = entity.dayTime
settings.emails = entity.emails
settings.subject = entity.subject

log.info("Saved settings are $entity")

request.setAttribute 'dayTime', settings.dayTime
request.setAttribute 'emails', settings.emails
request.setAttribute 'subject', settings.subject
if(areSettingsSaved) {
    request.setAttribute 'saved', areSettingsSaved
}

log.info "Forwarding to the template"

forward '/WEB-INF/pages/crawlersettings.gtpl'
