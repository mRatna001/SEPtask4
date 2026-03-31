/**
 * Represents a faculty member pre-registered in the system.
 *
 * <p>Faculty members are migrated eagerly from a university-provided
 * CSV file. Their login attempts are tracked.
 */
public class FacultyMember extends User {

    /** The number of times this faculty member has logged in. */
    private int loginAttempts;

    /**
     * Constructs a FacultyMember with the given email and password.
     *
     * <p>Login attempts start at zero on creation.
     *
     * @param email    the faculty member's email; must not be null or blank
     * @param password the encrypted password from the CSV file;
     *                 must not be null or blank
     */
    public FacultyMember(String email, String password) {
        super(email, password);
        this.loginAttempts = 0;
    }

    /**
     * Returns the number of login attempts made by this faculty member.
     *
     * @return the login attempt count
     */
    public int getLoginAttempts() {
        return loginAttempts;
    }

    /**
     * Increments the login attempt counter by one.
     */
    public void incrementLoginAttempts() {
        this.loginAttempts++;
    }

    /**
     * Returns whether this is the faculty member's first login.
     *
     * @return true if loginAttempts is zero
     */
    public boolean isFirstLogin() {
        return loginAttempts == 0;
    }

    /**
     * Returns a string representation of this faculty member.
     *
     * @return formatted string with email and login attempts
     */
    @Override
    public String toString() {
        return "FacultyMember{"
                + "email='" + getEmail() + "'"
                + ", loginAttempts=" + loginAttempts
                + "}";
    }
}