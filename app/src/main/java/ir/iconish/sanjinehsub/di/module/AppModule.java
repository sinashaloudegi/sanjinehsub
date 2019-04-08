package ir.iconish.sanjinehsub.di.module;






import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.iconish.sanjinehsub.config.AppController;


@Module
public class  AppModule {
    AppController appController;

    public AppModule(AppController appController) {

        this.appController=appController;
    }



  @Provides
    @Singleton
    AppController providesAppController() {
        return appController;
    }



}

