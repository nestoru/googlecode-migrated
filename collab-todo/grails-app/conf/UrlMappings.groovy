class UrlMappings {
    static mappings = {
          "/$rest/$realcontroller/$id?"{
            controller = "rest"
            action = [GET:"show", PUT:"create", POST:"update", DELETE:"delete"]
            constraints {
                rest(inList:["rest","json"])
            } 
         }
            
         "/$controller/$action?/$id?"{
    	      constraints {
    			 // apply constraints here
    		  }
    	  }         
          "500"(view:'/error')      
    }
}
