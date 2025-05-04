package PlaneStatement;

import java.sql.*;

public class InsertDataInToDB {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Mysql@250";
        String query = "INSERT INTO employee(id, name, job_title, salary)values(4, 'Raju', 'Assistand Developer', 20000.00),(5, 'Murali', 'C Developer', 25000.00)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection Establish Successfully");
            Statement stmt = con.createStatement();
            int rowsaffected = stmt.executeUpdate(query);

            if(rowsaffected>0){
                System.out.println("Insert successfull "+rowsaffected+"row(s) affected.");
            }
            else{
                System.out.println("Insertion failed");
            }

            stmt.close();
            con.close();
            System.out.println("conection close successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
