package Controller;

import java.sql.SQLException;

import Model.Database;
import Model.Employee;

public class CreateEmployee {
    
    private Employee e;
    private Database database;

    public CreateEmployee(Employee e, Database database) {
        this.e = e;
        this.database = database;
    }

    public boolean isCreated() {
        boolean created = false;
        String insert = "INSERT INTO `employees`(`FirstName`, `LastName`, `Email`,"
                +" `PhoneNumber`, `Password`, `Salary`, `Job`) VALUES"
                +" ('"+e.getFirstName()+"','"+e.getLastName()+"','"+e.getEmail()+
                "', '"+e.getPhoneNumber()+"','"+e.getPassword()+"','"+e.getSalary()
                +"','"+e.getJob()+"');";
        try {
            database.getStatement().executeUpdate(insert);
            created = true;
        } catch (SQLException e) {
            e.printStackTrace();
            created = false;
        }
        return created;
    }
}
