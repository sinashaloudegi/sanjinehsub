package ir.iconish.sanjinehsub.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.CheckPasswordApi;
import ir.iconish.sanjinehsub.data.source.api.ForgetPasswordApi;

@Module
public class ForgetPasswordActivityModule {



    @Provides
    @Singleton
    public ForgetPasswordApi provideForgetPasswordApi(AppController appController){
        return new ForgetPasswordApi(appController);
    }







}
