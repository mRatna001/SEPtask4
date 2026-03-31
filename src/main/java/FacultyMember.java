
public class FacultyMember extends User {

    /** The number of times this faculty member has logged in. */
    private int loginAttempts;


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


    public String toString() {
        // makes a string
        return "FacultyMember{"
                + "email='" + getEmail() + "'"
                + ", loginAttempts=" + loginAttempts
                + "}";
    }
}