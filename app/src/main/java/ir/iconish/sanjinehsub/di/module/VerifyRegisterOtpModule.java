package ir.iconish.sanjinehsub.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.SetPasswordApi;
import ir.iconish.sanjinehsub.data.source.api.VerifyRegisterOtpApi;

@Module
public class VerifyRegisterOtpModule {



    @Provides
    @Singleton
    public VerifyRegisterOtpApi provideVerifyRegisterOtpApi(AppController appController){
        return new VerifyRegisterOtpApi(appController);
    }







}
