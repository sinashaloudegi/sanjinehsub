package ir.iconish.sanjinehsub.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.AppConfigApi;
import ir.iconish.sanjinehsub.data.source.api.VerifyRegisterOtpApi;

@Module
public class AppConfigModule {



    @Provides
    @Singleton
    public AppConfigApi provideAppConfigApi(AppController appController){
        return new AppConfigApi(appController);
    }







}
