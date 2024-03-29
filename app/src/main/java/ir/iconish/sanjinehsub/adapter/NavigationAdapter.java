/**
 * com.sarbarg.viseo.adapter is SmsReceiver group of bar adapter for recyclerView.
 */
package ir.iconish.sanjinehsub.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.adapter.viewholder.NavigationViewHolder;
import ir.iconish.sanjinehsub.data.model.NavigationItem;


public class NavigationAdapter extends RecyclerView.Adapter<NavigationViewHolder> {


    RecyclerIemListener recyclerIemListener;
    private List<NavigationItem> navigationItems;

    public NavigationAdapter(List<NavigationItem> navigationItems, RecyclerIemListener recyclerIemListener) {
        this.navigationItems = navigationItems;

        this.recyclerIemListener = recyclerIemListener;

    }

    @NonNull
    @Override
    public NavigationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nav_item_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new NavigationViewHolder(itemView);

    }

    private static final String TAG = "NavigationAdapter";

    @Override
    public void onBindViewHolder(@NonNull final NavigationViewHolder viewHolder, final int i) {


        final NavigationItem navigationItem = navigationItems.get(i);
        Log.d(TAG, "onBindViewHolder: " + navigationItem.getDrawbleId());
        if (navigationItem.getDrawbleId() != 0) {
            viewHolder.navItemIcon.setImageResource(navigationItem.getDrawbleId());

        } else {
            viewHolder.navItemIcon.setVisibility(View.GONE);
        }
        viewHolder.txtTitle.setText(navigationItem.getTitle());
        viewHolder.view.setOnClickListener(v -> recyclerIemListener.onTap(navigationItem));

    }

    @Override
    public int getItemCount() {
        return (null != navigationItems ? navigationItems.size() : 0);

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
