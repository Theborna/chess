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
        if (Game.getInstance().reachedLimit()) {
            draw();
            return;
        }
        String input = App.getNextLine();
        if ((matcher = AppRegex.SELECT.getMatcher(input)) != null) {
            select(matcher.group("x"), matcher.group("y"));
        } else if (AppRegex.DESELECT.matches(input)) {
            Game.getInstance().deselect();
        } else if ((matcher = AppRegex.MOVE.getMatcher(input)) != null) {
            move(matcher.group("x"), matcher.group("y"));
        } else if (AppRegex.NEXT_TURN.matches(input)) {
            Game.getInstance().nextTurn();
        } else if (AppRegex.SHOW_TURN.matches(input)) {
            Game.getInstance().showTurn();
        } else if (AppRegex.UNDO.matches(input)) {
            Game.getInstance().undo();
        } else if (AppRegex.UNDO_NUMBER.matches(input)) {
            Game.getInstance().showUndo();
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
            Game.getInstance().forfeit();
        } else {
            App.print("invalid command");
        }
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

    private void draw() {
        App.print("draw");
        Game.getInstance().getBlackPlayer().win(0);
        Game.getInstance().getWhitePlayer().win(0);
        Game.getInstance().getBlackPlayer().score(1);
        Game.getInstance().getWhitePlayer().score(1);
        Game.getInstance().initialize();
        App.changeRoot(MainMenu.getInstance());
    }

    private void move(String x, String y) {
        if (x.length() > 1 || y.length() > 1)
            App.print("wrong coordination");
        else
            Game.getInstance().move(Integer.parseInt(y), Integer.parseInt(x));
    }

    private void select(String x, String y) {
        if (x.length() > 1 || y.length() > 1)
            App.print("wrong coordination");
        else
            Game.getInstance().select(Integer.parseInt(y), Integer.parseInt(x));
    }
}
