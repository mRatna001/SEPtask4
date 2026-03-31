import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility class responsible for pre-registering faculty members.
 *
 * <p>Implements eager migration — all faculty member accounts are
 * created immediately when registerFacultyMembers() is called.
 * The university provides a CSV file with email and encrypted password.
 */
public class RegistrationUtility {

    /** Path to the university-provided faculty members CSV file. */
    private String filePath;

    /**
     * Constructs a RegistrationUtility with the given file path.
     *
     * @param filePath path to the CSV file; must not be null or blank
     */
    public RegistrationUtility(String filePath) {
        assert filePath != null && !filePath.isBlank()
                : "File path must not be null or blank";
        this.filePath = filePath;
    }

    /**
     * Reads the CSV file and creates a FacultyMember account for every row.
     *
     * <p>This is eager migration — all accounts are created immediately.
     * The CSV format expected is: email,encryptedPassword
     * Duplicate emails and malformed rows are skipped with a warning.
     *
     * @return a collection of all created FacultyMember accounts
     */
    public Collection<FacultyMember> registerFacultyMembers() {
        assert filePath != null : "File path must not be null";

        Collection<FacultyMember> members = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(filePath))) {

            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {

                // Skip the header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", 2);
                if (parts.length < 2) {
                    System.out.println("Warning: skipping malformed row: "
                            + line);
                    continue;
                }

                String email = parts[0].trim();
                String password = parts[1].trim();

                if (email.isEmpty() || password.isEmpty()) {
                    System.out.println("Warning: skipping row with empty "
                            + "field: " + line);
                    continue;
                }

                // Check for duplicate emails
                boolean duplicate = false;
                for (FacultyMember existing : members) {
                    if (existing.getEmail().equals(email)) {
                        System.out.println("Warning: duplicate email "
                                + "skipped: " + email);
                        duplicate = true;
                        break;
                    }
                }

                if (!duplicate) {
                    members.add(new FacultyMember(email, password));
                }
            }

            System.out.println("Eager migration complete: "
                    + members.size() + " faculty accounts created.");

        } catch (IOException e) {
            System.out.println("Warning: could not read file at '"
                    + filePath + "'.");
        }

        return members;
    }

    /**
     * Returns the file path this utility reads from.
     *
     * @return the file path
     */
    public String getFilePath() {
        return filePath;
    }
}
