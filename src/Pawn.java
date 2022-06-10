public class Pawn extends Piece {

    public Pawn(int x, int y, Color color) {
        super(x, y, color, "P");
    }

    @Override
    protected boolean valid(int x2, int y2) {
        if (isOccupied(x2, y2))
            return false;
        boolean goodY = y2 == (color.equals(Color.WHITE) ? (y + 1) : (y - 1));
        if (hasRival(x2, y2)) {
            if (goodY)
                if (x2 == x - 1 || x2 == x + 1)
                    return true;
        } else {
            if (x2 == x) {
                if (moves == 0)
                    if (y2 == (color.equals(Color.WHITE) ? (y + 2) : (y - 2)))
                        return true;
                if (goodY)
                    return true;
            }
        }
        return false;
    }


}
