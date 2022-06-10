// // package release;

// // import java.io.PrintWriter;
// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Scanner;
// import java.util.Set;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

// public class App {
//     private static Scanner scanner;
//     private static String input;
//     private static Menu menu;
//     // private static PrintWriter pw;

//     private App() {

//     }

//     public static void main(String[] args) throws Exception {
//         // pw = new PrintWriter("out.txt");
//         scanner = new Scanner(System.in);
//         menu = LoginMenu.getInstance();
//         do {
//             menu.show();
//             // Game.getInstance().showBoard();
//         } while (true);
//     }

//     public static String getNextLine() {
//         if ((input = scanner.nextLine().trim()).equals("exit"))
//             end();
//         // pw.println();
//         // pw.println(input);
//         // pw.println();
//         return input;
//     }

//     public static void changeRoot(Menu menu) {
//         App.menu = menu;
//     }

//     public static void print(Object obj) {
//         // pw.println(obj);
//         System.out.println(obj);
//     }

//     private static void end() {
//         App.print("program ended");
//         // pw.close();
//         System.exit(0);
//     }
// }

// enum AppRegex { // all in-line inputs possible
//     REGISTER("^register (?<username>.+) (?<password>.+)$"), LOGIN("^login (?<username>.+) (?<password>.+)$"),
//     REMOVE("^remove (?<username>.+) (?<password>.+)$"), LIST_USERS("list_users"), HELP("help"),
//     NEW_GAME("^new_game (?<username>.+) (?<limit>\\d+)$"), SCORE_BOARD("scoreboard"), LOGOUT("logout"),
//     SELECT("select (?<x>\\d+),(?<y>\\d+)"), DESELECT("deselect"), MOVE("move (?<x>\\d+),(?<y>\\d+)"),
//     NEXT_TURN("next_turn"), SHOW_TURN("show_turn"), UNDO("undo"), UNDO_NUMBER("undo_number"),
//     SHOW_MOVES("show_moves"), SHOW_MOVES_ALL("show_moves -all"), SHOW_KILLED("show_killed"),
//     SHOW_KILLED_ALL("show_killed -all"), SHOW_BOARD("show_board"), FORFEIT("forfeit");

//     String regex;

//     AppRegex(String regex) {
//         this.regex = regex;
//     }

//     public Matcher getMatcher(String input) {
//         Matcher matcher = Pattern.compile(regex).matcher(input);
//         if (matcher.matches())
//             return matcher;
//         return null;
//     }

//     public boolean matches(String input) {
//         return input.matches(regex);
//     }
// }

// enum Color {
//     BLACK, WHITE;

//     @Override
//     public String toString() {
//         if (this == WHITE)
//             return "w";
//         return "b";
//     }

// }

// class Bishop extends Piece {

//     public Bishop(int x, int y, Color color) {
//         super(x, y, color, "B");
//     }

//     @Override
//     protected boolean valid(int x2, int y2) {
//         if (isOccupied(x2, y2))
//             return false;
//         return isWithinRay(1, 1, x2, y2) || isWithinRay(1, -1, x2, y2) || isWithinRay(-1, 1, x2, y2)
//                 || isWithinRay(-1, -1, x2, y2);
//     }

// }

// class Game {
//     private static Game instance;
//     private User WhitePlayer, BlackPlayer;
//     private User turn;
//     private Piece selectedPiece;
//     private boolean moved;
//     private Piece[][] pieces;
//     private static final int INDEFINITE = 0;
//     private int totalMoves;
//     private int moves;
//     private Move nextMove;
//     private boolean ended;

//     private Game() {
//         turn = WhitePlayer;
//         initialize();
//     }

//     private void initialize() {
//         ended = false;
//         moved = false;
//         selectedPiece = null;
//         Move.clear();
//         moves = 0;
//         pieces = new Piece[8][8];
//         for (int i = 0; i < 8; i++) {
//             pieces[i][1] = new Pawn(i, 1, Color.WHITE);
//             pieces[i][6] = new Pawn(i, 6, Color.BLACK);
//         }
//         // white pieces
//         pieces[0][0] = new Rook(0, 0, Color.WHITE);
//         pieces[7][0] = new Rook(7, 0, Color.WHITE);
//         pieces[1][0] = new Knight(1, 0, Color.WHITE);
//         pieces[6][0] = new Knight(6, 0, Color.WHITE);
//         pieces[2][0] = new Bishop(2, 0, Color.WHITE);
//         pieces[5][0] = new Bishop(5, 0, Color.WHITE);
//         pieces[3][0] = new Queen(3, 0, Color.WHITE);
//         pieces[4][0] = new King(4, 0, Color.WHITE);
//         // black pieces
//         pieces[0][7] = new Rook(0, 7, Color.BLACK);
//         pieces[7][7] = new Rook(7, 7, Color.BLACK);
//         pieces[1][7] = new Knight(1, 7, Color.BLACK);
//         pieces[6][7] = new Knight(6, 7, Color.BLACK);
//         pieces[2][7] = new Bishop(2, 7, Color.BLACK);
//         pieces[5][7] = new Bishop(5, 7, Color.BLACK);
//         pieces[3][7] = new Queen(3, 7, Color.BLACK);
//         pieces[4][7] = new King(4, 7, Color.BLACK);
//     }

//     public static Game getInstance() {
//         if (instance == null)
//             instance = new Game();
//         return instance;
//     }

//     public boolean reachedLimit() {
//         return (totalMoves == INDEFINITE) ? false : (moves >= totalMoves);
//     }

//     public Game setPlayers(User WhitePlayer, User BlackPlayer) {
//         this.WhitePlayer = WhitePlayer;
//         this.BlackPlayer = BlackPlayer;
//         this.turn = WhitePlayer;
//         return this;
//     }

//     public Game setTotalMoves(int totalMoves) {
//         this.totalMoves = totalMoves;
//         return this;
//     }

//     public User getWhitePlayer() {
//         return WhitePlayer;
//     }

//     public User getBlackPlayer() {
//         return BlackPlayer;
//     }

//     public Color getTurn() {
//         return turn.equals(WhitePlayer) ? Color.WHITE : Color.BLACK;
//     }

//     public void select(int x, int y) {
//         x--;
//         y--;
//         if (!validSelection(x, y).equals("valid")) {
//             App.print(validSelection(x, y));
//         } else {
//             App.print("selected");
//             selectedPiece = pieces[x][y];
//         }
//     }

//     private String validSelection(int x, int y) {
//         if (x < 0 || y < 0 || x >= 8 || y >= 8)
//             return "wrong coordination";
//         if (pieces[x][y] == null)
//             return "no piece on this spot";
//         if (pieces[x][y].getColor() != getTurn())
//             return "you can only select one of your pieces";
//         return "valid";
//     }

//     public void deselect() {
//         if (selectedPiece == null) {
//             App.print("no piece is selected");
//         } else {
//             App.print("deselected");
//             selectedPiece = null;
//         }
//     }

//     public void showBoard() {
//         StringBuilder sb = new StringBuilder();
//         for (int i = 0; i < 8; i++) {
//             for (int j = 0; j < 8; j++) {
//                 if (pieces[j][7 - i] == null)
//                     sb.append("  ");
//                 else
//                     sb.append(pieces[j][7 - i].toString());
//                 sb.append("|");
//             }
//             sb.append("\n");
//         }
//         sb.deleteCharAt(sb.length() - 1);
//         App.print(sb);
//     }

//     public void move(int x, int y) {
//         x--;
//         y--;
//         if (!validMovement(x, y).equals("valid")) {
//             App.print(validMovement(x, y));
//         } else {
//             nextMove = new Move(x, y, selectedPiece, pieces[x][y]);
//             if (selectedPiece.moveTo(x, y)) {
//                 if (nextMove.hit)
//                     App.print("rival piece destroyed");
//                 else
//                     App.print("moved");
//             } else
//                 App.print("cannot move to the spot");
//             moved = true;
//         }

//     }

//     public String validMovement(int x, int y) {
//         if (moved)
//             return "already moved";
//         if (x < 0 || y < 0 || x >= 8 || y >= 8)
//             return "wrong coordination";
//         if (selectedPiece == null)
//             return " do not have any selected piece";
//         return "valid";
//     }

//     public void setPiece(int x, int y, Piece piece) {
//         if (nextMove.hitKing)
//             ended = true;
//         pieces[x][y] = piece;
//     }

//     public void nextTurn() {
//         if (ended) {
//             App.print("turn completed");
//             endGame(turn, getTurn());
//             return;
//         }
//         if (!moved) {
//             App.print("you must move then proceed to next turn");
//             return;
//         }
//         moved = false;
//         if (turn == WhitePlayer)
//             turn = BlackPlayer;
//         else
//             turn = WhitePlayer;
//         App.print("turn completed");
//     }

//     public void showTurn() {
//         App.print("it is player " + turn.getUsername() + " turn with color " + getTurn().name().toLowerCase());
//     }

//     public void showKills(boolean all) {
//         for (String string : Move.getKills()) {
//             if (all || ((string.toCharArray()[1] == 'w') == (getTurn() == Color.WHITE)))
//                 App.print(string);
//         }
//     }

//     public void showMoves(boolean all) {
//         for (String string : Move.getMoves()) {
//             if (all || ((string.toCharArray()[1] == 'w') == (getTurn() == Color.WHITE)))
//                 App.print(string);
//         }
//     }

//     public Piece[][] getPieces() {
//         return pieces;
//     }

//     public void endGame(User winner, Color color) {
//         App.print("player " + winner.getUsername() + " with color " + ((color == Color.WHITE) ? "white" : "black")
//                 + " won");
//         this.initialize();
//         App.changeRoot(MainMenu.getInstance());
//     }

// }

// class GameMenu implements Menu {

//     private static GameMenu instance;
//     private Matcher matcher;

//     private GameMenu() {
//     }

//     public static GameMenu getInstance() {
//         if (instance == null)
//             instance = new GameMenu();
//         return instance;
//     }

//     @Override
//     public void show() {
//         String input = App.getNextLine();
//         if ((matcher = AppRegex.SELECT.getMatcher(input)) != null) {
//             Game.getInstance().select(Integer.parseInt(matcher.group("y")), Integer.parseInt(matcher.group("x")));
//         } else if (AppRegex.DESELECT.matches(input)) {
//             Game.getInstance().deselect();
//         } else if ((matcher = AppRegex.MOVE.getMatcher(input)) != null) {
//             Game.getInstance().move(Integer.parseInt(matcher.group("y")), Integer.parseInt(matcher.group("x")));
//         } else if (AppRegex.NEXT_TURN.matches(input)) {
//             Game.getInstance().nextTurn();
//         } else if (AppRegex.SHOW_TURN.matches(input)) {
//             Game.getInstance().showTurn();
//         } else if (AppRegex.UNDO.matches(input)) {
//             undo();
//         } else if (AppRegex.UNDO_NUMBER.matches(input)) {
//             undoNumber();
//         } else if (AppRegex.SHOW_MOVES.matches(input)) {
//             Game.getInstance().showMoves(false);
//         } else if (AppRegex.SHOW_MOVES_ALL.matches(input)) {
//             Game.getInstance().showMoves(true);
//         } else if (AppRegex.SHOW_KILLED.matches(input)) {
//             Game.getInstance().showKills(false);
//         } else if (AppRegex.SHOW_KILLED_ALL.matches(input)) {
//             Game.getInstance().showKills(true);
//         } else if (AppRegex.SHOW_BOARD.matches(input)) {
//             Game.getInstance().showBoard();
//         } else if (AppRegex.HELP.matches(input)) {
//             help();
//         } else if (AppRegex.FORFEIT.matches(input)) {
//             forfeit();
//         } else {
//             App.print("invalid command");
//         }
//     }

//     private void forfeit() {
//     }

//     private void help() {
//         App.print("select [x],[y]");
//         App.print("deselect");
//         App.print("move [x],[y]");
//         App.print("next_turn");
//         App.print("show_turn");
//         App.print("undo");
//         App.print("undo_number");
//         App.print("show_moves [-all]");
//         App.print("show_killed [-all]");
//         App.print("show_board");
//         App.print("help");
//         App.print("forfeit");
//     }

//     private void undoNumber() {
//     }

//     private void undo() {
//     }

// }

// class King extends Piece {

//     public King(int x, int y, Color color) {
//         super(x, y, color, "K");
//     }

//     @Override
//     protected boolean valid(int x2, int y2) {
//         if (isOccupied(x2, y2))
//             return false;
//         if (Math.abs(x - x2) <= 1 && Math.abs(y - y2) <= 1)
//             return true;
//         return false;
//     }

// }

// class Knight extends Piece {

//     public Knight(int x, int y, Color color) {
//         super(x, y, color, "N");
//     }

//     @Override
//     protected boolean valid(int x2, int y2) {
//         if (isOccupied(x2, y2))
//             return false;
//         if (((x2 - x) * (x2 - x)) + ((y2 - y) * (y2 - y)) == 5)
//             return true;
//         return false;
//     }

// }

// class LoginMenu implements Menu {
//     private static LoginMenu instance;
//     private Matcher matcher;
//     private User user;

//     private LoginMenu() {
//     }

//     public static LoginMenu getInstance() {
//         if (instance == null)
//             instance = new LoginMenu();
//         return instance;
//     }

//     public User getUser() {
//         return user;
//     }

//     @Override
//     public void show() {
//         String input = App.getNextLine();
//         if ((matcher = AppRegex.REGISTER.getMatcher(input)) != null) {
//             register(matcher.group("username"), matcher.group("password"));
//         } else if ((matcher = AppRegex.LOGIN.getMatcher(input)) != null) {
//             login(matcher.group("username"), matcher.group("password"));
//         } else if ((matcher = AppRegex.REMOVE.getMatcher(input)) != null) {
//             remove(matcher.group("username"), matcher.group("password"));
//         } else if (AppRegex.LIST_USERS.matches(input)) {
//             User.listUsers();
//         } else if (AppRegex.HELP.matches(input)) {
//             help();
//         } else {
//             App.print("invalid command");
//         }
//     }

//     private void help() {
//         App.print("register [username] [password]");
//         App.print("login [username] [password]");
//         App.print("remove [username] [password]");
//         App.print("list_users");
//         App.print("help");
//         App.print("exit");
//     }

//     private void login(String username, String password) {
//         log(username, password, () -> {
//             App.print("login successful");
//             App.changeRoot(MainMenu.getInstance());
//         });
//     }

//     private void remove(String username, String password) {
//         log(username, password, () -> {
//             App.print("removed " + username + " successfully");
//             User.logTo(username, password).remove();
//         });
//     }

//     private void log(String username, String password, Runnable r) {
//         if (!validate(username, password).equals("valid")) {
//             App.print(validate(username, password));
//         } else if (User.get(username) == null) {
//             App.print("no user exists with this username");
//         } else if ((user = User.logTo(username, password)) == null) {
//             App.print("incorrect password");
//         } else {
//             r.run();
//         }
//     }

//     private void register(String username, String password) {
//         if (!validate(username, password).equals("valid")) {
//             App.print(validate(username, password));
//         } else if (new User(username, password).add()) {
//             App.print("register successful");
//         } else
//             App.print("a user exists with this username");
//     }

//     private String validate(String username, String password) {
//         if (!username.matches("\\w+"))
//             return "username format is invalid";
//         if (!password.matches("\\w+"))
//             return "password format is invalid";
//         return "valid";
//     }
// }

// class MainMenu implements Menu {

//     private static MainMenu instance;
//     private Matcher matcher;
//     private User opponent;

//     private MainMenu() {

//     }

//     public static MainMenu getInstance() {
//         if (instance == null)
//             instance = new MainMenu();
//         return instance;
//     }

//     @Override
//     public void show() {
//         String input = App.getNextLine();
//         if ((matcher = AppRegex.NEW_GAME.getMatcher(input)) != null) {
//             newGame(matcher.group("username"), Integer.parseInt(matcher.group("limit")));
//         } else if (AppRegex.SCORE_BOARD.matches(input)) {
//             User.scoreBoard();
//         } else if (AppRegex.LIST_USERS.matches(input)) {
//             User.listUsers();
//         } else if (AppRegex.HELP.matches(input)) {
//             help();
//         } else if (AppRegex.LOGOUT.matches(input)) {
//             logout();
//         } else {
//             App.print("invalid command");
//         }
//     }

//     private void logout() {
//         App.print("logout successful");
//         App.changeRoot(LoginMenu.getInstance());
//     }

//     private void help() {
//         App.print("new_game [username] [limit]");
//         App.print("scoreboard");
//         App.print("list_users");
//         App.print("help");
//         App.print("logout");
//     }

//     private void newGame(String username, int limit) {
//         String validation;
//         if (!(validation = validate(username, limit)).equals("valid")) {
//             App.print(validation);
//         } else {
//             App.print("new game started successfully between " + LoginMenu.getInstance().getUser().getUsername()
//                     + " and " + username + " with limit " + limit);
//             Game.getInstance().setPlayers(LoginMenu.getInstance().getUser(), opponent).setTotalMoves(limit);
//             App.changeRoot(GameMenu.getInstance());
//         }
//     }

//     private String validate(String username, int limit) {
//         if (!username.matches("\\w+"))
//             return "invalid username format";
//         if (limit < 0)
//             return "number should be positive to have a limit or 0 for no limit";
//         if (username.equals(LoginMenu.getInstance().getUser().getUsername()))
//             return "you must choose another player to start a game";
//         if ((opponent = User.get(username)) == null) {
//             return "no user exists with this username";
//         }
//         return "valid";
//     }
// }

// interface Menu {

//     void show();

// }

// class Move {
//     private static List<String> moves = new ArrayList<String>();
//     private static List<String> kills = new ArrayList<String>();
//     public int x, y;
//     public Piece p1, p2;
//     public boolean hit, hitKing;

//     public Move(int x, int y, Piece p1, Piece p2) {
//         this.x = x;
//         this.y = y;
//         this.p1 = p1;
//         this.p2 = p2;
//         hit = p2 != null;
//         hitKing = hit && (p2 instanceof King);
//         this.logMove();
//         if (hit)
//             this.logKill();
//     }

//     public void logMove() {
//         String move = p1.toString() + " " + (p1.y + 1) + "," + (p1.x + 1) + " to " + (y + 1) + "," + (x + 1);
//         if (p2 != null)
//             move += " destroyed " + p2.toString();
//         moves.add(move);
//     }

//     public void logKill() {
//         String kill = p2.toString() + " killed in spot " + (y + 1) + "," + (x + 1);
//         kills.add(kill);
//     }

//     public static List<String> getKills() {
//         return kills;
//     }

//     public static List<String> getMoves() {
//         return moves;
//     }

//     public static void clear() {
//         moves.clear();
//         kills.clear();
//     }
// }

// class Pawn extends Piece {

//     public Pawn(int x, int y, Color color) {
//         super(x, y, color, "P");
//     }

//     @Override
//     protected boolean valid(int x2, int y2) {
//         if (isOccupied(x2, y2))
//             return false;
//         boolean goodY = y2 == (color.equals(Color.WHITE) ? (y + 1) : (y - 1));
//         if (hasRival(x2, y2)) {
//             if (goodY)
//                 if (x2 == x - 1 || x2 == x + 1)
//                     return true;
//         } else {
//             if (x2 == x) {
//                 if (moves == 0)
//                     if (y2 == (color.equals(Color.WHITE) ? (y + 2) : (y - 2)))
//                         return true;
//                 if (goodY)
//                     return true;
//             }
//         }
//         return false;
//     }

// }

// abstract class Piece {
//     protected int x, y;
//     protected Color color;
//     private String icon;
//     protected int moves;

//     protected Piece(int x, int y, Color color, String icon) {
//         this.x = x;
//         this.y = y;
//         this.color = color;
//         this.icon = icon;
//     }

//     public boolean moveTo(int x, int y) {
//         if (valid(x, y)) {
//             Game.getInstance().setPiece(this.x, this.y, null);
//             Game.getInstance().setPiece(x, y, this);
//             this.x = x;
//             this.y = y;
//             moves++;
//             return true;
//         }
//         return false;
//     }

//     protected abstract boolean valid(int x2, int y2);

//     protected boolean isOccupied(int x, int y) {
//         Piece piece = Game.getInstance().getPieces()[x][y];
//         return (piece != null) && (piece.getColor() == this.getColor());
//     }

//     protected boolean hasRival(int x, int y) {
//         Piece piece = Game.getInstance().getPieces()[x][y];
//         return (piece != null) && (piece.getColor() != this.getColor());

//     }

//     protected Piece rayHit(int dx, int dy) {
//         for (int i = x, j = y; (i < 8 && i > 0) && (j < 8 && j > 0); i += dx, j += dy)
//             if (Game.getInstance().getPieces()[i][j] != null && Game.getInstance().getPieces()[i][j] != this)
//                 return Game.getInstance().getPieces()[i][j];
//         return null;
//     }

//     protected boolean isWithinRay(int dx, int dy, int x2, int y2) {
//         if (dy * (x2 - x) != dx * (y2 - y))
//             return false;
//         if (rayHit(dx, dy) == null)
//             return true;
//         Piece hit = rayHit(dx, dy);
//         if (x2 == hit.x && y2 == hit.y)
//             return hit.getColor() != color;
//         return (hit.x - x2) * (x - x2) < 0;
//     }

//     @Override
//     public String toString() {
//         return icon + color.toString();
//     }

//     public Color getColor() {
//         return color;
//     }
// }

// class Queen extends Piece {

//     public Queen(int x, int y, Color color) {
//         super(x, y, color, "Q");
//     }

//     @Override
//     protected boolean valid(int x2, int y2) {
//         if (isOccupied(x2, y2))
//             return false;
//         boolean rook = isWithinRay(1, 0, x2, y2) || isWithinRay(0, 1, x2, y2) || isWithinRay(-1, 0, x2, y2)
//                 || isWithinRay(0, -1, x2, y2);
//         boolean Bishop = isWithinRay(1, 1, x2, y2) || isWithinRay(1, -1, x2, y2) || isWithinRay(-1, 1, x2, y2)
//                 || isWithinRay(-1, -1, x2, y2);
//         return rook || Bishop;
//     }

// }

// class Rook extends Piece {

//     public Rook(int x, int y, Color color) {
//         super(x, y, color, "R");
//     }

//     @Override
//     protected boolean valid(int x2, int y2) {
//         if (isOccupied(x2, y2))
//             return false;
//         return isWithinRay(1, 0, x2, y2) || isWithinRay(0, 1, x2, y2) || isWithinRay(-1, 0, x2, y2)
//                 || isWithinRay(0, -1, x2, y2);
//     }

// }

// class User {
//     private static Set<User> users = new HashSet<User>();
//     private String username, password;
//     private Integer score, wins, draws, loses;

//     public User(String username, String password) {
//         this.username = username;
//         this.password = password;
//     }

//     public boolean add() {
//         return users.add(this);
//     }

//     public boolean remove() {
//         return users.remove(this);
//     }

//     public static User logTo(String username, String password) {
//         for (User user : users)
//             if (user.username.equals(username) && user.password.equals(password))
//                 return user;
//         return null;
//     }

//     public static User get(String username) {
//         for (User user : users)
//             if (user.username.equals(username))
//                 return user;
//         return null;
//     }

//     public static void listUsers() {
//         ArrayList<User> userList = new ArrayList<User>();
//         userList.addAll(users);
//         userList.sort((i, j) -> i.username.compareTo(j.username));
//         userList.forEach(i -> App.print(i.username));
//     }

//     public static void scoreBoard() {
//         ArrayList<User> userList = new ArrayList<User>();
//         userList.addAll(users);
//         userList.sort(Comparator.comparing(User::getScore).thenComparing(User::getWins).thenComparing(User::getDraws)
//                 .thenComparing((i, j) -> j.loses - i.loses));
//         userList.forEach(i -> App
//                 .print(i.username + " " + i.getScore() + " " + i.getWins() + " " + i.getDraws() + " " + i.getLoses()));
//     }

//     @Override
//     public int hashCode() {
//         final int prime = 31;
//         int result = 1;
//         result = prime * result + ((username == null) ? 0 : username.hashCode());
//         return result;
//     }

//     @Override
//     public boolean equals(Object obj) {
//         if (this == obj)
//             return true;
//         if (obj == null)
//             return false;
//         if (getClass() != obj.getClass())
//             return false;
//         User other = (User) obj;
//         if (username == null) {
//             if (other.username != null)
//                 return false;
//         } else if (!username.equals(other.username))
//             return false;
//         return true;
//     }

//     public Integer getScore() {
//         return score;
//     }

//     public Integer getWins() {
//         return wins;
//     }

//     public Integer getDraws() {
//         return draws;
//     }

//     public Integer getLoses() {
//         return loses;
//     }

//     public String getUsername() {
//         return username;
//     }
// }