import java.util.ArrayList;
import java.util.List;

public class Move {
    private static List<String> moves = new ArrayList<String>();
    private static List<String> kills = new ArrayList<String>();
    private static int justAdded;
    public int x, y;
    public Piece p1, p2;
    public boolean hit, hitKing;

    public Move(int x, int y, Piece p1, Piece p2) {
        justAdded = 0;
        this.x = x;
        this.y = y;
        this.p1 = p1;
        this.p2 = p2;
        hit = p2 != null;
        hitKing = hit && (p2 instanceof King);
        this.logMove();
        if (hit)
            this.logKill();
    }

    public void logMove() {
        justAdded++;
        String move = p1.toString() + " " + (p1.y + 1) + "," + (p1.x + 1) + " to " + (y + 1) + "," + (x + 1);
        if (p2 != null)
            move += " destroyed " + p2.toString();
        moves.add(move);
    }

    public void logKill() {
        if (!hit)
            return;
        justAdded++;
        String kill = p2.toString() + " killed in spot " + (y + 1) + "," + (x + 1);
        kills.add(kill);
    }

    public static List<String> getKills() {
        return kills;
    }

    public static List<String> getMoves() {
        return moves;
    }

    public static void removeLast() {
        if (justAdded > 0)
            moves.remove(moves.size() - 1);
        if (justAdded > 1)
            kills.remove(kills.size() - 1);
    }

    public static void clear() {
        moves.clear();
        kills.clear();
    }
}
