package commonClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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

    LinearLayout linearLayout;
    EditText chatEditText;
    TextView nameText;
    ActivityLiveChatBinding activityLiveChatBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLiveChatBinding = ActivityLiveChatBinding.inflate(getLayoutInflater());
        setContentView(activityLiveChatBinding.getRoot());

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_live_chat, null);

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 20, 0, 0);
        linearLayout.setPadding(0, 0, 0, 30);
        linearLayout.setLayoutParams(params);

        setName();
        showChat();

        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.addView(linearLayout);

        setContentView(view);
    }


    public void showChat() {

        try {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            String collectionName = "Live Chat";

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference liveChat = db.collection(collectionName);

            //Toast.makeText(getApplicationContext(), "here", Toast.LENGTH_SHORT).show();

            liveChat.orderBy("writeTime", Query.Direction.ASCENDING)
                    .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    linearLayout.removeAllViews();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        String information = documentSnapshot.getString("Chat");
                        String name = documentSnapshot.getString("Name");

                        addData("(" + name + ")\n" + information);
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "exception", Toast.LENGTH_SHORT).show();
        }
    }


    public void addData(String data) {
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

        // Add your content to the cardView
        TextView textView = new TextView(this);
        textView.setText(data);
        textView.setTextSize(15);
        cardView.addView(textView);

        // Add the cardView to the linearLayout
        linearLayout.addView(cardView);
    }

    public void setName() {
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

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            String time = String.format("%04d%02d%02d%02d%02d%02d",year, month, day, hour, minute, second);

            String name = nameText.getText().toString();
            //chatEditText.setText("");

            info.put("Name", name);
            info.put("Chat", chat);
            info.put("writeTime", time);

            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    chatEditText.setText("");
                    //showChat();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Message send Failed!", Toast.LENGTH_SHORT).show();
                    //start_HomeScreenUser_Activity();
                }
            });
        } catch (Exception e) {
            //
        }
    }

    public void start_LiveChat_activity() {
        Intent intent = new Intent(LiveChat.this, LiveChat.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}