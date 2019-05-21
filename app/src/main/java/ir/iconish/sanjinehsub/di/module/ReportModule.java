package ir.iconish.sanjinehsub.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.ReportApi;
import ir.iconish.sanjinehsub.data.source.api.VerifyRegisterOtpApi;

@Module
public class ReportModule {



    @Provides
    @Singleton
    public ReportApi provideReportApi(AppController appController){
        return new ReportApi(appController);
    }







}
