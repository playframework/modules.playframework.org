package models;

public class UserBuilder {

    public User build() {
        User user = new User();
        user.userName = "aUser";
        user.displayName = "aUser";
        user.password = "secret";
        user.accountActive = true;
        return user;
    }

}
