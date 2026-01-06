package com.SPISE.k_bob;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    private List<FoodItem> foodItems;
    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(FoodItem foodItem);
    }

    public FoodListAdapter(List<FoodItem> foodItems, OnItemLongClickListener onItemLongClickListener) {
        this.foodItems = foodItems;
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem foodItem = foodItems.get(position);
        holder.foodName.setText(foodItem.getName());
        holder.foodImage.setImageResource(foodItem.getImageResId());
        holder.foodDescription.setText(foodItem.getDescription());
        holder.foodKeyIngredients.setText(foodItem.getKeyIngredients());

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, FoodInformationActivity.class);
            intent.putExtra("foodName", foodItem.getName());
            intent.putExtra("foodImageResId", foodItem.getImageResId());
            intent.putExtra("detailedDescription", foodItem.getDetailedDescription());
            intent.putExtra("majorIngredients", foodItem.getMajorIngredients());
            intent.putExtra("veganType", foodItem.getVeganType());
            intent.putExtra("dietaryRestrictions", foodItem.getDietaryRestrictions());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            onItemLongClickListener.onItemLongClick(foodItem);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView foodName;
        public ImageView foodImage;
        public TextView foodDescription;
        public TextView foodKeyIngredients;

        public ViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            foodImage = itemView.findViewById(R.id.food_image);
            foodDescription = itemView.findViewById(R.id.food_description);
            foodKeyIngredients = itemView.findViewById(R.id.food_key_ingredients);
        }
    }
}
