package ir.iconish.sanjinehsub.data.repository;


import androidx.annotation.Nullable;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;


public class LogoutRepository {


    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public LogoutRepository(SharedPreferencesManager sharedPreferencesManager) {


        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    public void logout() {
        sharedPreferencesManager.clearAll();
    }

    @Nullable
    public String getToken() {
        return sharedPreferencesManager.getTokenValue();
    }

}