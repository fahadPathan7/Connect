/*
to show the users about the current status of their area. safe, under warning or under danger.
 */

package user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityYourAreaBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import navigationBars.DrawerBaseActivity;

public class YourArea extends DrawerBaseActivity {


    ImageView imageView;
    TextView textView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference = db.collection("Areas").document("info");
    private DocumentReference documentReference1;

    ActivityYourAreaBinding activityYourAreaBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityYourAreaBinding = ActivityYourAreaBinding.inflate(getLayoutInflater());
        setContentView(activityYourAreaBinding.getRoot());
        allocateActivityTitle("Your Area");

        imageView = findViewById(R.id.imageViewID);
        textView = findViewById(R.id.textViewID);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        documentReference1 = db.collection("User Profile").document(uid);

        getDistrictInfo();
        updateData();
    }


    /*
    showing the user about the current status of their area.
     */
    private void updateData() {
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    return;
                }
                if (value.exists()) {
                    String key = textView.getText().toString().toUpperCase();

                    try {
                        String status = value.getString(key);

                        if (status.equals("yellow")) {
                            imageView.setImageResource(R.drawable.warning);
                            textView.setText(R.string.warning);
                            textView.setTextColor(Color.parseColor("#FF9800"));
                        }
                        else if (status.equals("red")) {
                            imageView.setImageResource(R.drawable.danger);
                            textView.setText(R.string.danger);
                            textView.setTextColor(Color.parseColor("#E91E63"));
                        }
                        else {
                            imageView.setImageResource(R.drawable.safe);
                            textView.setTextColor(Color.parseColor("#4CAF50"));
                            textView.setText(R.string.safe);
                        }
                    } catch (Exception e) {
                        imageView.setImageResource(R.drawable.safe);
                        textView.setTextColor(Color.parseColor("#4CAF50"));
                        textView.setText(R.string.safe);
                    }
                }
            }
        });
    }

    /*
    getting the user location(district) from database.
     */
    private void getDistrictInfo() {
        documentReference1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    return;
                }
                if (value.exists()) {
                    String district = value.getString("District").toUpperCase();
                    textView.setText(district);
                }
            }
        });
    }
}