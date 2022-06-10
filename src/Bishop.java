
public class Bishop extends Piece {

    public Bishop(int x, int y, Color color) {
        super(x, y, color, "B");
    }

    @Override
    protected boolean valid(int x2, int y2) {
        if (isOccupied(x2, y2))
            return false;
        return isWithinRay(1, 1, x2, y2) || isWithinRay(1, -1, x2, y2) || isWithinRay(-1, 1, x2, y2)
                || isWithinRay(-1, -1, x2, y2);
    }

}
