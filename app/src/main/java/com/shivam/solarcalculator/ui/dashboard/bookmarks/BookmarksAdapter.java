package com.shivam.solarcalculator.ui.dashboard.bookmarks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shivam.solarcalculator.R;
import com.shivam.solarcalculator.data.models.Address;

import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.MyViewHolder> {
 
    private List<Address> addressList;
    private View.OnClickListener onClickListener;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAddress;
 
        public MyViewHolder(View view) {
            super(view);
            tvAddress = view.findViewById(R.id.tv_address);
        }
    }
 
 
    public BookmarksAdapter(List<Address> notesList, View.OnClickListener onClickListener) {
        this.addressList = notesList;
        this.onClickListener = onClickListener;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address_list, parent, false);
        itemView.setOnClickListener(onClickListener);

        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.tvAddress.setText(address.getAddress());
        holder.itemView.setTag(Integer.valueOf(position));
    }
 
    @Override
    public int getItemCount() {
        return addressList.size();
    }

}