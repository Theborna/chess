import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.jws.soap.SOAPBinding.Use;

public class User {
    private static Set<User> users = new HashSet<User>();
    private String username, password;
    private int score, wins, draws, loses;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void score(int score) {
        this.score += score;
    }

    public void win(int wins) {
        if (wins < 0)
            loses++;
        else if (wins == 0)
            draws++;
        else
            this.wins++;
    }

    public boolean add() {
        return users.add(this);
    }

    public boolean remove() {
        return users.remove(this);
    }

    public static User logTo(String username, String password) {
        for (User user : users)
            if (user.username.equals(username) && user.password.equals(password))
                return user;
        return null;
    }

    public static User get(String username) {
        for (User user : users)
            if (user.username.equals(username))
                return user;
        return null;
    }

    public static void listUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        userList.addAll(users);
        userList.sort((i, j) -> i.username.compareTo(j.username));
        userList.forEach(i -> App.print(i.username));
    }

    public static void scoreBoard() {
        ArrayList<User> userList = new ArrayList<User>();
        userList.addAll(users);
        userList.sort(Comparator.comparing(User::getScore).thenComparing(User::getWins).thenComparing(User::getDraws)
                .thenComparing(Comparator.comparing(User::getLoses).reversed())
                .thenComparing(Comparator.comparing(User::getUsername).reversed()));
        Collections.reverse(userList);
        userList.forEach(i -> App
                .print(i.username + " " + i.getScore() + " " + i.getWins() + " " + i.getDraws() + " " + i.getLoses()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getWins() {
        return wins;
    }

    public Integer getDraws() {
        return draws;
    }

    public Integer getLoses() {
        return loses;
    }

    public String getUsername() {
        return username;
    }
}