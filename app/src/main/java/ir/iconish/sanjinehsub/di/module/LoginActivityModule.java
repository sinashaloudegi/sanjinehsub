package ir.iconish.sanjinehsub.di.module;


import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.LoginApi;

@Module
public class LoginActivityModule {


    @NonNull
    @Provides
    @Singleton
    public LoginApi provideLoginApi(AppController appController){
        return new LoginApi(appController);
    }







}
