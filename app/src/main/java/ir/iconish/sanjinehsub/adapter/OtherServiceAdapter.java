package ir.iconish.sanjinehsub.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.adapter.viewholder.OtherServiceViewHolder;
import ir.iconish.sanjinehsub.data.model.OtherServiceItem;

/**
 * @author s.shaloudegi
 * @date 8/19/2019
 */
public class OtherServiceAdapter extends RecyclerView.Adapter<OtherServiceViewHolder> {


    RecyclerIemListener recyclerIemListener;
    private List<OtherServiceItem> otherServiceItems;

    public OtherServiceAdapter(List<OtherServiceItem> otherServiceItems, RecyclerIemListener recyclerIemListener) {
        this.otherServiceItems = otherServiceItems;

        this.recyclerIemListener = recyclerIemListener;

    }

    @NonNull
    @Override
    public OtherServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.other_services_item_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new OtherServiceViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final OtherServiceViewHolder viewHolder, final int i) {


        final OtherServiceItem otherServiceItem = otherServiceItems.get(i);
        viewHolder.otherServicesLogo.setImageResource(otherServiceItem.getDrawbleId());
        viewHolder.otherServicesText.setText(otherServiceItem.getTitle());

     /*   viewHolder.navItemIcon.setImageResource(otherServiceItem.getDrawbleId());
        viewHolder.txtTitle.setText(otherServiceItem.getTitle());
*/

        viewHolder.view.setOnClickListener(v -> recyclerIemListener.onTap(otherServiceItem));

    }

    @Override
    public int getItemCount() {
        return (null != otherServiceItems ? otherServiceItems.size() : 0);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
