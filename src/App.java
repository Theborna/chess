import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class App {
    private static BufferedReader scanner;
    private static String input;
    private static Menu menu;
    private static PrintWriter pw;
    private static String num;

    private App() {

    }

    public static void main(String[] args) throws Exception {
        pw = new PrintWriter("out.txt");
        num = "15";
        scanner = new BufferedReader(new FileReader("in/input" + num + ".txt"));
        menu = LoginMenu.getInstance();
        do {
            menu.show();
            // Game.getInstance().showBoard();
        } while (true);

    }

    public static String getNextLine() {
        try {
            if ((input = scanner.readLine().trim()).equals("exit"))
                end();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // pw.println();
        // pw.println(input);
        // pw.println();
        return input;
    }

    public static void changeRoot(Menu menu) {
        App.menu = menu;
    }

    public static void print(Object obj) {
        pw.println(obj);
        // System.out.println(obj);
    }

    private static void end() {
        App.print("program ended");
        pw.close();
        // to check the output file
        /*
         * try (BufferedReader br1 = new BufferedReader(new FileReader("out.txt"));
         * BufferedReader br2 = new BufferedReader(new FileReader("out/output" +
         * String.valueOf(num) + ".txt"))) {
         * PrintWriter diff = new PrintWriter("differences" + num + ".txt");
         * String line1, line2;
         * int line = 0;
         * while ((line1 = br1.readLine()) != null && (line2 = br2.readLine()) != null)
         * {
         * line++;
         * if (!line1.equals(line2))
         * diff.println(line + ". " + line1 + " != " + line2);
         * }
         * diff.close();
         * } catch (Exception e) {
         * e.printStackTrace();
         * }
         */
        System.exit(0);
    }
}

// enum Color {
// BLACK, WHITE;

// @Override
// public String toString() {
// if (this == WHITE)
// return "w";
// return "b";
// }

// }
