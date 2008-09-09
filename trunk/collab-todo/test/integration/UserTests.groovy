class UserTests extends GroovyTestCase {

    void testCRUDOperations() {
        // Let's create the user
        def userTemp = new User(userName: 'testUser', firstName:'John',
        lastName:'Smith', password:'pass',
        email:"smith@gmail.com")
        // Create - let's save it
        userTemp.save()
        // grab the user id
        def userId = userTemp.id
        // Update - since we are still within the session we caught it
        // we shouldn't need to do anything explicit
        userTemp.password = 'A new password'
        // let's see if it got updated
        userTemp = User.get(userId)
        assert "A new password" == userTemp.password
        assert "John" == userTemp.firstName
        // let's show the delete
        userTemp.delete()
        // let's make sure it got deleted
        assert null == User.get(userId)
    }
}
