import static java.lang.System.*;

public class Test {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        sb.append("aSd813eij");
        out.println("sb = " + sb);
        out.println("---");
        sb.setLength(0);
        out.println("sb = " + sb);
        out.println("---");
        sb.append("ASDASDaSd813eij");
        out.println("sb = " + sb);
    }
}
