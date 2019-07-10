package ir.iconish.sanjinehsub.di.module;


import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.SetPasswordApi;

@Module
public class SetPasswordActivityModule {


    @NonNull
    @Provides
    @Singleton
    public SetPasswordApi provideSetPasswordApi(AppController appController){
        return new SetPasswordApi(appController);
    }







}
