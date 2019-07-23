package ir.iconish.sanjinehsub.data.source.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.CreditScrore;
import ir.iconish.sanjinehsub.data.model.DetailContract;
import ir.iconish.sanjinehsub.data.model.DetailInstallment;
import ir.iconish.sanjinehsub.data.model.FullContract;
import ir.iconish.sanjinehsub.data.model.FullInstallment;
import ir.iconish.sanjinehsub.data.model.Inquiry;
import ir.iconish.sanjinehsub.data.model.Person;
import ir.iconish.sanjinehsub.data.model.SummaryContract;
import ir.iconish.sanjinehsub.data.model.TerminateContract;
import ir.iconish.sanjinehsub.util.AppConstants;

public class ReportApi {


    AppController
            appController;


    @Inject
    public ReportApi(AppController appController) {
        this.appController = appController;
    }


    @NonNull
    public CreditScrore parseJson(@NonNull JSONObject jsonObject) {
        CreditScrore creditScrore = new CreditScrore();
        Person person = new Person();

        try {
            String placeOfBirth = jsonObject.getString("placeOfBirth");
            JSONObject jsonScore = jsonObject.getJSONObject("scoreDTO");
            creditScrore.setIcsScore(Integer.parseInt(jsonScore.getString("iCSScore")));
            creditScrore.setRiskGrade(jsonScore.getString("riskGrade"));
            creditScrore.setRiskGradeTitle(jsonScore.getString("riskDesc"));
            creditScrore.setScoreRenge(jsonScore.getString("scoreRange"));
            person.setName(jsonScore.getString("firstName"));
            person.setFamily(jsonScore.getString("lastName"));
            creditScrore.setReason(jsonScore.getString("resoneDesc"));
            person.setNtCode(jsonScore.getString("personalCode"));
            person.setBirthCity(placeOfBirth);
            JSONObject jsonObjectReport = jsonObject.getJSONObject("creditXmlObject").getJSONObject("report");

            JSONObject reportsBasicIndividualData = jsonObjectReport.getJSONObject("reportsBasicIndividualData");
            JSONArray reportsSubjectDataCurrentNegativeSubjectStatus = reportsBasicIndividualData.getJSONArray("reportsSubjectDataCurrentNegativeSubjectStatus");

            JSONObject relationsSubjectsPersonalData = reportsBasicIndividualData.getJSONObject("relationsSubjectsPersonalData");
            JSONObject relationsSubjectsBirthData = reportsBasicIndividualData.getJSONObject("relationsSubjectsBirthData");

            person.setFatherName(relationsSubjectsPersonalData.getString("farthersName"));
            person.setBorrowerClass(relationsSubjectsPersonalData.getString("borrowerClassification"));
            person.setGender(relationsSubjectsPersonalData.getString("gender"));
            long birhDay = relationsSubjectsBirthData.getLong("birthDataDateOfBirth");
            int city = relationsSubjectsBirthData.getInt("birthDataPlaceOfBirth");

            person.setBirthDate(String.valueOf(birhDay));
            person.setBirthCity(String.valueOf(city));

            person.setAddress(jsonObjectReport.getJSONObject("reportsSubjectDataAddressesIndividual").getJSONObject("reportsAddressesPermanentAddress").getJSONObject("relationsAddressesAddressTypeChoice").optString("relationsAddressesTextAddress", ""));

            creditScrore.setPerson(person);

            List<SummaryContract> contractList = new ArrayList<>(reportsSubjectDataCurrentNegativeSubjectStatus.length());
            for (int i = 0; i < reportsSubjectDataCurrentNegativeSubjectStatus.length(); i++) {
                JSONObject jsonObjectContract = reportsSubjectDataCurrentNegativeSubjectStatus.getJSONObject(i);
                long reportsLastUpdate = jsonObjectContract.getLong("reportsLastUpdate");
                String negativeSubjectStatus = jsonObjectContract.getString("negativeSubjectStatus");
                String reportscreditor = jsonObjectContract.getString("reportscreditor");
                SummaryContract contract = new SummaryContract();
                contract.setReportsLastUpdate(reportsLastUpdate);
                contract.setNegativeSubjectStatus(negativeSubjectStatus);
                contract.setReportsCreditor(reportscreditor);

                contractList.add(i, contract);


            }

            creditScrore.setContractList(contractList);


            JSONObject jsonReportsSummaryReport = jsonObjectReport.getJSONObject("reportsSummaryReport");

            JSONArray jsonArrayNumberOfInquiriesRecord = jsonReportsSummaryReport.getJSONObject("negativeStatusesAndInquiries").getJSONObject("numberOfInquiries").getJSONArray("numberOfInquiriesRecord");
            List<Inquiry> inquiryList = new ArrayList<>(jsonArrayNumberOfInquiriesRecord.length());
            for (int i = 0; i < jsonArrayNumberOfInquiriesRecord.length(); i++) {
                JSONObject jsonObjectContract = jsonArrayNumberOfInquiriesRecord.getJSONObject(i);
                String lookupsSubscriberType = jsonObjectContract.getString("lookupsSubscriberType");
                String key = jsonObjectContract.getString("key");
                int last1Month = jsonObjectContract.getInt("last1Month");
                int last1Year = jsonObjectContract.getInt("last1Year");
                Inquiry inquiry = new Inquiry();

                inquiry.setKey(key);
                inquiry.setLast1Month(last1Month);
                inquiry.setLast1Year(last1Year);
                inquiry.setLookupsSubscriberType(lookupsSubscriberType);

                inquiryList.add(i, inquiry);


            }

            creditScrore.setInquiryList(inquiryList);


            JSONObject jsonObjectContracts = jsonReportsSummaryReport.getJSONObject("contractsSummary").getJSONObject("contracts");
            JSONObject jsonObjectTotalContracts = jsonObjectContracts.getJSONObject("totalRecord");
            FullContract fullContract = new FullContract();
            JSONArray jsonArrayContractRecord = jsonObjectContracts.getJSONArray("contractRecord");


            List<DetailContract> detailContracts = new ArrayList<>(jsonArrayContractRecord.length());
            for (int i = 0; i < jsonArrayContractRecord.length(); i++) {
                JSONObject jsonObjectDetailContract = jsonArrayContractRecord.getJSONObject(i);
                DetailContract detailContract = new DetailContract();
                detailContract.setCreditorName(jsonObjectDetailContract.getString("productsTypesSubscriberLocalName"));
                detailContract.setCurrency(jsonObjectDetailContract.getString("lookupsCurrencyCodes"));
                detailContract.setTotalOpenConract(jsonObjectDetailContract.getInt("numberOfOpenContracts"));
                detailContract.setTotalTerminateContract(jsonObjectDetailContract.getInt("numberOfTerminatedContracts"));
                detailContract.setContractType(jsonObjectDetailContract.getString("lookupsTypeOfContract"));
                detailContract.setOutstandingAmount(jsonObjectDetailContract.getInt("outstandingAmount"));
                detailContract.setOverdueAmount(jsonObjectDetailContract.getInt("overdueAmount"));

                detailContracts.add(i, detailContract);


            }

            fullContract.setDetailContractList(detailContracts);
            fullContract.setLookupsCurrencyCodes(jsonObjectTotalContracts.getString("lookupsCurrencyCodes"));
            fullContract.setNumberOfOpenContracts(jsonObjectTotalContracts.getInt("numberOfOpenContracts"));
            fullContract.setNumberOfTerminatedContracts(jsonObjectTotalContracts.getInt("numberOfTerminatedContracts"));
            fullContract.setOutstandingAmount(jsonObjectTotalContracts.getInt("outstandingAmount"));
            fullContract.setOverdueAmount(jsonObjectTotalContracts.getInt("overdueAmount"));


            creditScrore.setFullContract(fullContract);


            JSONObject jsonReportsBaseDataOperations = jsonObjectReport.getJSONObject("reportsBaseDataOperations");

            JSONObject jsonInstalments = jsonReportsBaseDataOperations.getJSONObject("reportsContractDataExistingOperationsDebtor").getJSONObject("reportsContractDataInstalments");

            JSONArray jsonArrayInstalments = jsonInstalments.getJSONArray("reportsContractDataInstalment");
            Map<FullInstallment, List<DetailInstallment>> fullInstallmentListMap = new HashMap<>(jsonArrayInstalments.length());
            for (int i = 0; i < jsonArrayInstalments.length(); i++) {
                String lookupsTypeOfFinancingInstalments = jsonArrayInstalments.getJSONObject(i).getJSONObject("relationsContractsInstalmentDetails").getString("lookupsTypeOfFinancingInstalments");
                JSONArray jsonReportsHistoricalCalendarInstalmentRecord = jsonArrayInstalments.getJSONObject(i).getJSONObject("reportsHistoricalCalendarInstalment").getJSONArray("reportsHistoricalCalendarInstalmentRecord");
                List<DetailInstallment> detailInstallments = new ArrayList<>(jsonReportsHistoricalCalendarInstalmentRecord.length());

                for (int j = 0; j < jsonReportsHistoricalCalendarInstalmentRecord.length(); j++) {
                    DetailInstallment detailInstallment = new DetailInstallment();

                    JSONObject jsonHistoricalCalendarInstalmentRecord = jsonReportsHistoricalCalendarInstalmentRecord.getJSONObject(j);

                    JSONObject jsonRelationsAmountsOverdue = jsonHistoricalCalendarInstalmentRecord.optJSONObject("relationsAmountsOverdue");
                    if (null != jsonRelationsAmountsOverdue) {

                        int typesAmount = jsonRelationsAmountsOverdue.getInt("typesAmount");
                        detailInstallment.setTypesAmount(typesAmount);

                    }

                    int reportsHistoricalCalendarMonth = jsonHistoricalCalendarInstalmentRecord.getInt("reportsHistoricalCalendarMonth");
                    int reportsHistoricalCalendarYear = jsonHistoricalCalendarInstalmentRecord.getInt("reportsHistoricalCalendarYear");
                    detailInstallment.setReportsHistoricalCalendarMonth(reportsHistoricalCalendarMonth);
                    detailInstallment.setReportsHistoricalCalendarYear(reportsHistoricalCalendarYear);


                    detailInstallments.add(j, detailInstallment);
                }

                JSONObject jsonReportsContractDataGeneralInformation = jsonArrayInstalments.getJSONObject(i).getJSONObject("reportsContractDataGeneralInformation");
                String lookupsNegativeContractStatus = jsonReportsContractDataGeneralInformation.getString("lookupsNegativeContractStatus");
                String lookupsCurrencyCodes = jsonReportsContractDataGeneralInformation.getString("lookupsCurrencyCodes");
                String lookupsPurposeOfTheCredit = jsonReportsContractDataGeneralInformation.getString("lookupsPurposeOfTheCredit");
                String lookupsRoleOfConnectedSubject = jsonReportsContractDataGeneralInformation.getString("lookupsRoleOfConnectedSubject");
                String reportsContractDataCreditor = jsonReportsContractDataGeneralInformation.getString("reportsContractDataCreditor");
                String lookupsPhaseOfOperation = jsonReportsContractDataGeneralInformation.getString("lookupsPhaseOfOperation");
                long reportsLastUpdate = jsonReportsContractDataGeneralInformation.getLong("reportsLastUpdate");
                long startDate = jsonReportsContractDataGeneralInformation.getJSONObject("relationsContractsDates").getLong("typesContractDatesStart");
                long endDate = jsonReportsContractDataGeneralInformation.getJSONObject("relationsContractsDates").getLong("typesContractDatesExpectedEnd");
                FullInstallment fullInstallment = new FullInstallment();
                fullInstallment.setLookupsCurrencyCodes(lookupsCurrencyCodes);
                fullInstallment.setNegativeContractStatus(lookupsNegativeContractStatus);
                fullInstallment.setPurposeOfTheCredit(lookupsPurposeOfTheCredit);
                fullInstallment.setRoleOfConnectedSubject(lookupsRoleOfConnectedSubject);
                fullInstallment.setReportsContractDataCreditor(reportsContractDataCreditor);
                fullInstallment.setPhaseOfOperation(lookupsPhaseOfOperation);
                fullInstallment.setReportsLastUpdate(reportsLastUpdate);
                fullInstallment.setTypesContractDatesStart(startDate);
                fullInstallment.setTypesContractDatesExpectedEnd(endDate);
                fullInstallment.setLookupsTypeOfFinancingInstalments(lookupsTypeOfFinancingInstalments);
                fullInstallmentListMap.put(fullInstallment, detailInstallments);
            }

            creditScrore.setFullInstallmentListMap(fullInstallmentListMap);


            JSONArray jsonArrayTerminatedOperationsDebtor = jsonReportsBaseDataOperations.getJSONObject("reportsContractDataTerminatedOperationsDebtor").getJSONObject("reportsContractDataInstalments").getJSONArray("reportsContractDataInstalment");


            List<TerminateContract> terminateContracts = new ArrayList<>(jsonArrayTerminatedOperationsDebtor.length());
            for (int i = 0; i < jsonArrayTerminatedOperationsDebtor.length(); i++) {

                TerminateContract terminateContract = new TerminateContract();
                JSONObject jsonReportsContractDataInstalment = jsonArrayTerminatedOperationsDebtor.getJSONObject(i);
                JSONObject jsonRelationsContractsInstalmentDetails = jsonReportsContractDataInstalment.getJSONObject("relationsContractsInstalmentDetails");
                JSONObject jsonReportsContractDataGeneralInformation = jsonReportsContractDataInstalment.getJSONObject("reportsContractDataGeneralInformation");
                terminateContract.setNegativeContractStatus(jsonReportsContractDataGeneralInformation.getString("lookupsNegativeContractStatus"));
                terminateContract.setTypeOfFinancingInstalments(jsonRelationsContractsInstalmentDetails.getString("lookupsTypeOfFinancingInstalments"));
                terminateContract.setPurposeOfTheCredit(jsonReportsContractDataGeneralInformation.getString("lookupsPurposeOfTheCredit"));
                JSONObject jsonRelationsContractsDates = jsonReportsContractDataGeneralInformation.getJSONObject("relationsContractsDates");
                terminateContract.setTypesContractDatesStart(jsonRelationsContractsDates.getLong("typesContractDatesStart"));
                terminateContract.setTypesContractDatesExpectedEnd(jsonRelationsContractsDates.getLong("typesContractDatesExpectedEnd"));
                terminateContract.setTypesContractDatesRealEnd(jsonRelationsContractsDates.getLong("typesContractDatesRealEnd"));
                terminateContract.setCurrency(jsonReportsContractDataGeneralInformation.getString("lookupsCurrencyCodes"));
                terminateContract.setRoleOfConnectedSubject(jsonReportsContractDataGeneralInformation.getString("lookupsRoleOfConnectedSubject"));
                terminateContract.setReportsContractDataCreditor(jsonReportsContractDataGeneralInformation.getString("reportsContractDataCreditor"));
                terminateContract.setReportsContractDataCreditor(jsonReportsContractDataGeneralInformation.getString("reportsContractDataCreditor"));
                JSONArray jsonArrayReportsContractDataCollateral = jsonReportsContractDataInstalment.getJSONObject("reportsContractDataCollaterals").getJSONArray("reportsContractDataCollateral");
                StringBuilder guarantee = new StringBuilder();
                StringBuilder amount = new StringBuilder();
                for (int j = 0; j < jsonArrayReportsContractDataCollateral.length(); j++) {
                    JSONObject jsonObjectReportsContractDataCollateral = jsonArrayReportsContractDataCollateral.getJSONObject(j);
                    guarantee.append(jsonObjectReportsContractDataCollateral.getString("lookupsTypeOfGuarantee") + "-");
                    amount.append(jsonObjectReportsContractDataCollateral.getJSONObject("relationsAmountsGuarantee").getInt("typesAmount") + "-");
                }

                terminateContract.setCollateralType(guarantee.toString());
                terminateContract.setCollateralAmount(amount.toString());

                terminateContract.setRelationsAmountsOutstanding(jsonRelationsContractsInstalmentDetails.getJSONObject("relationsAmountsOutstanding").getInt("typesAmount"));
                terminateContract.setRelationsAmountsOverdue(jsonRelationsContractsInstalmentDetails.getJSONObject("relationsAmountsOutstanding").getInt("typesAmount"));
                terminateContract.setRelationsAmountsTotalCredit(jsonRelationsContractsInstalmentDetails.getJSONObject("relationsAmountsTotalCredit").getInt("typesAmount"));
                terminateContract.setContractNumberOfInstalments(jsonRelationsContractsInstalmentDetails.getInt("typesContractNumberOfInstalments"));
                terminateContract.setMethodOfPayment(jsonRelationsContractsInstalmentDetails.getString("lookupsMethodOfPayment"));
                terminateContract.setPeriodicityOfPayments(jsonRelationsContractsInstalmentDetails.getString("lookupsPeriodicityOfPayments"));
                terminateContract.setTypeOfInstalments(jsonRelationsContractsInstalmentDetails.getString("lookupsTypeOfInstalments"));
                terminateContracts.add(i, terminateContract);
            }


            creditScrore.setTerminateContracts(terminateContracts);


        } catch (JSONException e) {
            Log.e("err", e.toString());
            e.printStackTrace();
        }

        return creditScrore;
      /*  "scoreDTO": {
            "responseStatus": null,
                    "gtoday": null,
                    "servertime": null,
                    "jtoday": null,
                    "token": null,
                    "desc": null,
                    "reqCode": null,
                    "responseStatusCode": null,
                    "validMobile": null,
                    "firstName": "شباب",
                    "lastName": "کوهي",
                    "personalCode": "3720037614",
                    "iCSScore": "576",
                    "riskGrade": "RiskGrade.C1",
                    "scoreDescription": "ScoreDescription.AverageRisk",
                    "riskDesc": "ریسک متوسط",
                    "scoreRange": "560 - 579",
                    "resoneCode": "CIPSReasonCodes.AGE2",
                    "resoneDesc": "گروه سنی جوان",
                    "low": 560,
                    "high": 579,
                    "scoreArchiveId": null
        }*/


    }


    public void callReportsApi(String reqToken, @NonNull final VolleyCallback volleyCallback) {


        String url = ConstantUrl.BASE_CREDITSCORE + ConstantUrl.REPORTS;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", reqToken);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                response -> {


                    //Log.e("Server response",response.toString());

                    if (response != null) {

                        CreditScrore creditScrore = parseJson(response);
                        volleyCallback.onSuccess(creditScrore);

                    }


                }, error -> {
            if ((error instanceof NetworkError) || (error instanceof NoConnectionError)) {

                volleyCallback.onClientNetworkError();


                return;
            }
            if (error instanceof TimeoutError) {

                volleyCallback.onTimeOutError();


                return;
            }


            if ((error instanceof ServerError)) {


                volleyCallback.onServerError();

                return;
            }


        }

        ) {


       /*     @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }*/


        };


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.CLIENT_TIMEOUT,
                1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "reposrtsApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);


    }


}
