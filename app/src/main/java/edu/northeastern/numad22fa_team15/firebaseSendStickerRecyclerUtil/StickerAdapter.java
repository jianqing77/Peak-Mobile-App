package edu.northeastern.numad22fa_team15.firebaseSendStickerRecyclerUtil;

import static edu.northeastern.numad22fa_team15.utils.commonUtils.displayMessageInSnackbar;
import static edu.northeastern.numad22fa_team15.utils.firebaseUtils.checkUserExistenceInFirebase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.activities.firebaseActivities.FirebaseFriendListActivity;
import edu.northeastern.numad22fa_team15.model.StickerRecord;

public class StickerAdapter extends RecyclerView.Adapter<StickerViewHolder> {

    private static final String TAG = "StickerAdapter_________";

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
        String receiverName = stickerRecord.getReceiver();
        String stickerName = stickerRecord.getStickerName();
        String message = String.format("Are you sure to send sticker %s to user %s?", stickerName, receiverName);
        b.setMessage(message);
        b.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Get current timestamp
                Date currentTime = Calendar.getInstance().getTime();
                long timestampLong = currentTime.getTime();
                stickerRecord.setTimestamp(timestampLong);
                performSendStickerOperation(stickerRecord);
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

    private void performSendStickerOperation(StickerRecord stickerRecord) {
        // TODO: Implement send sticker operation.
        // +1 to the numOfStickersReceived in the database (sticker receiver)
        // Set the updated numOfStickersReceived value as the stickerID of the StickerRecord
        String receiverName = stickerRecord.getReceiver();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference friendDatabaseReference = firebaseDatabase.getReference().child("users").child(receiverName);
        DatabaseReference friendNumOfStickersReceivedReference = friendDatabaseReference.child("numOfStickersReceived");
        DatabaseReference friendStickerRecordsReference = friendDatabaseReference.child("stickerRecords");

        String senderName = stickerRecord.getSender();
        DatabaseReference userDatabaseReference = firebaseDatabase.getReference().child("users").child(senderName);
        DatabaseReference userNumOfStickersSentDataReference = userDatabaseReference.child("numOfStickersSent");

        // Task 1: Retrieve numOfStickersReceived from friend user
        Task<DataSnapshot> task1 = friendNumOfStickersReceivedReference.get();

        task1.addOnCompleteListener(task -> {
            if (!task1.isSuccessful()) {
                Log.v(TAG, "Send sticker feature is currently unavailable.");
                Log.v(TAG, "Failed to retrieve numOfStickersReceived info from firebase.");

            } else {
                int numOfStickersReceived = (int) (long) task1.getResult().getValue();
                String message = String.format("Friend %s. Number of Stickers Received so Far: %d", receiverName, numOfStickersReceived);
                Log.v(TAG, message);
                int stickerID = numOfStickersReceived + 1;
                stickerRecord.setStickerID(stickerID);

                // Task 2: Update friendStickerRecords: Add new StickerRecord
                Task<Void> task2 = friendStickerRecordsReference.child(String.valueOf(stickerID)).setValue(stickerRecord);

                task2.addOnCompleteListener(anotherTask -> {
                    if (!task2.isSuccessful()) {
                        Log.v(TAG, "Send sticker feature is currently unavailable.");
                        Log.v(TAG, "Failed to add the StickerRecord object to stickerRecords");
                    } else {
                        // Task 3 : Update numOfStickersSent/Received values for sender and receiver
                        Task<DataSnapshot> task3 = userNumOfStickersSentDataReference.get();

                        task3.addOnCompleteListener(finalTask -> {
                           if (!task3.isSuccessful()) {
                               friendStickerRecordsReference.child(String.valueOf(stickerID)).removeValue();
                               Log.v(TAG, "Send sticker feature is currently unavailable.");
                               Log.v(TAG, "Failed to retrieve numOfStickersSent info from firebase.");
                           } else {
                               int numOfStickerSent = (int) (long) task3.getResult().getValue();
                               // Update numOfStickersSent value for current user.
                               userNumOfStickersSentDataReference.setValue(numOfStickerSent+1);
                               // Update numOfStickersReceived value for friend user.
                               friendNumOfStickersReceivedReference.setValue(stickerID);
                               Log.v(TAG, "Send sticker operation successfully completed.");
                           }
                        });
                    }
                });
            }
        });
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
