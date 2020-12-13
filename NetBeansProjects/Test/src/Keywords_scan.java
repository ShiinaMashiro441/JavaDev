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

        Scanner sc = new Scanner(System.in);
        out.println("---Method 1---");
        out.print("Input the txt file URL: ");
        fileScan(sc.nextLine());

        out.printf("---Method 2---\n---Loading---\n");
        out.print("Input the txt file URL: ");
        List list = fileInput(sc.nextLine());

        out.println("---Parsing---");
        out.print("Input delimeter (regex): ");
        list = parseString(sc.nextLine(), list);
        out.println("---Sorting---");
        for (Object s : list) {
            out.println(s);
        }
    }

    public static void fileScan(String filePath) {

        String data;
        List<String> list = new ArrayList<String>();

        try (FileReader fr = new FileReader(filePath); BufferedReader br = new BufferedReader(fr);) {

            while ((data = br.readLine()) != null) {
                list.add(data);
            }

        } catch (IOException ioe) {
            err.println(ioe);
        }

        out.println("---Sorted---");
        Collections.sort(list);

        for (String s : list)
            out.println(s);

    }

    public static List<String> fileInput(String filepath) {
        List<String> locList = new ArrayList<>();
        StringBuilder loc = new StringBuilder();
        File f = new File(filepath);

        try (FileReader fr = new FileReader(f); BufferedReader br = new BufferedReader(fr);) {

            do {
                loc.append(br.readLine());
                if (loc.toString().equalsIgnoreCase("null"))
                    break;
                else {
                    locList.add(loc.toString().trim());
                    loc.setLength(0);
                }
            } while (true);

        } catch (Exception e) {
            err.println(e);
        }

        return locList;

    }

    public static List parseString(String regex, List<String> list) {
        Pattern ptn = Pattern.compile(regex);
        Iterator iter = list.iterator();
        List<String> tmp = new ArrayList<String>();
        while (iter.hasNext()) {
            String[] parsed = ptn.split(iter.next().toString());
            Collections.addAll(tmp, parsed);
        }
        Collections.sort(tmp);
        out.println(tmp);
        return tmp;
    }
}
