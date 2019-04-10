package ir.iconish.sanjinehsub.di.module;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.LoginApi;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;

@Module
public class LoginActivityModule {



    @Provides
    @Singleton
    public LoginApi provideLoginApi(AppController appController){
        return new LoginApi(appController);
    }







}
