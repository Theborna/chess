public abstract class Piece {
    protected int x, y, lastX, lastY;
    protected Color color;
    private String icon;
    protected int moves;

    protected Piece(int x, int y, Color color, String icon) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.icon = icon;
    }

    public boolean moveTo(int x, int y) {
        if (valid(x, y)) {
            Game.getInstance().setPiece(this.x, this.y, null);
            Game.getInstance().setPiece(x, y, this);
            lastX = this.x;
            lastY = this.y;
            this.x = x;
            this.y = y;
            moves++;
            return true;
        }
        return false;
    }

    public void goBack() {
        x = lastX;
        y = lastY;
        moves--;
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
        for (int i = x, j = y; (i < 8 && i >= 0) && (j < 8 && j >= 0); i += dx, j += dy)
            if (Game.getInstance().getPieces()[i][j] != null && Game.getInstance().getPieces()[i][j] != this)
                return Game.getInstance().getPieces()[i][j];
        return null;
    }

    protected boolean isWithinRay(int dx, int dy, int x2, int y2) {
        if (dy * (x2 - x) != dx * (y2 - y))
            return false;
        if ((x == 0 && dx < 0) || (y == 0 && dy < 0) || (x == 7 && dx > 0) || (y == 7 && dy > 0))
            return false;
        Piece hit = rayHit(dx, dy);
        if (hit == null)
            return true;
        if (x2 == hit.x && y2 == hit.y)
            return hit.getColor() != color;
        return ((hit.x - x2) * (x - x2) < 0) || ((hit.y - y2) * (y - y2) < 0);
    }

    @Override
    public String toString() {
        return icon + color.toString();
    }

    public Color getColor() {
        return color;
    }
}
