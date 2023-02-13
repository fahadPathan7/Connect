package volunteer;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityRescueRequestsBinding;
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
import commonClasses.LiveChat;
import navigationBars.DrawerBaseActivity;

public class RescueRequests extends DrawerBaseActivity implements View.OnClickListener {

    public final String KEY_INFORMATION = "Information";

    BottomNavigationItemView home;
    BottomNavigationItemView helpline;
    BottomNavigationItemView aboutUs;

    LinearLayout linearLayout;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference rescueRequests = db.collection("Rescue Requests");

    ActivityRescueRequestsBinding activityRescueRequestsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRescueRequestsBinding = ActivityRescueRequestsBinding.inflate(getLayoutInflater());
        setContentView(activityRescueRequestsBinding.getRoot());


        // here
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_rescue_requests, null);

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 20, 0, 0);
        linearLayout.setPadding(0, 0, 0, 30);
        linearLayout.setLayoutParams(params);

        showRequests();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ScrollView scrollView = view.findViewById(R.id.rescueScroll_viewID);
        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.addView(linearLayout);

        setContentView(view);


        allocateActivityTitle("Rescue Requests");


        connectWithIDs();

        home.setOnClickListener(this);
        helpline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
    }

    public void showRequests() {

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            rescueRequests.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    linearLayout.removeAllViews();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        String information = documentSnapshot.getString("Information");
                        String userID = documentSnapshot.getString("ID");
                        String type = documentSnapshot.getString("Type");

                        if (userID.equals(uid)) continue;

                        String documentID = documentSnapshot.getId();

                        addData(information, documentID, userID, type);
                    }
                }
            });
        } catch (Exception e) {
            //
        }
    }

    public void connectWithIDs() {
        home=findViewById(R.id.homeMenuID);
        helpline=findViewById(R.id.liveChatMenuID);
        aboutUs=findViewById(R.id.aboutUsMenuID);
    }


    @SuppressLint("ResourceAsColor")
    public void addData(String information, String documentID, String userID, String type) {
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

        //cardView.addView(linearLayout);

        LinearLayout innerLinearLayout = new LinearLayout(this);
        innerLinearLayout.setOrientation(LinearLayout.VERTICAL);

        // Add your content to the cardView
        TextView textView = new TextView(this);
        textView.setText(information);
        textView.setTextSize(16);
        textView.setPadding(0, 0, 0, 15);

        Button button = new Button(this);
        button.setText("Rescue");
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(R.color.light_theme_color);
        Typeface typeface = Typeface.create(button.getTypeface(), Typeface.BOLD);
        button.setTypeface(typeface);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform action on click
                button.setEnabled(false);
                writeOnYourGoals(information, documentID, userID, type);
            }
        });


        innerLinearLayout.addView(textView);
        innerLinearLayout.addView(button);

        cardView.addView(innerLinearLayout);

        linearLayout.addView(cardView);
    }

    public void writeOnYourGoals(String information, String documentID, String userID, String type) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        try {
            String uid = user.getUid();
            String collectionName = "Your Goals: " + uid;
            String documentName = documentID;

            DocumentReference documentReference = db.collection(collectionName).document(documentName);
            DocumentReference documentReference1 = db.collection("Rescue Requests").document(documentName);

            Map<String, Object> info = new HashMap<>();

            info.put("ID", userID);
            info.put(KEY_INFORMATION, information);
            info.put("Type", type);

            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    //cardViews[idx].setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Thanks for your help.", Toast.LENGTH_SHORT).show();
                    writeOnYourRequests(userID, uid, documentID, information, type);
                    documentReference1.delete();

                    //showRequests();
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

    public void writeOnYourRequests(String userID, String volunteerID, String documentID, String information, String type) {

        Map<String, Object> info = new HashMap<>();

        info.put("Information", information);
        info.put("Type", type);
        info.put("VolunteerID", volunteerID);
        info.put("Status", "2");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference1 = db.collection("Your Requests: " + userID).document(documentID);
        documentReference1.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.homeMenuID)
        {
            start_HomeScreenVolunteer_activity();
        }
        else if(v.getId()==R.id.liveChatMenuID)
        {
            start_LiveChat_activity();
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

    public void start_LiveChat_activity()
    {
        Intent intent = new Intent(this, LiveChat.class);
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