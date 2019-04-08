package ir.iconish.sanjinehsub.di.component;






import javax.inject.Singleton;
import dagger.Component;
import ir.iconish.sanjinehsub.di.module.AppModule;
import ir.iconish.sanjinehsub.di.module.NetModule;


@Singleton
@Component(modules = {AppModule.class, NetModule.class
})
public interface AppComponent {


}
