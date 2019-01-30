package com.sidd.royalfitnessclub.Fragments.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.Button;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.sidd.royalfitnessclub.Activities.MainActivity;
import com.sidd.royalfitnessclub.R;
import com.sidd.royalfitnessclub.Utils.CustomToast;
import com.sidd.royalfitnessclub.Utils.SharedConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sidd.royalfitnessclub.Utils.SharedConstants.PREF_LOGIN;
import static com.sidd.royalfitnessclub.Utils.SharedConstants.regEx;

public class LoginFragment extends Fragment implements View.OnClickListener {

    protected View view;
    private static FragmentManager fragmentManager;
    private static ProgressDialog progressDialog;
    private static SharedPreferences sharedPreferences;
    private Animation animation;
    private EditText emailID, password;
    private Button loginButton;
    private TextView forgotPassword, signUp;
    private CheckBox show_hide_password;
    private RelativeLayout loginLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, viewGroup, false);
        sharedPreferences = getActivity().getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        inItViews();
        setListeners();
        return view;
    }

    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    show_hide_password.setText(R.string.hide_pwd);
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    show_hide_password.setText(R.string.show_pwd);
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void inItViews() {
        fragmentManager     = this.getFragmentManager();
        progressDialog      = new ProgressDialog(getActivity(), "HI");
                                progressDialog.setCancelable(false);
        emailID             = (EditText) view.findViewById(R.id.login_emailid);
        password            = (EditText) view.findViewById(R.id.login_password);
        loginButton         = (Button) view.findViewById(R.id.loginBtn);
        forgotPassword      = (TextView) view.findViewById(R.id.forgot_password);
        signUp              = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password  = (CheckBox) view.findViewById(R.id.show_hide_password);
        loginLayout         = (RelativeLayout) view.findViewById(R.id.login_layout);
        animation           = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);

        XmlResourceParser xmlResourceParser = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList stateList = ColorStateList.createFromXml(getResources(), xmlResourceParser);
            forgotPassword.setTextColor(stateList);
            show_hide_password.setTextColor(stateList);
            show_hide_password.setTextColor(stateList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;
            case R.id.forgot_password:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        .replace(R.id.frame, new ForgotFragment(), SharedConstants.Forgot_Password).commit();
                break;
            case R.id.createAccount:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frame, new SignupFragment(), SharedConstants.SignUp_Fragment).commit();
                break;
        }
    }

    private void checkValidation() {
        String CemailID = emailID.getText().toString();
        String Cpaassword = password.getText().toString();
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(CemailID);
        if (CemailID.equals("") || CemailID.length() == 0 || Cpaassword.equals("") || Cpaassword.length() == 0) {
            loginLayout.startAnimation(animation);
            new CustomToast().showToast(getActivity(), view, getString(R.string.credentials));
        } else if (!matcher.find()) {
            loginLayout.startAnimation(animation);
            new CustomToast().showToast(getActivity(), view, getString(R.string.invalid));
        } else {
            progressDialog.setTitle(getString(R.string.logging));
            showDialog();
            LoginProcess(CemailID, Cpaassword);
        }
    }

    private void LoginProcess(String cemailID, String cpaassword) {

        goToProfile();
    }


    private void hideDialog() {
        if (progressDialog.isShowing() && getActivity().getWindow().getDecorView().isShown()) {
            progressDialog.hide();
        }
    }

    private void showDialog() {
        if (!progressDialog.isShowing() && getActivity().getWindow().getDecorView().isShown()) {
            progressDialog.show();
        }
    }

    private void goToProfile() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
