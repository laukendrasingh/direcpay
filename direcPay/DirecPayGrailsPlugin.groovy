class DirecPayGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.3 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Direc Pay Plugin" // Headline display name of the plugin
    def author = "Laukendra Singh"
    def authorEmail = "Laukendras@gmail.com"
    def description = '''\
Payment integration with DirecPay
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/direc-pay"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
        application.config.grails.plugins.direcPay.URL = "https://test.timesofmoney.com/direcpay/secure/dpMerchantPayment.jsp"
        application.config.grails.plugins.direcPay.pull.transaction.details.URL = "https://test.timesofmoney.com/direcpay/secure/dpPullMerchAtrnDtls.jsp"
//------------------
        application.config.grails.plugins.direcPay.return.transaction.details.URL = "http://localhost:8080/direcPay/paymentDetails"
        application.config.grails.plugins.direcPay.loadingText = "Loading..."
        application.config.grails.plugins.direcPay.operatingMode = "DOM"
        application.config.grails.plugins.direcPay.collaborator = "TOML"
        application.config.grails.plugins.direcPay.encryption.secretKey = "qcAHa6tt8s0l5NN7UWPVAQ=="
        application.config.grails.plugins.direcPay.merchantId = "200904281000001"
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
