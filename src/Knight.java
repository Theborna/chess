public class Knight extends Piece {

    public Knight(int x, int y, Color color) {
        super(x, y, color, "N");
    }

    @Override
    protected boolean valid(int x2, int y2) {
        if (isOccupied(x2, y2))
            return false;
        if (((x2 - x) * (x2 - x)) + ((y2 - y) * (y2 - y)) == 5)
            return true;
        return false;
    }

}
