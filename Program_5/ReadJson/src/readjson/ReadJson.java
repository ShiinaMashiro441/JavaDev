/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readjson;

import java.io.*;
import static java.lang.System.*;
import java.util.*;
import java.sql.*;
import com.google.gson.*;

/**
 * org.json api doc url = https://stleary.github.io/JSON-java/index.html
 * com.google.gson api doc = https://www.javadoc.io/doc/com.google.code.gson/gson/latest/com.google.gson/module-summary.html
 * @author MH588
 */
public class ReadJson {

    /**
     * @param args the command line arguments
     */
    static Map<?, ?> map[];
    static Gson gson;
    static List<String> files;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        out.println("Please input the target json file's parent directory = ");
        String path = sc.nextLine();
        jsonToMap(getCurrentDirectoryAllFiles(path));
        writeToDB();
        saveToFile();
        
        // SYSTEM PAUSE
        out.println("按下ENTER鍵離開...");
        while(sc.hasNextLine()) {
            String s = sc.nextLine();
            if (s.isEmpty())
                break;
        }
    }
    
    public static List<String> getCurrentDirectoryAllFiles(String path) {
        files = new ArrayList<>();
        File[] file = new File(path).listFiles();
        
        for(File f : file) {
            if(f.isFile() & f.getName().endsWith(".EZ")) {
                files.add(f.getAbsolutePath());
            }
        }
        return files;
    }
    
    public static void jsonToMap(List<String> files) {
        int file_length = files.size();
        map = new Map[file_length];
        gson = new Gson();
        
        for(int i=0; i<file_length; i++) {
            try(Reader r = new FileReader(files.get(i))) {
                map[i] = gson.fromJson(r, Map.class);
                for (Map.Entry<?,?> entry : map[i].entrySet()){
                    out.println(entry.getKey() + " = " + entry.getValue());
                }
            } catch (FileNotFoundException fnfe) {
                err.println(fnfe);
            } catch (IOException ioe) {
                err.println(ioe);
            }
        }
        
    }
        
    public static void saveToFile() {
        File f;
        
        for(int i=0; i<map.length; i++) {
            String extract_directly = map[i].get("TXN_DATA").toString();
            JsonObject json = new JsonParser().parse(extract_directly).getAsJsonObject();
            gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonPrettier = gson.toJson(json);
            
            try(FileWriter fw = new FileWriter(files.get(i) + ".RST")) {
                fw.write(jsonPrettier);
            } catch (IOException ioe) {
                err.println(ioe);
            }
            
            f = new File(files.get(i));
            File tmp = new File(f.getParent() + "/success/");
            if(!tmp.exists())
                tmp.mkdir();
            if(f.renameTo(new File(f.getParentFile() + "/success/" + f.getName())))
                f.delete();
            
            f = new File(files.get(i) + ".RST");
            tmp = new File(f.getParent() + "/result/");
            if(!tmp.exists())
                tmp.mkdir();
            if(f.renameTo(new File(f.getParentFile() + "/result/" + f.getName())))
                f.delete();
        }
        
    }
    
    public static void writeToDB() {
        String url = "jdbc:mysql://127.0.0.1:9999/test?allowPublicKeyRetrieval=true&useSSL=false";
        String sql = "INSERT INTO json_record VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        try(Connection conn = 
                DriverManager.getConnection(url, "user", "password");
            ) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for(int i=0; i<map.length; i++) {
                pstmt.setString(1, map[i].get("card_id".toUpperCase()).toString());
                pstmt.setInt(2, Integer.parseInt(map[i].get("tx_mode".toUpperCase()).toString()));
                pstmt.setInt(3, Integer.parseInt(map[i].get("issuer_id".toUpperCase()).toString()));
                pstmt.setString(4, map[i].get("entry_time".toUpperCase()).toString());
                pstmt.setString(5, map[i].get("exit_time".toUpperCase()).toString());
//                pstmt.setTimestamp(4, Timestamp.valueOf(map[i].get("entry_time".toUpperCase()).toString()));
//                pstmt.setTimestamp(5, Timestamp.valueOf(map[i].get("exit_time".toUpperCase()).toString()));
                pstmt.setInt(6, Integer.parseInt(map[i].get("entry_station_id".toUpperCase()).toString()));
                pstmt.setInt(7, Integer.parseInt(map[i].get("exit_station_id".toUpperCase()).toString()));
                pstmt.setString(8, map[i].get("qr_data".toUpperCase()).toString());
                
                pstmt.executeUpdate();
            }
            
            
        } catch(SQLException sqle) {
            err.println(sqle);
        }
    }
    
}
