package ir.iconish.sanjinehsub.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.CheckPasswordApi;
import ir.iconish.sanjinehsub.data.source.api.SetPasswordApi;

@Module
public class SetPasswordActivityModule {



    @Provides
    @Singleton
    public SetPasswordApi provideSetPasswordApi(AppController appController){
        return new SetPasswordApi(appController);
    }







}
