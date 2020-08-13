package com.example.calorietracker;

import java.util.Date;

public class Course {
    private int userid;
    private  String First_name;
    private  String Surname;
    private  String Email;
    private  Date Dob;
    private  int Height;
    private int Weight;
    private  String Gender;
    private  String Address;
    private  int Postcode;
    private int Levelofactivity;
    private  int Steps_per_mile;

public Course(Integer userid,String First_name,String Surname,String Email,
              Date Dob,int Height,int Weight,String Gender,String Address,int Postcode,
              int Levelofactivity,int Steps_per_mile){
    this.userid = userid;
    this.First_name = First_name;
    this.Surname = Surname;
    this.Email = Email;
    this.Dob = Dob;
    this.Height = Height;
    this.Weight = Weight;
    this.Gender = Gender;
    this.Address = Address;
    this.Postcode = Postcode;
    this.Levelofactivity = Levelofactivity;
    this.Steps_per_mile = Steps_per_mile;

}
public Course(){
}
public Integer getUserID(){
    return userid;
}
    public String getFirst_name(){
        return First_name;
    }
    public String getSurname(){
        return Surname;
    }
    public String getEmail(){
        return Email;
    }
    public Date getDob(){
        return Dob;
    }
    public Integer getHeight(){
        return Height;
    }
    public Integer getWeight(){
        return Weight;
    }
    public String getGender(){
        return Gender;
    }
    public String getAddress(){
        return Address;
    }
    public Integer getPostcode(){
        return Postcode;
    }
    public Integer getLevelofactivity(){
        return Levelofactivity;
    }
    public Integer getSteps_per_mile(){
        return Steps_per_mile;
    }
    public void setUserid(Integer userid){
    this.userid=userid;
    }
    public void setFirst_name(String First_name){
        this.First_name=First_name;
    }
    public void setSurname(String Surname){
        this.Surname=Surname;
    }
    public void setEmail(String Email){
        this.Email=Email;
    }
    public void setDob(Date Dob){
        this.Dob=Dob;
    }
    public void setHeight(Integer Height){
        this.Height=Height;
    }
    public void setWeight(Integer Weight){
        this.Weight=Weight;
    }
    public void setGender(String Gender){
        this.Gender=Gender;
    }
    public void setAddress(String Address){
        this.Address=Address;
    }
    public void setPostcode(Integer Postcode){
        this.Postcode=Postcode;
    }    public void setLevelofactivity(Integer Levelofactivity){
        this.Levelofactivity=Levelofactivity;
    }    public void setSteps_per_mile(Integer Steps_per_mile){
        this.Steps_per_mile=Steps_per_mile;
    }



}
