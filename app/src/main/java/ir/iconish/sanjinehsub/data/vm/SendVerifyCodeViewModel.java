package ir.iconish.sanjinehsub.data.vm;



import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.VerifyCodeOthersResponse;
import ir.iconish.sanjinehsub.data.repository.SendVerifyCodeRepository;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;

public class SendVerifyCodeViewModel extends ViewModel {


    private MutableLiveData<VerifyCodeOthersResponse> apiSuccessLiveDataResponse;
    private MutableLiveData<String> apiErrorLiveData;
    private MutableLiveData<String> apiServerErrorLiveData;
    private MutableLiveData<String> apiClientNetworkErrorLiveData;
    private MutableLiveData<String> apiTimeOutErrorLiveData;
    private MutableLiveData<String> apiAuthFailureErrorLiveData;



    SendVerifyCodeRepository sendVerifyCodeRepository;

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

    public MutableLiveData<VerifyCodeOthersResponse> getApiSuccessLiveDataResponse() {
        return apiSuccessLiveDataResponse;
    }

    @Inject
    public SendVerifyCodeViewModel(SendVerifyCodeRepository sendVerifyCodeRepository)
    {

        this.sendVerifyCodeRepository=sendVerifyCodeRepository;



        apiSuccessLiveDataResponse = new MutableLiveData<VerifyCodeOthersResponse>();
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

    public void callSendVerifyCodeViewModel(String ntcode, String ownermobile) {


      sendVerifyCodeRepository.callSendVerifyCodeRepository(ntcode,ownermobile,new VolleyCallback() {
          @Override
          public void onSuccess(Object obj) {
            apiSuccessLiveDataResponse.setValue((VerifyCodeOthersResponse) obj);

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


}
