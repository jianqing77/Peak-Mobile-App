package edu.northeastern.numad22fa_team15.firebaseStickerHistoryRecyclerUtil;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numad22fa_team15.R;

public class StickerHistoryViewHolder extends RecyclerView.ViewHolder {

    public ImageView itemStickerImage;
    public TextView itemStickerID;
    public TextView itemSenderUsername;
    public TextView itemTimestamp;

    public StickerHistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        itemStickerImage = itemView.findViewById(R.id.iv_sticker_history);
        itemStickerID = itemView.findViewById(R.id.tv_edit_resourceID);
        itemSenderUsername = itemView.findViewById(R.id.tv_edit_sentBy);
        itemTimestamp = itemView.findViewById(R.id.tv_edit_sentOn);
    }

}
