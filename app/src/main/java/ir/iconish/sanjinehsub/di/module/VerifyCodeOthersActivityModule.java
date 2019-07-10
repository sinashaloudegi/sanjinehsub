package ir.iconish.sanjinehsub.di.module;


import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.ConfirmVerifyCodeApi;

@Module
public class VerifyCodeOthersActivityModule {


    @NonNull
    @Provides
    @Singleton
    public ConfirmVerifyCodeApi provideConfirmVerifyCodeApi(AppController appController){
        return new ConfirmVerifyCodeApi(appController);
    }

//    @Provides
//    @Singleton
//    public CheckReportApi provideCheckReportApi(AppController appController){
//        return new CheckReportApi(appController);
//    }
//
//    @Provides
//    @Singleton
//    public BazaarKeyApi provideBazaarKeyApi(AppController appController){
//        return new BazaarKeyApi(appController);
//    }
//


}
