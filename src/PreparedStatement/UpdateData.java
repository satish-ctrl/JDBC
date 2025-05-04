package PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Mysql@250";
        String query = "UPDATE employee SET job_title = ? WHERE job_title = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection Establish Successfully");
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, "Cloud Computing");
            preparedStatement.setString(2, "Cloud Computin");

            int rowsaffected = preparedStatement.executeUpdate();

            if(rowsaffected>0){
                System.out.println("Insert successfull "+rowsaffected+"row(s) affected.");
            }
            else{
                System.out.println("Insertion failed");
            }

            preparedStatement.close();
            con.close();
            System.out.println("conection close successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
