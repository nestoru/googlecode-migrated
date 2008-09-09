/**
 * Authority class for Authority.
 */
class Authority {

	static hasMany = [user: User]

	/** description */
	String description
	/** ROLE String */
	String authority = 'ROLE_'

	static constraints = {
		authority(blank: false)
		description()
	}
}
