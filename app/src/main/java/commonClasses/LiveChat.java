/*
to make a platform where all the users and volunteers can communicate among them without any barrier.
 */

package commonClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityLiveChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import navigationBars.DrawerBaseActivity;

public class LiveChat extends DrawerBaseActivity {

    // creating variables.
    LinearLayout linearLayout;
    EditText chatEditText;
    TextView nameText;
    ActivityLiveChatBinding activityLiveChatBinding;

    BottomNavigationView bottomNavigationView;
    BottomNavigationItemView home, aboutUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLiveChatBinding = ActivityLiveChatBinding.inflate(getLayoutInflater());
        setContentView(activityLiveChatBinding.getRoot());


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_live_chat, null);


        // this layout will contain all the chats. in addData method we will add chats in it.
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 20, 0, 0);
        linearLayout.setPadding(0, 0, 0, 30);
        linearLayout.setLayoutParams(params);

        setName(); // setting the users name in a hidden textView.
        showChat(); // showing the chats.

        // this is the mother layout. it will contain the linearLayout of the chats.
        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.addView(linearLayout);

        setContentView(view); // showing the view.


        allocateActivityTitle("Live Chat");
    }


    /*
    to show the chats
     */
    private void showChat() {

        try {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            String collectionName = "Live Chat";

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference liveChat = db.collection(collectionName); // reference of the database where the chats
                // are stored.


            // this will read chats from database and show the users according to the ascending
            // order of the field writeTime. so the sequence will be maintained.
            liveChat.orderBy("writeTime", Query.Direction.ASCENDING)
                    .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    linearLayout.removeAllViews(); // removing all the chats from linearLayout because in the next loop
                        // the chats will be read again and it will write the chats on the screen again. If I don't remove here
                        // all the chats will be written again and again.

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        String information = documentSnapshot.getString("Chat");
                        String name = documentSnapshot.getString("Name");

                        addData("(" + name + ")\n" + information); // passing the chat to show.
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "exception", Toast.LENGTH_SHORT).show();
        }
    }


    private void addData(String data) {
        CardView cardView = new CardView(this); // inside the cardView single chats will be shown

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(30, 5, 30, 5);

        cardView.setLayoutParams(params);
        cardView.setRadius(10);
        cardView.setContentPadding(20, 20, 20, 20);
        cardView.setCardElevation(20);

        // Adding chat text into the cardView
        TextView textView = new TextView(this);
        textView.setText(data);
        textView.setTextSize(15);
        cardView.addView(textView); // adding text

        linearLayout.addView(cardView); // adding all the cardViews into the linearLayout.
    }


    /*
    to set the name of the current user into a hidden textView.
    from the textView we will read the name and attach it with the chat.
     */
    private void setName() {
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userProfile = db.collection("User Profile").document(uid);
            userProfile.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                    if (error != null) {
                        return;
                    }
                    if (value.exists()) {
                        nameText = findViewById(R.id.nameTextViewID);
                        nameText.setText(value.getString("Name"));
                    }
                }
            });
        } catch (Exception e) {
            //
        }
    }

    /*
    to write the chat in the database.
     */
    public void sendChat(View v) {
        try {
            chatEditText = findViewById(R.id.chatEditTextID);


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            String collectionName = "Live Chat";

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference documentReference = db.collection(collectionName).document();

            String chat = chatEditText.getText().toString().trim();

            if (chat.isEmpty()) {
                chatEditText.setError("Message can not be empty!");
                chatEditText.requestFocus();
                return;
            }


            Map<String, Object> info = new HashMap<>();

            // getting the time to sort the chats according to it.
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            String time = String.format("%04d%02d%02d%02d%02d%02d",year, month, day, hour, minute, second);

            String name = nameText.getText().toString();

            info.put("Name", name);
            info.put("Chat", chat);
            info.put("writeTime", time);

            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    chatEditText.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Message send Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            // nothing to show the user.
        }
    }

}