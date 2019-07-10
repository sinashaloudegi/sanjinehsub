package ir.iconish.sanjinehsub.di.module;


import android.content.Context;

import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;

@Module
public class NetModule {


    @NonNull
    @Provides
    @Singleton
    public SharedPreferencesManager provideSharedPrefrences(@NonNull AppController application) {
        return new SharedPreferencesManager(application.getSharedPreferences(SharedPreferencesManager.APP_SETTINGS, Context.MODE_PRIVATE));
    }







}
