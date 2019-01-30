package com.sidd.royalfitnessclub.Activities.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sidd.royalfitnessclub.Fragments.Login.LoginFragment;
import com.sidd.royalfitnessclub.Activities.MainActivity;
import com.sidd.royalfitnessclub.R;
import com.sidd.royalfitnessclub.Utils.SharedConstants;

import static com.sidd.royalfitnessclub.Utils.SharedConstants.IS_LOGGED_IN;
import static com.sidd.royalfitnessclub.Utils.SharedConstants.PREF_LOGIN;

public class LoginActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;
    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        checkLoggedInStatus(savedInstanceState);
    }

    private void checkLoggedInStatus(Bundle savedIns) {
        preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        if (preferences.getBoolean(IS_LOGGED_IN, false)){
            goToProfile();
        } else {
            if (savedIns == null) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frame, new LoginFragment())
                        .commit();
            }
        }

    }

    public void replaceFragment(String TAG) {

        if (TAG.equals("right")) {
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                    .replace(R.id.frame, new LoginFragment(), SharedConstants.Login_Fragment)
                    .commit();
        } else if (TAG.equals("left")) {
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                    .replace(R.id.frame, new LoginFragment(), SharedConstants.Login_Fragment)
                    .commit();
        } else {

            Log.d("Error in replacing", TAG);
        }

    }

    public void goToProfile() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        Fragment ForgotFragment = fragmentManager.findFragmentByTag(SharedConstants.Forgot_Password);
        Fragment SignupFragment = fragmentManager.findFragmentByTag(SharedConstants.SignUp_Fragment);
        if (ForgotFragment != null) {
            replaceFragment("left");
        } else if (SignupFragment != null) {
            replaceFragment("right");
        } else {
            super.onBackPressed();
        }
    }
}
