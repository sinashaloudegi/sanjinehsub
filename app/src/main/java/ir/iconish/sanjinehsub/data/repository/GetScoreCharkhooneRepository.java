package ir.iconish.sanjinehsub.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.IrancellMainDTO;
import ir.iconish.sanjinehsub.data.model.IrancellPurchaseDTO;
import ir.iconish.sanjinehsub.data.model.IrancellReportDTO;
import ir.iconish.sanjinehsub.data.model.IrancellSubDTO;
import ir.iconish.sanjinehsub.data.source.api.GetScoreCharkhooneApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;

/**
 * @author s.shaloudegi
 * @date 11/13/2019
 */
public class GetScoreCharkhooneRepository {

    private static final String TAG = "_score";
    IrancellMainDTO irancellMainDTO;
    GetScoreCharkhooneApi getScoreCharkhooneApi;
    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public GetScoreCharkhooneRepository(GetScoreCharkhooneApi getScoreCharkhooneApi, SharedPreferencesManager sharedPreferencesManager) {

        this.getScoreCharkhooneApi = getScoreCharkhooneApi;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    public void callGetScoreCharkhooneRepository(IrancellPurchaseDTO irancellPurchaseDTO, IrancellSubDTO irancellSubDTO, IrancellReportDTO irancellReportDTO, @NonNull final VolleyCallback volleyCallback) {

        Log.d(TAG, "callGetScoreRepository: ____");
        String mobileNumber = sharedPreferencesManager.getMobileNumberValue();
        Crashlytics.setString("MobileNumber", mobileNumber);

        Log.d(TAG, "callGetScoreRepository: MobileNumber: " + mobileNumber);

        String token = sharedPreferencesManager.getTokenValue();


        // TODO: 11/13/2019 add these version code and version name
       /*
        irancellSubDTO.setVersionCode();
        irancellSubDTO.setVersionName();

*/
        irancellMainDTO = new IrancellMainDTO();
        irancellMainDTO.setIrancellReportDTO(irancellReportDTO);
        irancellMainDTO.setIrancellPurchaseDTO(irancellPurchaseDTO);
        irancellMainDTO.setIrancellSubDTO(irancellSubDTO);

        getScoreCharkhooneApi.callGetScoreCharkhooneApi(irancellMainDTO, token, new VolleyCallback() {
            @Override
            public void onSuccess(Object o) {
                volleyCallback.onSuccess(o);

            }

            @Override
            public void onFail(String volleyError) {
                volleyCallback.onFail(volleyError);
            }

            @Override
            public void onServerError() {
                volleyCallback.onServerError();
            }

            @Override
            public void onClientNetworkError() {
                volleyCallback.onClientNetworkError();
            }

            @Override
            public void onTimeOutError() {
                volleyCallback.onTimeOutError();
            }

            @Override
            public void onForbiden403(String volleyError) {
                volleyCallback.onForbiden403(volleyError);
            }

            @Override
            public void on404(String volleyError) {
                volleyCallback.on404(volleyError);
            }

            @Override
            public void onValidationError422(String volleyError) {
                volleyCallback.onValidationError422(volleyError);
            }

            @Override
            public void onAuthFailureError401(String volleyError) {
                volleyCallback.onAuthFailureError401(volleyError);
            }
        });


    }


    @Nullable
    public String getMarketKey() {
        return sharedPreferencesManager.getMarketKeyValue();
    }

}
