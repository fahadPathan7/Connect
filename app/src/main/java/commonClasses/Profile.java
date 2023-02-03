package commonClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import navigationBars.DrawerBaseActivity;

public class Profile extends DrawerBaseActivity {
    private static final String TAG = "MainActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView textViewData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef;
    //= db.document("Infobook/My First Note");

    ActivityProfileBinding activityProfileBinding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());

//        editTextTitle = findViewById(R.id.edit_text_title);
//        editTextDescription = findViewById(R.id.edit_text_description);
//        textViewData = findViewById(R.id.text_view_data);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        noteRef = db.collection("Infobook").document(uid);
    }

    public void saveNote(View v) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_TITLE, title);
        note.put(KEY_DESCRIPTION, description);

        noteRef.set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Profile.this, "Note saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    public void loadNote(View v) {
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String title = documentSnapshot.getString(KEY_TITLE);
                            String description = documentSnapshot.getString(KEY_DESCRIPTION);

                            //Map<String, Object> note = documentSnapshot.getData();

                            textViewData.setText("Title: " + title + "\n" + "Description: " + description);
                        } else {
                            Toast.makeText(Profile.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}