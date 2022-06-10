public class Queen extends Piece {

    public Queen(int x, int y, Color color) {
        super(x, y, color, "Q");
    }

    @Override
    protected boolean valid(int x2, int y2) {
        if (isOccupied(x2, y2))
            return false;
        boolean rook = isWithinRay(1, 0, x2, y2) || isWithinRay(0, 1, x2, y2) || isWithinRay(-1, 0, x2, y2)
                || isWithinRay(0, -1, x2, y2);
        boolean Bishop = isWithinRay(1, 1, x2, y2) || isWithinRay(1, -1, x2, y2) || isWithinRay(-1, 1, x2, y2)
                || isWithinRay(-1, -1, x2, y2);
        return rook || Bishop;
    }

}
