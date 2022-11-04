package edu.northeastern.numad22fa_team15.firebaseFriendTvRecyclerUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.firebaseSendStickerRecyclerUtil.StickerAdapter;
import edu.northeastern.numad22fa_team15.model.Friend;
import edu.northeastern.numad22fa_team15.model.Sticker;

public class FriendTvAdapter extends RecyclerView.Adapter<FriendTvViewHolder> {

    private List<Friend> results;
    private final Context context;

    public FriendTvAdapter(@NonNull List<Friend> results, @NonNull Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_firebase_friend_tv, parent, false);
        return new FriendTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendTvViewHolder holder, int position) {
        String username = results.get(position).getUsername();
        holder.itemFriend.setText(username);
        // Set an OnClick event on the itemview
        holder.itemView.setOnClickListener(view -> {
            // Open the dialog that sends a sticker to this friend
            openSendStickerDialog(username, holder.itemView);
        });

    }

    private void openSendStickerDialog(String username, View view) {
        AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());
        b.setCancelable(false);
        String title = String.format("Send sticker to: %s", username);
        b.setTitle(title);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_send_sticker_w_recycler_view, null);
        TextView stickerSelected = (TextView) dialogView.findViewById(R.id.sticker_selected_text_view);

        // TO DO:
        // Implement RecyclerView
        List<Sticker> availableStickers = new ArrayList<Sticker>();
        // Fill List with Sticker objects

        RecyclerView availableStickersRecyclerView = dialogView.findViewById(R.id.available_stickers_recycler_view);
        availableStickersRecyclerView.setLayoutManager(new LinearLayoutManager(dialogView.getContext())); // ???
        availableStickersRecyclerView.setAdapter(new StickerAdapter(availableStickers, dialogView.getContext()));

        b.setView(dialogView);
        b.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TO DO:
                // +1 to the numOfStickersSent in the database
                // Friend should receive notification of this sticker
                // Friend's sticker history should reflect that a new sticker is added
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = b.create();
        alertDialog.show();
    }


    /**
     * Return the number of friends in the result list
     * @return the number of friends in the result list
     */
    @Override
    public int getItemCount() {
        return results.size();
    }
}
