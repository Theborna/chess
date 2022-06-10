import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Game {
    private static Game instance;
    private User WhitePlayer, BlackPlayer;
    private User turn;
    private Piece selectedPiece = null;
    private boolean moved = false;
    private Piece[][] pieces = new Piece[8][8];
    private List<String> killsLog, movesLog;
    private static final int INDEFINITE = 0;
    private int totalMoves;
    private int moves;

    private Game() {
        turn = WhitePlayer;
        initialize();
    }

    private void initialize() {
        moves = 0;
        killsLog = new ArrayList<String>();
        movesLog = new ArrayList<String>();
        for (int i = 0; i < 8; i++) {
            pieces[i][1] = new Pawn(i, 1, Color.WHITE);
            pieces[i][6] = new Pawn(i, 6, Color.BLACK);
        }
        // white pieces
        pieces[0][0] = new Rook(0, 0, Color.WHITE);
        pieces[7][0] = new Rook(7, 0, Color.WHITE);
        pieces[1][0] = new Knight(1, 0, Color.WHITE);
        pieces[6][0] = new Knight(6, 0, Color.WHITE);
        pieces[2][0] = new Bishop(2, 0, Color.WHITE);
        pieces[5][0] = new Bishop(5, 0, Color.WHITE);
        pieces[3][0] = new Queen(3, 0, Color.WHITE);
        pieces[4][0] = new King(4, 0, Color.WHITE);
        // black pieces
        pieces[0][7] = new Rook(0, 7, Color.BLACK);
        pieces[7][7] = new Rook(7, 7, Color.BLACK);
        pieces[1][7] = new Knight(1, 7, Color.BLACK);
        pieces[6][7] = new Knight(6, 7, Color.BLACK);
        pieces[2][7] = new Bishop(2, 7, Color.BLACK);
        pieces[5][7] = new Bishop(5, 7, Color.BLACK);
        pieces[3][7] = new Queen(3, 7, Color.BLACK);
        pieces[4][7] = new King(4, 7, Color.BLACK);
    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    public boolean reachedLimit() {
        return (totalMoves == INDEFINITE) ? false : (moves >= totalMoves);
    }

    public Game setPlayers(User WhitePlayer, User BlackPlayer) {
        this.WhitePlayer = WhitePlayer;
        this.BlackPlayer = BlackPlayer;
        this.turn = WhitePlayer;
        return this;
    }

    public Game setTotalMoves(int totalMoves) {
        this.totalMoves = totalMoves;
        return this;
    }

    public User getWhitePlayer() {
        return WhitePlayer;
    }

    public User getBlackPlayer() {
        return BlackPlayer;
    }

    public Color getTurn() {
        return turn.equals(WhitePlayer) ? Color.WHITE : Color.BLACK;
    }

    public void select(int x, int y) {
        x--;
        y--;
        if (!validSelection(x, y).equals("valid")) {
            App.print(validSelection(x, y));
        } else {
            App.print("selected");
            selectedPiece = pieces[x][y];
        }
    }

    private String validSelection(int x, int y) {
        if (x < 0 || y < 0 || x >= 8 || y >= 8)
            return "wrong coordination";
        if (pieces[x][y] == null)
            return "no piece on this spot";
        if (pieces[x][y].getColor() != getTurn())
            return "you can only select one of your pieces";
        return "valid";
    }

    public void deselect() {
        if (selectedPiece == null) {
            App.print("no piece is selected");
        } else {
            App.print("deselected");
            selectedPiece = null;
        }
    }

    public void showBoard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[j][7 - i] == null)
                    sb.append("  ");
                else
                    sb.append(pieces[j][7 - i].toString());
                sb.append("|");
            }
            sb.append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        App.print(sb);
    }

    public void move(int x, int y) {
        x--;
        y--;
        String move;
        if (!validMovement(x, y).equals("valid")) {
            App.print(validMovement(x, y));
        } else {
            String log;
            Piece last = pieces[x][y];
            System.out.println(last + "...................");
            log = selectedPiece.toString() + " " + selectedPiece.x + "," + selectedPiece.y + " to " + x + "," + y;
            App.print(move = selectedPiece.moveTo(x, y));// moves the piece and outputs the result
            if (move.equals("cannot move to the spot"))
                return;
            if (move.equals("rival piece destroyed"))
                log += " destroyed " + last.toString();
            movesLog.add(log);
            moved = true;
        }
    }

    public String validMovement(int x, int y) {
        if (moved)
            return "already moved";
        if (x < 0 || y < 0 || x >= 8 || y >= 8)
            return "wrong coordination";
        if (selectedPiece == null)
            return " do not have any selected piece";
        return "valid";
    }

    public boolean setPiece(int x, int y, Piece piece) {
        boolean hit = (pieces[x][y] != null) ? true : false;
        if (hit)
            killsLog.add(pieces[x][y].toString() + " killed in spot " + x + "," + y);
        pieces[x][y] = piece;
        return hit;
    }

    public void nextTurn() {
        if (!moved) {
            App.print("you must move then proceed to next turn");
            return;
        }
        moved = false;
        if (turn == WhitePlayer)
            turn = BlackPlayer;
        else
            turn = WhitePlayer;
        App.print("turn completed");
    }

    public void showTurn() {
        App.print("it is player " + turn.getUsername() + " turn with color " + getTurn().name().toLowerCase());
    }

    public void showKills(boolean all) {
        for (String string : killsLog) {
            if (all || ((string.toCharArray()[1] == 'w') == (getTurn() == Color.WHITE)))
                App.print(string);
        }
    }

    public void showMoves(boolean all) {
        for (String string : movesLog) {
            if (all || ((string.toCharArray()[1] == 'w') == (getTurn() == Color.WHITE)))
                App.print(string);
        }
    }

    public Piece[][] getPieces() {
        return pieces;
    }
}
