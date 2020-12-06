/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MH588
 * 
 * Task:
 * Keywords saved in a txt file,
 * Load into program, and sort by alphabet.
 * 
 * Suggest:
 * main() : File I/O
 * parseString()
 * sort()
 * 
 * 
 */

import static java.lang.System.*;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Keywords_scan {
    public static void main(String args[]) {
        fileScan();
    }

    public static void fileScan() {

        String data;
        List<String> list = new ArrayList<String>();

        try (FileReader fr = new FileReader("./src/keywords.txt"); BufferedReader br = new BufferedReader(fr);) {

            while ((data = br.readLine()) != null) {
                out.println(data);
                list.add(data);
                Thread.sleep(150);
            }

        } catch (IOException ioe) {
            err.println(ioe);
        } catch (Exception e) {
            err.println(e);
        }

        Collections.sort(list);

        for (String s : list)
            out.println(s);

    }
}
