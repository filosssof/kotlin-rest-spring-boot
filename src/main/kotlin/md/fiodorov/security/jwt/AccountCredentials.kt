package md.fiodorov.security.jwt

/**
 * @author rfiodorov
 * on 19/11/17.
 */
data class AccountCredentials(val username: String = "", val password: String = "", val remember: Boolean = false)
