package ir.iconish.sanjinehsub.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.SendVerifyCodeApi;

@Module
public class GetScoreOthersActivityModule {



    @Provides
    @Singleton
    public SendVerifyCodeApi provideSendVerifyCodeApi(AppController appController){
        return new SendVerifyCodeApi(appController);
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
