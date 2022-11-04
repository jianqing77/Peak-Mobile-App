package edu.northeastern.numad22fa_team15.firebaseFriendTvRecyclerUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.model.Friend;

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
        holder.itemFriend.setText(results.get(position).getUsername());
        // Set an OnClick event on the itemview
        holder.itemView.setOnClickListener(view -> {
            // Open the dialog that sends a sticker to this friend
            openSendStickerDialog(holder.itemView);
        });

    }

    private void openSendStickerDialog(View view) {
        AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());
        b.setCancelable(false);
//        b.setTitle("Add a Friend");

        b.setView(R.layout.dialog_send_sticker_w_recycler_view);
//        if (!matchResults.isEmpty()) {
//            alertDialog.setMessage("Are you sure to ignore the search results and close the activity?");
//        } else {
//            alertDialog.setMessage("Are you sure to close the activity?");
//        }
//        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                finish();
//            }
//        });
//        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
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
