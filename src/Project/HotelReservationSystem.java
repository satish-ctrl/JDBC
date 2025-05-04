package Project;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;


public class HotelReservationSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password ="Mysql@250";
    private static Statement statement;
    private  static int room_status;


    public static void main(String[] args) throws ClassNotFoundException {

//        Driver loaded
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

//        Database connection
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            statement = con.createStatement();

            while (true){
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner sc = new Scanner(System.in);
                System.out.println("1.]  Reserve a room");
                System.out.println("2.]  View Reservation");
                System.out.println("3.]  Get Room Number");
                System.out.println("4.]  Update Reservations");
                System.out.println("5.]  Delete Reservation");
                System.out.println("6.]  Exit");
                System.out.println("7.]  Check Room Available Or Not");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                switch (choice){
                    case 1:
                        reserveRoom(con, sc);
                        break;
                    case 2:
                        viewReservation(con);
                        break;
                    case 3:
                        getRoomNumber(con, sc);
                        break;
                    case 4:
                        updateReservation(con, sc);
                        break;
                    case 5:
                        deleteReservation(con, sc);
                        break;
                    case 6:
                        exit();
                        sc.close();
                        return;
//                    case 7:
//                        checkAvailableRooms();
//                        break;
                    default:
                        System.out.println("Invalid choice, Try again !!");
                }
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
//    private static void checkAvailableRooms(){
//        try {
//            String sql = "SELECT total_rooms, booked_rooms FROM room_status";
//            ResultSet rs = statement.executeQuery(sql);
//
//            if (rs.next()) {
//                int totalRooms = rs.getInt("total_rooms");
//                int bookedRooms = rs.getInt("booked_rooms");
//                int available = totalRooms - bookedRooms;
//                System.out.println("Available Rooms: " + available);
//            }
//            else{
//                System.out.println("Sorry Room not available!!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    private static void reserveRoom(Connection con, Scanner sc){
        try{
            System.out.print("Enter guest name: ");
            String guestName = sc.next();
            System.out.print("Enter room number: ");
            int roomNumber = sc.nextInt();
            System.out.print("Enter contact number: ");
            String contactNumber = sc.next();
            System.out.print("Enter aadhaar number: ");
            String aadhaarNumber = sc.next();
            String sql = "INSERT INTO reservations(guest_name, room_no, contact_no, aadhar_no)" +"VALUES('" + guestName +"','" + roomNumber +"','" + contactNumber +"','" + aadhaarNumber +"')" ;
            try {
                int affectedRows = statement.executeUpdate(sql);

                if(affectedRows > 0){
                    System.out.println("Reservation successful..");
//                    String updateSql = "UPDATE room_status SET booked_rooms = booked_rooms + 1";
//                    statement.executeUpdate(updateSql);
                }
                else{
                    System.out.println("Reservation failed!!");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void  viewReservation(Connection con) throws SQLException{
        String sql =  "SELECT * FROM reservations";

        try (ResultSet resultSet = statement.executeQuery(sql)){
            System.out.println("Current Reservation:");
            System.out.println("+----------------+------------------+---------+--------------+------------------+-----------------------+");
            System.out.println("| Reservation_Id |   Guest_name     | room_no |  contact_no  |    aadhaar_no    |   reservation_date    |");
            System.out.println("+----------------+------------------+---------+--------------+------------------+-----------------------+");

            while (resultSet.next()){
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_no");
                String contactNumber = resultSet.getString("contact_no");
                String aadharNumber = resultSet.getString("aadhar_no");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                System.out.printf("| %-14d | %-16s | %-7d | %-12s | %-16s | %-19s |\n",reservationId, guestName, roomNumber,contactNumber, aadharNumber, reservationDate);
            }
            System.out.println("+----------------+------------------+---------+--------------+------------------+-----------------------+");
        }

    }

    private static void getRoomNumber(Connection con, Scanner sc){
        try{
            System.out.print("Enter reservation Id: ");
            int reservationId = sc.nextInt();
            System.out.print("Enter guest name: ");
            String guestName = sc.next();

            String sql = "SELECT room_no FROM reservations WHERE reservation_id = '" + reservationId +"' AND guest_name = '"+ guestName +"'";
            try (ResultSet resultSet = statement.executeQuery(sql)){

                if(resultSet.next()){
                    int roomNumber = resultSet.getInt("room_no");
                    System.out.println("Reservation Id " + reservationId + " Guest "+ guestName + " your room number is "+ roomNumber);
                }
                else{
                    System.out.println("Reservation not found for the given Id and guest name..");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void updateReservation (Connection con, Scanner sc){
        try {
            System.out.print("Enter reservation Id to update: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            if(!reservationExists(con, reservationId)){
                System.out.println("Reservation not found for the given Id..");
                return;
            }

            System.out.print("Enter new guest name: ");
            String newGuestName = sc.next();
            System.out.print("Enter new room number: ");
            int newRoomNumber = sc.nextInt();
            System.out.print("Enter new contact number: ");
            String newContactNumber = sc.next();
            System.out.print("Enter new aadhar number: ");
            String newAadharNumber = sc.next();

            String sql = "UPDATE reservations SET guest_name = '"+ newGuestName +"', room_no = '"+ newRoomNumber +"', contact_no = '"+ newContactNumber +"', aadhar_no = '"+ newAadharNumber +"' WHERE reservation_id = '"+ reservationId +"'";

            try {
                int affectedRows = statement.executeUpdate(sql);

                if(affectedRows > 0){
                    System.out.println("Reservation updated successfully..");
                }
                else{
                    System.out.println("Reservation update failed");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void deleteReservation(Connection con, Scanner sc){
        try {
            System.out.print("Enter reservation Id to delete: ");
            int reservationId = sc.nextInt();
            if(!reservationExists(con, reservationId)){
                System.out.println("Reservation not found for the given Id..");
                return;
            }

            String sql = "DELETE FROM reservations WHERE reservation_id = '"+ reservationId +"'";
            try{
                int affectedRows = statement.executeUpdate(sql);

                if(affectedRows > 0){
                    System.out.println("Reservation deleted successfully..");
//                    String updateSql = "UPDATE room_status SET booked_rooms = booked_rooms - 1";
//                    statement.executeUpdate(updateSql);
                }
                else{
                    System.out.println("Reservation deletion failed");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection con, int reservationId){
            try{
                String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = '"+ reservationId +"'";
                try(ResultSet resultSet = statement.executeQuery(sql)){
                    return resultSet.next();
                }
            }
            catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }
    public static void exit() throws InterruptedException{
            System.out.print("Exiting System");
            int i = 5;
            while(i!=0){
                System.out.print(".");
                Thread.sleep(450);
                i--;
            }
            System.out.println();
            System.out.println("ThankYou For Giving Chance Of Service..");
        }
}
