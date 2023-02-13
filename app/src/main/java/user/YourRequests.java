package user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.android.databinding.ActivityYourRequestsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import navigationBars.DrawerBaseActivity;

public class YourRequests extends DrawerBaseActivity {

    LinearLayout linearLayout;

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

        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.addView(linearLayout);

        setContentView(view);


        allocateActivityTitle("Your Requests");
    }

    public void showRequests() {

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            CollectionReference collectionReference = db.collection("Your Requests: " + uid);
            collectionReference.orderBy("Status", Query.Direction.ASCENDING)
                    .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                            if (e != null) {
                                return;
                            }

                            linearLayout.removeAllViews();

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                String information = documentSnapshot.getString("Information");
                                String volunteerID = documentSnapshot.getString("VolunteerID");
                                String type = documentSnapshot.getString("Type");
                                String status = documentSnapshot.getString("Status");

                                String documentID = documentSnapshot.getId();

                                addData(information, documentID, uid, volunteerID, type, status);
                            }
                        }
                    });
        } catch (Exception e) {
            //
        }


    }

    @SuppressLint("ResourceAsColor")
    public void addData(String information, String documentID, String userID, String volunteerID, String type, String status) {
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
        if (status.equals("3")) status = "Pending";
        else if (status.equals("2")) status = "Assigned";
        else if (status.equals("1")) status = "Verifying";
        else status = "Completed";
        textView.setText(status + "\n\n" + type + "\n\n" + information);
        textView.setTextSize(16);
        textView.setPadding(0, 0, 0, 15);


        LinearLayout buttonLinearLayout = new LinearLayout(this);
        buttonLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        Button button = new Button(this);
        if (status.equals("Verifying")) {
            if (type.equals("Help")) button.setText("Got Help");
            else button.setText("Rescued");
        }

        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.parseColor("#4CAF50"));
        Typeface typeface = Typeface.create(button.getTypeface(), Typeface.BOLD);
        button.setTypeface(typeface);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        button.setLayoutParams(buttonParams);

        Button button1 = new Button(this);
        button1.setText("Discard");
        button1.setTextColor(Color.WHITE);
        button1.setBackgroundColor(Color.parseColor("#E91E63"));
        Typeface typeface1 = Typeface.create(button1.getTypeface(), Typeface.BOLD);
        button1.setTypeface(typeface1);
        LinearLayout.LayoutParams button1Params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        button1.setLayoutParams(button1Params);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                button1.setEnabled(false);
                //Perform action on click
                writeOnYourSuccess(information, documentID, userID, volunteerID, type);
            }
        });

        final String tempStatus = status;
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1.setEnabled(false);
                button.setEnabled(false);
                if (tempStatus.equals("Pending")) {
                    removeRequest(documentID, type, userID);
                }
                else writeOnYourGoals(information, documentID, userID, volunteerID, type);
            }
        });

        buttonLinearLayout.addView(button);
        buttonLinearLayout.addView(button1);

        if (status.equals("Pending")) {
            buttonLinearLayout.removeView(button);
        }
        else if (status.equals("Assigned") || status.equals("Completed")) {
            buttonLinearLayout.removeAllViews();
        }


        innerLinearLayout.addView(textView);
        innerLinearLayout.addView(buttonLinearLayout);


        cardView.addView(innerLinearLayout);

        linearLayout.addView(cardView);
    }

    public void writeOnYourGoals(String information, String documentID, String userID, String volunteerID, String type) {

        try {
            String collectionName = "Your Goals: " + volunteerID;

            DocumentReference documentReference = db.collection(collectionName).document(documentID);

            Map<String, Object> info = new HashMap<>();

            info.put("ID", userID);
            info.put("Information", information);
            info.put("Type", type);


            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Request assigned again!", Toast.LENGTH_SHORT).show();
                    writeOnYourRequests(userID, volunteerID, documentID, information, type, "2");
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

    public void writeOnYourRequests(String userID, String volunteerID, String documentID, String information, String type, String status) {

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

    public void removeRequest(String documentID, String type, String userID) {
        try {
            DocumentReference documentReference;
            if (type.equals("Help")) documentReference = db.collection("Help Requests").document(documentID);
            else documentReference = db.collection("Rescue Requests").document(documentID);

            documentReference.delete();

            documentReference = db.collection("Your Requests: " + userID).document(documentID);
            documentReference.delete();

            Toast.makeText(getApplicationContext(), "Your request has been removed.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Try again later!", Toast.LENGTH_SHORT).show();
        }
    }


    public void writeOnYourSuccess(String information, String documentID, String userID, String volunteerID, String type) {

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
                    writeOnYourRequests(userID, volunteerID, documentID, information, type, "4");
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
}