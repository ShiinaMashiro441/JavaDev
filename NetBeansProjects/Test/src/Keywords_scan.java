/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaDev.NetBeansProjects.Test.src;

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
import java.util.Scanner;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.*;

public class Keywords_scan {
    public static void main(String args[]) {
        // fileScan();

        List list = fileInput(new File(new Scanner(System.in).nextLine()));
        for (int i = 0; i < list.size(); i++) {
            out.println(list.get(i));
        }
        out.println("-----");
        parseString(list);
    }

    public static void fileScan() {

        String data;
        List<String> list = new ArrayList<String>();

        try (FileReader fr = new FileReader("./JavaDev/NetBeansProjects/Test/src/keywords.txt");
                BufferedReader br = new BufferedReader(fr);) {

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

    public static List fileInput(File inputFile) {
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<String>();
        try (FileReader fr = new FileReader(inputFile); BufferedReader br = new BufferedReader(fr);) {
            do {
                sb.append(br.readLine());
                if (sb.toString().equalsIgnoreCase("null"))
                    break;
                else {
                    out.println(sb);
                    list.add(sb.toString());
                    sb.setLength(0);
                }
            } while (true);

        } catch (IOException ioe) {
            err.println(ioe);
        }
        return list;
    }

    public static void parseString(List<String> list) {
        Pattern ptn = Pattern.compile("[\t]");
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            String[] parsed = ptn.split(iter.next().toString());
            for (String s : parsed)
                out.println(s);
        }
    }
}
