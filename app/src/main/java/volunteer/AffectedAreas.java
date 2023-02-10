package volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.R;
import com.example.android.databinding.ActivityAffectedAreasBinding;
import com.example.android.databinding.ActivityYourSuccessBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import navigationBars.DrawerBaseActivity;

public class AffectedAreas extends DrawerBaseActivity {
    public final String KEY_INFORMATION = "Information";


    LinearLayout linearLayout;
    ActivityAffectedAreasBinding activityAffectedAreasBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAffectedAreasBinding = ActivityAffectedAreasBinding.inflate(getLayoutInflater());
        setContentView(activityAffectedAreasBinding.getRoot());
        allocateActivityTitle("Affected Areas");


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_your_success, null);

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 20, 0, 0);
        linearLayout.setPadding(0, 0, 0, 30);
        linearLayout.setLayoutParams(params);

        showSuccessList();

        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        scrollView.addView(linearLayout);

        setContentView(view);
    }

    public void showSuccessList() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Areas").document("info");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> data = document.getData();
                        for (Map.Entry<String, Object> entry : data.entrySet()) {
                            String dis = entry.getKey();
                            String status = entry.getValue().toString();
                            addData(dis, status);
                        }
                    } else {
                        //
                    }
                } else {
                    //
                }
            }
        });
    }


    public void addData(String data, String status) {
        CardView cardView = new CardView(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(30, 5, 30, 5);

        cardView.setLayoutParams(params);
        cardView.setRadius(10);
        cardView.setContentPadding(20, 20, 20, 20);
        cardView.setCardElevation(20);

        // Add your content to the cardView
        TextView textView = new TextView(this);
        if (status.equals("yellow")) {
            textView.setTextColor(getResources().getColor(R.color.yellow));
        }
        else {
            textView.setTextColor(getResources().getColor(R.color.red));
        }
        textView.setText(data);
        textView.setTextSize(17);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);
        cardView.addView(textView);

        // Add the cardView to the linearLayout
        linearLayout.addView(cardView);
    }
}