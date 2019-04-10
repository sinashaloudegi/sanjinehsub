/**
 * com.sarbarg.viseo.adapter is SmsReceiver group of bar adapter for recyclerView.
 */
package ir.iconish.sanjinehsub.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.adapter.viewholder.NavigationViewHolder;
import ir.iconish.sanjinehsub.data.model.NavigationItem;


public class NavigationAdapter extends RecyclerView.Adapter<NavigationViewHolder> {


    private  List<NavigationItem> navigationItems;

    RecyclerIemListener recyclerIemListener ;

    public NavigationAdapter(List<NavigationItem> navigationItems, RecyclerIemListener recyclerIemListener) {
        this.navigationItems = navigationItems;

        this.recyclerIemListener = recyclerIemListener;

    }
    @Override
    public NavigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nav_item_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new NavigationViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final NavigationViewHolder viewHolder, final int i) {


        final NavigationItem navigationItem = navigationItems.get(i);
        viewHolder.navItemIcon.setImageResource(navigationItem.getDrawbleId());
        viewHolder.txtTitle.setText(navigationItem.getTitle());



viewHolder.view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        recyclerIemListener.onTap(navigationItem);
    }
});

    }

    @Override
    public int getItemCount() {
        return (null != navigationItems ? navigationItems.size() : 0);

    }
    @Override public long getItemId(int position) { return position; }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
