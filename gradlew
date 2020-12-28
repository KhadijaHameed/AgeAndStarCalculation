package com.abdulwaheed.agecalculator;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindViews;
import butterknife.OnClick;

import butterknife.BindViews;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,TextToSpeech.OnInitListener{

    EditText et_date_of_birth;

    TextView tv_current_day,tv_current_date,tv_year,tv_month,tv_days,tv_birthdate_day,tv_calculation_total_years,tv_calculation_total_months,
    tv_calculation_total_weeks,tv_calculation_total_days,tv_calculation_total_hours,tv_calculation_total_mintues,tv_calculation_total_seconds,
            tv_star_name;

    ImageView iv_today_calender_icon,iv_bithdate_calender_icon;

    Button btn_calculate,btn_clear;

    LinearLayout ll_upcoming_birthdays,ll_star;

    private TextToSpeech tts;

    Calendar myCalendar;

    int day,month,year,age,months,days;

    String currentDateinString ,birthdateinString,currentdate,birthdate;

    long diff,diffSeconds,diffMinutes,diffHours,diffDays,diffWeeks,diffmonth,diffyear,remaingdays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);

        initViews();

        currentDateSet();

       SetListners();


        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

              if (et_date_of_birth.getText().toString().trim().equals("")){
                  et_date_of_birth.setError("this field can not be empty");
                  alertDailogeForEpmtyEditTexet();
              }
              else {
                  calculationOfDateandTime();
                  layoutVISIBLE();
                  setDataFromTextviews();
                  speakOut();

                  char myBirthday, today, bday, diff, days;
                  myBirthday = [6,2]; // 6th of February
                  today = new Date();
                  bday = new Date(today.getFullYear(),myBirthday[1]-1,myBirthday[0]);
                  if( today.getTime() > bday.getTime()) {
                      bday.setFullYear(bday.getFullYear()+1);
                  }
                  diff = bday.getTime()-today.getTime();
                  days = Math.floor(diff/(1000*60*60*24));
                  alert(days+" days until Niet's birthday!");

              }


            }

        });


    }

        @Override
    protected void onDestroy() {

        if (tts != null){
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }

    private void speakOut() {

        String  text = tv_year.getText().toString();
        tts.speak("your age is "+text,TextToSpeech.QUEUE_FLUSH,null);


    }

    public void SetListners(){
        iv_today_calender_icon.setOnClickListener(this);
        iv_bithdate_calender_icon.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       if (v.getId() == R.id.iv_today_calender_icon){
         datepickerMethod();
       }
       if(v.getId() == R.id.iv_bithdate_calender_icon){
           birthdatePickerMethod();
       }if (v.getId() == R.id.btn_clear){
          String tv_total =  tv_calculation_total_seconds.getText().toString();
           if (tv_total.isEmpty()){
               Toast.makeText(MainActivity.this,"Plese Enter Date And Calculate Them Then Click Clear ",Toast.LENGTH_SHORT).show();
           }else {
           layoutINVISIBLE();
            ClearDateFromTextviews();
            cleardataSpeakOut();
        }
       }

    }

    private void cleardataSpeakOut() {
        tts.speak("Your Data will be clear now tou ready to input new date ",TextToSpeech.QUEUE_FLUSH,null);
    }

    public void datepickerMethod(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
