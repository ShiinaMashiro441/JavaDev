package JavaDev.NetBeansProjects.Test.src;

import static java.lang.System.*;
import java.io.*;
import java.util.*;

public class ScanFileKeywords {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> keywords, sourceCode, vocList;
        Map<String, Integer> map = new TreeMap<>();

        out.println("Reading Java Keywords...");
        out.print("Java Keywords Path: ");
        keywords = Keywords_scan.fileInput(new File(sc.nextLine()));

        out.print("Input delimeter (regex); ");
        keywords = Keywords_scan.parseString(sc.nextLine(), keywords);

        out.println("Creating Keywords Map...");
        for (String s : keywords) {
            map.put(s, 0);
        }

        out.print("Input the source code (*.java) : ");
        sourceCode = Keywords_scan.fileInput(new File(sc.nextLine()));

        out.println("----");
        out.print("Input delimeter (regex): ");
        vocList = Keywords_scan.parseString(sc.nextLine(), sourceCode);

        compare(map, vocList);

        Iterator it = map.keySet().iterator();

        while (it.hasNext()) {
            String key = (String) it.next();
            out.println(key + " : " + map.get(key));
        }

    }

    public static void compare(Map<String, Integer> m, List<String> vocList) {
        for (String voc : vocList) {

            if (m.containsKey(voc))
                m.put(voc, m.get(voc) + 1);

        }
    }
}
