package user;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.databinding.ActivityEmergencyRescueSosBinding;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import navigationBars.DrawerBaseActivity;

public class EmergencyRescueSOS extends DrawerBaseActivity {

    public final String KEY_NAME = "Name";
    public final String KEY_CONTACT = "Contact";
    public final String KEY_LOCATION = "Location";

    ActivityEmergencyRescueSosBinding activityEmergencyRescueSosBinding;

    TextInputEditText locationEditText;
    TextInputEditText nameEditText;
    TextInputEditText contactEditText;
    TextInputLayout locationTextInputLayout;

    Button submitButton;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEmergencyRescueSosBinding=ActivityEmergencyRescueSosBinding.inflate(getLayoutInflater());
        setContentView(activityEmergencyRescueSosBinding.getRoot());
        allocateActivityTitle("Emergency Rescue SOS");


        locationEditText = findViewById(R.id.locationEditTextID);
        nameEditText = findViewById(R.id.nameEditTextID);
        contactEditText = findViewById(R.id.contactEditTextID);
        submitButton = findViewById(R.id.submitButtonID);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);


        getCurrentLocation();

    }

    public void sendRequest(View v) {
        if (isGPSEnabled()) {
            getCurrentLocation();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        documentReference = db.collection("Rescue Requests").document(uid);

        String name = nameEditText.getText().toString().trim();
        String contact = contactEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError("Name can not be empty!");
            nameEditText.requestFocus();
            return;
        }
        if (contact.isEmpty()) {
            contactEditText.setError("Contact can not be empty!");
            contactEditText.requestFocus();
            return;
        }
        if (location.isEmpty()) {
            locationEditText.setError("Location can not be empty!");
            locationEditText.requestFocus();
            return;
        }

        Map<String, Object> info = new HashMap<>();

        info.put(KEY_NAME, name);
        info.put(KEY_CONTACT, contact);
        info.put(KEY_LOCATION, location);

        documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Request Submitted", Toast.LENGTH_SHORT).show();
                start_HomeScreenUser_activity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Request Submit Failed!", Toast.LENGTH_SHORT).show();
                start_HomeScreenUser_activity();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (isGPSEnabled()) {
                    getCurrentLocation();
                }
                else {
                    turnOnGPS();
                }
            }
        }
    }

    private void getCurrentLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (isGPSEnabled()) {
                    LocationServices.getFusedLocationProviderClient(getApplicationContext())
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(EmergencyRescueSOS.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() > 0){
                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        locationEditText.setText("Latitude: "+ latitude + ", Longitude: "+ longitude);
                                        locationEditText.setEnabled(false);
                                    }
                                }
                            }, Looper.getMainLooper());
                }
                else {
                    turnOnGPS();
                }
            }
            else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }


    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(getApplicationContext(), "GPS is already turned on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(EmergencyRescueSOS.this, 2);

                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }


    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return isEnabled;
    }
}