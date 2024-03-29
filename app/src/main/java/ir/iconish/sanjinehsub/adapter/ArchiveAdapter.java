/**
 * com.sarbarg.viseo.adapter is SmsReceiver group of bar adapter for recyclerView.
 */
package ir.iconish.sanjinehsub.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.listener.ArchiveRecyclerListener;
import ir.iconish.sanjinehsub.adapter.viewholder.ArchiveViewHolder;
import ir.iconish.sanjinehsub.data.model.Archive;
import ir.iconish.sanjinehsub.util.AnimationHelper;


public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveViewHolder> {

    ArchiveRecyclerListener archiveRecyclerListener;
    private List<Archive> archiveList;

    public ArchiveAdapter(List<Archive> archiveList, ArchiveRecyclerListener archiveRecyclerListener) {
        this.archiveList = archiveList;

        this.archiveRecyclerListener = archiveRecyclerListener;

    }

    @NonNull
    @Override
    public ArchiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.archive_item_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new ArchiveViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ArchiveViewHolder viewHolder, final int i) {
        if (i % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
        }


        final Archive archive = archiveList.get(i);
        viewHolder.txtDate.setText(archive.getReportDate());
        viewHolder.txtNum.setText(String.valueOf(i + 1));


        viewHolder.imgDownload.setOnClickListener(v -> {
            AnimationHelper.fadInFadeout(v);
            archiveRecyclerListener.onDownloadTap(archive);

        });


        viewHolder.imgVisit.setOnClickListener(v -> {
            AnimationHelper.fadInFadeout(v);
            archiveRecyclerListener.onVisitTap(archive);
        });


    }

    @Override
    public int getItemCount() {
        return (null != archiveList ? archiveList.size() : 0);

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
