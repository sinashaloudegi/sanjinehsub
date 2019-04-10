package com.visit24.therapist.data.vm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.visit24.therapist.data.model.Credit;
import com.visit24.therapist.data.repositpory.CreditRepository;
import com.visit24.therapist.data.source.api.VolleyCallback;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class CreditViewModel extends ViewModel {

    private MutableLiveData<Map<String,List<Credit>> > apiSuccessLiveDataCreditsResponse;
    private MutableLiveData<Integer > apiSuccessLiveDataBalanceResponse;
    private MutableLiveData<String> apiErrorLiveData;
    private MutableLiveData<String> apiServerErrorLiveData;
    private MutableLiveData<String> apiClientNetworkErrorLiveData;
    private MutableLiveData<String> apiTimeOutErrorLiveData;
    private MutableLiveData<String> apiAuthFailureErrorLiveData;
    CreditRepository creditRepository;

    private MutableLiveData<String> apiForbiden403ErrorLiveData;
    private MutableLiveData<String> apiValidation422ErrorLiveData;
    private MutableLiveData<String> api404LiveData;

    public MutableLiveData<String> getApi404LiveData() {
        return api404LiveData;
    }
    public MutableLiveData<String> getApiForbiden403ErrorLiveData() {
        return apiForbiden403ErrorLiveData;
    }

    public MutableLiveData<String> getApiValidation422ErrorLiveData() {
        return apiValidation422ErrorLiveData;
    }


    @Inject
    public CreditViewModel(CreditRepository creditRepository)
    {

        this.creditRepository=creditRepository;


        apiSuccessLiveDataCreditsResponse = new MutableLiveData<>();
        apiSuccessLiveDataBalanceResponse = new MutableLiveData<>();
        apiErrorLiveData = new MutableLiveData<>();
        apiServerErrorLiveData = new MutableLiveData<>();
        apiClientNetworkErrorLiveData = new MutableLiveData<>();
        apiTimeOutErrorLiveData = new MutableLiveData<>();
        apiAuthFailureErrorLiveData = new MutableLiveData<>();
        apiForbiden403ErrorLiveData = new MutableLiveData<>();
        apiValidation422ErrorLiveData = new MutableLiveData<>();
        api404LiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Map<String, List<Credit>>> getApiSuccessLiveDataCreditsResponse() {
        return apiSuccessLiveDataCreditsResponse;
    }

    public MutableLiveData<Integer> getApiSuccessLiveDataBalanceResponse() {
        return apiSuccessLiveDataBalanceResponse;
    }

    public MutableLiveData<String> getApiErrorLiveData() {
        return apiErrorLiveData;
    }

    public MutableLiveData<String> getApiServerErrorLiveData() {
        return apiServerErrorLiveData;
    }

    public MutableLiveData<String> getApiClientNetworkErrorLiveData() {
        return apiClientNetworkErrorLiveData;
    }

    public MutableLiveData<String> getApiTimeOutErrorLiveData() {
        return apiTimeOutErrorLiveData;
    }

    public MutableLiveData<String> getApiAuthFailureErrorLiveData() {
        return apiAuthFailureErrorLiveData;
    }

    public void callRemoteApi() {


      creditRepository.getRemoteCredit(new VolleyCallback() {
          @Override
          public void onSuccess(Object obj) {
              apiSuccessLiveDataBalanceResponse.setValue(creditRepository.getBalance());
              apiSuccessLiveDataCreditsResponse.setValue(creditRepository.getMapCredit());
          }
          @Override
          public void on404(String volleyError) {
              api404LiveData.setValue(volleyError);
          }
          @Override
          public void onFail(String volleyError) {
              apiErrorLiveData.setValue(volleyError);
          }

          @Override
          public void onServerError( ) {
              apiServerErrorLiveData.setValue(null);
          }

          @Override
          public void onClientNetworkError( ) {
              apiClientNetworkErrorLiveData.setValue(null);
          }

          @Override
          public void onTimeOutError() {
              apiTimeOutErrorLiveData.setValue(null);
          }

          @Override
          public void onForbiden403(String volleyError) {
              apiForbiden403ErrorLiveData.setValue(volleyError);
          }

          @Override
          public void onValidationError422(String volleyError) {
              apiValidation422ErrorLiveData.setValue(volleyError);
          }

          @Override
          public void onAuthFailureError401(String volleyError) {
              apiAuthFailureErrorLiveData.setValue(volleyError);
          }

      });
    }
    public String getName(){
        return creditRepository.getName();
    }
    public int getAvailable(){
        return creditRepository.getAvailable();
    }
    public String getProfileImage(){
        return creditRepository.getProfileImage();

    }


    public String getUserId(){
        return creditRepository.getUserId();

    }
    public void   clearLocalData(){
        creditRepository.clearLocalData();
    }


    public String getRole(){
        return creditRepository.getRole();
    }
    public int getNewRequestCount(){
        return creditRepository.getNewRequestCount();
    }
}
