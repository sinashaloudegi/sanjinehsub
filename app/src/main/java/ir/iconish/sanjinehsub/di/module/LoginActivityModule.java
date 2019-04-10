package ir.iconish.sanjinehsub.di.module;


import android.content.Context;



import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;

@Module
public class NetModule {



    @Provides
    @Singleton
    public SharedPreferencesManager provideSharedPrefrences(AppController application){
        return new SharedPreferencesManager(application.getSharedPreferences(SharedPreferencesManager.APP_SETTINGS, Context.MODE_PRIVATE));
    }







}
