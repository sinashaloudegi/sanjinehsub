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
import ir.iconish.sanjinehsub.adapter.viewholder.TeachBourseViewHolder;
import ir.iconish.sanjinehsub.data.model.TeachBourseItem;

/**
 * @author s.shaloudegi
 * @date 8/19/2019
 */
public class TeachBourseAdapter extends RecyclerView.Adapter<TeachBourseViewHolder> {


    RecyclerIemListener recyclerIemListener;
    private List<TeachBourseItem> mTeachBourseItems;

    public TeachBourseAdapter(List<TeachBourseItem> teachBourseItems, RecyclerIemListener recyclerIemListener) {
        this.mTeachBourseItems = teachBourseItems;

        this.recyclerIemListener = recyclerIemListener;

    }

    @NonNull
    @Override
    public TeachBourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teach_bourse_item_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new TeachBourseViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final TeachBourseViewHolder viewHolder, final int i) {


        final TeachBourseItem teachBourseItem = mTeachBourseItems.get(i);
        viewHolder.teachBourseLogo.setImageResource(teachBourseItem.getDrawbleId());
        viewHolder.teachBourseText.setText(teachBourseItem.getTitle());

     /*   viewHolder.navItemIcon.setImageResource(otherServiceItem.getDrawbleId());
        viewHolder.txtTitle.setText(otherServiceItem.getTitle());
*/

        viewHolder.view.setOnClickListener(v -> recyclerIemListener.onTap(teachBourseItem));

    }

    @Override
    public int getItemCount() {
        return (null != mTeachBourseItems ? mTeachBourseItems.size() : 0);

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
