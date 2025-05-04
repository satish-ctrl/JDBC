package PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionHandling {
    public static void main(String[] args) throws ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Mysql@250";
        String withdrawQuery = " UPDATE accounts SET balance = balance - ? WHERE account_no = ? ";
        String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_no = ? ";

        try{
            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("driver loaded");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url, username, password);
//            System.out.println("connection sucessful");
            con.setAutoCommit((false));

            try {
                PreparedStatement withdrawStatement = con.prepareStatement(withdrawQuery);
                PreparedStatement depositStatement = con.prepareStatement(depositQuery);
                withdrawStatement.setDouble(1, 500);
                withdrawStatement.setString(2, ("account123"));
                depositStatement.setDouble(1, 500);
                depositStatement.setString(2, "account456");
                int rowsAffectedWithdrawl = withdrawStatement.executeUpdate();
                int rowsAffectedDeposit = depositStatement.executeUpdate();

                if(rowsAffectedWithdrawl > 0 && rowsAffectedDeposit > 0){
                    con.commit();
                    System.out.println("Transaction successful...");
                }
                else{
                    con.rollback();
                    System.out.println("Transaction failed..");
                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
