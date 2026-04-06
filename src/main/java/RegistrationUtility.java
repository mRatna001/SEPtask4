import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

//Class to read file and create Faculty Member accounts
//Eager migration creates all account immediately
public class RegistrationUtility {

    /** Path to the faculty members CSV file. */
    private String filePath;

    //constructor takes file path as input stores it assert used to check null
    public RegistrationUtility(String filePath) {
        assert filePath != null && !filePath.isBlank()
                : "File path must not be null or blank";
        this.filePath = filePath;
    }

    //Creates accounts
    public Collection<FacultyMember> registerFacultyMembers() {
        assert filePath != null : "Cannot be empty";

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
                    System.out.println("Warning"
                            + line);
                    continue;
                }

                String email = parts[0].trim();
                String password = parts[1].trim();

                if (email.isEmpty() || password.isEmpty()) {
                    System.out.println(" Empty Email or Password" + line);
                    continue;
                }

                // Check for duplicate emails
                boolean duplicate = false;
                for (FacultyMember existing : members) {
                    if (existing.getEmail().equals(email)) {
                        System.out.println("A duplicate email was found " + email);
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
            System.out.println("Could not read file at"
                    + filePath );
        }

        return members;
    }

    /**
     * Returns the file path this utility reads from.
     * @return the file path
     */
    public String getFilePath() {
        return filePath;
    }
}
