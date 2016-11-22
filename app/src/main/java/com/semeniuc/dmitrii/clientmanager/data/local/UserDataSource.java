package com.semeniuc.dmitrii.clientmanager.data.local;

import com.semeniuc.dmitrii.clientmanager.model.User;

public interface UserDataSource {

        User getUserByEmail(String email);

        User getUserByEmailAndPassword(String email, String password);

        Integer saveGoogleUser(User user);

        Integer saveRegisteredUser(User user);

        Integer setGlobalUserWithEmail(String email);

}
