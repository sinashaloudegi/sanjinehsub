package ir.iconish.sanjinehsub.data.vm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.User;
import ir.iconish.sanjinehsub.data.repository.LoginRepository;
import ir.iconish.sanjinehsub.data.repository.SplashRepository;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;

public class SplashViewModel extends ViewModel {





    SplashRepository splashRepository;



    @Inject
    public SplashViewModel(SplashRepository splashRepository)
    {

        this.splashRepository=splashRepository;



    }


public long getUserId(){
        return splashRepository.getUserId();
}

}
