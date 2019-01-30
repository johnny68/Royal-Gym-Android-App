package com.sidd.royalfitnessclub.Fragments.Login;

import com.sidd.royalfitnessclub.Utils.CustomToast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sidd.royalfitnessclub.Activities.Login.LoginActivity;
import com.sidd.royalfitnessclub.R;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sidd.royalfitnessclub.Utils.SharedConstants.regEx;

public class SignupFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    protected View view;
    private EditText fullName, lastName1, lastName2, emailID, mobileNumber, DateofBirth, password, confirmPassword;
    private TextView login;
    private Button signUpButton;
    private CheckBox terms_conditions;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;
    private Animation shakeAnimation;
    private DatePickerDialog datePickerDialog;
    private String DOB_DAY, DOB_MONTH, DOB_YEAR;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){

        view = layoutInflater.inflate(R.layout.fragment_signup, container, false);
        initViews();
        setListeners();
        return view;
    }

    private void setListeners(){
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    private void initViews() {

        fullName         = (EditText)view.findViewById(R.id.fullName);
        lastName1        = (EditText)view.findViewById(R.id.userlastname1);
        lastName2        = (EditText)view.findViewById(R.id.userlastname2);
        emailID          = (EditText)view.findViewById(R.id.userEmailId);
        mobileNumber     = (EditText)view.findViewById(R.id.mobileNumber);
        DateofBirth      = (EditText)view.findViewById(R.id.DateOfBirthE);
        password         = (EditText)view.findViewById(R.id.password);
        confirmPassword  = (EditText)view.findViewById(R.id.confirmPassword);
        signUpButton     = (Button)view.findViewById(R.id.signUpBtn);
        login            = (TextView)view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox)view.findViewById(R.id.terms_conditions);
        linearLayout     = (LinearLayout)view.findViewById(R.id.signup_layout);
        shakeAnimation   = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        progressDialog   = new ProgressDialog(getActivity());

        progressDialog.setCancelable(false);
        DateofBirth.setInputType(InputType.TYPE_NULL);
        DateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicked", "Clicked");
                ShowDatePicker();
            }
        });
        DateofBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("Clicked", "Clicked");
                    ShowDatePicker();
                }
            }
        });

        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),xrp);
            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signUpBtn:
                checkValidation();
                break;
            case R.id.already_user:
                new LoginActivity().replaceFragment("right");
                break;
        }
    }

    private void checkValidation() {
        String getFullName        = fullName.getText().toString();
        String getEmailID         = emailID.getText().toString();
        String getMobileNumber    = mobileNumber.getText().toString();
        String getLocation        = DateofBirth.getText().toString();
        String getPassword        = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();
        String getLastName        = lastName1.getText().toString();
        String getLastName2       = lastName2.getText().toString();
        String getdob_day         = DOB_DAY;
        String getdob_year        = DOB_YEAR;
        String getdob_month       = DOB_MONTH;

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(getEmailID);

        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailID.equals("") || getEmailID.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0
                || getLastName.length() == 0
                || getLastName2.length() == 0
                || getdob_day.length() == 0)
        {
            linearLayout.startAnimation(shakeAnimation);

            new CustomToast().showToast(getActivity(), view,
                    "All fields are required.");
        }

        // Check if email id valid or not
        else if (!matcher.find())
        {
            new CustomToast().showToast(getActivity(), view,"Your Email Id is Invalid.");
        }
        else if (!getConfirmPassword.equals(getPassword)) {

            new CustomToast().showToast(getActivity(), view,"Both password doesn't match.");
        }
        else if (!terms_conditions.isChecked())
        {
            new CustomToast().showToast(getActivity(), view,"Please select Terms and Conditions.");
        }
        else

        {
            progressDialog.setMessage("Registering ......");
            showDialog();
            registerProcess(getFullName, getEmailID, getPassword, getLastName, getLastName2, getdob_day, getdob_month, getdob_year, getMobileNumber);
        }
    }

    private void registerProcess(String getFullName, String getEmailID, String getPassword, String getLastName, String getLastName2, String getdob_day, String getdob_month, String getdob_year, String getMobileNumber) {
        hideDialog();
        new LoginActivity().replaceFragment("right");
    }

    private void ShowDatePicker() {
        Calendar calendar = Calendar.getInstance();
        if (datePickerDialog == null){
            datePickerDialog = DatePickerDialog.newInstance(
                    SignupFragment.this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
        }
        else{
            datePickerDialog.initialize(
                    SignupFragment.this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
        }
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.vibrate(true);
        datePickerDialog.showYearPickerFirst(true);
        datePickerDialog.setTitle(getString(R.string.Birthday));
        datePickerDialog.show(getActivity().getFragmentManager(), "DatePicker");

    }
    private void showDialog(){
        if (!progressDialog.isShowing() && getActivity().getWindow().getDecorView().isShown())
        {
            progressDialog.show();
        }
    }

    private void hideDialog(){
        if (progressDialog.isShowing() && getActivity().getWindow().getDecorView().isShown())
        {
            progressDialog.hide();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }
}
