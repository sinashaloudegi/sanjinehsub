package ir.iconish.sanjinehsub.data.vm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.RegisterPurchaseInfoResultDto;
import ir.iconish.sanjinehsub.data.repository.GetScoreRepository;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.util.Purchase;

public class GetScoreViewModel extends ViewModel {


    private MutableLiveData<RegisterPurchaseInfoResultDto> apiSuccessLiveDataResponse;
    private MutableLiveData<String> apiErrorLiveData;
    private MutableLiveData<String> apiServerErrorLiveData;
    private MutableLiveData<String> apiClientNetworkErrorLiveData;
    private MutableLiveData<String> apiTimeOutErrorLiveData;
    private MutableLiveData<String> apiAuthFailureErrorLiveData;



    GetScoreRepository getScoreRepository;

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

    public MutableLiveData<RegisterPurchaseInfoResultDto> getApiSuccessLiveDataResponse() {
        return apiSuccessLiveDataResponse;
    }

    @Inject
    public GetScoreViewModel(GetScoreRepository getScoreRepository)
    {

        this.getScoreRepository=getScoreRepository;



        apiSuccessLiveDataResponse = new MutableLiveData<>();
        apiErrorLiveData = new MutableLiveData<>();
        apiServerErrorLiveData = new MutableLiveData<>();
        apiClientNetworkErrorLiveData = new MutableLiveData<>();
        apiTimeOutErrorLiveData = new MutableLiveData<>();
        apiAuthFailureErrorLiveData = new MutableLiveData<>();
        apiForbiden403ErrorLiveData = new MutableLiveData<>();
        apiValidation422ErrorLiveData = new MutableLiveData<>();
        api404LiveData = new MutableLiveData<>();
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

    public void callGetScoreViewModel(Purchase purchase, String othersmsisdn,String ntcode) {


      getScoreRepository.callGetScoreRepository(purchase,othersmsisdn,ntcode,new VolleyCallback() {
          @Override
          public void onSuccess(Object obj) {
            apiSuccessLiveDataResponse.setValue((RegisterPurchaseInfoResultDto) obj);

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

    public String getMarketKey(){
        return getScoreRepository.getMarketKey();
    }


}
