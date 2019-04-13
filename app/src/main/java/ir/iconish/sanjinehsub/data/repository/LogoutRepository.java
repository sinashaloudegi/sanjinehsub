package ir.iconish.sanjinehsub.data.repository;



import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.ResponseCodeEnum;
import ir.iconish.sanjinehsub.data.model.User;
import ir.iconish.sanjinehsub.data.source.api.LoginApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
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
}