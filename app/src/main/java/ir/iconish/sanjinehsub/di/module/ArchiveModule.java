package ir.iconish.sanjinehsub.di.module;


import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.api.ArchiveApi;

@Module
public class ArchiveModule {


    @NonNull
    @Provides
    @Singleton
    public ArchiveApi provideArchiveApi(AppController appController){
        return new ArchiveApi(appController);
    }







}
