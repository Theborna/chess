public enum Color {
    BLACK, WHITE;

    @Override
    public String toString() {
        if (this == WHITE)
            return "w";
        return "b";
    }

}
