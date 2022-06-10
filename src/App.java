import java.io.PrintWriter;
import java.util.Scanner;

public class App {
    private static Scanner scanner;
    private static String input;
    private static Menu menu;
    private static PrintWriter pw;

    private App() {

    }

    public static void main(String[] args) throws Exception {
        pw = new PrintWriter("out.txt");
        scanner = new Scanner(System.in);
        menu = LoginMenu.getInstance();
        do {
            menu.show();
            Game.getInstance().showBoard();
        } while (true);
    }

    public static String getNextLine() {
        if ((input = scanner.nextLine().trim()).equals("exit"))
            end();
        pw.println();
        pw.println(input);
        pw.println();
        return input;
    }

    public static void changeRoot(Menu menu) {
        App.menu = menu;
    }

    public static void print(Object obj) {
        pw.println(obj);
        System.out.println(obj);
    }

    private static void end() {
        System.out.println("program ended");
        pw.close();
        System.exit(0);
    }
}
