package ir.iconish.sanjinehsub.di.module;


import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.ReportApi;

@Module
public class ReportModule {


    @NonNull
    @Provides
    @Singleton
    public ReportApi provideReportApi(AppController appController){
        return new ReportApi(appController);
    }







}
