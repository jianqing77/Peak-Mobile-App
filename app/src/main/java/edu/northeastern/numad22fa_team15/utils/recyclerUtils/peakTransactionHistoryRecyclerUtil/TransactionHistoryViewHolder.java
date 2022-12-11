package edu.northeastern.numad22fa_team15.utils.recyclerUtils.peakTransactionHistoryRecyclerUtil;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numad22fa_team15.R;

public class TransactionHistoryViewHolder extends RecyclerView.ViewHolder {

    public ImageView itemCategoryIcon;
    public TextView itemDescription;
    public TextView itemExpense;

    public TransactionHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemCategoryIcon = (ImageView) itemView.findViewById(R.id.category_icon);
        this.itemDescription = (TextView) itemView.findViewById(R.id.home_transaction_description);
        this.itemExpense = (TextView) itemView.findViewById(R.id.home_transaction_amount);
    }
}
