package JavaDev.Test;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        try (FileReader fr = new FileReader("E:\\GitBash\\GitRepository\\JavaDev\\Test\\a.txt");
                BufferedReader br = new BufferedReader(fr);) {
            for (int i = 0; i < 5; i++) {
                sb.append(br.readLine());
                if (sb.toString().equalsIgnoreCase("null")) {
                    break;
                } else {
                    System.out.println(sb);
                    sb.setLength(0);
                }
            }

        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    public static List<String> parseLOC(String regex, List<String> loc_list) {
        StringBuilder sb = new StringBuilder();
        Iterator iter = loc_list.iterator();
        List<String> tmp = new ArrayList<>();
        Pattern ptn = Pattern.compile("//");
        Matcher mch;

        /**
         * Remove loc start & end whitespace
         */
        while (iter.hasNext()) {
            sb.append(iter.next().toString().trim());
            tmp.add(sb.toString());
            sb.setLength(0);
        }

        ListIterator li = tmp.listIterator();
        loc_list.clear();
        ptn = Pattern.compile(regex);

        while (li.hasNext()) {
            sb.append(li.next().toString());
            /**
             * Remove multi-line comments
             */
            if (sb.toString().startsWith("/**")) {
                li.remove();
                do {
                    sb.setLength(0);
                    sb.append(li.next().toString());
                    if (sb.toString().endsWith("*/")) {
                        li.remove();
                        break;
                    } else
                        li.remove();
                } while (true);
            }
            /**
             * Remove single comment
             */
            else if (sb.toString().contains("//")) {
                if (sb.toString().startsWith("//"))
                    li.remove();
                else {
                    mch = ptn.matcher(sb.toString());
                    while (mch.find()) {
                        sb.replace(mch.start(), mch.start() + 2, " ");
                        // li.remove();
                        // li.add(sb.toString());
                    }
                }
                sb.setLength(0);
            }
            /**
             * Split each loc into vocabularies by special symbol Store back to the loc list
             */
            Collections.addAll(loc_list, ptn.split(sb.toString()));

        }

        return loc_list;
    }
}