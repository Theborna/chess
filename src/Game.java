public class Game {
    private static Game instance;
    private User WhitePlayer, BlackPlayer;
    private User turn;
    private Piece selectedPiece;
    private boolean moved;
    private Piece[][] pieces;
    private static final int INDEFINITE = 0;
    private int totalMoves;
    private int moves;
    private Move nextMove;
    private boolean ended;
    private Integer undoWhite, undoBlack;

    private Game() {
        turn = WhitePlayer;
        initialize();
    }

    public void initialize() {
        ended = false;
        moved = false;
        selectedPiece = null;
        Move.clear();
        moves = 0;
        undoBlack = 2;
        undoWhite = 2;
        pieces = new Piece[8][8];
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
        if (!validMovement(x, y).equals("valid")) {
            App.print(validMovement(x, y));
        } else {
            nextMove = new Move(x, y, selectedPiece, pieces[x][y]);
            if (selectedPiece.moveTo(x, y)) {
                if (nextMove.hit)
                    App.print("rival piece destroyed");
                else
                    App.print("moved");
            } else
                App.print("cannot move to the spot");
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

    public void setPiece(int x, int y, Piece piece) {
        if (nextMove.hitKing)
            ended = true;
        pieces[x][y] = piece;
    }

    public void nextTurn() {
        if (ended) {
            App.print("turn completed");
            endGame(turn, getTurn());
            return;
        }
        if (!moved) {
            App.print("you must move then proceed to next turn");
            return;
        }
        moves++;
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
        for (String string : Move.getKills()) {
            if (all || ((string.toCharArray()[1] == 'w') == (getTurn() == Color.WHITE)))
                App.print(string);
        }
    }

    public void showMoves(boolean all) {
        for (String string : Move.getMoves()) {
            if (all || ((string.toCharArray()[1] == 'w') == (getTurn() == Color.WHITE)))
                App.print(string);
        }
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void endGame(User winner, Color color) {
        App.print("player " + winner.getUsername() + " with color " + ((color == Color.WHITE) ? "white" : "black")
                + " won");
        this.initialize();
        App.changeRoot(MainMenu.getInstance());
    }

    public void forfeit() {
        App.print("you have forfeited");
        endGame(turn.equals(WhitePlayer) ? BlackPlayer : WhitePlayer,
                (getTurn() == Color.BLACK) ? Color.WHITE : Color.BLACK);
    }

}
