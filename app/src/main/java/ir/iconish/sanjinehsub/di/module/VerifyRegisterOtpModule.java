package ir.iconish.sanjinehsub.di.module;


import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.VerifyRegisterOtpApi;

@Module
public class VerifyRegisterOtpModule {


    @NonNull
    @Provides
    @Singleton
    public VerifyRegisterOtpApi provideVerifyRegisterOtpApi(AppController appController){
        return new VerifyRegisterOtpApi(appController);
    }







}
