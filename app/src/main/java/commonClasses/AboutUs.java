/*
to show the users about the developers of this app.
 */

package commonClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.android.R;
import com.example.android.databinding.ActivityAboutUsBinding;

import navigationBars.DrawerBaseActivity;

public class AboutUs extends DrawerBaseActivity {
    ActivityAboutUsBinding activityAboutUsBinding;

    LinearLayout fahad; // to work with Fahad
    RelativeLayout sanzida; // to work with Sanzida

    Animation left, right; // creating anim variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutUsBinding=ActivityAboutUsBinding.inflate(getLayoutInflater()) ;
        setContentView(activityAboutUsBinding.getRoot());
        allocateActivityTitle("About Us");

        fahad = findViewById(R.id.fahadID);
        sanzida = findViewById(R.id.sanzidaID);

        left = AnimationUtils.loadAnimation(this, R.anim.left); // settings with id
        right = AnimationUtils.loadAnimation(this, R.anim.right); // setting with id

        fahad.setAnimation(left); // views of fahad wil come from the left
        sanzida.setAnimation(right); // views of sanzida will come from rigth
    }
}