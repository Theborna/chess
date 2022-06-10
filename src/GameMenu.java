import java.util.regex.Matcher;

public class GameMenu implements Menu {

    private static GameMenu instance;
    private Matcher matcher;

    private GameMenu() {
    }

    public static GameMenu getInstance() {
        if (instance == null)
            instance = new GameMenu();
        return instance;
    }

    @Override
    public void show() {
        String input = App.getNextLine();
        if ((matcher = AppRegex.SELECT.getMatcher(input)) != null) {
            Game.getInstance().select(Integer.parseInt(matcher.group("y")), Integer.parseInt(matcher.group("x")));
        } else if (AppRegex.DESELECT.matches(input)) {
            Game.getInstance().deselect();
        } else if ((matcher = AppRegex.MOVE.getMatcher(input)) != null) {
            Game.getInstance().move(Integer.parseInt(matcher.group("y")), Integer.parseInt(matcher.group("x")));
        } else if (AppRegex.NEXT_TURN.matches(input)) {
            Game.getInstance().nextTurn();
        } else if (AppRegex.SHOW_TURN.matches(input)) {
            Game.getInstance().showTurn();
        } else if (AppRegex.UNDO.matches(input)) {
            undo();
        } else if (AppRegex.UNDO_NUMBER.matches(input)) {
            undoNumber();
        } else if (AppRegex.SHOW_MOVES.matches(input)) {
            Game.getInstance().showMoves(false);
        } else if (AppRegex.SHOW_MOVES_ALL.matches(input)) {
            Game.getInstance().showMoves(true);
        } else if (AppRegex.SHOW_KILLED.matches(input)) {
            Game.getInstance().showKills(false);
        } else if (AppRegex.SHOW_KILLED_ALL.matches(input)) {
            Game.getInstance().showKills(true);
        } else if (AppRegex.SHOW_BOARD.matches(input)) {
            Game.getInstance().showBoard();
        } else if (AppRegex.HELP.matches(input)) {
            help();
        } else if (AppRegex.FORFEIT.matches(input)) {
            forfeit();
        } else {
            App.print("invalid command");
        }
    }

    private void forfeit() {
    }

    private void help() {
        App.print("select [x],[y]");
        App.print("deselect");
        App.print("move [x],[y]");
        App.print("next_turn");
        App.print("show_turn");
        App.print("undo");
        App.print("undo_number");
        App.print("show_moves [-all]");
        App.print("show_killed [-all]");
        App.print("show_board");
        App.print("help");
        App.print("forfeit");
    }

    private void undoNumber() {
    }

    private void undo() {
    }

}
