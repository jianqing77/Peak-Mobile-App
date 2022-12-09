package edu.northeastern.numad22fa_team15.utils;

import static edu.northeastern.numad22fa_team15.utils.CommonUtils.getYearMonthAndDayFromDateString;
import static edu.northeastern.numad22fa_team15.utils.CommonUtils.setPictureToGivenImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.models.databaseModels.TransactionModel;

public class ReceiptsGridViewAdapter extends ArrayAdapter<TransactionModel> {

    public ReceiptsGridViewAdapter(@NonNull Context context, List<TransactionModel> transactionModelListList) {
        super(context, 0, transactionModelListList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_receipt, parent, false);
        }

        TransactionModel transactionModel = getItem(position);
        TextView receiptDateTextView = listItemView.findViewById(R.id.item_date_text_view);
        ImageView receiptImageView = listItemView.findViewById(R.id.item_receipt_image_view);

        // Retrieve transaction date and receipt byte array
        String transactionDate = transactionModel.getTransactionDate();
        byte[] receiptByteArray = transactionModel.getReceiptPhoto();

        // Correct transaction date format
        int[] yearMonthAndDay = getYearMonthAndDayFromDateString(transactionDate);
        int year = yearMonthAndDay[0];
        int month = yearMonthAndDay[1];
        int day = yearMonthAndDay[2];
        String transactionDateCorrectFormat = String.format("%d/%d/%d", year, month, day);

        // Set date to text view and receipt picture to image view
        receiptDateTextView.setText(transactionDateCorrectFormat);
        setPictureToGivenImageView(receiptByteArray, receiptImageView);

        return listItemView;
    }
}
