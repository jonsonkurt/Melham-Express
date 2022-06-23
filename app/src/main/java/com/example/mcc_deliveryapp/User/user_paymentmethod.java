package com.example.mcc_deliveryapp.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mcc_deliveryapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class user_paymentmethod extends AppCompatActivity {
	FirebaseDatabase db =FirebaseDatabase.getInstance();
	DatabaseReference root = db.getReference().child("user parcel infos");
	EditText notes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_paymentmethod);

		TextView editTextPayment = findViewById(R.id.textPayment);

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

		//getting the save shared preference of sender
		String senderloc =sharedPref.getString("key 1","");
		String sendercontact =sharedPref.getString("key 2","");
		String sendername =sharedPref.getString("key 3","");
		//getting the save shared preference of receiver
		String receiverloc =sharedPref.getString("key 4","");
		String receivercontact =sharedPref.getString("key 5","");
		String receivername =sharedPref.getString("key 5","");

		notes = findViewById(R.id.editTextNotes);

		String comment = notes.getEditableText().toString();

		editTextPayment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
						user_paymentmethod.this,R.style.BottomSheetDialogTheme
				);
				View bottomSheetView = LayoutInflater.from(getApplicationContext())
						.inflate(
								R.layout.user_paymentdialog,
								(LinearLayout)findViewById(R.id.paymentDialog)
						);


				bottomSheetView.findViewById(R.id.btnPlaceOrder).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {

						HashMap<String, String> usermap = new HashMap<>();
						usermap.put("Comment/Notes", comment);

						usermap.put("sender location", senderloc);
						usermap.put("sender contact", sendercontact);
						usermap.put("sender name", sendername);

						usermap.put("receiver location", receiverloc);
						usermap.put("receiver contact", receivercontact);
						usermap.put("receiver name", receivername);


						root.push().setValue(usermap);


						Intent intent = new Intent(user_paymentmethod.this,user_checkrate.class);
						startActivity(intent);
						Toast.makeText(user_paymentmethod.this, "Place Order test", Toast.LENGTH_SHORT).show();
						bottomSheetDialog.dismiss();
					}
				});
				bottomSheetDialog.setContentView(bottomSheetView);
				bottomSheetDialog.show();
			}
		});
	}
}