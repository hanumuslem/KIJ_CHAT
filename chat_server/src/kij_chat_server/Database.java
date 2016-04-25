package kij_chat_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
   private static final String dbURL = "jdbc:mysql://localhost:3306/kij";
   private static final String username = "root";
   private static final String password = "";
 

   public static String[][] getAll()
   {
       String[][] hasildata=new String[20][2];
       try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
           String sql="SELECT username, password FROM user";
           PreparedStatement statement=conn.prepareStatement(sql);
           ResultSet result = statement.executeQuery();
           int count=0;
               while (result.next()){
                   
                   hasildata[count][0]=result.getString(1);
                   hasildata[count][1]=result.getString(2);
                   count++;
                   //System.out.println(hasildata[0]+" "+hasildata[1]+" "+hasildata[2]+"\n");
               }
           
       } catch (SQLException ex) {
           Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return hasildata;
   }

   public static String[] Check(String _username,String _password)
   {
       String[] hasildata=new String[10];
       try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
           String sql="SELECT username FROM user WHERE username=? and password=?";
           PreparedStatement statement=conn.prepareStatement(sql);
           statement.setString(1, _username);
           statement.setString(2, _password);
           ResultSet result = statement.executeQuery();
           int count=0;
               while (result.next()){
                   count++;
                   String i=result.getString(1);
                   hasildata[count]=i;

                   //System.out.println(hasildata[0]+" "+hasildata[1]+" "+hasildata[2]+"\n");
               }
           
       } catch (SQLException ex) {
           Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return hasildata;
   }

   public static void updatePublic(String _username, String _public_key)
   {
       
       try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
           
           String sql1="UPDATE user SET public_key=? WHERE username = ? ";
           PreparedStatement statement=conn.prepareStatement(sql1);
           statement.setString(1, _public_key);
           statement.setString(2, _username);
           
           
           System.out.println(statement);
           statement.executeUpdate();
                      

           
       } catch (SQLException ex) {
           Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
       }
       
   }



}
