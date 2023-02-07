package volunteer;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.android.R;
import com.example.android.databinding.ActivityHelpRequestsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Map;

import navigationBars.DrawerBaseActivity;

public class HelpRequests extends DrawerBaseActivity implements View.OnClickListener {

    public final String KEY_NAME = "Name";
    public final String KEY_CONTACT = "Contact";
    public final String KEY_LOCATION = "Location";
    public final String KEY_DETAILS = "details";

    CardView[] cardViews = new CardView[6];
    TextView[] textViews = new TextView[6];
    ImageView[] imageViews = new ImageView[6];
    String[] documentSnapShotIDs = new String[6];

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference helpRequests = db.collection("Help Requests");
    //private DocumentReference noteRef = db.document("Notebook/My First Note");

    ActivityHelpRequestsBinding activityHelpRequestsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHelpRequestsBinding = ActivityHelpRequestsBinding.inflate(getLayoutInflater());
        setContentView(activityHelpRequestsBinding.getRoot());
        allocateActivityTitle("Help Requests");


//        try {
//            for (int i = 0; i < 6; i++) {
//                imageViews[i].setOnClickListener(this);
//            }
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "onClick fault", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        connectWithIDs();
        makeViewsInvisible();

        helpRequests.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                String data = "";

                int cnt = 0;
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    if (cnt == 6) break;

                    String name = documentSnapshot.getString(KEY_NAME);
                    String contact = documentSnapshot.getString(KEY_CONTACT);
                    String location = documentSnapshot.getString(KEY_LOCATION);
                    String details = documentSnapshot.getString(KEY_DETAILS);

                    documentSnapShotIDs[cnt] = documentSnapshot.getId();

                    data += "Name: " + name + "\n\nContact: " + contact + "\n\nLocation: " + location +
                            "\n\nDetails: " + details + "\n\n";

                    addData(data, cnt++);
                }
            }
        });
    }

    public void connectWithIDs() {
        cardViews[0] = findViewById(R.id.cardView0ID);
        cardViews[1] = findViewById(R.id.cardView1ID);
        cardViews[2] = findViewById(R.id.cardView2ID);
        cardViews[3] = findViewById(R.id.cardView3ID);
        cardViews[4] = findViewById(R.id.cardView4ID);
        cardViews[5] = findViewById(R.id.cardView5ID);

        textViews[0] = findViewById(R.id.textView0ID);
        textViews[1] = findViewById(R.id.textView1ID);
        textViews[2] = findViewById(R.id.textView2ID);
        textViews[3] = findViewById(R.id.textView3ID);
        textViews[4] = findViewById(R.id.textView4ID);
        textViews[5] = findViewById(R.id.textView5ID);

        imageViews[0] = findViewById(R.id.imageView0ID);
        imageViews[1] = findViewById(R.id.imageView1ID);
        imageViews[2] = findViewById(R.id.imageView2ID);
        imageViews[3] = findViewById(R.id.imageView3ID);
        imageViews[4] = findViewById(R.id.imageView4ID);
        imageViews[5] = findViewById(R.id.imageView5ID);
    }
    public void makeViewsInvisible() {
        for (int i = 0; i < 6; i++) {
            cardViews[i].setVisibility(View.GONE);
        }
    }

    public void addData(String data, int pos) {
        textViews[pos].setText(data);
        cardViews[pos].setVisibility(View.VISIBLE);
        imageViews[pos].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeOnYourGoals(0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textView0ID) {
            writeOnYourGoals(0);
        }
        if (v.getId() == R.id.textView1ID) {
            writeOnYourGoals(1);
        }
        if (v.getId() == R.id.textView2ID) {
            writeOnYourGoals(2);
        }
        if (v.getId() == R.id.textView3ID) {
            writeOnYourGoals(3);
        }
        if (v.getId() == R.id.textView4ID) {
            writeOnYourGoals(4);
        }
        if (v.getId() == R.id.textView5ID) {
            writeOnYourGoals(5);
        }
    }

    public void writeOnYourGoals(int idx) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            String uid = user.getUid();
            String collectionName = "Your Goals: " + uid;
            String documentName = documentSnapShotIDs[idx];
            DocumentReference documentReference = db.collection(collectionName).document(documentName);

            Map<String, Object> info = new HashMap<>();
            info.put("Type", "Help");
            info.put("Info", textViews[idx].getText().toString().trim());

            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Thanks for your help.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Something wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "YourGoals write fault", Toast.LENGTH_SHORT).show();
        }
    }
}