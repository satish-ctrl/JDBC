package PlaneStatement;

import java.sql.*;

public class ConnectAndRetrieveDB {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Mysql@250";
        String query = "Select * from employee";

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
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String job_title = rs.getString("job_title");
                double salary = rs.getDouble("salary");
                System.out.println("+++++++++++++++++++++++++++++++++++++");
                System.out.println("Id: "+id);
                System.out.println("Name: "+name);
                System.out.println("Job Title: "+job_title);
                System.out.println("Salary: "+salary);
            }
            rs.close();
            stmt.close();
            con.close();
            System.out.println("conection close successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
