package edu.northeastern.numad22fa_team15.firebaseFriendTvRecyclerUtil;

import static edu.northeastern.numad22fa_team15.activities.firebaseActivities.FirebaseFriendListActivity.currentUsername;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.firebaseSendStickerRecyclerUtil.StickerAdapter;
import edu.northeastern.numad22fa_team15.model.Friend;
import edu.northeastern.numad22fa_team15.model.StickerRecord;

public class FriendTvAdapter extends RecyclerView.Adapter<FriendTvViewHolder> {

    public static AlertDialog sendStickerDialog;

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

    /**
     * Open the send sticker dialog that displays all the available stickers to the user.
     * @param receiverUsername the username of the receiver
     * @param view view
     */
    private void openSendStickerDialog(String receiverUsername, View view) {
        AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());
        b.setCancelable(false);
        String title = String.format("Send sticker to: %s", receiverUsername);
        b.setTitle(title);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_send_sticker_w_recycler_view, null);

        // Fill List with StickerRecord objects
        List<StickerRecord> availableStickers = addStickersToList(receiverUsername, view);

        RecyclerView availableStickersRecyclerView = dialogView.findViewById(R.id.available_stickers_recycler_view);
        availableStickersRecyclerView.setLayoutManager(new LinearLayoutManager(dialogView.getContext()));
        availableStickersRecyclerView.setAdapter(new StickerAdapter(availableStickers, dialogView.getContext()));

        b.setView(dialogView);
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        sendStickerDialog = b.create();
        sendStickerDialog.show();
    }

    /**
     * This helper method adds available sticker images in the res/drawable folder to a list.
     * @param view view
     * @return a list of StickerRecord objects
     */
    private List<StickerRecord> addStickersToList(@NonNull String receiverUsername, @NonNull View view) {
        List<StickerRecord> output = new ArrayList<StickerRecord>();
        // XH Note: This is not a very good design. Ideally, this method should loop through all available stickers in the res/drawable folder
        // and create Sticker objects.
        List<String> stickerNames = Arrays.asList("smiling", "smiling_with_hearts", "heart_eyes", "star_struck",
                "winking", "zany", "partying", "shushing", "tired", "frowning");
        for (String stickerName : stickerNames) {
            StickerRecord sticker = createSticker(stickerName, receiverUsername, view);
            output.add(sticker);
        }
        return output;
    }

    /**
     * This helper method creates a StickerRecord object based on the given sticker name.
     * @param stickerName sticker name
     * @param view view
     * @return a sticker
     */
    private StickerRecord createSticker(@NonNull String stickerName, @NonNull String receiverName, View view) {
        String drawablePath = "@drawable/";
        int drawableResourceID = view.getContext().getResources().getIdentifier(drawablePath+stickerName, null, view.getContext().getPackageName());
        StickerRecord sticker = new StickerRecord(drawableResourceID, stickerName, currentUsername, receiverName);
        return sticker;
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
