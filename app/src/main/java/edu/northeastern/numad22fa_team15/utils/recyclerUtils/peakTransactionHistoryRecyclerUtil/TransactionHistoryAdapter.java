package edu.northeastern.numad22fa_team15.utils.recyclerUtils.peakTransactionHistoryRecyclerUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // TODO: change the item layout id based on Jaime's
        View view = LayoutInflater.from(context).inflate(R.layout.item_daily_ransaction, parent, false);
        return new TransactionHistoryViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryViewHolder holder, int position) {
        String categoryImageName = "";
        Category category = transactions.get(position).getCategory();
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
        holder.itemExpense.setText(Float.toString(transactions.get(position).getExpense()));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
