package ir.iconish.sanjinehsub.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.ArchiveApi;
import ir.iconish.sanjinehsub.data.source.api.VerifyRegisterOtpApi;

@Module
public class ArchiveModule {



    @Provides
    @Singleton
    public ArchiveApi provideArchiveApi(AppController appController){
        return new ArchiveApi(appController);
    }







}
