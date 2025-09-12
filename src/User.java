package src;
public class User implements PeopleInterface {
    private Identity identity;
    private String userRole;

    public User(Identity identity, String userRole) {
        this.identity = identity;
        this.userRole = userRole;
    }

    @Override
    public void setInfo(Identity identity) {
        this.identity = identity;
    }

    @Override
    public String getInfo() {
        return identity.toString() + ", Role: " + userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Identity getIdentity() {
        return identity;
    }
}