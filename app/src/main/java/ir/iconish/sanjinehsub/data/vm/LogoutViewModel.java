package ir.iconish.sanjinehsub.data.vm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.User;
import ir.iconish.sanjinehsub.data.repository.LoginRepository;
import ir.iconish.sanjinehsub.data.repository.LogoutRepository;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;

public class LogoutViewModel extends ViewModel {




    LogoutRepository logoutRepository;


    @Inject
    public LogoutViewModel(LogoutRepository logoutRepository)
    {

        this.logoutRepository=logoutRepository;



    }



    public void logout(){
        logoutRepository.logout();
    }

}
