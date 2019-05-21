/**
 * com.sarbarg.viseo.adapter is SmsReceiver group of bar adapter for recyclerView.
 */
package ir.iconish.sanjinehsub.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.adapter.viewholder.InquiryViewHolder;
import ir.iconish.sanjinehsub.data.model.Inquiry;


public class InquiryAdapter extends RecyclerView.Adapter<InquiryViewHolder> {

    private  List<Inquiry> inquiryList;

    RecyclerIemListener recyclerIemListener ;

    public InquiryAdapter(List<Inquiry> inquiryList, RecyclerIemListener recyclerIemListener) {
        this.inquiryList = inquiryList;

        this.recyclerIemListener = recyclerIemListener;

    }
    @Override
    public InquiryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inquiry_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new InquiryViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final InquiryViewHolder viewHolder, final int i) {


        final Inquiry inquiry = inquiryList.get(i);
        viewHolder.txtCreditorType.setText(inquiry.getLookupsSubscriberType());
        viewHolder.txtThisMonth.setText(String.valueOf(inquiry.getLast1Month()));
        viewHolder.txtThisYear.setText(String.valueOf(inquiry.getLast1Year()));






viewHolder.view.setOnClickListener(v -> {

recyclerIemListener.onTap(inquiry);
});





    }

    @Override
    public int getItemCount() {
        return (null != inquiryList ? inquiryList.size() : 0);

    }
    @Override public long getItemId(int position) { return position; }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
