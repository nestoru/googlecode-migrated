import org.grails.plugins.springsecurity.service.AuthenticateService
class CategoryController {
    AuthenticateService authenticateService
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        //[ categoryList: Category.list( params ) ]
        //def user = User.get(session.user.id)
        def user = authenticateService.userDomain() 
        [ categoryList: Category.findAllByUser(user, params) ]
    }

    def show = {
        def category = Category.get( params.id )

        if(!category) {
            flash.message = "Category not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ category : category ] }
    }

    def delete = {
        def category = Category.get( params.id )
        if(category) {
            category.delete()
            flash.message = "Category ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Category not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def category = Category.get( params.id )

        if(!category) {
            flash.message = "Category not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ category : category ]
        }
    }

    def update = {
        def category = Category.get( params.id )
        if(category) {
            category.properties = params
            //category.user = session.user
            def user = authenticateService.userDomain()
            category.user = user
            if(!category.hasErrors() && category.save()) {
                flash.message = "Category ${params.id} updated"
                redirect(action:show,id:category.id)
            }
            else {
                render(view:'edit',model:[category:category])
            }
        }
        else {
            flash.message = "Category not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def category = new Category()
        category.properties = params
        return ['category':category]
    }

    def save = {
        def category = new Category(params)
        //category.user = session.user
        def user = authenticateService.userDomain()
        category.user = user
        if(!category.hasErrors() && category.save()) {
            flash.message = "Category ${category.id} created"
            redirect(action:show,id:category.id)
        }
        else {
            render(view:'create',model:[category:category])
        }
    }
}
