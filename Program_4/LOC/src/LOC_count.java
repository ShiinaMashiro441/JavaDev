/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MH588
 */

import java.io.*;
import java.util.*;
import static java.lang.System.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class LOC_count {
    private static final Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
//        loadFile();
        out.println(getSpecificLine(4));
    }
    public static int lineCount (LineNumberReader lnr) throws IOException {
        int line=0;
        while(lnr.readLine() != null)
            line++;
        return line;
    }
    
    public static String getSpecificLine(int ln) {
        String line = "";
//      https://stackoverflow.com/questions/2312756/how-to-read-a-specific-line-using-the-specific-line-number-from-a-file-in-java

//        // way 1 for small files
//        try {
//            line = Files.readAllLines(Paths.get("C:\\Users\\MH588\\Desktop\\git\\JavaDev\\Program_4\\LOC\\src\\keywords.txt")).get(ln);
//        } catch (IOException ioe) {
//            out.println(ioe);
//        }
        
        // way 2 for large files
        try (Stream<String> lines = Files.lines(Paths.get("C:\\Users\\MH588\\Desktop\\git\\JavaDev\\Program_4\\LOC\\src\\keywords.txt"))) {
            line = lines.skip(ln-1).findFirst().get();
        } catch (IOException ioe) {
            err.println(ioe);
        }
        return line;
    }
    
    public static void loadFile() {
        File f = new File("C:\\Users\\MH588\\Desktop\\git\\JavaDev\\Program_4\\LOC\\src\\keywords.txt");
        StringBuilder ln = new StringBuilder();
        try (FileReader fr = new FileReader(f);
             LineNumberReader lnr = new LineNumberReader(fr);) {
            do {
                ln.setLength(0);
                ln.append(lnr.readLine());
                if(ln.toString().equalsIgnoreCase("null"))
                    break;
                
                
                
            } while (true);
        } catch (IOException ioe) {
            err.println(ioe);
        }
        
    }
    
    public static void readSector() {
        
    }
}

