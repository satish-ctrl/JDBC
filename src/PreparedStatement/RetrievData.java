package PreparedStatement;

import java.sql.*;

public class RetrievData {
    public static void main(String[] args) throws ClassNotFoundException{

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Mysql@250";
        String query = "SELECT * FROM employee WHERE id = ? AND name =?";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection success");
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, "3");
            preparedStatement.setString(2, "Kamlesh");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String jobTitle = rs.getString("job_title");
                Double salary = rs.getDouble("salary");
                System.out.println("Id: "+id);
                System.out.println("Name: "+name);
                System.out.println("Job title: "+jobTitle);
                System.out.println("Salary: "+salary);
            }
            rs.close();
            preparedStatement.close();
            con.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
