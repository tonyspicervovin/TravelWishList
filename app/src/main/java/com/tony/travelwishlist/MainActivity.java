package com.tony.travelwishlist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WishListClickListener {

    private RecyclerView mWishListRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button mAddButton;
    private EditText mNewPlaceNameEditText;
    private EditText mGetReasonVisit;

    private List<Place> mPlaces;
    private String TAG = "MAIN_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlaces = new ArrayList<>();
        // list of places
        mWishListRecyclerView = findViewById(R.id.wish_list);
        mAddButton = findViewById(R.id.add_place_button);
        mNewPlaceNameEditText = findViewById(R.id.new_place_name);
        mGetReasonVisit = findViewById(R.id.reason_for_visit);
        mWishListRecyclerView.setHasFixedSize(true);
        //locating components in GUI
        mLayoutManager = new LinearLayoutManager(this);
        mWishListRecyclerView.setLayoutManager(mLayoutManager);
        // setting layout to wishlistview
        mAdapter = new WishListAdapter(mPlaces, this);
        mWishListRecyclerView.setAdapter(mAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPlace = mNewPlaceNameEditText.getText().toString();
                String newReason = mGetReasonVisit.getText().toString();
                if (newPlace.isEmpty() || newReason.isEmpty()) {
                    return;
                }
                mPlaces.add(new Place(newPlace, newReason));
                mAdapter.notifyItemInserted(mPlaces.size() -1); //last element
                mNewPlaceNameEditText.getText().clear();
                mGetReasonVisit.getText().clear();
                //when add is clicked, validate both fields are not empty
                //create a new place object with name and reason, clear both fields
            }
        });
    }

    @Override
    public void onListClick(int position) {
        Place place = mPlaces.get(position);
        Uri locationUri = Uri.parse("geo:0,0?q=" + Uri.encode(place.getName()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri );
        startActivity(mapIntent);
        // opens map when a location is short clicked
    }

    @Override
    public void onListLongClick(int position) {
        final int itemPosition = position;

        AlertDialog confirmDeleteDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.delete_place_message, mPlaces.get(position).getName()))
                .setTitle(getString(R.string.delete_dialog_title))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPlaces.remove(itemPosition);
                        mAdapter.notifyItemRemoved(itemPosition);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        confirmDeleteDialog.show();
        // delete with confimation when an item is long clicked
    }
}
