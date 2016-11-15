package com.semeniuc.dmitrii.clientmanager.login;

import com.semeniuc.dmitrii.clientmanager.BasePresenter;

public interface LoginPresenter extends BasePresenter{
    void validateCredentials(String username, String password);

    void onDestroy();
}