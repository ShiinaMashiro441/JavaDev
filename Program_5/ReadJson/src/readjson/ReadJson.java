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
        String path = "E:\\GitBash\\GitRepository\\Repository\\JavaDev\\Program_5\\ReadJson\\src\\readjson";
        jsonToMap(getCurrentDirectoryAllFiles(path));
        process();
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
    
    public static void process() {
        writeToDB();
        saveToFile();
    }
    
    public static void saveToFile() {
        out.println("=====TEST=====");
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
        String url = "jdbc:mysql://111.241.71.171:3334/json_record?user=root&password=P@ssw0rd";
        String sql = "insert into json_record (card_id, tx_mode,"
                   + " issuer_id, entry_time, exit_time, entry_stationid, exit_stationid, qr_data)"
                   + "values(?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = 
                DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareCall(sql);) {
            
            for(int i=0; i<map.length; i++) {
                pstmt.setString(1, map[i].get("card_id").toString());
                pstmt.setInt(2, Integer.parseInt(map[i].get("tx_mode").toString()));
                pstmt.setInt(3, Integer.parseInt(map[i].get("issuer_id").toString()));
                pstmt.setTimestamp(4, Timestamp.valueOf(map[i].get("entry_time").toString()));
                pstmt.setTimestamp(5, Timestamp.valueOf(map[i].get("exit_time").toString()));
                pstmt.setInt(6, Integer.parseInt(map[i].get("entry_stationid").toString()));
                pstmt.setInt(7, Integer.parseInt(map[i].get("exit_stationid").toString()));
                pstmt.setString(8, map[i].get("qr_data").toString());
                
                pstmt.execute();
            }
            
            
        } catch(SQLException sqle) {
            err.println(sqle);
        }
    }
    
}
