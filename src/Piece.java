public abstract class Piece {
    protected int x, y;
    protected Color color;
    private String icon;
    protected int moves;

    protected Piece(int x, int y, Color color, String icon) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.icon = icon;
    }

    public String moveTo(int x, int y) {
        if (valid(x, y)) {
            Game.getInstance().setPiece(this.x, this.y, null);
            boolean b = Game.getInstance().setPiece(x, y, this);
            this.x = x;
            this.y = y;
            moves++;
            if (b)
                return "rival piece destroyed";
            return "moved";
        }
        return "cannot move to the spot";
    }

    protected abstract boolean valid(int x2, int y2);

    protected boolean isOccupied(int x, int y) {
        Piece piece = Game.getInstance().getPieces()[x][y];
        return (piece != null) && (piece.getColor() == this.getColor());
    }

    protected boolean hasRival(int x, int y) {
        Piece piece = Game.getInstance().getPieces()[x][y];
        return (piece != null) && (piece.getColor() != this.getColor());

    }

    protected Piece rayHit(int dx, int dy) {
        for (int i = x, j = y; (i < 8 && i > 0) && (j < 8 && j > 0); i += dx, j += dy)
            if (Game.getInstance().getPieces()[i][j] != null && Game.getInstance().getPieces()[i][j] != this)
                return Game.getInstance().getPieces()[i][j];
        return null;
    }

    protected boolean isWithinRay(int dx, int dy, int x2, int y2) {
        if (dy * (x2 - x) != dx * (y2 - y))
            return false;
        if (rayHit(dx, dy) == null)
            return true;
        Piece hit = rayHit(dx, dy);
        if (x2 == hit.x && y2 == hit.y)
            return hit.getColor() != color;
        return (hit.x - x2) * (x - x2) < 0;
    }

    @Override
    public String toString() {
        return icon + color.toString();
    }

    public Color getColor() {
        return color;
    }
}
