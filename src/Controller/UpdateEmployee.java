package Controller;

import java.sql.SQLException;

import Model.Database;
import Model.Employee;
import Model.Doctor;

public class UpdateEmployee {

    private Employee e;
    private Database database;

    public UpdateEmployee(Employee e, Database database) {
        this.e = e;
        this.database = database;
    }

    public boolean isUpdated() {
        boolean updated = false;
        String specialization = "";
        if (e.getJob() == 0) {
            specialization = ((Doctor) e).getSpecialization();
        }
        String update = "UPDATE `employees` SET `FirstName`='" + e.getFirstName() +
                "',`LastName`='" + e.getLastName() + "',`Email`='" + e.getEmail() +
                "',`PhoneNumber`='" + e.getPhoneNumber() + "',`Salary`='" + e.getSalary() +
                "',`Specialization`='" + specialization + "', `Job` = '" + e.getJob() +
                "' WHERE `ID` = " + e.getID() + " ;";
        try {
            database.getStatement().executeUpdate(update);
            updated = true;
        } catch (SQLException e) {
            e.printStackTrace();
            updated = false;
        }
        return updated;

    }
}
