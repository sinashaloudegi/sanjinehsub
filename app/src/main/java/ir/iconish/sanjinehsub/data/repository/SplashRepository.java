package ir.iconish.sanjinehsub.data.repository;



import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.User;
import ir.iconish.sanjinehsub.data.source.api.LoginApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;


public class SplashRepository {



SharedPreferencesManager sharedPreferencesManager;

@Inject
    public SplashRepository( SharedPreferencesManager sharedPreferencesManager) {


this.sharedPreferencesManager=sharedPreferencesManager;
    }

   public long getUserId(){
    return sharedPreferencesManager.getUserIdValue();
   }


   public String getUserPassword(){
    return sharedPreferencesManager.getPasswordValue();
   }


}
