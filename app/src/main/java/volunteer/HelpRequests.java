/*
to show the volunteers about the help requests of the users.
 */

package volunteer;

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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.android.R;
import com.example.android.databinding.ActivityHelpRequestsBinding;
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

public class HelpRequests extends DrawerBaseActivity implements View.OnClickListener {

    public final String KEY_INFORMATION = "Information";

    BottomNavigationItemView home;
    BottomNavigationItemView helpline;
    BottomNavigationItemView aboutUs;


    LinearLayout linearLayout;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference helpRequests = db.collection("Help Requests");

    ActivityHelpRequestsBinding activityHelpRequestsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHelpRequestsBinding = ActivityHelpRequestsBinding.inflate(getLayoutInflater());
        setContentView(activityHelpRequestsBinding.getRoot());


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_help_requests, null);

        // in this layout all the requests will be added.
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 20, 0, 0);
        linearLayout.setPadding(0, 0, 0, 30);
        linearLayout.setLayoutParams(params);

        showRequests(); // showing the requests.

        // the linearLayout will all the requests will be added in this view.
        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.addView(linearLayout);

        setContentView(view); // setting the view


        allocateActivityTitle("Help Requests");


        home=findViewById(R.id.homeMenuID);
        helpline=findViewById(R.id.liveChatMenuID);
        aboutUs=findViewById(R.id.aboutUsMenuID);

        home.setOnClickListener(this);
        helpline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
    }

    /*
    showing all the help requests.
     */
    private void showRequests() {

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            helpRequests.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    linearLayout.removeAllViews(); // removing all the views before re-showing.

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        String information = documentSnapshot.getString("Information");
                        String userID = documentSnapshot.getString("ID");
                        String type = documentSnapshot.getString("Type");

                        if (userID.equals(uid)) continue; // checking if the request is sent by the same volunteer or not.
                            // if the volunteer himself sent the request then he will not see the request.

                        String documentID = documentSnapshot.getId();

                        addData(information, documentID, userID, type); // passing the info's to another method to add them
                            // in a view and after that to show on the screen.
                    }
                }
            });
        } catch (Exception e) {
            // nothing to show.
        }
    }


    /*
    adding the requests in some views to show the volunteers.
     */
    @SuppressLint("ResourceAsColor")
    private void addData(String information, String documentID, String userID, String type) {
        // in this view everything about a single request will be added.
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

        // the info's and a button will be added
        LinearLayout innerLinearLayout = new LinearLayout(this);
        innerLinearLayout.setOrientation(LinearLayout.VERTICAL);

        // adding the info's
        TextView textView = new TextView(this);
        textView.setText(information);
        textView.setTextSize(16);
        textView.setPadding(0, 0, 0, 15);

        // adding a button to make sure that the volunteers can help by clicking on it.
        Button button = new Button(this);
        button.setText("Help");
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(R.color.light_theme_color);
        Typeface typeface = Typeface.create(button.getTypeface(), Typeface.BOLD);
        button.setTypeface(typeface);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                // if a volunteer accepts a request then the request will be written on volunteers goals list.
                writeOnYourGoals(information, documentID, userID, type);
            }
        });


        innerLinearLayout.addView(textView);
        innerLinearLayout.addView(button);

        cardView.addView(innerLinearLayout);

        linearLayout.addView(cardView);
    }

    /*
    writing on volunteer's goal list.
     */
    private void writeOnYourGoals(String information, String documentID, String userID, String type) {

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            String collectionName = "Your Goals: " + uid;
            String documentName = documentID;

            DocumentReference documentReference = db.collection(collectionName).document(documentName);
            DocumentReference documentReference1 = db.collection("Help Requests").document(documentName);

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
                    documentReference1.delete(); // after adding to volunteers goal list. it should be removed from the help
                        // requests.
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Something wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            // nothing to show the volunteers.
        }
    }

    /*
    if the request of the user is accepted by any volunteer then it should be updated on user's requests list.
     */
    private void writeOnYourRequests(String userID, String volunteerID, String documentID, String information, String type) {

        Map<String, Object> info = new HashMap<>();

        info.put("Information", information);
        info.put("Type", type);
        info.put("VolunteerID", volunteerID);
        info.put("Status", "2"); // assigned.

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference1 = db.collection("Your Requests: " + userID).document(documentID);
        documentReference1.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // no need to show.
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // no need to show.
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