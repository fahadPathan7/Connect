package volunteer;

import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
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
    CardView[] cardViews = new CardView[6];
    TextView[] textViews = new TextView[6];


    ActivityYourSuccessBinding activityYourSuccessBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityYourSuccessBinding = ActivityYourSuccessBinding.inflate(getLayoutInflater());
        setContentView(activityYourSuccessBinding.getRoot());
        allocateActivityTitle("Your Success");

        connectWithIDs();
        showSuccessList();
    }

    private void connectWithIDs() {
        cardViews[0] = findViewById(R.id.scardView0ID);
        cardViews[1] = findViewById(R.id.scardView1ID);
        cardViews[2] = findViewById(R.id.scardView2ID);
        cardViews[3] = findViewById(R.id.scardView3ID);
        cardViews[4] = findViewById(R.id.scardView4ID);
        cardViews[5] = findViewById(R.id.scardView5ID);

        textViews[0] = findViewById(R.id.stextView0ID);
        textViews[1] = findViewById(R.id.stextView1ID);
        textViews[2] = findViewById(R.id.stextView2ID);
        textViews[3] = findViewById(R.id.stextView3ID);
        textViews[4] = findViewById(R.id.stextView4ID);
        textViews[5] = findViewById(R.id.stextView5ID);
    }

    public void showSuccessList() {
        makeViewsInvisible();

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

                    String data = "";

                    int cnt = 0;
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (cnt == 6) break;

                        String information = documentSnapshot.getString(KEY_INFORMATION);

                        data = information;

                        addData(data, cnt);
                        cnt++;
                    }
                }
            });
        } catch (Exception e) {
            //
        }
    }

    public void makeViewsInvisible() {
        for (int i = 0; i < 6; i++) {
            cardViews[i].setVisibility(View.GONE);
        }
    }

    public void addData(String data, int pos) {
        textViews[pos].setText(data);
        cardViews[pos].setVisibility(View.VISIBLE);
    }
}