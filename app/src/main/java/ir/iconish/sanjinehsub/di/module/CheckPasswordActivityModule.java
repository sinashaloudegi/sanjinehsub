package ir.iconish.sanjinehsub.di.module;


import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.CheckPasswordApi;

@Module
public class CheckPasswordActivityModule {


    @NonNull
    @Provides
    @Singleton
    public CheckPasswordApi provideCheckPasswordApi(AppController appController){
        return new CheckPasswordApi(appController);
    }







}
