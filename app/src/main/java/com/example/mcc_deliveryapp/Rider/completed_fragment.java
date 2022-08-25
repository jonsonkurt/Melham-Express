package com.example.mcc_deliveryapp.Rider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mcc_deliveryapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class completed_fragment extends Fragment {
	private RecyclerView recyclerView;
	private String riderPhoneNum, riderName;
	private TextView emptyTextCourier;
	private ImageView emptyCompleted;


	completed_adapter adapter; // Create Object of the Adapter class
	DatabaseReference mbase; // Create object of the

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
						 Bundle savedInstanceState)
	{

		View view =  inflater.inflate(R.layout.fragment_completed_fragment, container, false);

		System.out.println("Completed view");
		// Create a instance of the database and get
		// its reference
		mbase = FirebaseDatabase.getInstance().getReference().child("userparcel");

		System.out.println(mbase);
		recyclerView = view.findViewById(R.id.recycler_courier_completed);
		emptyCompleted = view.findViewById(R.id.emptyCourier);
		emptyTextCourier = view.findViewById(R.id.emptyTextCourier);

		// To display the Recycler view linearly
		recyclerView.setLayoutManager(
				new LinearLayoutManager(getContext()));

		Intent intent = getActivity().getIntent();
		riderPhoneNum = intent.getStringExtra("phonenum");
//		riderName = intent.getStringExtra("username");
		System.out.println(riderPhoneNum);


		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		final DatabaseReference dr = database.getReference().child("riders");
		Query query = dr.orderByChild("riderphone").equalTo(riderPhoneNum);

		query.addChildEventListener(
				new ChildEventListener() {
					@Override
					public void onChildAdded(DataSnapshot dataSnapshot, String s) {
						riderName = dataSnapshot.child("name").getValue(String.class);
					}

					@Override
					public void onChildRemoved(DataSnapshot dataSnapshot) {

					}

					@Override
					public void onChildMoved(DataSnapshot dataSnapshot, String s) {
					}

					@Override
					public void onCancelled(@NonNull DatabaseError error) {

					}

					@Override
					public void onChildChanged(DataSnapshot dataSnapshot, String s) {
					}
				});


// It is a class provide by the FirebaseUI to make a
		// query in the database to fetch appropriate data
		FirebaseRecyclerOptions<model> options
				= new FirebaseRecyclerOptions.Builder<model>()
				.setQuery(FirebaseDatabase.getInstance().getReference()
						.child("userparcel").orderByChild("parcelstatus")
						.equalTo("Completed"+riderPhoneNum), model.class)
				.build();

		// Connecting object of required Adapter class to
		// the Adapter class itself
		adapter = new completed_adapter(options);
		adapter.getUserNum(riderPhoneNum);
		adapter.getUserName(riderName);
		recyclerView.setAdapter(adapter);

		DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("userparcel");
		Query checkRider = databaseReference.orderByChild("ridernum");

		checkRider.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for (DataSnapshot parcelSnapshot : snapshot.getChildren()) {
					if (parcelSnapshot.child("ridernum").getValue().equals(riderPhoneNum))
					{
						if (parcelSnapshot.child("parcelstatus").getValue().equals("Completed"+riderPhoneNum))
						{
							emptyCompleted.setVisibility(View.GONE);
							emptyTextCourier.setVisibility(View.GONE);
							recyclerView.setVisibility(View.VISIBLE);
						}
					}
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		return view;
	}

	// Function to tell the app to start getting
	// data from database on starting of the activity
	@Override
	public void onStart()
	{
		super.onStart();
		adapter.startListening();
	}

	// Function to tell the app to stop getting
	// data from database on stopping of the activity
	@Override
	public void onStop()
	{
		super.onStop();
		adapter.stopListening();
	}
}