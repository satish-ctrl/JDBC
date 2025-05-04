package PreparedStatement;

import java.sql.*;
import java.util.Scanner;

public class BatchProcessing {
    public static void main(String[] args) throws ClassNotFoundException{

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Mysql@250";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            con.setAutoCommit(false);
            String query = " INSERT INTO employee(name, job_title, salary) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            Scanner sc = new Scanner(System.in);
            while (true){
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Job Title: ");
                String job_title = sc.nextLine();
                System.out.print("Salary: ");
                Double salary = sc.nextDouble();
                sc.nextLine();
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, job_title);
                preparedStatement.setDouble(3, salary);
                preparedStatement.addBatch();
                System.out.print("Add more values Y/N: ");
                String decision = sc.nextLine();
                if(decision.toUpperCase().equals("N")){
                    break;
                }
            }
            int[] batchResult = preparedStatement.executeBatch();
            con.commit();
            System.out.println("Batch Execute successfully");

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
