package edu.northeastern.numad22fa_team15.firebaseSendStickerRecyclerUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.model.Sticker;

public class StickerAdapter extends RecyclerView.Adapter<StickerViewHolder> {

    private List<Sticker> results;
    private final Context context;

    public StickerAdapter(@NonNull List<Sticker> results, @NonNull Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_send_sticker_cardview, parent, false);
        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        // TO DO:
        // Render sticker image based on sticker resource ID

        holder.itemStickerResourceID.setText(results.get(position).getStickerResourceID());
    }

    /**
     * Return the number of stickers in the result list
     * @return the number of stickers in the result list
     */
    @Override
    public int getItemCount() {
        return results.size();
    }

}
