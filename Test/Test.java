package JavaDev.Test;

import java.io.*;

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
}