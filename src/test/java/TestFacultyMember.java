import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the FacultyMember class.
 *
 * <p>Tests cover construction, getters, login attempts,
 * first login detection, and password changes.
 */
public class TestFacultyMember {

    private FacultyMember member;

    /**
     * Sets up a fresh FacultyMember before each test.
     */
    @BeforeEach
    void setUp() {
        member = new FacultyMember("edward@forks.edu", "$278584gibvgGJ");
    }

    /**
     * Tests that email is stored correctly on construction.
     */
    @Test
    void testGetEmailReturnsCorrectEmail() {
        assertEquals("edward@forks.edu", member.getEmail(),
                "Email should match what was passed to constructor");
    }

    /**
     * Tests that password is stored correctly on construction.
     */
    @Test
    void testGetPasswordReturnsCorrectPassword() {
        assertEquals("$278584gibvgGJ", member.getPassword(),
                "Password should match what was passed to constructor");
    }

    /**
     * Tests that login attempts start at zero.
     */
    @Test
    void testLoginAttemptsStartAtZero() {
        assertEquals(0, member.getLoginAttempts(),
                "Login attempts should be 0 when account is first created");
    }

    /**
     * Tests that isFirstLogin returns true when loginAttempts is zero.
     */
    @Test
    void testIsFirstLoginTrueWhenNoAttempts() {
        assertTrue(member.isFirstLogin(),
                "isFirstLogin should be true when loginAttempts is 0");
    }

    /**
     * Tests that incrementLoginAttempts increases count by one.
     */
    @Test
    void testIncrementLoginAttemptsIncreasesByOne() {
        member.incrementLoginAttempts();
        assertEquals(1, member.getLoginAttempts(),
                "Login attempts should be 1 after one increment");
    }

    /**
     * Tests that isFirstLogin returns false after incrementing.
     */
    @Test
    void testIsFirstLoginFalseAfterIncrement() {
        member.incrementLoginAttempts();
        assertFalse(member.isFirstLogin(),
                "isFirstLogin should be false after incrementing login attempts");
    }

    /**
     * Tests that login attempts can be incremented multiple times.
     */
    @Test
    void testIncrementLoginAttemptsMultipleTimes() {
        member.incrementLoginAttempts();
        member.incrementLoginAttempts();
        member.incrementLoginAttempts();
        assertEquals(3, member.getLoginAttempts(),
                "Login attempts should be 3 after three increments");
    }

    /**
     * Tests that setPassword updates the password correctly.
     */
    @Test
    void testSetPasswordUpdatesPassword() {
        member.setPassword("BellaSwan1!");
        assertEquals("BellaSwan1!", member.getPassword(),
                "Password should be updated after setPassword is called");
    }

    /**
     * Tests that setPassword does not change the email.
     */
    @Test
    void testSetPasswordDoesNotChangeEmail() {
        member.setPassword("BellaSwan1!");
        assertEquals("edward@forks.edu", member.getEmail(),
                "Email should remain unchanged after setPassword");
    }

    /**
     * Tests that toString contains the email.
     */
    @Test
    void testToStringContainsEmail() {
        assertTrue(member.toString().contains("edward@forks.edu"),
                "toString should contain the faculty member's email");
    }

    /**
     * Tests that toString contains login attempts.
     */
    @Test
    void testToStringContainsLoginAttempts() {
        assertTrue(member.toString().contains("0"),
                "toString should contain the login attempts count");
    }

    /**
     * Tests that two faculty members are independent objects.
     */
    @Test
    void testTwoMembersAreIndependent() {
        FacultyMember other = new FacultyMember(
                "jacob@lapush.edu", "$2acvhjASDFG");
        other.incrementLoginAttempts();
        assertEquals(0, member.getLoginAttempts(),
                "Incrementing Jacob's attempts should not affect Edward's");
    }
}