package edu.northeastern.numad22fa_team15.firebaseSendStickerRecyclerUtil;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numad22fa_team15.R;

public class StickerViewHolder extends RecyclerView.ViewHolder {

    public ImageView itemStickerImage;
    public TextView itemStickerResourceID;

    public StickerViewHolder(@NonNull View itemView) {
        super(itemView);

        itemStickerImage = itemView.findViewById(R.id.iv_sticker);
        itemStickerResourceID = itemView.findViewById(R.id.tv_resourceId);
    }

}
