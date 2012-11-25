<html>
    <head>
        <title>Coding for fun</title>
    </head>
    <body>
        <%
            log.info("User is $user")
            if(user == null) {
                response.sendRedirect(users.createLoginURL("/"));
            }
        %>
        <p>This site is for experiments and fun. Currently there is only one small application for producing daily reports from<br/>
            Trademe and send it to recipients from
            <%
                if(users?.isUserAdmin()?:false) {
            %>
            <a href="/crawlersettings">settings</a>
            <% } else {
            %>
            settings
            <% }
            %>
        </p>
    </body>
</html>