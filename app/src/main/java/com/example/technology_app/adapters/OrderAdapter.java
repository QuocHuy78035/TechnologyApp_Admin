package com.example.technology_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technology_app.R;
import com.example.technology_app.interfaces.IItemClickListener;
import com.example.technology_app.models.EventBus.OrderEvent;
import com.example.technology_app.models.GetOrder.Order;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private static RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    List<Order> orderList;
    Context context;

    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtIdOrder.setText("Order: " + orderList.get(position).getId());
        LinearLayoutManager manager = new LinearLayoutManager(
                holder.recyclerViewOrder.getContext(),
                LinearLayoutManager.VERTICAL, false);
        manager.setInitialPrefetchItemCount(order.getProducts().size());

        DetailOrderAdapter detailOrderAdapter = new DetailOrderAdapter(order.getProducts(), context, order);
        holder.recyclerViewOrder.setLayoutManager(manager);
        holder.recyclerViewOrder.setAdapter(detailOrderAdapter);
        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClicked) {
                if(isLongClicked){
                    Order order1 = orderList.get(pos);
                    EventBus.getDefault().postSticky(new OrderEvent(order1));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView txtIdOrder;
        RecyclerView recyclerViewOrder;
        IItemClickListener itemClickListener;
        public IItemClickListener getItemClickListener() {
            return itemClickListener;
        }

        public void setItemClickListener(IItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdOrder = itemView.findViewById(R.id.idItemOrder);
            recyclerViewOrder = itemView.findViewById(R.id.recycleItemOrder);
            recyclerViewOrder.setRecycledViewPool(viewPool);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }

        @Override
        public boolean onLongClickUseDefaultHapticFeedback(@NonNull View v) {
            return View.OnLongClickListener.super.onLongClickUseDefaultHapticFeedback(v);
        }
    }
}
