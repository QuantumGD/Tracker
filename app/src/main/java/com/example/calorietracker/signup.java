package com.example.calorietracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class signup extends AppCompatActivity implements View.OnClickListener {
    EditText dob ;
    EditText mFirstname;
    EditText mSurname;
    EditText mEmail;
    EditText mWeight;
    EditText mHeight;
    EditText mAddr;
    EditText mPost;
    EditText mSPM;
    EditText mUser;
    EditText mPwd;
    TextView cor;
    String password ;
    String fname ;
    String email;
    String sname ;
    String weight;
    String height;
    String addr ;
    String post ;
    String spm;
    String user ;
    String dobb ;
    String level;
    RadioGroup sex;
    RadioButton male;
    RadioButton female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Button btn1 = (Button) findViewById(R.id.Cancel);
        Button btn2 = (Button) findViewById(R.id.Conf);
        Spinner levell = (Spinner) findViewById(R.id.level);
        male =(RadioButton)findViewById(R.id.btnMan);
        female = (RadioButton) findViewById(R.id.btnWoman);

         dob =(EditText) findViewById(R.id.Dob);
         sex = (RadioGroup)findViewById(R.id.ganderGroup);
         mFirstname = (EditText) findViewById(R.id.firstName);
        mEmail = (EditText) findViewById(R.id.email);
         mSurname = (EditText) findViewById(R.id.surName);
         mEmail = (EditText) findViewById(R.id.email);
         mWeight = (EditText) findViewById(R.id.weight);
         mHeight = (EditText) findViewById(R.id.height);
         mAddr = (EditText) findViewById(R.id.addr);
         mPost = (EditText) findViewById(R.id.post);
         mSPM = (EditText) findViewById(R.id.step);
         mUser = (EditText) findViewById(R.id.newUserName);
         mPwd = (EditText) findViewById(R.id.newPwd);
         cor = (TextView) findViewById(R.id.corret);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        male.setOnClickListener(this);
        female.setOnClickListener(this);
        dob.setOnClickListener(this);
        dob.setInputType(InputType.TYPE_NULL);
        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                showDatePickerDialog();
                }
            }
        });


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.Cancel:
                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.Conf:
                confsigup();
             //   submit();
                break;
            case R.id.Dob:
                showDatePickerDialog();
                break;
        }

    }
    protected void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(signup.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                signup.this.dob.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    public boolean validate() {
        boolean valid = true;
         password = mPwd.getText().toString().trim();
         fname = mFirstname.getText().toString().trim();
         sname = mSurname.getText().toString().trim();
         weight = mWeight.getText().toString();
         height = mHeight.getText().toString();
         addr = mAddr.getText().toString().trim();
         post = mPost.getText().toString();
         spm = mSPM.getText().toString();
         user = mUser.getText().toString().trim();
         dobb = dob.getText().toString();



        Pattern p = Pattern.compile("^"+
                          "(?=.*[0-9])"+          //1 number
                          "(?=.*[a-z])"+          // 1 lower a
                          "(?=.*[A-Z])"+          //upper A
                     "(?=.*[@#$%^&=+])"+          //special
                            "(?=\\S+$)"+          //no spaces
                                ".{8,}"+          //min 8
                                    "$");

        Matcher m = p.matcher(password);

        email = mEmail.getText().toString().trim();



        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("pls,Enter correct Email address");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() ||!m.matches()) {
            mPwd.setError("pls,Enter correct Password");
            valid = false;
        } else {
            mPwd.setError(null);
        }

        if (fname.isEmpty() || fname.length()<1 || fname.length()> 20) {
            mFirstname.setError("the length is between 1 and 20");
            valid = false;
        }else{
            mFirstname.setError(null);
        }

        if (sname.isEmpty() || sname.length()<1 || sname.length()>20){
            mSurname.setError("the length is between 1 and 20");
            valid = false;
        }else{
             mSurname.setError(null);
        }
        if (height.isEmpty() || Integer.parseInt(height)< 56 || Integer.parseInt(height)> 319){
            mHeight.setError("pls,Enter correct height");
            valid = false;
        }else{
            mHeight.setError(null);
        }
        if (weight.isEmpty()|| Integer.parseInt(weight)<6 ||Integer.parseInt(weight)>700 ){
            mWeight.setError("pls,Enter correct weight in KG");
            valid = false;
        }else{
            mWeight.setError(null);
        }
        if (addr.isEmpty()) {
            mAddr.setError("pls,Enter correct address");
            valid = false;
        }else{
            mAddr.setError(null);
        }
        if (post.isEmpty()|| post.length() < 3 || post.length() >5 ){
            mPost.setError("pls,Enter correct Postcode");
            valid = false;
        }else{
            mPost.setError(null);
        }
        if (spm.isEmpty()||Integer.parseInt(spm) <1400 || Integer.parseInt(spm)>2600){
            mSPM.setError("Are you OK ? may be 1400 - 2600");
            valid = false;
        }else{
            mSPM.setError(null);
        }
        if (user.isEmpty()){
            mUser.setError("Enter username ");
            valid = false;
        }else{
            mUser.setError(null);
        }
        if (dobb.isEmpty()){
            dob.setError("pls,Enter your birthday");
            valid = false;
        }else{
            dob.setError(null);
        }
        return valid;
    }


    public void exsit (){
        String name =  mUser.getText().toString();
        UsandeAsyncTask u = new  UsandeAsyncTask();
        u.execute(name);
    }

    private class UsandeAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            return RestClient.findUsername(name);
        }

        protected void onPostExecute(String message) {
            try {

                JSONArray jsonObjs = new JSONArray(message);
                String Uname = "";
                String Email = "";
                for (int i = 0; i < jsonObjs.length(); i++) {
                    JSONObject jobject = (JSONObject) jsonObjs.get(i);
                    Uname = jobject.getString("username");
                    JSONObject j = new JSONObject(jobject.getString("userid"));
                    Email = j.getString("email");
                }
                if (mUser.getText().toString().equals(Uname)) {
                    Toast.makeText(getApplicationContext(), "Username already exsit", Toast.LENGTH_SHORT).show();
                }
                if (mEmail.getText().toString().equalsIgnoreCase(Email)){
                    mEmail.setError("Email already exsit");
                    Toast.makeText(getApplicationContext(), "Email already exsit", Toast.LENGTH_SHORT).show();
                }
                if( !mUser.getText().toString().equals(Uname) && !mEmail.getText().toString().equalsIgnoreCase(Email) ){
                    cor.setText("ALL CORRECT");
                }
            }catch (JSONException e) {
            e.printStackTrace();
        }
        }
    }
    public void confsigup(){
        if (validate()) {
            exsit();
        }
        if(cor.getText().toString() == "ALL CORRECT") {
            Intent back = new Intent(signup.this, MainActivity.class);
            startActivity(back);
        }

    }
   // private class PostAsyncTask extends AsyncTask<String, Void, String> {
    //    @Override protected String doInBackground(String... params) {
   //         Course course=new Course(Integer.valueOf(params[0]),params[1],params[2],params[3],params[4],Integer.valueOf(params[5]),Integer.valueOf(params[6]),params[7],params[8],params[9],Integer.valueOf(params[10]),Integer.valueOf(params[11]),Integer.valueOf(params[12]));
    //        RestClient.createCourse(course);
     //       return "Course was added";
     //   }
     //   @Override protected void onPostExecute(String response) {

     //   }
    }
 // public void submit(){
  //    PostAsyncTask post = PostAsyncTask();
   //   post.execute(5,fname,sname,email,dob,weight,height,);


  //  }










