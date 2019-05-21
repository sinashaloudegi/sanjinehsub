package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.DetailContractAdapter;
import ir.iconish.sanjinehsub.adapter.InstallmentSection;
import ir.iconish.sanjinehsub.adapter.SummaryContractAdapter;
import ir.iconish.sanjinehsub.adapter.InquiryAdapter;
import ir.iconish.sanjinehsub.adapter.TerminateContractAdapter;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.DetailInstallment;
import ir.iconish.sanjinehsub.data.model.FullContract;
import ir.iconish.sanjinehsub.data.model.FullInstallment;
import ir.iconish.sanjinehsub.data.model.SummaryContract;
import ir.iconish.sanjinehsub.data.model.CreditScrore;
import ir.iconish.sanjinehsub.data.model.Inquiry;
import ir.iconish.sanjinehsub.data.model.Person;
import ir.iconish.sanjinehsub.data.vm.ReportViewModel;
import ir.iconish.sanjinehsub.ui.guage.SpeedometerGauge;
import ir.iconish.sanjinehsub.util.DateHepler;

public class ReportActivity extends AppCompatActivity implements RecyclerIemListener {
@Inject
ReportViewModel reportViewModel;

    @BindView(R.id.speedometer)
    SpeedometerGauge speedometerGauge;

    @BindView(R.id.txtRisk)
    TextView txtRisk;

    @BindView(R.id.txtCreditScoreValue)
    TextView txtCreditScoreValue;

    @BindView(R.id.txtCreditScoreMarkValue)
    TextView txtCreditScoreMarkValue;

    @BindView(R.id.txtCreditScoreRengeValue)
    TextView txtCreditScoreRengeValue;


    @BindView(R.id.txtCreditScoreCauseValue)
    TextView txtCreditScoreCauseValue;




    @BindView(R.id.txtNationalCode)
    TextView txtNationalCode;



    @BindView(R.id.txtName)
    TextView txtName;



    @BindView(R.id.txtFamily)
    TextView txtFamily;


    @BindView(R.id.txtFatheName)
    TextView txtFatheName;


    @BindView(R.id.txtGender)
    TextView txtGender;

    @BindView(R.id.txtBarrowerSection)
    TextView txtBarrowerSection;


    @BindView(R.id.txtBirthCity)
    TextView txtBirthCity;


    @BindView(R.id.txtBirthDate)
    TextView txtBirthDate;


    @BindView(R.id.txtAddress)
    TextView txtAddress;




    @BindView(R.id.txtJamQarardadJariani)
    TextView txtJamQarardadJariani;




    @BindView(R.id.txtJamQaradadKhatemeYafte)
    TextView txtJamQaradadKhatemeYafte;





    @BindView(R.id.txtJamMablaghSarresidNashode)
    TextView txtJamMablaghSarresidNashode;




    @BindView(R.id.txtJamMablaghSarresidShodePardakhtNashode)
    TextView txtJamMablaghSarresidShodePardakhtNashode;





    @BindView(R.id.txtCurrency)
    TextView txtCurrency;




















    @BindView(R.id.recyclerConract)
    RecyclerView recyclerConract;





    @BindView(R.id.recyclerInquiry)
    RecyclerView recyclerInquiry;







    @BindView(R.id.recyclerConractDetail)
    RecyclerView recyclerConractDetail;




    @BindView(R.id.recyclerInstallment)
    RecyclerView recyclerInstallment;





    @BindView(R.id.recyclerTerminateContract)
    RecyclerView recyclerTerminateContract;





    @BindView(R.id.imgExpandTerrminateContract)
ImageView imgExpandTerrminateContract;




    @BindView(R.id.imgExpandInstallment)
ImageView imgExpandInstallment;



    @BindView(R.id.imgExpandContracts)
ImageView imgExpandContracts;



    @BindView(R.id.imgUserExpand)
ImageView imgUserExpand;



    @BindView(R.id.rootLayoutUser)
    LinearLayout rootLayoutUser;



    @BindView(R.id.rootLayoutContract)
    LinearLayout rootLayoutContract;



    @BindView(R.id.rootLayoutInstallment)
    LinearLayout rootLayoutInstallment;




    @BindView(R.id.rootLayoutTerminateContract)
    LinearLayout rootLayoutTerminateContract;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
initGuage();
      ((AppController) getApplication()).getAppComponent().inject(this);
attachViewModel();

String reqToken=getIntent().getStringExtra("reqToken");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerConract.setLayoutManager(mLayoutManager);




        LinearLayoutManager mLayoutManagerInquiry = new LinearLayoutManager(this);
        recyclerInquiry.setLayoutManager(mLayoutManagerInquiry);





        LinearLayoutManager mLayoutManagerContract= new LinearLayoutManager(this);
        recyclerConractDetail.setLayoutManager(mLayoutManagerContract);




        RecyclerView.LayoutManager mLayoutManagerInstallment = new LinearLayoutManager(this);


        recyclerInstallment.setLayoutManager(mLayoutManagerInstallment);




        LinearLayoutManager mLayoutManagerTerminateContract= new LinearLayoutManager(this);
        recyclerTerminateContract.setLayoutManager(mLayoutManagerTerminateContract);




reportViewModel.callReportViewModel(reqToken);




    }


    @OnClick(R.id.layoutTerminateContract)
    public void layoutTerminateAction() {

        expandCollapsView(rootLayoutTerminateContract,imgExpandTerrminateContract);
        }


    @OnClick(R.id.layoutInstallment)
    public void layoutInstallmentAction() {

        expandCollapsView(rootLayoutInstallment,imgExpandInstallment);
        }


    @OnClick(R.id.layoutContracts)
    public void layoutContractsAction() {

        expandCollapsView(rootLayoutContract,imgExpandContracts);
        }


    @OnClick(R.id.layoutPersonTitle)
    public void layoutPersonTitleAction() {

        expandCollapsView(rootLayoutUser,imgUserExpand);
        }






        private void expandCollapsView(View childeView,ImageView imgExpand){

        if(childeView.getVisibility() == View.VISIBLE) {
            childeView.setVisibility(View.GONE);
            imgExpand.setImageResource(R.drawable.ic_expand_more);

        }
        else{
            childeView.setVisibility(View.VISIBLE);
            imgExpand.setImageResource(R.drawable.ic_expand_less);
            }
        }

private void initGuage(){

    speedometerGauge.setLabelConverter(new SpeedometerGauge.LabelConverter() {
        @Override
        public String getLabelFor(double progress, double maxProgress) {
            return String.valueOf((int) Math.round(progress));
        }
    });

    speedometerGauge.setMaxSpeed(900);
    speedometerGauge.setUnitsText("");
    speedometerGauge.setMajorTickStep(100);
    speedometerGauge.setMinorTicks(0);

    speedometerGauge.setSpeed(0,true);

    // Configure value range colors
    speedometerGauge.addColoredRange(0, 459, getResources().getColor(R.color.orange));
    speedometerGauge.addColoredRange(459, 519,  getResources().getColor(R.color.dark_yellow));
    speedometerGauge.addColoredRange(519, 579,  getResources().getColor(R.color.white));
    speedometerGauge.addColoredRange(579, 639,  getResources().getColor(R.color.light_green));
    speedometerGauge.addColoredRange(639, 900,  getResources().getColor(R.color.dark_green));

}

    private void attachViewModel() {
        reportViewModel.getApiSuccessLiveDataResponse().observe(this, creditScrore -> {
setDataOnViews(creditScrore);

/*                   startActivity(new Intent(SplashActivity.this, MainActivity.class));
finish();*/

//if 1010 go to enter pass -- if 1011 go to otp

                }
        );

        reportViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {});

        reportViewModel.getApiErrorLiveData().observe(this, volleyError ->{
            goToFailApiPage("ApiError");

        });
        reportViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

        {
            goToFailApiPage("ServerError");

        });
        reportViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    goToFailApiPage("TimeOutError");
                }

        );
        reportViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");


        });


        reportViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError ->{} );
        reportViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError ->{} );

    }
    private void goToFailApiPage(String failCause){

        Intent intent=new Intent(this,FailApiActivity.class);
        intent.putExtra("failCause",failCause);
        startActivity(intent);
        finish();

    }

private void setDataOnViews(CreditScrore creditScrore){

    speedometerGauge.setSpeed(creditScrore.getIcsScore(),true);
    txtRisk.setText(creditScrore.getRiskGradeTitle());
    txtCreditScoreValue.setText(creditScrore.getRiskGrade());
    txtCreditScoreMarkValue.setText(String.valueOf(creditScrore.getIcsScore()));
    txtCreditScoreRengeValue.setText(creditScrore.getScoreRenge());
    txtCreditScoreCauseValue.setText(creditScrore.getReason());



    /////////////////////////////////////////////
Person person=creditScrore.getPerson();

     txtNationalCode.setText(person.getNtCode());

     txtName.setText(person.getName());

     txtFamily.setText(person.getFamily());

     txtFatheName.setText(person.getFatherName());

     txtGender.setText(person.getGender());
     txtBarrowerSection.setText(person.getBorrowerClass());

     txtBirthCity.setText(person.getBorrowerClass());
    Log.e("date",person.getBirthDate()+"");
     txtBirthDate.setText(DateHepler.convertTimeStampToPersianDate(Long.parseLong(person.getBirthDate())));
     if (!"null".equals(person.getAddress())){
     txtAddress.setText(person.getAddress());}
/////////////////////////////////////////


    List<SummaryContract> contracts=creditScrore.getContractList();



    SummaryContractAdapter contractAdapter=new SummaryContractAdapter(contracts,this);
    recyclerConract.setAdapter(contractAdapter);
    contractAdapter.notifyDataSetChanged();
//////////////////////////////////////////////////////////////////////////////////

    List<Inquiry> inquiryList=creditScrore.getInquiryList();
    InquiryAdapter inquiryAdapter=new InquiryAdapter(inquiryList,this);
    recyclerInquiry.setAdapter(inquiryAdapter);
    inquiryAdapter.notifyDataSetChanged();

//////////////////////////////////////////
    FullContract fullContract=creditScrore.getFullContract();

     txtJamQarardadJariani.setText(String.valueOf(fullContract.getNumberOfOpenContracts()));




     txtJamQaradadKhatemeYafte.setText(String.valueOf(fullContract.getNumberOfTerminatedContracts()));



     txtJamMablaghSarresidNashode.setText(String.valueOf(fullContract.getOutstandingAmount()));


     txtJamMablaghSarresidShodePardakhtNashode.setText(String.valueOf(fullContract.getOverdueAmount()));



     txtCurrency.setText(fullContract.getLookupsCurrencyCodes());

    DetailContractAdapter detailContractAdapter=new DetailContractAdapter(fullContract.getDetailContractList(),this);
    recyclerConractDetail.setAdapter(detailContractAdapter);
    detailContractAdapter.notifyDataSetChanged();
//////////////////////////////////////////////
    Map<FullInstallment,List<DetailInstallment>> fullInstallmentListMap=creditScrore.getFullInstallmentListMap();
     SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();;
    if(fullInstallmentListMap.size()>0){
        int index=1;

        for (Map.Entry<FullInstallment, List<DetailInstallment>> entry : fullInstallmentListMap.entrySet())
        {
            FullInstallment header=entry.getKey();
            InstallmentSection installmentSection=new InstallmentSection(header,index,entry.getValue(),this);
            sectionAdapter.addSection(header.getReportsContractDataCreditor(),installmentSection);
            index++;
        }

        recyclerInstallment.setAdapter(sectionAdapter);

    }
//////////////////////////////////////////////////

    TerminateContractAdapter terminateContractAdapter=new TerminateContractAdapter(creditScrore.getTerminateContracts(),this);
    recyclerTerminateContract.setAdapter(terminateContractAdapter);
    terminateContractAdapter.notifyDataSetChanged();


}

    @Override
    public void onTap(Object obj) {

    }
}
