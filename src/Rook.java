public class Rook extends Piece {

    public Rook(int x, int y, Color color) {
        super(x, y, color, "R");
    }

    @Override
    protected boolean valid(int x2, int y2) {
        if (isOccupied(x2, y2))
            return false;
        return isWithinRay(1, 0, x2, y2) || isWithinRay(0, 1, x2, y2) || isWithinRay(-1, 0, x2, y2)
                || isWithinRay(0, -1, x2, y2);
    }

}
