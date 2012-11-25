<!doctype html>
<html>
    <head>
        <title>Trademe crawler scheduler settings</title>
        <link rel="stylesheet" href="css/main.css"/>
        <script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
        <script src="js/main.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="settings-area">
            <h2>Scheduler settings</h2>
            <form id="settingsForm" name="settingsForm" action="crawlersettingsGroovlet.groovy" method="get">
                <div class="row">
                    <label for="dayTime"/>Execution hour:</label>
                    <input id="dayTime" type="text" name="dayTime" style="width: 30px" value="${request.dayTime}"/>
                </div>
                <div class="row">
                    <label for="emails"/>Emails:</label>
                    <input id="emails" type="text" name="emails" value="${request.emails}"/>
                </div>
                <div class="row">
                    <label for="subject">Subject:</label>
                    <input id="subject" name="subject" type="text" value="${request.subject}"/>
                </div>
                <div class="row">
                    <input type="submit" value="Save" class="submit-button"/>
                </div>
            </form>
            <div style="clear: both;"/>
            <% if(request.saved != null) { %>
            <p>* settings have successfully been saved</p>
            <% } %>
        </div>
    </body>
</html>