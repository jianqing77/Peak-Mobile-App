package edu.northeastern.numad22fa_team15.firebaseStickerHistoryRecyclerUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.firebaseSendStickerRecyclerUtil.StickerAdapter;
import edu.northeastern.numad22fa_team15.model.StickerRecord;

public class StickerHistoryAdapter  extends RecyclerView.Adapter<StickerHistoryViewHolder> {

    private static final String TAG = "StickerHistoryAdapter_________";

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
        // Consider the edge case that different versions of the app may contain different stickers.
        try {
            holder.itemStickerImage.setImageResource(results.get(position).getStickerResourceID());
        } catch (Exception e) {
            // If setImageResource operation fails, set the image to a "sticker not available" image
            Log.v(TAG, "setImageResource operation failed. ");
            holder.itemStickerImage.setImageResource(R.mipmap.ic_launcher);
            holder.itemStickerImage.setOnClickListener(view -> {
                // Open the dialog that tells user the potential reason why a sticker is not available
                openUnavailableStickerDialog(holder.itemView);
            });
        }

        holder.itemStickerID.setText(String.valueOf(results.get(position).getStickerResourceID()));
        holder.itemSenderUsername.setText(results.get(position).getSender());
        // Convert timestamp long value to Date to String
        long timestampLong = results.get(position).getTimestamp();
        String timestamp = new Date(timestampLong).toString();
        holder.itemTimestamp.setText(timestamp);
    }

    /**
     * Open a dialog that notifies the user the potential reason of why the sticker is unavailable.
     * @param view view
     */
    private void openUnavailableStickerDialog(View view) {
        AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());
        b.setCancelable(false);
        b.setTitle("Unavailable Sticker");
        String message = "Potential reason:\nYou may not have the latest version of app. " +
                "Consider updating your app. Thank you.";
        b.setMessage(message);
        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = b.create();
        alertDialog.show();
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
