package ir.iconish.sanjinehsub.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.CheckReportApi;
import ir.iconish.sanjinehsub.data.source.api.CreditScorePreProcessApi;

@Module
public class GetScoreActivityModule {



    @Provides
    @Singleton
    public CreditScorePreProcessApi provideGetScoreApi(AppController appController){
        return new CreditScorePreProcessApi(appController);
    }

    @Provides
    @Singleton
    public CheckReportApi provideCheckReportApi(AppController appController){
        return new CheckReportApi(appController);
    }



}
