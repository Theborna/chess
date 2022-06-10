import java.util.regex.Matcher;

public class LoginMenu implements Menu {
    private static LoginMenu instance;
    private Matcher matcher;
    private User user;

    private LoginMenu() {
    }

    public static LoginMenu getInstance() {
        if (instance == null)
            instance = new LoginMenu();
        return instance;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void show() {
        String input = App.getNextLine();
        if ((matcher = AppRegex.REGISTER.getMatcher(input)) != null) {
            register(matcher.group("username"), matcher.group("password"));
        } else if ((matcher = AppRegex.LOGIN.getMatcher(input)) != null) {
            login(matcher.group("username"), matcher.group("password"));
        } else if ((matcher = AppRegex.REMOVE.getMatcher(input)) != null) {
            remove(matcher.group("username"), matcher.group("password"));
        } else if (AppRegex.LIST_USERS.matches(input)) {
            User.listUsers();
        } else if (AppRegex.HELP.matches(input)) {
            help();
        } else {
            App.print("invalid command");
        }
    }

    private void help() {
        App.print("register [username] [password]");
        App.print("login [username] [password]");
        App.print("remove [username] [password]");
        App.print("list_users");
        App.print("help");
        App.print("exit");
    }

    private void login(String username, String password) {
        log(username, password, () -> {
            App.print("login successful");
            App.changeRoot(MainMenu.getInstance());
        });
    }

    private void remove(String username, String password) {
        log(username, password, () -> {
            App.print("removed " + username + " successfully");
            User.logTo(username, password).remove();
        });
    }

    private void log(String username, String password, Runnable r) {
        if (!validate(username, password).equals("valid")) {
            App.print(validate(username, password));
        } else if (User.get(username) == null) {
            App.print("no user exists with this username");
        } else if ((user = User.logTo(username, password)) == null) {
            App.print("incorrect password");
        } else {
            r.run();
        }
    }

    private void register(String username, String password) {
        if (!validate(username, password).equals("valid")) {
            App.print(validate(username, password));
        } else if (new User(username, password).add()) {
            App.print("register successful");
        } else
            App.print("a user exists with this username");
    }

    private String validate(String username, String password) {
        if (!username.matches("\\w+"))
            return "username format is invalid";
        if (!password.matches("\\w+"))
            return "password format is invalid";
        return "valid";
    }
}
