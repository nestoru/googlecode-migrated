class UserController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        [ userList: User.list( params ) ]
    }

    def show = {
        def user = User.get( params.id )

        if(!user) {
            flash.message = "User not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ user : user ] }
    }

    def delete = {
        def user = User.get( params.id )
        if(user) {
            user.delete()
            flash.message = "User ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "User not found with id ${params.id}"
            redirect(action:list)
        }
    }

        def edit = {
        
        //ERRATA:
        //if (session.user.id != params.id) {
        if (session.user.id != params.id.toInteger()) {
            flash.message = "You can only edit yourself"
            redirect(action:list)
            return
        }
        
        def user = User.get( params.id )

        if(!user) {
            flash.message = "User not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ user : user ]
        }
    }

    def update = {
        def user = User.get( params.id )
        if(user) {
            user.properties = params
            if(!user.hasErrors() && user.save()) {
                flash.message = "User ${params.id} updated"
                redirect(action:show,id:user.id)
            }
            else {
                render(view:'edit',model:[user:user])
            }
        }
        else {
            flash.message = "User not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def user = new User()
        user.properties = params
        return ['user':user]
    }

    def save = {
        def user = new User(params)
        if(!user.hasErrors() && user.save()) {
            //flash.message = "User ${user.id} created"
            flash.message = "user.saved.message"
			flash.args = [user.firstName, user.lastName]
			flash.defaultMsg = "User Saved"
            redirect(action:show,id:user.id)
        }
        else {
            render(view:'create',model:[user:user])
        }
    }
    
    def login = {}
    
    def handleLogin = {
        
	    def user = User.findByUserName(params.userName)
        
		if (!user) {
			flash.message = "User not found for userName: ${params.userName}"
			redirect(action:'login')
		}else{
		    session.user = user
			redirect(controller:'todo')
		}		
	}
	def logout = {
		if(session.user) {
			session.user = null
			redirect(action:'login')
		}
	}
    
    
}
