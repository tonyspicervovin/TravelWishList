package com.tony.travelwishlist;

import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter <WishListAdapter.WishListViewHolder> {
    private String TAG = "WISH_LIST_ADAPTER";
    private List<Place> data;
    //adapters internal data store

    private WishListClickListener listener;
    //click and long click listener

    public WishListAdapter(List<Place> data, WishListClickListener listener) {
        this.listener = listener;
        this.data = data;


    }


    public WishListAdapter(List<Place> data) {
        this.data = data;
    }

    static class WishListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    ,View.OnLongClickListener{

        LinearLayout layout;
        TextView nameTextView;
        TextView dateCreatedTextView;
        TextView reasonVisitTextView;

        WishListClickListener listener;

        WishListViewHolder(LinearLayout layout, WishListClickListener listener) {
            super(layout);
            this.listener = listener;
            this.layout = layout;
            nameTextView = layout.findViewById(R.id.placeNameTextView);
            dateCreatedTextView = layout.findViewById(R.id.dateCreatedTextView);
            reasonVisitTextView = layout.findViewById(R.id.reasonVisitTextView);
            layout.setOnClickListener(this);
            layout.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View view){
            listener.onListClick(getAdapterPosition());
            //notify listener of event and which item was clicked
        }
        @Override
        public boolean onLongClick(View view) {
            listener.onListLongClick(getAdapterPosition());
            //notify listener of event and which item was long clicked
            return true;
            // indicates event is consumed, no further processing
        }
    }

    @NonNull
    @Override
    public WishListAdapter.WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wish_list_element, parent, false);
        //getting reference to wish list element and inflate in
        WishListViewHolder viewHolder = new WishListViewHolder(layout,listener);
        //create new viewholder to contain this textview
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListViewHolder holder, int position) {
        Place place = data.get(position);
        holder.nameTextView.setText(place.getName());
        holder.dateCreatedTextView.setText("Created on " + place.getDateCreated());
        String reason = place.getReasonVisit();
        holder.reasonVisitTextView.setText(place.getReasonVisit());
        //configures viewholder to display data for given position, binds view and it's data
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
