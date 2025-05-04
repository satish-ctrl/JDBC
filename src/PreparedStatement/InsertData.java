package PreparedStatement;

import java.sql.*;

public class InsertData {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Mysql@250";
        String query = "INSERT INTO employee(id, name, job_title, salary)values(?,?, ?, ?)";

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
            preparedStatement.setInt(1, 5);
            preparedStatement.setString(2, "Saurabh");
            preparedStatement.setString(3, "Cloud Computin");
            preparedStatement.setDouble(4, 50000.0);
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
