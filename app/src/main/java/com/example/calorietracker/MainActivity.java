package com.example.calorietracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    Button signon;
    ToggleButton togglePwd;
    EditText uername;
    EditText pwd;
    TextView checkName;
    TextView checkPwd;
    SharedPreferences myhealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.btnLogin);
        signon = (Button) findViewById(R.id.signUp);
        togglePwd = (ToggleButton) findViewById(R.id.show);
        uername = (EditText) findViewById(R.id.userName);
        pwd = (EditText) findViewById(R.id.pwd);
        checkName = (TextView) findViewById(R.id.checkName);
        checkPwd = (TextView) findViewById(R.id.checkPwd);
        myhealth = getSharedPreferences("fname",Context.MODE_PRIVATE);
        login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    login.requestFocus();
                    login.setFocusableInTouchMode(true);
                    login.setFocusable(true);
                }
            }
        });

        togglePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //show
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //not show
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        login.setOnClickListener(this);
        signon.setOnClickListener(this);
    }
    //*******
    //signin

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnLogin:
                login();
                break;
            case R.id.signUp:
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public boolean nameValid(){
        if (checkName.getText().toString() == "Right"&& checkPwd.getText().toString() == "Right"){
            return true;
        }
        else{
            return false;
        }
    }

    public void login (){
        if(valid()){
            String name =  uername.getText().toString();
            UsernameAsyncTask u = new  UsernameAsyncTask();
            u.execute(name);
            if(nameValid() == true) {
                Intent logint = new Intent(MainActivity.this, homepage.class);
                startActivity(logint);
            }
        }
    }
    //hash pwd MD5
    public static String generateMd5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff).toUpperCase();
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    private class UsernameAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = params[0];

            return RestClient.findUsername(name);
        }

        protected void onPostExecute(String message) {
            try {
                JSONArray jsonObjs = new JSONArray(message);
                String Uname = "";
                String pwds = "";
                String fname = "";
                String uid = "";
                String addr = "";

                checkName.setText("");
                checkPwd.setText("");
                for (int i = 0; i < jsonObjs.length(); i++) {
                    JSONObject jobject = (JSONObject) jsonObjs.get(i);
                    Uname = jobject.getString("username");
                    JSONObject j = new JSONObject(jobject.getString("userid"));
                    uid = j.getString("userid");
                    pwds = jobject.getString("pwdHash");
                    fname = j.getString("firstName");
                    addr = j.getString("address");

                }
                SharedPreferences.Editor eFname = myhealth.edit();
                eFname.putString("firstNA",fname);
                eFname.putString("Uid",uid);
                eFname.putString("Addr",addr);
                eFname.commit();
                checkName.setText(pwds);
                String pwds5 = generateMd5(pwd.getText().toString().trim());
                if (uername.getText().toString().trim().equals(Uname)) {
                    checkName.setText("Right");
                } else {
                    checkName.setText("Wrong");
                    Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                }
                if (pwds5.equals(pwds)) {
                    checkPwd.setText("Right");
                    Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_SHORT).show();
                } else {
                    checkPwd.setText("Wrong");
                    Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean valid(){
        boolean valid = true;
        String uname = uername.getText().toString();
        String ppwd = pwd.getText().toString();
        if (uname.isEmpty() ){
            uername.setError("Enter username");
            valid = false;
        }else{
            uername.setError(null);
        }
        if (ppwd.isEmpty()){
            pwd.setError("pls,Enter your password");
            valid = false;
        }else{
            pwd.setError(null);
        }
        return valid;
    }
}
