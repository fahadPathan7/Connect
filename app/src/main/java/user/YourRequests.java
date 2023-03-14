/*
this class is used to track user requests.
there are four levels of requests.
1 -> verifying (volunteer sends that he completed his task and now the user will verify it)
2 -> Assigned (a volunteer has accepted the request of the user)
3 -> pending (the request is not accepted by any volunteer)
4 -> completed (the request is now completed)
 */

package user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityYourRequestsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class YourRequests extends DrawerBaseActivity implements View.OnClickListener {

    LinearLayout linearLayout;

    BottomNavigationItemView home, aboutUs, liveChat;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ActivityYourRequestsBinding activityYourRequestsBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityYourRequestsBinding = ActivityYourRequestsBinding.inflate(getLayoutInflater());
        setContentView(activityYourRequestsBinding.getRoot());


        // here
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_help_requests, null);

        // in this layout the cardViews of requests (info) will be added.
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

        // mother layout. in this layout the linearLayout will be added.
        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.addView(linearLayout);

        setContentView(view); // setting the view.


        allocateActivityTitle("Your Requests"); // nav bar title.

        connectWithIDs();

        home.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        liveChat.setOnClickListener(this);
    }

    private void connectWithIDs() {
        home = findViewById(R.id.homeMenuID);
        aboutUs = findViewById(R.id.aboutUsMenuID);
        liveChat = findViewById(R.id.liveChatMenuID);
    }


    /*
    showing the user about the requests.
     */
    private void showRequests() {

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            CollectionReference collectionReference = db.collection("Your Requests: " + uid); // reference
            collectionReference.orderBy("Status", Query.Direction.ASCENDING) // shorting the requests.
                    .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                            if (e != null) {
                                return;
                            }

                            linearLayout.removeAllViews(); // removing views. because if there is a change in database the
                                // the views will be added again. so after re-adding removing the previous views to show
                                // the latest.

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                // getting info's
                                String information = documentSnapshot.getString("Information");
                                String volunteerID = documentSnapshot.getString("VolunteerID");
                                String type = documentSnapshot.getString("Type");
                                String status = documentSnapshot.getString("Status");

                                String documentID = documentSnapshot.getId();

                                addData(information, documentID, uid, volunteerID, type, status); // for adding info's in
                                    // a cardView and after that all the cardViews will be added to the linearLayout.
                            }
                        }
                    });
        } catch (Exception e) {
            // nothing to do
        }


    }

    /*
    to add the info's in cardView and after that all the cardViews will be added to the linearLayout
     */
    @SuppressLint("ResourceAsColor")
    private void addData(String information, String documentID, String userID, String volunteerID, String type, String status) {
        // each info will be added in each cardView
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

        // this layout contains the info's and the buttons required for a single request.
        LinearLayout innerLinearLayout = new LinearLayout(this);
        innerLinearLayout.setOrientation(LinearLayout.VERTICAL);

        // adding the info into the cardView
        TextView textView = new TextView(this);
        // status is according to it's type. here we using numbers primarily because we need to sort according to the states.
        if (status.equals("3")) status = "Pending";
        else if (status.equals("2")) status = "Assigned";
        else if (status.equals("1")) status = "Verifying";
        else status = "Completed";
        textView.setText(status + "\n\n" + type + "\n\n" + information);
        textView.setTextSize(16);
        textView.setPadding(0, 0, 0, 15);

        // if there is choice for the user then maximum two buttons will be needed. for setting the buttons
        // horizontally we need an extra linearLayout.
        LinearLayout buttonLinearLayout = new LinearLayout(this);
        buttonLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        // the positive button. it will be shown to the user if the volunteer thinks he has completed his task.
        // and if the user verifies then it will be added to the success list of the volunteer.
        Button button = new Button(this);
        if (status.equals("Verifying")) {
            // button text will be according to the type
            if (type.equals("Help")) button.setText("Got Help");
            else button.setText("Rescued");
        }

        // style of the positive button. (to verify)
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.parseColor("#4CAF50"));
        Typeface typeface = Typeface.create(button.getTypeface(), Typeface.BOLD);
        button.setTypeface(typeface);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        button.setLayoutParams(buttonParams);

        // the negative button (to discard)
        Button button1 = new Button(this);
        button1.setText("Discard");
        button1.setTextColor(Color.WHITE);
        button1.setBackgroundColor(Color.parseColor("#E91E63"));
        Typeface typeface1 = Typeface.create(button1.getTypeface(), Typeface.BOLD);
        button1.setTypeface(typeface1);
        LinearLayout.LayoutParams button1Params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        button1.setLayoutParams(button1Params);

        // by clicking the button he verifies that he got what he requested.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // for avoiding unnecessary inputs setting buttons disabled.
                button.setEnabled(false);
                button1.setEnabled(false);

                writeOnYourSuccess(information, documentID, userID, volunteerID, type); // writing on volunteer's success list.
            }
        });

        // if the status is in pending or he didn't get the help he requested he can discard.
        final String tempStatus = status;
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // for avoiding unnecessary inputs setting buttons disabled.
                button1.setEnabled(false);
                button.setEnabled(false);
                if (tempStatus.equals("Pending")) {
                    removeRequest(documentID, type, userID); // if he thinks that he doesn't want the help/rescue he
                        // requested then he can discard the request. it will only be possible if the request is in
                        // pending state. otherwise he can not cancel. but the volunteer who accepted the request can cancel
                        // from his side. after that the request can be finally cancelled by the user.
                }
                else writeOnYourGoals(information, documentID, userID, volunteerID, type); // if he thinks he did not
                    // the help he requested then he can discard and after that it will be written on the volunteer's goal list.
            }
        });

        // adding the buttons the layout.
        buttonLinearLayout.addView(button);
        buttonLinearLayout.addView(button1);

        if (status.equals("Pending")) {
            // if the status is in pending state. then the positive button should be hidden.
            buttonLinearLayout.removeView(button);
        }
        else if (status.equals("Assigned") || status.equals("Completed")) {
            // if the states are in assigned or completed then there is no choice for the user.
            buttonLinearLayout.removeAllViews();
        }

        // this is the layout which contains the info and the buttons.
        innerLinearLayout.addView(textView);
        innerLinearLayout.addView(buttonLinearLayout);

        // and the cardView contains everything of a single request.
        cardView.addView(innerLinearLayout);

        // all the requests are contained by this layout. and a scrollView contains this layout.
        linearLayout.addView(cardView);
    }


    /*
    if the user does not verify that he didn't what he wanted. then the request will be written again on volunteer's goal list.
     */
    private void writeOnYourGoals(String information, String documentID, String userID, String volunteerID, String type) {

        try {
            String collectionName = "Your Goals: " + volunteerID;

            DocumentReference documentReference = db.collection(collectionName).document(documentID); // reference

            Map<String, Object> info = new HashMap<>();

            info.put("ID", userID);
            info.put("Information", information);
            info.put("Type", type);

            // writing on volunteer's success list
            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Request assigned again!", Toast.LENGTH_SHORT).show();
                    writeOnYourRequests(userID, volunteerID, documentID, information, type, "2"); // updating on user's
                        // requests. that his request has been completed.
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
    to update the user's request list.
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
                //
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });
    }

    /*
    if the user thinks he does not need what he requested. then he can discard the request if the request
    is in pending state.
     */
    private void removeRequest(String documentID, String type, String userID) {
        try {
            DocumentReference documentReference;
            if (type.equals("Help")) documentReference = db.collection("Help Requests").document(documentID);
            else documentReference = db.collection("Rescue Requests").document(documentID);

            documentReference.delete(); // deleting from help/rescue requests

            documentReference = db.collection("Your Requests: " + userID).document(documentID);
            documentReference.delete(); // deleting from user's requests.

            Toast.makeText(getApplicationContext(), "Your request has been removed.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Try again later!", Toast.LENGTH_SHORT).show();
        }
    }


    /*
    after the user verifies that he got what he requested, then it will be written the the volunteers success list.
     */
    private void writeOnYourSuccess(String information, String documentID, String userID, String volunteerID, String type) {

        try {
            String collectionName = "Your Success: " + volunteerID;
            DocumentReference documentReference = db.collection(collectionName).document(documentID);

            Map<String, Object> info = new HashMap<>();

            info.put("ID", userID);
            info.put("Information", information);
            info.put("Type", type);

            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(getApplicationContext(), "We are pledged!", Toast.LENGTH_SHORT).show();
                    writeOnYourRequests(userID, volunteerID, documentID, information, type, "4"); // updating the
                        // user's request list. (he now got the help what he wanted. '4' represents that)
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


    private void start_AboutUs_activity() {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    private void start_LiveChat_activity() {
        Intent intent = new Intent(this, LiveChat.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_HomeScreenUser_activity() {
        Intent intent = new Intent(this, HomeScreenUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.aboutUsMenuID) {
            start_AboutUs_activity();
        }
        else if (v.getId() == R.id.liveChatMenuID) {
            start_LiveChat_activity();
        }
        else if (v.getId() == R.id.homeMenuID) {
            start_HomeScreenUser_activity();
        }
    }
}