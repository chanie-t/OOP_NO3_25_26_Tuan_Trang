package test;
import src.User;
import src.Identity;

import java.util.ArrayList;
import java.util.List;

public class ListOfUser {
    private List<User> users = new ArrayList<>();

    // CREATE
    public void addUser(User user) {
        users.add(user);
    }

    // READ
    public User getUserById(String id) {
        for (User u : users) {
            if (u.getIdentity().getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }

    // UPDATE
    public boolean updateUser(String id, String newName, String newDob, String newRole) {
        User u = getUserById(id);
        if (u != null) {
            u.getIdentity().setFullName(newName);
            u.getIdentity().setDateOfBirth(newDob);
            u.setUserRole(newRole);
            return true;
        }
        return false;
    }

    // DELETE
    public boolean deleteUser(String id) {
        User u = getUserById(id);
        if (u != null) {
            users.remove(u);
            return true;
        }
        return false;
    }
}