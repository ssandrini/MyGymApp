package ar.edu.itba.mygymapp.backend.store;

import ar.edu.itba.mygymapp.backend.models.User;

public class UserStore {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserStore.user = user;
    }
}
