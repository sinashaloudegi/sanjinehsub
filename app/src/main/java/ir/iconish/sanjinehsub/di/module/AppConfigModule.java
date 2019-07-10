package ir.iconish.sanjinehsub.di.module;


import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.AppConfigApi;

@Module
public class AppConfigModule {


    @NonNull
    @Provides
    @Singleton
    public AppConfigApi provideAppConfigApi(AppController appController){
        return new AppConfigApi(appController);
    }







}
