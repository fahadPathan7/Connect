package volunteer;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityYourGoalsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
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

import java.util.HashMap;
import java.util.Map;

import commonClasses.AboutUs;
import navigationBars.DrawerBaseActivity;
import commonClasses.Helpline;

public class YourGoals extends DrawerBaseActivity implements View.OnClickListener {

    public final String KEY_INFORMATION = "Information";
    BottomNavigationItemView home;
    BottomNavigationItemView helpline;
    BottomNavigationItemView aboutUs;


    CardView[] cardViews = new CardView[6];
    TextView[] textViews = new TextView[6];
    ImageView[] confirmed = new ImageView[6];
    ImageView[] rejected = new ImageView[6];
    String[] documentSnapShotIDs = new String[6];

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ActivityYourGoalsBinding activityYourGoalsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityYourGoalsBinding = ActivityYourGoalsBinding.inflate(getLayoutInflater());
        setContentView(activityYourGoalsBinding.getRoot());
        allocateActivityTitle("Your Goals");

        connectWithIDs();
        showGoals();



        home.setOnClickListener(this);
        helpline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
    }

    public void connectWithIDs() {
        cardViews[0] = findViewById(R.id.ycardView0ID);
        cardViews[1] = findViewById(R.id.ycardView1ID);
        cardViews[2] = findViewById(R.id.ycardView2ID);
        cardViews[3] = findViewById(R.id.ycardView3ID);
        cardViews[4] = findViewById(R.id.ycardView4ID);
        cardViews[5] = findViewById(R.id.ycardView5ID);

        textViews[0] = findViewById(R.id.textView0ID);
        textViews[1] = findViewById(R.id.textView1ID);
        textViews[2] = findViewById(R.id.textView2ID);
        textViews[3] = findViewById(R.id.textView3ID);
        textViews[4] = findViewById(R.id.textView4ID);
        textViews[5] = findViewById(R.id.textView5ID);

        confirmed[0] = findViewById(R.id.confirm0ID);
        confirmed[1] = findViewById(R.id.confirm1ID);
        confirmed[2] = findViewById(R.id.confirm2ID);
        confirmed[3] = findViewById(R.id.confirm3ID);
        confirmed[4] = findViewById(R.id.confirm4ID);
        confirmed[5] = findViewById(R.id.confirm5ID);

        rejected[0] = findViewById(R.id.reject0ID);
        rejected[1] = findViewById(R.id.reject1ID);
        rejected[2] = findViewById(R.id.reject2ID);
        rejected[3] = findViewById(R.id.reject3ID);
        rejected[4] = findViewById(R.id.reject4ID);
        rejected[5] = findViewById(R.id.reject5ID);


        home=findViewById(R.id.homeMenuID);
        helpline=findViewById(R.id.helplineMenuID);
        aboutUs=findViewById(R.id.aboutUsMenuID);
    }

    public void showGoals() {
        makeViewsInvisible();

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            CollectionReference collectionReference = db.collection("Your Goals: " + uid);
            collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    int cnt = 0;
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (cnt == 6) break;

                        String information = documentSnapshot.getString("Information");

                        documentSnapShotIDs[cnt] = documentSnapshot.getId();

                        String data = information;

                        addData(data, cnt++);
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
        cardViews[pos].setVisibility(View.VISIBLE);
        textViews[pos].setText(data);
        confirmed[pos].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeOnYourSuccess(pos);
            }
        });

        rejected[pos].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnData(pos);
            }
        });
    }

    public void writeOnYourSuccess(int idx) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            String uid = user.getUid();
            String collectionName = "Your Success: " + uid;
            String documentName = documentSnapShotIDs[idx];
            DocumentReference documentReference = db.collection(collectionName).document();

            DocumentReference documentReference1 = db.collection("Your Goals: " + uid).document(documentName);

            Map<String, Object> info = new HashMap<>();
            String information = textViews[idx].getText().toString().trim();

            //info.put(KEY_TYPE, "Help");
            info.put(KEY_INFORMATION, information);

            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(getApplicationContext(), "Congratulations!", Toast.LENGTH_SHORT).show();
                    documentReference1.delete();

                    showGoals();
                    //cardViews[idx].setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Something wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "YourGoals write fault", Toast.LENGTH_SHORT).show();
        }
    }

    public void returnData(int idx) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            String uid = user.getUid();
            String information = textViews[idx].getText().toString().trim();

            String collectionName = null;
            char type = information.charAt(0);
            if (type == 'H') collectionName = "Help Requests";
            else collectionName = "Rescue Requests";

            String details[] = information.split("Details:");
            String location[] = details[0].split("Location:");
            String contact[] = location[0].split("Contact:");
            String name[] = contact[0].split("Name:");

            String documentName = documentSnapShotIDs[idx];
            DocumentReference documentReference = db.collection(collectionName).document(documentName);

            DocumentReference documentReference1 = db.collection("Your Goals: " + uid).document(documentName);

            Map<String, Object> info = new HashMap<>();

            if (type == 'H') info.put("Information", "Name: " + name[1].trim() + "\nContact: " + contact[1].trim() + "\nLocation: " + location[1].trim() + "\nDetails: " + details[1].trim());
            else info.put("Information", "Name: " + name[1].trim() + "\nContact: " + contact[1].trim() + "\nLocation: " + location[1].trim());
            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(getApplicationContext(), "We are feeling Sad.", Toast.LENGTH_SHORT).show();
                    documentReference1.delete();

                    showGoals();
                    //cardViews[idx].setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Something wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "YourGoals write fault", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.homeMenuID)
        {
            start_HomeScreenVolunteer_activity();
        }
        else if(v.getId()==R.id.helplineMenuID)
        {
            start_Helpline_activity();
        }
        else if(v.getId()==R.id.aboutUsMenuID)
        {
            start_AboutUs_activity();
        }
    }

    public void start_HomeScreenVolunteer_activity() {
        Intent intent = new Intent(getApplicationContext(), HomeScreenVolunteer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void start_Helpline_activity()
    {
        Intent intent = new Intent(this, Helpline.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void start_AboutUs_activity()
    {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}