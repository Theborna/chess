import java.util.regex.Matcher;

public class MainMenu implements Menu {

    private static MainMenu instance;
    private Matcher matcher;
    private User opponent;

    private MainMenu() {

    }

    public static MainMenu getInstance() {
        if (instance == null)
            instance = new MainMenu();
        return instance;
    }

    @Override
    public void show() {
        String input = App.getNextLine();
        if ((matcher = AppRegex.NEW_GAME.getMatcher(input)) != null) {
            newGame(matcher.group("username"), Integer.parseInt(matcher.group("limit")));
        } else if (AppRegex.SCORE_BOARD.matches(input)) {
            User.scoreBoard();
        } else if (AppRegex.LIST_USERS.matches(input)) {
            User.listUsers();
        } else if (AppRegex.HELP.matches(input)) {
            help();
        } else if (AppRegex.LOGOUT.matches(input)) {
            logout();
        } else {
            App.print("invalid command");
        }
    }

    private void logout() {
        App.print("logout successful");
        App.changeRoot(LoginMenu.getInstance());
    }

    private void help() {
        App.print("new_game [username] [limit]");
        App.print("scoreboard");
        App.print("list_users");
        App.print("help");
        App.print("logout");
    }

    private void newGame(String username, int limit) {
        String validation;
        if (!(validation = validate(username, limit)).equals("valid")) {
            App.print(validation);
        } else {
            App.print("new game started successfully between " + LoginMenu.getInstance().getUser().getUsername()
                    + " and " + username + " with limit " + limit);
            Game.getInstance().setPlayers(LoginMenu.getInstance().getUser(), opponent).setTotalMoves(limit);
            App.changeRoot(GameMenu.getInstance());
        }
    }

    private String validate(String username, int limit) {
        if (!username.matches("\\w+"))
            return "invalid username format";
        if (limit < 0)
            return "number should be positive to have a limit or 0 for no limit";
        if (username.equals(LoginMenu.getInstance().getUser().getUsername()))
            return "you must choose another player to start a game";
        if ((opponent = User.get(username)) == null) {
            return "no user exists with this username";
        }
        return "valid";
    }
}
