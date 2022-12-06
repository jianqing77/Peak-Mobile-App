package edu.northeastern.numad22fa_team15.utils.recyclerUtils.peakTransactionHistoryRecyclerUtil;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.getYearMonthAndDayFromDateString;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.setPictureToGivenImageView;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.models.databaseModels.TransactionModel;
import edu.northeastern.numad22fa_team15.utils.Category;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryViewHolder> {

        private List<TransactionModel> transactions;
        private final Context context;

        public TransactionHistoryAdapter(List<TransactionModel> transaction, Context context) {
            if (transaction == null || context == null) {
                throw new IllegalArgumentException("Results and context cannot be null.");
            }
            this.transactions = transaction;
            this.context = context;
        }

        @NonNull
        @Override
        public TransactionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_peak_transaction_cardview, parent, false);
            return new TransactionHistoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TransactionHistoryViewHolder holder, int position) {
            String categoryImageName = "";
            TransactionModel transaction = transactions.get(position);
            Category category = transaction.getCategory();
            switch (category) {
                case DINING:
                    categoryImageName = "ic_dining";
                    break;
                case EDUCATION:
                    categoryImageName = "ic_education";
                    break;
                case BEAUTY:
                    categoryImageName = "ic_beauty";
                    break;
                case HEALTH:
                    categoryImageName = "ic_health";
                    break;
                case SHOPPING:
                    categoryImageName = "ic_shopping";
                    break;
                case GROCERIES:
                    categoryImageName = "ic_groceries";
                    break;
                case LIVING:
                    categoryImageName = "ic_living";
                    break;
                case TRAVEL:
                    categoryImageName = "ic_travel";
                    break;
                case TRANSPORTATION:
                    categoryImageName = "ic_transportation";
                    break;
                case PET:
                    categoryImageName = "ic_pet";
                    break;
                case ENTERTAINMENT:
                    categoryImageName = "ic_entertainment";
                    break;
                case OTHER:
                    categoryImageName = "ic_other";
                    break;
            }
            String drawablePath = "@drawable/";
            Context itemViewContext = holder.itemView.getContext();
            int drawableResourceID = itemViewContext.getResources().getIdentifier(drawablePath+categoryImageName, null , itemViewContext.getPackageName());
            // set the image
            holder.itemCategoryIcon.setImageResource(drawableResourceID);
            // set the description
            holder.itemDescription.setText(transactions.get(position).getDescription());
            // set the expense text
            holder.itemExpense.setText(String.valueOf(transactions.get(position).getExpense()));
            holder.itemView.setOnClickListener(view -> {
                // TODO: XH
                // Open the dialog that displays the detail of a transaction
                openViewTransactionDetailDialog(transaction, holder.itemView);
            });
        }

    private void openViewTransactionDetailDialog(TransactionModel transaction, View view) {
        AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());
        b.setCancelable(false);
        b.setTitle("Transaction Detail");
        // Retrieve transaction info from TransactionModel object
        float expense = transaction.getExpense();
        Category category = transaction.getCategory();
        String transactionDate = transaction.getTransactionDate();
        int[] yearMonthAndDay = getYearMonthAndDayFromDateString(transactionDate);
        String yearMonthAndDayString = String.format("%d/%d/%d", yearMonthAndDay[1], yearMonthAndDay[2], yearMonthAndDay[0]);
        String description = transaction.getDescription();
        byte[] receiptPictureByteArray = transaction.getReceiptPhoto();
//        System.out.println("In adapter. Receipt Byte Array: " + receiptPictureByteArray);

        // Retrieve TextView(s), ImageView, and OK Button
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_view_transaction, null);
        TextView expenseTextView = (TextView) dialogView.findViewById(R.id.expense_tv_dvt);
        TextView categoryTextView = (TextView) dialogView.findViewById(R.id.category_tv_dvt);
        TextView dateTextView = (TextView) dialogView.findViewById(R.id.date_tv_dvt);
        TextView descriptionTextView = (TextView) dialogView.findViewById(R.id.description_tv_dvt);
        ImageView receiptPhotoImageView = (ImageView) dialogView.findViewById(R.id.receipt_photo_preview_image_view);
        Button okButton = (Button) dialogView.findViewById(R.id.ok_button_drpp);

        // Update TextView values
        String expenseString = String.format("%s %.2f", expenseTextView.getText().toString(), expense);
        expenseTextView.setText(expenseString);
        String categoryString = String.format("%s %s", categoryTextView.getText().toString(), category);
        categoryTextView.setText(categoryString);
        String dateString = String.format("%s %s", dateTextView.getText().toString(), yearMonthAndDayString);
        dateTextView.setText(dateString);
        String descriptionString = String.format("%s %s", descriptionTextView.getText().toString(), description);
        descriptionTextView.setText(descriptionString);
        // Set picture to receiptPhotoImageView if there is a receipt picture
        setPictureToGivenImageView(receiptPictureByteArray, receiptPhotoImageView);
        if (receiptPictureByteArray == null || receiptPictureByteArray.length == 0) {
            receiptPhotoImageView.setVisibility(View.GONE);
        }
        b.setView(dialogView);
        AlertDialog alertDialog = b.create();
        // Add setOnClickListener to OK button
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    @Override
        public int getItemCount() {
            return transactions.size();
        }
    }
