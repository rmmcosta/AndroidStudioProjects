package com.example.droidcafe_withsettings;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {
    private static final String DONUT = "DONUT";
    private static final String ICE_CREAM = "ICECREAM";
    private static final String FROYO = "FROYO";
    private static final String LOG_TAG = "LOG_CartItemsAdapter";

    private List<Map.Entry<String, Integer>> mCartItems;

    public CartItemsAdapter(Set<Map.Entry<String, Integer>> mCartItems) {
        this.mCartItems = new ArrayList<>(mCartItems);
        Log.d(LOG_TAG, "cart items adapter constructor list size:" + mCartItems.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cartItemView = inflater.inflate(R.layout.item_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(cartItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(LOG_TAG, "on bind view holder position:" + position);
        Map.Entry<String, Integer> cartItem = mCartItems.get(position);
        TextView tvCartItemQty = holder.tvCartItemQty;
        TextView tvCartItemName = holder.tvCartItemName;
        ImageView ivCartItem = holder.ivCartItem;
        tvCartItemName.setText(cartItem.getKey());
        tvCartItemQty.setText(String.valueOf(cartItem.getValue()));
        int drawableId;
        switch (cartItem.getKey().toUpperCase()) {
            case DONUT:
                drawableId = R.drawable.donut_circle_small;
                break;
            case ICE_CREAM:
                drawableId = R.drawable.icecream_circle_small;
                break;
            case FROYO:
                drawableId = R.drawable.froyo_circle_small;
                break;
            default:
                throw new RuntimeException("Unknown cart item!");
        }
        ivCartItem.setImageResource(drawableId);
    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "get item count:" + mCartItems.size());
        return mCartItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCartItemName;
        TextView tvCartItemQty;
        ImageView ivCartItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCartItemName = itemView.findViewById(R.id.tvCartItemName);
            tvCartItemQty = itemView.findViewById(R.id.tvCartItemQty);
            ivCartItem = itemView.findViewById(R.id.ivCartItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(LOG_TAG, "clicked on cart item");
                    String item = tvCartItemName.getText().toString();
                    boolean wasRemoved = MainActivity.subtractToCart(item);
                    mCartItems = new ArrayList<>(MainActivity.getCartItems());
                    if (wasRemoved) {
                        notifyItemRemoved(getLayoutPosition());
                    } else {
                        notifyItemChanged(getLayoutPosition());
                    }
                }
            });
        }
    }
}