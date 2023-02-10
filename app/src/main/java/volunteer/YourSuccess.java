package volunteer;

import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.R;
import com.example.android.databinding.ActivityYourSuccessBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import navigationBars.DrawerBaseActivity;

public class YourSuccess extends DrawerBaseActivity {

    public final String KEY_INFORMATION = "Information";


    LinearLayout linearLayout;
    ActivityYourSuccessBinding activityYourSuccessBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityYourSuccessBinding = ActivityYourSuccessBinding.inflate(getLayoutInflater());
        setContentView(activityYourSuccessBinding.getRoot());
        allocateActivityTitle("Your Success");


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_your_success, null);

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        showSuccessList();

        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        scrollView.addView(linearLayout);

        setContentView(view);
    }


    public void showSuccessList() {

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            String collectionName = "Your Success: " + uid;

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference yourSuccess = db.collection(collectionName);

            yourSuccess.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        String information = documentSnapshot.getString(KEY_INFORMATION);

                        addData(information);
                    }
                }
            });
        } catch (Exception e) {
            //
        }
    }


    public void addData(String data) {
        CardView cardView = new CardView(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(30, 5, 30, 5);

        cardView.setLayoutParams(params);
        cardView.setRadius(30);
        cardView.setContentPadding(20, 20, 20, 20);
        cardView.setCardElevation(20);

        // Add your content to the cardView
        TextView textView = new TextView(this);
        textView.setText(data);
        textView.setTextSize(15);
        cardView.addView(textView);

        // Add the cardView to the linearLayout
        linearLayout.addView(cardView);
    }
}