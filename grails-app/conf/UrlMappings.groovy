class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }

//        "/"(view:"/index")
        "/"(controller: 'home', action: 'index')
        "/returnTransactionDetail"(controller: 'home', action: 'returnTransactionDetail')
        "/appSuccess"(controller: 'home', action: 'success')
        "/appFailure"(controller: 'home', action: 'failure')
        "500"(view:'/error')
	}
}
