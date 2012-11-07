import org.forfuncoding.MailSender
import java.text.SimpleDateFormat

def auckland = 1
def aucklandCity = 7
def suburbs = ["Ponsonby" : 83, "Eden terrace" : 135, "Newmarket" : 99, "Mount eden" : 90, "Ramuera" : 40, "Mission Bay" : 62]
def unit = "Unit"
def bedsCount = 2
def maxPrice = 45000
def newLinksResult = ""
def oldLinksResult = ""
def resultPage = ""
for(suburb in suburbs) {
    def url = "http://www.trademe.co.nz/Browse/CategoryAttributeSearchResults.aspx?sort_order=price_asc&search=1&cid=5748&sidebar=1&rptpath=0350-&132=FLAT&selected135=7" +
            "&selected136=122&134=${auckland}&135=${aucklandCity}&136=${suburb.value}&153=&29=${unit}&122=${bedsCount}&122=${bedsCount}" +
            "&59=0&59=${maxPrice}&178=0&178=0&sidebarSearch_keypresses=0&sidebarSearch_suggested=0";
    def page = new URL(url).getText()

    def tagSoupParser = new org.ccil.cowan.tagsoup.Parser();
    def slurper = new XmlSlurper(tagSoupParser)
    def htmlParser = slurper.parseText(page)
    def links = []
    def newLinks = []
    htmlParser.'**'.findAll {it.@class == 'pTH'}.a.each {
        def link = "http://trademe.co.nz" + it.@href
        //links += link + "\n"
        def linkContent = new URL(link).getText()
        def linkContentParser = slurper.parseText(linkContent)
        def rowWithDateTime = linkContentParser.'**'.find{it.@id == "ListingTitle_titleTime"}.toString().substring(8)
        def dateTime = new SimpleDateFormat("EEE dd MMM, h:mm a").parse(rowWithDateTime)
        dateTime.setYear(new Date().getYear())
        if(dateTime.clearTime() == new Date().clearTime()) {
            newLinks.add(link)
        } else {
            links.add(link)
        }
    }

    if(!newLinks.empty) {
        newLinksResult += suburb.key + "\n"
        newLinks.each {newLinksResult += it + "\n"}
        newLinksResult += "\n"
    }

    if(!links.empty) {
        oldLinksResult += suburb.key + "\n"
        links.each {oldLinksResult += it + "\n"}
        oldLinksResult += "\n"
    }
}

resultPage += "New daily links:\n"
resultPage += newLinksResult
resultPage += "\n"

resultPage += "Old links:\n"
resultPage += oldLinksResult
resultPage += "\n"

println resultPage

//MailSender.sendMail("this.behappy2009@gmail.com", "Daily update", resultPage)
