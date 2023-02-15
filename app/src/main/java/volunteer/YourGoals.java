/*
the volunteer can see his goals. he can complete his goal or he can discard the request.
 */

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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import commonClasses.AboutUs;
import commonClasses.LiveChat;
import navigationBars.DrawerBaseActivity;

public class YourGoals extends DrawerBaseActivity implements View.OnClickListener {

    public final String KEY_INFORMATION = "Information";
    BottomNavigationItemView home;
    BottomNavigationItemView helpline;
    BottomNavigationItemView aboutUs;

    LinearLayout linearLayout;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ActivityYourGoalsBinding activityYourGoalsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityYourGoalsBinding = ActivityYourGoalsBinding.inflate(getLayoutInflater());
        setContentView(activityYourGoalsBinding.getRoot());


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_help_requests, null);

        // the goals will be added on this view
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 20, 0, 0);
        linearLayout.setPadding(0, 0, 0, 30);
        linearLayout.setLayoutParams(params);

        showGoals(); // showing the goals.

        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.addView(linearLayout);

        setContentView(view); // setting view.


        allocateActivityTitle("Your Goals");


        connectWithIDs();

        home.setOnClickListener(this);
        helpline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
    }

    private void connectWithIDs() {
        home=findViewById(R.id.homeMenuID);
        helpline=findViewById(R.id.liveChatMenuID);
        aboutUs=findViewById(R.id.aboutUsMenuID);
    }

    /*
    to show the goals of volunteer.
     */
    private void showGoals() {
        //makeViewsInvisible();

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            CollectionReference collectionReference = db.collection("Your Goals: " + uid);
            collectionReference.orderBy("Type", Query.Direction.DESCENDING)
                    .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    linearLayout.removeAllViews(); // removing all views before re-showing.

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        String information = documentSnapshot.getString("Information");
                        String userID = documentSnapshot.getString("ID");
                        String type = documentSnapshot.getString("Type");

                        String documentID = documentSnapshot.getId();

                        addData(information, documentID, userID, type); // to add data in the views. and
                            // to show the volunteers about his goals.
                    }
                }
            });
        } catch (Exception e) {
            //
        }


    }


    /*
    to show the volunteers about his goals.
     */
    @SuppressLint("ResourceAsColor")
    private void addData(String information, String documentID, String userID, String type) {
        // will contain of a goal
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

        // the buttons and the info's will be added here
        LinearLayout innerLinearLayout = new LinearLayout(this);
        innerLinearLayout.setOrientation(LinearLayout.VERTICAL);

        // adding the info's
        TextView textView = new TextView(this);
        textView.setText(type + "\n\n" + information);
        textView.setTextSize(16);
        textView.setPadding(0, 0, 0, 15);

        // to set the buttons.
        LinearLayout buttonLinearLayout = new LinearLayout(this);
        buttonLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        // positive button
        Button button = new Button(this);
        button.setText("Completed");
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.parseColor("#4CAF50"));
        Typeface typeface = Typeface.create(button.getTypeface(), Typeface.BOLD);
        button.setTypeface(typeface);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        button.setLayoutParams(buttonParams);

        // negative button
        Button button1 = new Button(this);
        button1.setText("Discard");
        button1.setTextColor(Color.WHITE);
        button1.setBackgroundColor(Color.parseColor("#E91E63"));
        Typeface typeface1 = Typeface.create(button1.getTypeface(), Typeface.BOLD);
        button1.setTypeface(typeface1);
        LinearLayout.LayoutParams button1Params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        button1.setLayoutParams(button1Params);

        // if he completes his goal he will click on this button. after that it will be verified from the user.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                button1.setEnabled(false);
                // Perform action on click
                sendForVerification(information, documentID, userID, type); // sending for user verification.
            }
        });

        // by clicking on it volunteer will discard his goal. and the request will go to it's previous place.
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1.setEnabled(false);
                button.setEnabled(false);
                // Perform action on click
                returnData(information, documentID, userID, type); // returning the request to it's previous place.
            }
        });

        buttonLinearLayout.addView(button);
        buttonLinearLayout.addView(button1);


        innerLinearLayout.addView(textView);
        innerLinearLayout.addView(buttonLinearLayout);

        cardView.addView(innerLinearLayout);

        linearLayout.addView(cardView);
    }

    /*
    sending for the user verification
     */
    private void sendForVerification(String information, String documentID, String userID, String type) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            String uid = user.getUid();

            DocumentReference documentReference = db.collection("Your Goals: " + uid).document(documentID);

            writeOnYourRequests(userID, uid, documentID, information, type, "1"); // updating on user's requests.
            documentReference.delete(); // deleting from volunteer's goal

            Toast.makeText(getApplicationContext(), "Congratulations\nSent for Verification!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Try again later!", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    if the volunteer discards then the request will be written in it's previous place.
     */
    private void returnData(String information, String documentID, String userID, String type) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            String uid = user.getUid();

            String collectionName = null;
            if (type.equals("Help")) collectionName = "Help Requests";
            else collectionName = "Rescue Requests";


            DocumentReference documentReference = db.collection(collectionName).document(documentID);
            DocumentReference documentReference1 = db.collection("Your Goals: " + uid).document(documentID);

            Map<String, Object> info = new HashMap<>();

            info.put("ID", userID);
            info.put("Information", information);
            info.put("Type", type);

            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(getApplicationContext(), "We are feeling Sad.", Toast.LENGTH_SHORT).show();
                    writeOnYourRequests(userID, "", documentID, information, type, "3"); // updating on user's request
                    documentReference1.delete(); // deleting from volunteers goals.
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Something wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            // nothing to show.
        }
    }

    /*
    updating the user's requests according to the response of volunteers.
     */
    private void writeOnYourRequests(String userID, String volunteerID, String documentID, String information, String type, String status) {

        Map<String, Object> info = new HashMap<>();

        info.put("Information", information);
        info.put("Type", type);
        info.put("VolunteerID", volunteerID);
        info.put("Status", status);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference1 = db.collection("Your Requests: " + userID).document(documentID);
        documentReference1.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // nothing to show
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // nothing to show.
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

    private void start_HomeScreenVolunteer_activity() {
        Intent intent = new Intent(getApplicationContext(), HomeScreenVolunteer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void start_LiveChat_activity()
    {
        Intent intent = new Intent(this, LiveChat.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    private void start_AboutUs_activity()
    {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}