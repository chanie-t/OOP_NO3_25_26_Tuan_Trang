package src;
public class Identity {
    private String fullName;
    private String dateOfBirth;
    private String id;

    public Identity(String fullName, String dateOfBirth, String id) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + fullName + ", DOB: " + dateOfBirth;
    }
}