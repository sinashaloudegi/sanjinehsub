package ir.iconish.sanjinehsub.adapter.listener;

import ir.iconish.sanjinehsub.data.model.Archive;

public interface ArchiveRecyclerListener {
    void onDownloadTap(Archive archive);

    void onVisitTap(Archive archive);
}
