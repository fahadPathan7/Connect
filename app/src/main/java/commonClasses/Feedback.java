package commonClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityFeedbackBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import navigationBars.DrawerBaseActivity;

public class Feedback extends DrawerBaseActivity {

    TextInputEditText feedbackEditText;
    Button sendButton;

    ActivityFeedbackBinding activityFeedbackBinding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFeedbackBinding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(activityFeedbackBinding.getRoot());
        allocateActivityTitle("Feedback");

        feedbackEditText = findViewById(R.id.feedbackEditTextID);
        sendButton = findViewById(R.id.sendButtonID);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
    }

    public void sendFeedback() {
        try {
            String feedback = feedbackEditText.getText().toString().trim();
            if (feedback.isEmpty()) {
                feedbackEditText.setError("Can not be empty!");
                feedbackEditText.requestFocus();
                return;
            }


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DocumentReference documentReference = db.collection("Feedback").document(uid);


            Map<String, Object> info = new HashMap<>();
            info.put("Feedback", feedback);

            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Thank you for your valuable feedback!", Toast.LENGTH_SHORT).show();
                    //start_HomeScreenUser_Activity();
                    feedbackEditText.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Feedback send Failed!", Toast.LENGTH_SHORT).show();
                    //start_HomeScreenUser_Activity();
                }
            });
        } catch (Exception e) {
            //
        }
    }
}