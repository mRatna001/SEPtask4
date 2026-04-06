import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * System-level tests for the Pre-register Faculty Members use case.
 *
 * <p>Tests cover eager migration from CSV, correct account creation,
 * duplicate handling,row handling, and empty file handling.
 * A mock CSV file is used to simulate the university-provided faculty data.
 */
public class PreRegisterFacultyMemberSystemTests {

    @TempDir
    Path tempDir;

    private Path csvFile;

    /**
     * Creates a fresh temporary CSV file before each test.
     */
    @BeforeEach
    void setUp() throws IOException {
        csvFile = tempDir.resolve("faculty_members.csv");
    }

    /**
     * Helper to write lines to the temporary CSV file.
     */
    private void writeCsv(String... lines) throws IOException {
        try (PrintWriter pw = new PrintWriter(csvFile.toFile())) {
            for (String line : lines) {
                pw.println(line);
            }
        }
    }

    /**
     * Tests that all faculty accounts in the CSV are created at startup.
     */
    @Test
    void testEagerMigrationCreatesAllAccounts() throws IOException {
        writeCsv(
                "email,encryptedPassword",
                "alice@uni.ac.uk,$2a$10$hashedAlice",
                "bob@uni.ac.uk,$2a$10$hashedBob"
        );

        RegistrationUtility utility = new RegistrationUtility(
                csvFile.toString());
        Collection<FacultyMember> members =
                utility.registerFacultyMembers();

        assertEquals(2, members.size(),
                "All faculty members in the CSV should be created at startup");
    }

    /**
     * Tests that the correct email is stored for a migrated faculty member.
     */
    @Test
    void testEagerMigrationStoresCorrectEmail() throws IOException {
        writeCsv(
                "email,encryptedPassword",
                "alice@uni.ac.uk,$2a$10$hashedAlice"
        );

        RegistrationUtility utility = new RegistrationUtility(
                csvFile.toString());
        Collection<FacultyMember> members =
                utility.registerFacultyMembers();

        assertEquals("alice@uni.ac.uk",
                members.iterator().next().getEmail(),
                "Migrated faculty member should have the correct email");
    }

    /**
     * Tests that all migrated accounts start with zero login attempts.
     */
    @Test
    void testEagerMigrationAllAccountsStartWithZeroLoginAttempts()
            throws IOException {
        writeCsv(
                "email,encryptedPassword",
                "alice@uni.ac.uk,$2a$10$hashedAlice"
        );

        RegistrationUtility utility = new RegistrationUtility(
                csvFile.toString());
        Collection<FacultyMember> members =
                utility.registerFacultyMembers();

        assertEquals(0, members.iterator().next().getLoginAttempts(),
                "Newly migrated faculty accounts should start with 0 login attempts");
    }

    /**
     * Tests that an empty CSV file results in zero accounts created.
     */
    @Test
    void testEagerMigrationWithEmptyFileCreatesZeroAccounts()
            throws IOException {
        writeCsv("email,encryptedPassword");

        RegistrationUtility utility = new RegistrationUtility(
                csvFile.toString());
        Collection<FacultyMember> members =
                utility.registerFacultyMembers();

        assertEquals(0, members.size(),
                "An empty CSV should result in zero faculty accounts");
    }

    /**
     * Tests that duplicate emails in the CSV result in only one account.
     */
    @Test
    void testEagerMigrationSkipsDuplicateEmails() throws IOException {
        writeCsv(
                "email,encryptedPassword",
                "alice@uni.ac.uk,$2a$10$hashedAlice",
                "alice@uni.ac.uk,$2a$10$differentHash"
        );

        RegistrationUtility utility = new RegistrationUtility(
                csvFile.toString());
        Collection<FacultyMember> members =
                utility.registerFacultyMembers();

        assertEquals(1, members.size(),
                "Duplicate emails in the CSV should only create one account");
    }

    /**
     * Tests that a malformed CSV row is skipped without crashing.
     */
    @Test
    void testEagerMigrationSkipsMalformedRows() throws IOException {
        writeCsv(
                "email,encryptedPassword",
                "alice@uni.ac.uk,$2a$10$hashedAlice",
                "badrow"
        );

        RegistrationUtility utility = new RegistrationUtility(
                csvFile.toString());
        Collection<FacultyMember> members =
                utility.registerFacultyMembers();

        assertEquals(1, members.size(),
                "Malformed rows should be skipped without crashing");
    }

    /**
     * Tests that three faculty members are all created correctly.
     */
    @Test
    void testEagerMigrationWithMultipleAccounts() throws IOException {
        writeCsv(
                "email,encryptedPassword",
                "alice@uni.ac.uk,$2a$10$hashedAlice",
                "bob@uni.ac.uk,$2a$10$hashedBob",
                "carol@uni.ac.uk,$2a$10$hashedCarol"
        );

        RegistrationUtility utility = new RegistrationUtility(
                csvFile.toString());
        Collection<FacultyMember> members =
                utility.registerFacultyMembers();

        assertEquals(3, members.size(),
                "All three faculty members in the CSV should be created");
    }
    
    /**
     * Tests that a migrated faculty member starts with isFirstLogin true.
     */
    @Test
    void testMigratedMemberHasFirstLoginTrue() throws IOException {
        writeCsv(
                "email,encryptedPassword",
                "alice@uni.ac.uk,$2a$10$hashedAlice"
        );

        RegistrationUtility utility = new RegistrationUtility(
                csvFile.toString());
        Collection<FacultyMember> members =
                utility.registerFacultyMembers();

        assertTrue(members.iterator().next().isFirstLogin(),
                "Newly migrated faculty member should have isFirstLogin as true");
    }

    /**
     * Tests that incrementing login attempts sets isFirstLogin to false.
     */
    @Test
    void testFirstLoginFalseAfterLoginAttempt() throws IOException {
        writeCsv(
                "email,encryptedPassword",
                "alice@uni.ac.uk,$2a$10$hashedAlice"
        );

        RegistrationUtility utility = new RegistrationUtility(
                csvFile.toString());
        Collection<FacultyMember> members =
                utility.registerFacultyMembers();

        FacultyMember member = members.iterator().next();
        member.incrementLoginAttempts();

        assertFalse(member.isFirstLogin(),
                "isFirstLogin should be false after a login attempt");
    }

    /**
     * Tests that a faculty member's password can be changed after migration.
     */
    @Test
    void testMigratedMemberPasswordCanBeChanged() throws IOException {
        writeCsv(
                "email,encryptedPassword",
                "alice@uni.ac.uk,$2a$10$hashedAlice"
        );

        RegistrationUtility utility = new RegistrationUtility(
                csvFile.toString());
        Collection<FacultyMember> members =
                utility.registerFacultyMembers();

        FacultyMember member = members.iterator().next();
        member.setPassword("mynewpassword");

        assertEquals("mynewpassword", member.getPassword(),
                "Faculty member password should be able to update after migration");
    }
}