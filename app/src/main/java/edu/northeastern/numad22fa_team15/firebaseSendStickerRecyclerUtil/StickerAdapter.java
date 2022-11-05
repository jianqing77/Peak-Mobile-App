package edu.northeastern.numad22fa_team15.firebaseSendStickerRecyclerUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.model.StickerRecord;

public class StickerAdapter extends RecyclerView.Adapter<StickerViewHolder> {

    private List<StickerRecord> results;
    private final Context context;

    public StickerAdapter(@NonNull List<StickerRecord> results, @NonNull Context context) {
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
        StickerRecord stickerRecord = results.get(position);
        holder.itemStickerResourceID.setText(stickerRecord.getStickerName());
        holder.itemStickerImage.setImageResource(stickerRecord.getStickerResourceID());

        // TODO: Open SendStickerConfirmation dialog
        holder.itemView.setOnClickListener(view -> {
            // Open the dialog that confirms sending a sticker to this friend
            openConfirmSendStickerDialog(stickerRecord, holder.itemView);
        });
    }

    private void openConfirmSendStickerDialog(StickerRecord stickerRecord, View view) {
        AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());
        b.setCancelable(false);
        b.setTitle("Confirmation");
        String receiverName = stickerRecord.getReceiverUsername();
        String stickerName = stickerRecord.getStickerName();
        String message = String.format("Are you sure to send sticker %s to user %s?", stickerName, receiverName);
        b.setMessage(message);
        b.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: Implement send sticker operation.
                // +1 to the numOfStickersSent in the database (current user)
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
     * Return the number of stickers in the result list
     * @return the number of stickers in the result list
     */
    @Override
    public int getItemCount() {
        return results.size();
    }

}
