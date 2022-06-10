public class King extends Piece {

    public King(int x, int y, Color color) {
        super(x, y, color, "K");
    }

    @Override
    protected boolean valid(int x2, int y2) {
        if (isOccupied(x2, y2))
            return false;
        if (Math.abs(x - x2) <= 1 && Math.abs(y - y2) <= 1)
            return true;
        return false;
    }

}
