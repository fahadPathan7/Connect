/*
to show about the current weather.
 */

package user;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.R;
import com.example.android.databinding.ActivityForecastBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import navigationBars.DrawerBaseActivity;

public class Forecast extends DrawerBaseActivity {

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appID = "e53301e27efa0b66d05045d91b2742d3";
    String city = null;
    DecimalFormat df = new DecimalFormat("#");

    TextView temperatureTextView;
    TextView locationTextView;
    TextView feelsLikeView;
    TextView descriptionView;
    TextView humidityView;
    TextView windView;
    TextView pressureView;
    ActivityForecastBinding activityForecastBinding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;

    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForecastBinding = ActivityForecastBinding.inflate(getLayoutInflater());
        setContentView(activityForecastBinding.getRoot());
        allocateActivityTitle("ForeCast");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        documentReference = db.collection("User Profile").document(uid);

        temperatureTextView = findViewById(R.id.temperatureTextViewID);
        locationTextView = findViewById(R.id.locationTextViewID);
        feelsLikeView = findViewById(R.id.feelsLikeID);
        descriptionView = findViewById(R.id.descriptionID);
        humidityView = findViewById(R.id.humidityID);
        windView = findViewById(R.id.windID);
        pressureView = findViewById(R.id.pressureID);


        getUserLocation(); // getting the user location
        setForeCast(); // showing the forecast.
    }

    /*
    showing the user about forecast.
     */
    private void setForeCast() {

        String tempUrl = url + "?q=" + city + "&appid=" + appID;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.16;
                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.16;
                    float pressure = jsonObjectMain.getInt("pressure");
                    int humidity = jsonObjectMain.getInt("humidity");
                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");
                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");
                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");
                    String cityName = jsonResponse.getString("name");


                    String location = cityName + " (" + countryName + ")";
                    locationTextView.setText(location);

                    String temperature = df.format(temp) + "°C\n";
                    temperatureTextView.setText(temperature);

                    String feels = df.format(feelsLike) + "°C";
                    feelsLikeView.setText(feels);
                    descriptionView.setText(description);
                    windView.setText(wind + " m/s");
                    humidityView.setText(humidity + "%");
                    pressureView.setText(pressure + " hpa");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // solving a simple bug here.
                cnt++;
                if (cnt > 1) {
                    //Toast.makeText(getApplicationContext(), "Update Profile!", Toast.LENGTH_SHORT).show();
                    city = "Dhaka";
                }
                else if (cnt > 2) return;
                setForeCast();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    /*
    getting the user location(district) from database.
     */
    private void getUserLocation() {
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    return;
                }
                assert value != null;
                if (value.exists()) {
                    String loc = value.getString("District");
                    locationTextView.setText(loc);
                    city = loc;
                }
            }
        });
    }
}