package JavaDev.others;

import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
import static java.lang.System.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        out.println("Keywords file path: ");
        List<String> keywords = scanFileNextLine(sc.nextLine());
        out.println("regex: ");
        keywords = parseKeywords(sc.nextLine(), keywords);

        out.println("Source code file path: ");
        List<String> locList = scanFileNextLine(sc.nextLine());
        out.println("regex: ");
        List<List> loc = parser(sc.nextLine(), locList);

        out.println("\nResult: \n");

        output(loc, keywords);
    }

    /**
     * Read a file by absoulte path Store each line as elements to locList, and
     * return caller
     */
    public static List<String> scanFileNextLine(String filepath) {
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

    public static List<List> parser(String regex, List<String> locList) {
        ListIterator li = locList.listIterator();

        while (li.hasNext()) {
            String s = li.next().toString();
            /**
             * Remove multi-line comment
             */
            if (s.startsWith("/**")) {
                li.remove();
                do {
                    if (li.next().toString().endsWith("*/")) {
                        li.remove();
                        break;
                    }
                    li.remove();
                } while (true);
            }
            /**
             * Remove single-line comment
             */
            else if (s.startsWith("//")) {
                li.remove();
            }
        }

        List<List> top_dimension = new ArrayList<>();
        List<String> tmp;

        Pattern ptn = Pattern.compile(regex);
        for (String ln : locList) {

            if ((ln.contains("public class ") & ln.endsWith("{"))
                    || (ln.contains("protected class ") & ln.endsWith("{"))
                    || (ln.contains("private class ") & ln.endsWith("{"))
                    || (ln.contains("class ") & ln.endsWith("{"))) {

                tmp = new ArrayList<>();
                for (String word : ptn.split(ln)) {
                    tmp.add(word);
                }
                top_dimension.add(tmp);

            } else if ((ln.contains("public static ") & ln.contains("("))
                    || (ln.contains("protected static ") & ln.contains("("))
                    || (ln.contains("private static") & ln.contains("("))
                    || (ln.contains("static ") & ln.contains("("))) {

                tmp = new ArrayList<>();
                for (String word : ptn.split(ln)) {
                    tmp.add(word);
                }
                top_dimension.add(tmp);
            } else if ((ln.startsWith("import") || ln.startsWith("package"))) {
                tmp = new ArrayList<>();
                for (String word : ptn.split(ln)) {
                    tmp.add(word);
                }
                top_dimension.add(tmp);
            } else {
                int last = top_dimension.size() - 1;
                if (last > 0) {
                    tmp = top_dimension.get(last);
                    for (String word : ptn.split(ln)) {
                        tmp.add(word);
                    }
                }
            }

        }

        return top_dimension;
    }

    public static List parseKeywords(String regex, List<String> list) {
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

    public static void output(List<List> top_dimension, List<String> keywords) {

        for (List<String> list : top_dimension) {

            Map<String, Integer> m = compare(list, keywords);
            Iterator it = m.keySet().iterator();
            String key;
            String first_element = list.get(0), second_element = list.get(1);

            if (first_element.equalsIgnoreCase("import") || first_element.equalsIgnoreCase("package")) {
                continue;
            } else if (second_element.equalsIgnoreCase("static")) {
                out.println("In static " + list.get(3) + "() :");

                while (it.hasNext()) {
                    key = (String) it.next();
                    if (m.get(key) != 0) {
                        out.println(key + " : " + m.get(key));
                    }
                }
                out.println();

            } else if (first_element.equalsIgnoreCase("static")) {
                out.print("In static " + list.get(2) + "() :");

                while (it.hasNext()) {
                    key = (String) it.next();
                    if (m.get(key) != 0) {
                        out.println(key + " : " + m.get(key));
                    }
                }
                out.println();

            } else if (first_element.equalsIgnoreCase("public") || first_element.equalsIgnoreCase("protected")
                    || first_element.equalsIgnoreCase("private")) {
                if (second_element.equalsIgnoreCase("class"))
                    continue;
                out.println("In " + list.get(2) + "() :");

                while (it.hasNext()) {
                    key = (String) it.next();
                    if (m.get(key) != 0) {
                        out.println(key + " : " + m.get(key));
                    }
                }
                out.println();

            } else {

                out.println("In " + second_element + "() :");
                while (it.hasNext()) {
                    key = (String) it.next();
                    if (m.get(key) != 0) {
                        out.println(key + " : " + m.get(key));
                    }
                }

            }
        }

        out.printf("In this source code: \n");

        Map<String, Integer> m = compare(top_dimension, keywords);
        Iterator it = m.keySet().iterator();
        String key = "";

        while (it.hasNext()) {
            key = (String) it.next();
            if (m.get(key) != 0) {
                out.println(key + " : " + m.get(key));
            }
        }
    }

    public static Map<String, Integer> compare(List contentList, List<String> keywords) {

        Map<String, Integer> m = new TreeMap<>();

        for (String s : keywords) {
            m.put(s, 0);
        }

        if (contentList.get(0).getClass().getName().equalsIgnoreCase("java.util.ArrayList")) {

            List<String> tmp;

            for (int i = 0; i < contentList.size(); i++) {
                tmp = (List) contentList.get(i);
                for (String word : tmp) {
                    if (m.containsKey(word)) {
                        m.put(word, m.get(word) + 1);
                    }
                }
            }
        } else if (contentList.get(0).getClass().getName().equalsIgnoreCase("java.lang.String")) {
            for (int i = 0; i < contentList.size(); i++) {
                String word = (String) contentList.get(i);
                if (m.containsKey(word)) {
                    m.put(word, m.get(word) + 1);
                }
            }
        }

        return m;
    }

}