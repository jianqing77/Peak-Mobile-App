package edu.northeastern.numad22fa_team15.firebaseStickerHistoryRecyclerUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.model.StickerRecord;

public class StickerHistoryAdapter  extends RecyclerView.Adapter<StickerHistoryViewHolder> {

    private List<StickerRecord> results;
    private final Context context;

    public StickerHistoryAdapter(@NonNull List<StickerRecord> results, @NonNull Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public StickerHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sticker_record, parent, false);
        return new StickerHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerHistoryViewHolder holder, int position) {
        // TO DO:
        // Render sticker image based on sticker ID.

        holder.itemStickerID.setText(results.get(position).getStickerResourceID());
        holder.itemSenderUsername.setText(results.get(position).getSenderUsername());
        holder.itemTimestamp.setText(results.get(position).getTimeStamp());
    }

    /**
     * Return the number of sticker records in the result list.
     * @return the number of sticker records in the result list
     */
    @Override
    public int getItemCount() {
        return results.size();
    }
}
