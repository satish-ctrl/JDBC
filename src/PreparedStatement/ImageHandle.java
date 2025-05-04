package PreparedStatement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageHandle {
    public static void main(String[] args) throws ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Mysql@250";
        String imagePath = "C:\\Users\\satis\\Pictures\\aaaaa.jpg";

        String query = "INSERT INTO image_table(image_data)VALUES(?)";


        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("driver loaded");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection done");

            FileInputStream fileInputStream = new FileInputStream(imagePath);
            byte[] imageData = new byte[fileInputStream.available()];
            fileInputStream.read(imageData);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setBytes(1, imageData);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0){
                System.out.println("Image Insertion successful..");
            }
            else {
                System.out.println("Image Insertion failed");
            }

            con.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
