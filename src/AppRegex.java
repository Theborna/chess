import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AppRegex { // all in-line inputs possible

    number("[-+]?[0-9]\\d*(\\.\\d+)?"),
    REGISTER("^register (?<username>[^ ]+) (?<password>[^ ]+)$"),
    LOGIN("^login (?<username>[^ ]+) (?<password>[^ ]+)$"),
    REMOVE("^remove (?<username>[^ ]+) (?<password>[^ ]+)$"), LIST_USERS("list_users"), HELP("help"),
    NEW_GAME("^new_game (?<username>[^ ]+) (?<limit>" + number + ")$"), SCORE_BOARD("scoreboard"), LOGOUT("logout"),
    SELECT("select (?<x>" + number + "),(?<y>" + number + ")"), DESELECT("deselect"),
    MOVE("move (?<x>" + number + "),(?<y>" + number + ")"),
    NEXT_TURN("next_turn"), SHOW_TURN("show_turn"), UNDO("undo"), UNDO_NUMBER("undo_number"),
    SHOW_MOVES("show_moves"), SHOW_MOVES_ALL("show_moves -all"), SHOW_KILLED("show_killed"),
    SHOW_KILLED_ALL("show_killed -all"), SHOW_BOARD("show_board"), FORFEIT("forfeit");

    String regex;

    AppRegex(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }

    public boolean matches(String input) {
        return input.matches(regex);
    }

    @Override
    public String toString() {
        return regex;
    }

}
