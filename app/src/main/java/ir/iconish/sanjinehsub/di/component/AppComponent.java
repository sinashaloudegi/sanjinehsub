package ir.iconish.sanjinehsub.di.component;






import javax.inject.Singleton;

import dagger.Component;
import ir.iconish.sanjinehsub.di.module.AppConfigModule;
import ir.iconish.sanjinehsub.di.module.AppModule;
import ir.iconish.sanjinehsub.di.module.ArchiveModule;
import ir.iconish.sanjinehsub.di.module.CheckPasswordActivityModule;
import ir.iconish.sanjinehsub.di.module.ForgetPasswordActivityModule;
import ir.iconish.sanjinehsub.di.module.GetScoreActivityModule;
import ir.iconish.sanjinehsub.di.module.LoginActivityModule;
import ir.iconish.sanjinehsub.di.module.NetModule;
import ir.iconish.sanjinehsub.di.module.ReportModule;
import ir.iconish.sanjinehsub.di.module.SetPasswordActivityModule;
import ir.iconish.sanjinehsub.di.module.VerifyRegisterOtpModule;
import ir.iconish.sanjinehsub.ui.activity.ArchiveActivity;
import ir.iconish.sanjinehsub.ui.activity.CheckPasswordActivity;
import ir.iconish.sanjinehsub.ui.activity.ForgetPassworReciverActivity;
import ir.iconish.sanjinehsub.ui.activity.ForgetPasswordActivity;
import ir.iconish.sanjinehsub.ui.activity.GetScoreActivity;
import ir.iconish.sanjinehsub.ui.activity.GetScoreOthersActivity;
import ir.iconish.sanjinehsub.ui.activity.LoginActivity;
import ir.iconish.sanjinehsub.ui.activity.MainActivity;
import ir.iconish.sanjinehsub.ui.activity.ReportActivity;
import ir.iconish.sanjinehsub.ui.activity.SetPasswordActivity;
import ir.iconish.sanjinehsub.ui.activity.SplashActivity;
import ir.iconish.sanjinehsub.ui.activity.VerifyCodeOthersActivity;
import ir.iconish.sanjinehsub.ui.activity.VerifyRegisterOtpActivity;


@Singleton
@Component(modules = {AppModule.class, NetModule.class, LoginActivityModule.class, GetScoreActivityModule.class, GetScoreActivityModule.class, CheckPasswordActivityModule.class, ForgetPasswordActivityModule.class, SetPasswordActivityModule.class,
        VerifyRegisterOtpModule.class, AppConfigModule.class, ArchiveModule.class, ReportModule.class
})
public interface AppComponent {

    void inject(LoginActivity activity);
    void inject(SplashActivity activity);
    void inject(GetScoreActivity activity);
    void inject(CheckPasswordActivity activity);
    void inject(ForgetPasswordActivity activity);
    void inject(ForgetPassworReciverActivity activity);
    void inject(SetPasswordActivity activity);
    void inject(VerifyRegisterOtpActivity activity);
    void inject(MainActivity activity);
    void inject(GetScoreOthersActivity activity);
    void inject(VerifyCodeOthersActivity activity);
    void inject(ArchiveActivity activity);
    void inject(ReportActivity activity);


}
