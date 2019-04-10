package ir.iconish.sanjinehsub.di.component;






import javax.inject.Singleton;
import dagger.Component;
import ir.iconish.sanjinehsub.di.module.AppModule;
import ir.iconish.sanjinehsub.di.module.LoginActivityModule;
import ir.iconish.sanjinehsub.di.module.NetModule;
import ir.iconish.sanjinehsub.ui.activity.LoginActivity;
import ir.iconish.sanjinehsub.ui.activity.SplashActivity;


@Singleton
@Component(modules = {AppModule.class, NetModule.class, LoginActivityModule.class
})
public interface AppComponent {

    void inject(LoginActivity activity);
    void inject(SplashActivity activity);

}
