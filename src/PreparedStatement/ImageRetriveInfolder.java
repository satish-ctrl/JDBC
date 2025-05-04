package PreparedStatement;

import java.io.*;
import java.sql.*;

public class ImageRetriveInfolder {
    public static void main(String[] args) throws ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Mysql@250";
        String folderPath = "C:\\Users\\satis\\Pictures\\Camera Roll\\";
        String query = "SELECT image_data FROM image_table WHERE image_id = (?)";

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
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                byte[] imageData = rs.getBytes("image_data");
                String imagePath = folderPath+"brother.jpg";
                OutputStream outputStream = new FileOutputStream(imagePath);
                outputStream.write(imageData);
                System.out.println("Image retrieve success..");
            }
            else{
                System.out.println("Image not found");
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
