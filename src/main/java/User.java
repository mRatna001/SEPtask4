/**
 * Abstract class a generic user of the system.
 *
 * <p>Stores the default login credentials shared by all user types.
 */
public abstract class User {

    /** The user's email address. */
    private String email;

    /** The user's password. */
    private String password;

    /**
     * Constructs a User with email and password.
     *
     * @param email    the user's email
     *
     * @param password the user's password
     */
    public User(String email, String password) {
        assert email != null && !email.isBlank()
                : "Empty Please enter an email";
        assert password != null && !password.isBlank()
                : "Empty Please enter a password";
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the email address.
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
     * @param password the new password
     */
    public void setPassword(String password) {
        assert password != null && !password.isBlank()
                : "Empty Please enter a password";
        this.password = password;
    }
}