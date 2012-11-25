get "/", forward: "/WEB-INF/pages/index.gtpl"
get "/schedulerexecutor", forward: "schedulerExecutorGroovlet.groovy"
get "/crawlersettings", forward: "crawlersettingsGroovlet.groovy"

