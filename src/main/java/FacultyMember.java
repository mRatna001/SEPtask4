
public class FacultyMember extends User {

    /** Tracks how many times a person has logged in*/
    private int loginAttempts;


    public FacultyMember(String email, String password) {
        super(email, password);
        this.loginAttempts = 0;
    }

    /**
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