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
import ir.iconish.sanjinehsub.adapter.viewholder.NewsViewHolder;
import ir.iconish.sanjinehsub.data.model.NewsItem;

/**
 * @author s.shaloudegi
 * @date 8/21/2019
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {


    RecyclerIemListener recyclerIemListener;
    private List<NewsItem> newsItems;

    public NewsAdapter(List<NewsItem> newsItems, RecyclerIemListener recyclerIemListener) {
        this.newsItems = newsItems;

        this.recyclerIemListener = recyclerIemListener;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new NewsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder viewHolder, final int i) {


        final NewsItem newsItem = newsItems.get(i);
        viewHolder.newsImage.setImageResource(newsItem.getDrawbleId());
        viewHolder.newsText.setText(newsItem.getTitle());
        viewHolder.newsDescription.setText(newsItem.getDecribtion());

     /*   viewHolder.navItemIcon.setImageResource(otherServiceItem.getDrawbleId());
        viewHolder.txtTitle.setText(otherServiceItem.getTitle());
*/

        viewHolder.view.setOnClickListener(v -> recyclerIemListener.onTap(newsItem));

    }

    @Override
    public int getItemCount() {
        return (null != newsItems ? newsItems.size() : 0);

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
