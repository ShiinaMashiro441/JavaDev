/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Scanner;
import java.sql.*;
import static java.lang.System.*;
/**
 *
 * @author MH588
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    private static final Scanner sc = new Scanner(System.in);
    private static String main_portocol;
    private static String sub_portocol;
    private static String url;
    private static String table_name;
    private static String username;
    private static String password;
    private static String sql;
    private static boolean eof;
    
    
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(initial_connection());
             Statement stmt = conn.createStatement();) {
            sql = parseSQL();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i=1; i<=rsmd.getColumnCount(); i++) {
                out.printf("| %25s |", rsmd.getColumnName(i));
            }
            out.println();
            while(rs.next()) {
                for (int i=1; i<=rsmd.getColumnCount(); i++){
                    switch(rsmd.getColumnTypeName(i)) {
                        case "VARCHAR":
                            out.printf("| %-25s |", rs.getString(i));
                            break;
                        case "SMALLINT":
                            out.printf("| %25s |", rs.getInt(i));
                            break;
                    }
                }
                out.println();
            }
            
        }
        catch(SQLSyntaxErrorException sqlsee) {
            if(sql.equalsIgnoreCase("exit")) {
                out.println("Connection close");
            }
        }
        catch(SQLException sqle) {
            err.println(sqle);
        }
        
        // SYSTEM PAUSE
        out.println("按下ENTER鍵離開...");
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            if (s.isEmpty())
                break;
        }
    }
    
    public static String initial_connection() {
        
        out.println("Main portocol: ");
        main_portocol = sc.nextLine();
        out.println("Sub portocol: ");
        sub_portocol = sc.nextLine();
        out.println("DB URL (IP:PORT): ");
        url = sc.nextLine();
        out.println("Table name: ");
        table_name = sc.nextLine();
        out.println("User: ");
        username = sc.nextLine();
        out.println("Password: ");
        password = sc.nextLine();
        
        return main_portocol + ":" + sub_portocol + "://" + url + "/" 
               + table_name + "?user=" + username + "&password=" + password;
    }
    
    public static String parseSQL() {
        String sql;
        while(true) {
            sql = multiline_input();
            if(eof)
                break;
        }
        return sql;
    }
    
    public static String multiline_input() {
        StringBuilder sql = new StringBuilder();
        boolean first_line = true;
        out.print("mariaDB >_ ");
        
        do {
            String read = sc.nextLine();
            
            if(first_line && read.equalsIgnoreCase("exit")) {
                first_line = false;
                sql.append(read);
                eof = true;
                break;
            }
            else if (read.isEmpty()) {
                break;
            }
            else if (read.endsWith(";")) {
                sql.append(" ").append(read.toUpperCase());
                eof = true;
                break;
            }
            else {
                sql.append(" ").append(read.toUpperCase());
            }                            // 12345678901
            out.printf("%11s", " -> ");  // _______ -> sometxt
        }while(sc.hasNextLine());
        
        return sql.toString();
    }
    
}
