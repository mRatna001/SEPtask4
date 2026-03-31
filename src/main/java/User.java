/**
 * Abstract class representing a generic user of the system.
 *
 * <p>Stores the core login credentials shared by all user types.
 */
public abstract class User {

    /** The user's email address. */
    private String email;

    /** The user's password. */
    private String password;

    /**
     * Constructs a User with the given email and password.
     *
     * @param email    the user's email; must not be null or blank
     * @param password the user's password; must not be null or blank
     */
    public User(String email, String password) {
        assert email != null && !email.isBlank()
                : "Email must not be null or blank";
        assert password != null && !password.isBlank()
                : "Password must not be null or blank";
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the new password; must not be null or blank
     */
    public void setPassword(String password) {
        assert password != null && !password.isBlank()
                : "Password must not be null or blank";
        this.password = password;
    }
}