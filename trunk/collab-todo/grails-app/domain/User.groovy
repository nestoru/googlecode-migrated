/**
 * User for user account.
 */
class User {
	static transients = ['pass']
	static hasMany = [authorities: Role]
	static belongsTo = Role

	/** Username */
	String username
	
	String firstName
    String lastName
    String description
    
	/** MD5 Password */
	String passwd
	/** enabled */
	boolean enabled

	String email
	boolean emailShow


	/** plain password to create a MD5 password */
	String pass = '[secret]'

	static constraints = {
		username(blank: false, unique: true)
		firstName(blank:false)
		lastName(blank:false)
		passwd(blank: false)
		enabled()
	}
	
	String toString () {
	    "$lastName, $firstName"
	} 
}
