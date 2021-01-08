package sample;

import java.sql.*;

public class Koneksi {

    private Connection conn;

    private Statement st;
    private String query;

    public Koneksi() {
        try {
            String user = "root";
            String password = "";
            String url = "jdbc:mysql://localhost:3306/pbo-reguler";
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public int manipulasiData(String query){
        try {
            st = conn.createStatement();
            return st.executeUpdate(query); //pasti hasilnya 1
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }

    public ResultSet getData(String query){
        try{
            st = conn.createStatement();
            return st.executeQuery(query);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}