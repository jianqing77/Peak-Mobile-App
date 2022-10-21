package edu.northeastern.numad22fa_team15;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieTvViewHolder extends RecyclerView.ViewHolder {

    public ImageView itemPoster;
    public TextView itemTitle;
    public TextView itemYear;
    public TextView itemID;
    public TextView itemType;

    public MovieTvViewHolder(@NonNull View itemView) {
        super(itemView);

        this.itemPoster = (ImageView) itemView.findViewById(R.id.iv_movieTvImage);
        this.itemTitle = (TextView) itemView.findViewById(R.id.tv_movieTvTitle);
        this.itemYear = (TextView) itemView.findViewById(R.id.tv_setYear);
        this.itemID = (TextView) itemView.findViewById(R.id.tv_setImdbID);
        this.itemType = (TextView) itemView.findViewById(R.id.tv_setType);
    }
}
