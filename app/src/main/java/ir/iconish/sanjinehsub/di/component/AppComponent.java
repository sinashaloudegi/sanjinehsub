package ir.iconish.sanjinehsub.di.component;






import javax.inject.Singleton;
import dagger.Component;
import ir.iconish.sanjinehsub.di.module.AppModule;
import ir.iconish.sanjinehsub.di.module.CheckPasswordActivityModule;
import ir.iconish.sanjinehsub.di.module.ForgetPasswordActivityModule;
import ir.iconish.sanjinehsub.di.module.LoginActivityModule;
import ir.iconish.sanjinehsub.di.module.NetModule;
import ir.iconish.sanjinehsub.di.module.SetPasswordActivityModule;
import ir.iconish.sanjinehsub.di.module.VerifyRegisterOtpModule;
import ir.iconish.sanjinehsub.ui.activity.CheckPasswordActivity;
import ir.iconish.sanjinehsub.ui.activity.ForgetPassworReciverActivity;
import ir.iconish.sanjinehsub.ui.activity.ForgetPasswordActivity;
import ir.iconish.sanjinehsub.ui.activity.LoginActivity;
import ir.iconish.sanjinehsub.ui.activity.SetPasswordActivity;
import ir.iconish.sanjinehsub.ui.activity.SplashActivity;
import ir.iconish.sanjinehsub.ui.activity.VerifyRegisterOtpActivity;


@Singleton
@Component(modules = {AppModule.class, NetModule.class, LoginActivityModule.class, CheckPasswordActivityModule.class, ForgetPasswordActivityModule.class, SetPasswordActivityModule.class,
        VerifyRegisterOtpModule.class
})
public interface AppComponent {

    void inject(LoginActivity activity);
    void inject(SplashActivity activity);
    void inject(CheckPasswordActivity activity);
    void inject(ForgetPasswordActivity activity);
    void inject(ForgetPassworReciverActivity activity);
    void inject(SetPasswordActivity activity);
    void inject(VerifyRegisterOtpActivity activity);

}
