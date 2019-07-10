package ir.iconish.sanjinehsub.di.module;


import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.ForgetPasswordApi;

@Module
public class ForgetPasswordActivityModule {


    @NonNull
    @Provides
    @Singleton
    public ForgetPasswordApi provideForgetPasswordApi(AppController appController){
        return new ForgetPasswordApi(appController);
    }







}
