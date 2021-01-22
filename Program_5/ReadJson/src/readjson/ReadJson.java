/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readjson;

import java.io.*;
import static java.lang.System.*;
import java.util.*;
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
    public static void main(String[] args) {
        
        try(Reader r = new FileReader("src/readjson/test.json")) {
            Gson g = new Gson();
            Map<?,?> m = g.fromJson(r, Map.class);
            for (Map.Entry<?,?> entry : m.entrySet()){
                out.println(entry.getKey() + " = " + entry.getValue());
                out.println(entry.getValue().getClass().getName());
            }
        } catch (Exception e) {
            err.println(e);
        }
    }
    
}
