package org.forfuncoding

import org.forfuncoding.entities.RealEstate
import groovy.text.SimpleTemplateEngine
import com.google.inject.Inject
import com.google.inject.name.Named

/**
 * Generate email text from template
 */
class MailGenerator {

    String pathToTemplate

    @Inject
    MailGenerator(@Named("templatePath") String pathToTemplate) {
        this.pathToTemplate = pathToTemplate
    }

    private String loadTemplate() {
        return new File(pathToTemplate).text
    }

    String generateMailText(Map dailyRealEstates, Map realEstates) {
        def template = loadTemplate()
        def binding = [realEstateDailyLinkLists: dailyRealEstates, realEstateLinkLists: realEstates]
        def templateEngine = new SimpleTemplateEngine()
        templateEngine.createTemplate(template).make(binding)
    }
}
