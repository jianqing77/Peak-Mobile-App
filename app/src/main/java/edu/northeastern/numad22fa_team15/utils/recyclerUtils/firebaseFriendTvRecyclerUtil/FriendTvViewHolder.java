package edu.northeastern.numad22fa_team15.utils.recyclerUtils.firebaseFriendTvRecyclerUtil;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numad22fa_team15.R;

public class FriendTvViewHolder extends RecyclerView.ViewHolder {

    public TextView itemFriend;

    public FriendTvViewHolder(@NonNull View itemView) {
        super(itemView);

        itemFriend = itemView.findViewById(R.id.friend_nameTV);
    }

}
