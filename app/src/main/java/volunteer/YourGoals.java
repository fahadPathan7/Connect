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
import android.widget.ImageView;
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

    LinearLayout linearLayout;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ActivityYourGoalsBinding activityYourGoalsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityYourGoalsBinding = ActivityYourGoalsBinding.inflate(getLayoutInflater());
        setContentView(activityYourGoalsBinding.getRoot());

        connectWithIDs();

        home.setOnClickListener(this);
        helpline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);


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

        showGoals();

        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.addView(linearLayout);

        setContentView(view);


        allocateActivityTitle("Your Goals");


        connectWithIDs();

        home.setOnClickListener(this);
        helpline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
    }

    public void connectWithIDs() {
        home=findViewById(R.id.homeMenuID);
        helpline=findViewById(R.id.liveChatMenuID);
        aboutUs=findViewById(R.id.aboutUsMenuID);
    }

    public void showGoals() {
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

                    linearLayout.removeAllViews();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        String information = documentSnapshot.getString("Information");
                        String userID = documentSnapshot.getString("ID");
                        String type = documentSnapshot.getString("Type");

                        String documentID = documentSnapshot.getId();

                        addData(information, documentID, userID, type);
                    }
                }
            });
        } catch (Exception e) {
            //
        }


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
        textView.setText(type + "\n\n" + information);
        textView.setTextSize(16);
        textView.setPadding(0, 0, 0, 15);

        LinearLayout buttonLinearLayout = new LinearLayout(this);
        buttonLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        Button button = new Button(this);
        button.setText("Completed");
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.parseColor("#4CAF50"));
        Typeface typeface = Typeface.create(button.getTypeface(), Typeface.BOLD);
        button.setTypeface(typeface);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        button.setLayoutParams(buttonParams);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform action on click
                writeOnYourSuccess(information, documentID, userID, type);
            }
        });

        Button button1 = new Button(this);
        button1.setText("Cancel");
        button1.setTextColor(Color.WHITE);
        button1.setBackgroundColor(Color.parseColor("#E91E63"));
        Typeface typeface1 = Typeface.create(button1.getTypeface(), Typeface.BOLD);
        button1.setTypeface(typeface1);
        LinearLayout.LayoutParams button1Params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        button1.setLayoutParams(button1Params);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform action on click
                returnData(information, documentID, userID, type);
            }
        });

        buttonLinearLayout.addView(button);
        buttonLinearLayout.addView(button1);


        innerLinearLayout.addView(textView);
        innerLinearLayout.addView(buttonLinearLayout);

        cardView.addView(innerLinearLayout);

        linearLayout.addView(cardView);
    }

    public void writeOnYourSuccess(String information, String documentID, String userID, String type) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            String uid = user.getUid();
            String collectionName = "Your Success: " + uid;
            String documentName = documentID;
            DocumentReference documentReference = db.collection(collectionName).document(documentName);

            DocumentReference documentReference1 = db.collection("Your Goals: " + uid).document(documentName);

            Map<String, Object> info = new HashMap<>();


            info.put("ID", userID);
            info.put(KEY_INFORMATION, information);
            info.put("Type", type);

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

    public void returnData(String information, String documentID, String userID, String type) {

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
                    documentReference1.delete();

                    //showGoals();
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