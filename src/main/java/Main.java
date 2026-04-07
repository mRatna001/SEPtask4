public class Main {

    public static void main(String[] args) {

        // file path to csv
        String mockFilePath = "data/faculty_members.csv";

        RegistrationUtility utility =
                new RegistrationUtility(mockFilePath);

        java.util.Collection<FacultyMember> members =
                utility.registerFacultyMembers();

        System.out.println("Faculty members registered:");
        for (FacultyMember member : members) {
            System.out.println(member.toString());
        }
    }
}