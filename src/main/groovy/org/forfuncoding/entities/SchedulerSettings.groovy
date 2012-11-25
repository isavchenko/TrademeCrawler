package org.forfuncoding.entities

/**
 * Scheduler settings
 */
class SchedulerSettings {
    Integer dayTime
    String emails
    String subject

    static getDefaultSettings() {
        def settings = new SchedulerSettings()
        settings.dayTime = 17
        settings.emails = "this.behappy2009@gmail.com"
        settings.subject = "Daily update"
        return settings
    }
}
