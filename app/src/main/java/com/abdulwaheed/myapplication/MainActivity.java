package com.abdulwaheed.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.abdulwaheed.myapplication.R.drawable.capricorn;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener ,TextToSpeech.OnInitListener{

    EditText et_date_of_birth;

    TextView tv_current_day,tv_current_date,tv_year,tv_month,tv_days,tv_birthdate_day,tv_calculation_total_years,tv_calculation_total_months,
            tv_calculation_total_weeks,tv_calculation_total_days,tv_calculation_total_hours,tv_calculation_total_mintues,tv_calculation_total_seconds,
            tv_star_name,tv_next_birthday_days,tv_next_birthday_month,tv_first_upcoming_date,tv_second_upcoming_date,tv_third_upcoming_date,tv_fourth_upcoming_date,
            tv_fifth_upcoming_date,tv_first_upcoming_day,tv_second_upcoming_day,tv_third_upcoming_day,tv_fourth_upcoming_day,tv_fifth_upcoming_day;

    ImageView iv_today_calender_icon,iv_bithdate_calender_icon,iv_star;

    Button btn_calculate,btn_clear;

    LinearLayout ll_upcoming_birthdays,ll_star,ll_star_logos;

    private TextToSpeech tts;

    Calendar myCalendar;

    int day,month,year,age,months,days;

    String currentDateinString;
    String birthdateinString;
    String currentdate;
    String birthdate;
    String CheckDAte;

    long diff,diffSeconds,diffMinutes,diffHours,diffDays,diffWeeks,diffmonth,diffyear,remaingdays;

    TextWatcher tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



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

                String checkingfirstDate = tv_current_date.getText().toString();
                String checkingSecondDate = et_date_of_birth.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date currentdate = null;
                Date PreviousDate = null;

                try {
                    currentdate = format.parse(checkingfirstDate);
                    PreviousDate = format.parse(checkingSecondDate);

                    if (PreviousDate.before(currentdate)){
                        calculationOfDateandTime();
                        layoutVISIBLE();
                        setDataFromTextviews();
                        calculateNextBirthday();
                        checkStarDates();
                        speakOut();





      /*           String todaysYear = tv_current_date.getText().toString();
                 String birthDateorMonth = et_date_of_birth.getText().toString();

                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.YEAR,+1);
                    Date date = calendar.getTime();
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
                        String firstdateOutput = format1.format(date);

                    Calendar secondcalendar = Calendar.getInstance();
                    secondcalendar.add(Calendar.YEAR,+2);
                    Date seconddate = calendar.getTime();
                        SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
                        String secondDateOutput = format2.format(seconddate);

                    Calendar thirdcalendar = Calendar.getInstance();
                    thirdcalendar.add(Calendar.YEAR,+3);
                    Date thirddate = calendar.getTime();
                        SimpleDateFormat format3 = new SimpleDateFormat("yyyy");
                        String thirdDateOutput = format3.format(thirddate);

                    Calendar fourthcalendar = Calendar.getInstance();
                        fourthcalendar.add(Calendar.YEAR,+4);
                    Date fourthdate = calendar.getTime();
                        SimpleDateFormat format4 = new SimpleDateFormat("yyyy");
                        String fourthDateOutput = format4.format(fourthdate);

                    Calendar fifthcalendar = Calendar.getInstance();
                        fifthcalendar.add(Calendar.YEAR,+5);
                    Date fifthdate = calendar.getTime();
                        SimpleDateFormat format5 = new SimpleDateFormat("yyyy");
                        String fifthDateOutput = format5.format(fifthdate);
                    tv_second_upcoming_date .setText(firstdateOutput);
                    tv_first_upcoming_date.setText(secondDateOutput);
                    tv_third_upcoming_date.setText(thirdDateOutput);
                    tv_fourth_upcoming_date.setText(fourthDateOutput);
                    tv_fifth_upcoming_date.setText(fifthDateOutput);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
                    Date CurrentYear = null;
                    Date DateorMonth = null;
                    try {
                       CurrentYear = dateFormat.parse(todaysYear);
                        DateorMonth = simpleDateFormat.parse(birthDateorMonth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal1 = Calendar.getInstance();
                        Calendar cal2 = Calendar.getInstance();
                        cal1.setTime(CurrentYear);

             */


                    }
                    if (PreviousDate.after(currentdate)){
                        ClearDateFromTextviews();
                        afterDateSpeek();
                        Toast.makeText(MainActivity.this,"The date you enter is greater then TODAY plese enter correct BIRTHDATE",Toast.LENGTH_LONG).show();
                    }if ( PreviousDate.equals(currentdate)){
                        ClearDateFromTextviews();
                        equalDateSpeek();
                        Toast.makeText(MainActivity.this,"The date you enter is equal then TODAY plese enter correct BIRTHDATE",Toast.LENGTH_LONG).show();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    @Override
    public void onBackPressed() {
        backPressalertDailoge();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static Date addYear(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        return cal.getTime();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.fungames.sniper3d \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one to share application"));
            } catch(Exception e) {
            }
        }
        if (id == R.id.action_rate_it) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fungames.sniper3d")));
        }
          if (id == R.id.action_feedback) {

              Intent Email = new Intent(Intent.ACTION_SEND);
              Email.setType("text/email");
              Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "abdulwajid9997@gmail.com" });
              Email.putExtra(Intent.EXTRA_SUBJECT, "Age And Star Calculation");
              Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
              startActivity(Intent.createChooser(Email, "Send Your Feedback:"));
        }
        if (id == R.id.action_about_us){
           Intent aboutintent  = new Intent(MainActivity.this,Aboutus.class);
           startActivity(aboutintent);
    }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {

        if (tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


    public void backPressalertDailoge(){

        DialogInterface.OnClickListener dialogClickLIstener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                    case  DialogInterface.BUTTON_NEUTRAL:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fungames.sniper3d")));
                       break;

                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Age And Star Calculation").setMessage("Do You Want To Close This App ?").setIcon(R.drawable.logos);
        builder.setPositiveButton("Yes",dialogClickLIstener).setNegativeButton("No",dialogClickLIstener).setNeutralButton("Rate us",dialogClickLIstener).show();

        }

    public void  afterDateSpeek(){

        tts.speak("The date you enter is greater then TODAY plese enter correct BIRTHDATE",TextToSpeech.QUEUE_FLUSH,null);
    }

    public void  equalDateSpeek(){

        tts.speak("The date you enter is Equal then TODAY plese enter correct BIRTHDATE",TextToSpeech.QUEUE_FLUSH,null);
    }

    private void speakOut() {

        String  text = tv_year.getText().toString();
        String startext = tv_star_name.getText().toString();
        tts.speak("your age is "+text+"and "+startext,TextToSpeech.QUEUE_FLUSH,null);
    }

    public void checkStarDates(){

        CheckDAte = et_date_of_birth.getText().toString();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM");
        Date testingDate = null;
        try {
            Date AquariusstartDate = format.parse("19/01"); //jan to feb
            Date AquariusendDate = format.parse("19/02");//jan to feb

            Date PiscesStartDate = format.parse("18/02");//feb to march
            Date PiscesEndDate = format.parse("21/03");//feb to march

            Date AriesStartDate = format.parse("20/03");// march to april
            Date AriesEndDate = format.parse("20/04");//march to april

            Date TaurusStartDate = format.parse("19/04");//  april to may
            Date TaurusEndDate = format.parse("21/05");// april to may

            Date GeminiStartDate = format.parse("20/05");// may to june
            Date GeminiEndDate = format.parse("22/06");//may to june

            Date CancerStartDate = format.parse("21/06");// june to july
            Date CancerEndDate = format.parse("23/07");//june to july

            Date LeoStartDate = format.parse("22/07");//july to auguest
            Date LeoEndDate = format.parse("23/08");//july to auguest

            Date VirgoStartDate = format.parse("22/08");//auguest to sep
            Date VirgoEndDate = format.parse("23/09");//auguest to sep

            Date LibraStartDate = format.parse("22/09");//sep to oct
            Date LibraEndDate = format.parse("23/10");//sep to oct

            Date ScorpioStartDate = format.parse("22/10");//oct to nov
            Date ScorpioEndDate = format.parse("22/11");//oct to nov

            Date SagittariusStartDate = format.parse("21/11");//nov to dec
            Date SagittariusEndDate = format.parse("23/12");//nov to dec

            Date CapricornStartDate = format.parse("22/12");//dec to dec
            Date CapricornEndDate = format.parse("32/12");//dec to dec

           Date CapricornjanStartDate = format.parse("00/01");//jan to jan
            Date CapricornjanEndDate = format.parse("20/01");//jan to jan

            testingDate = format.parse(CheckDAte);

            if (testingDate.after(CapricornStartDate) && testingDate.before(CapricornEndDate)){
                tv_star_name.setText("Your Horoscope Star is Capricorn جدی");
                iv_star.setImageResource(R.drawable.capricorn);
            }
            if (testingDate.after(CapricornjanStartDate) && testingDate.before(CapricornjanEndDate)){
                tv_star_name.setText("Your Horoscope Star is Capricorn جدی");
                iv_star.setImageResource(R.drawable.capricorn);
            }
            if (testingDate.after(AquariusstartDate) && testingDate.before(AquariusendDate)) {
                tv_star_name.setText("Your Horoscope Star is Aquarius دلو ");
                iv_star.setImageResource(R.drawable.aquarius);
            }
            if (testingDate.after(PiscesStartDate) && testingDate.before(PiscesEndDate)){
                tv_star_name.setText("Your Horoscope Star is Pisces حوت");
                iv_star.setImageResource(R.drawable.pisces);
            }
            if (testingDate.after(AriesStartDate) && testingDate.before(AriesEndDate)){
                tv_star_name.setText("Your Horoscope Star is Aries حمل");
                iv_star.setImageResource(R.drawable.aries);
            }
            if (testingDate.after(TaurusStartDate) && testingDate.before(TaurusEndDate)){
                tv_star_name.setText("Your Horoscope Star is Taurus ثور");
                iv_star.setImageResource(R.drawable.taurus);
            }
            if (testingDate.after(GeminiStartDate) && testingDate.before(GeminiEndDate)){
                tv_star_name.setText("Your Horoscope Star is Gemini جوزا");
                iv_star.setImageResource(R.drawable.gemini);
            }
            if (testingDate.after(CancerStartDate) && testingDate.before(CancerEndDate)){
                tv_star_name.setText("Your Horoscope Star is Cancer سرطان");
                iv_star.setImageResource(R.drawable.cancer);
            }
            if (testingDate.after(LeoStartDate) && testingDate.before(LeoEndDate)){
                tv_star_name.setText("Your Horoscope Star is Leo اسد");
                iv_star.setImageResource(R.drawable.leo);
            }
            if (testingDate.after(VirgoStartDate) && testingDate.before(VirgoEndDate)){
                tv_star_name.setText("Your Horoscope Star is Virgo سنبلہ");
                iv_star.setImageResource(R.drawable.virgo);
            }
            if (testingDate.after(LibraStartDate) && testingDate.before(LibraEndDate)){
                tv_star_name.setText("Your Horoscope Star is Libra میزان");
                iv_star.setImageResource(R.drawable.libra);
            }
            if (testingDate.after(ScorpioStartDate) && testingDate.before(ScorpioEndDate)){
                tv_star_name.setText("Your Horoscope Star is Scorpio عقرب");
                iv_star.setImageResource(R.drawable.scorpio);
            }
            if (testingDate.after(SagittariusStartDate) && testingDate.before(SagittariusEndDate)){
                tv_star_name.setText("Your Horoscope Star is Sagittarius قوس");
                iv_star.setImageResource(R.drawable.sagittarius);
            }
        }   catch (Exception e) {
            Toast.makeText(MainActivity.this,"Exception occurred", Toast.LENGTH_SHORT).show();
        }
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
                tv_current_date.setText(dayOfMonth+"/"+month+"/"+year);
                currentDateinString = dayOfMonth+"/"+month+"/"+year;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date =null;
                try {
                    date = simpleDateFormat.parse(currentDateinString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat format = new SimpleDateFormat("EEEE");
                String finalCurrentDate = format.format(date);
                tv_current_day.setText(finalCurrentDate);
            }
        },year,month,day);
        datePickerDialog.show();
    }

    public void birthdatePickerMethod(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                et_date_of_birth.setText(dayOfMonth+"/"+month+"/"+year);
                birthdateinString = dayOfMonth+"/"+month+"/"+year;
                SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                Date dt1= null;
                try {
                    dt1 = format1.parse(birthdateinString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat format2=new SimpleDateFormat("EEEE");
                String finalDay=format2.format(dt1);
                tv_birthdate_day.setText(finalDay);
            }
        },year,month,day);
        datePickerDialog.show();

    }

    public void currentDateSet(){
        myCalendar = Calendar.getInstance();
        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        month = myCalendar.get(Calendar.MONTH);
        year = myCalendar.get(Calendar.YEAR);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        tv_current_day.setText(dayOfTheWeek);
        month = month+1;
        tv_current_date.setText(day + "/" + month + "/" + year);

    }

    public void alertDailogeForEpmtyEditTexet(){
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("BE ALERT");
        alertDialog.setMessage("Firstly Enter Your Birthdate Then Click To Calculate  ");
        alertDialog.setIcon(R.drawable.alert);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void  layoutVISIBLE(){
        if (ll_upcoming_birthdays.getVisibility() == View.VISIBLE || ll_star.getVisibility() == View.VISIBLE || ll_star_logos.getVisibility() == View.VISIBLE){
            ll_upcoming_birthdays.setVisibility(View.VISIBLE);ll_star.setVisibility(View.VISIBLE);ll_star_logos.setVisibility(View.VISIBLE);
        }else {
            ll_star.setVisibility(View.VISIBLE);
            ll_star_logos.setVisibility(View.VISIBLE);
            ll_upcoming_birthdays.setVisibility(View.VISIBLE);
        }
    }

    public void  layoutINVISIBLE(){
        if (ll_upcoming_birthdays.getVisibility() == View.VISIBLE || ll_star.getVisibility() == View.VISIBLE || ll_star_logos.getVisibility() == View.VISIBLE){
            ll_upcoming_birthdays.setVisibility(View.GONE);ll_star.setVisibility(View.GONE);ll_star_logos.setVisibility(View.GONE);
        }else {
            ll_star.setVisibility(View.GONE);
            ll_star_logos.setVisibility(View.GONE);
            ll_upcoming_birthdays.setVisibility(View.GONE);
        }
    }

    public void calculationOfDateandTime() {
        /*
        this calculation in this link below
        https://kodejava.org/how-do-i-calculate-difference-in-days-between-two-dates/*/

        currentdate = tv_current_date.getText().toString();
        birthdate = et_date_of_birth.getText().toString();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = null;
        Date birthdayDate = null;
        try {
            currentDate = simpleDateFormat.parse(currentdate);
            birthdayDate = simpleDateFormat.parse(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        // Set the date for both of the calendar instance
        cal1.setTime(currentDate);
        cal2.setTime(birthdayDate);

        // Get the represented date in milliseconds
        long millis1 = cal1.getTimeInMillis();
        long millis2 = cal2.getTimeInMillis();

        // Calculate difference in milliseconds
        diff = millis1 - millis2;

        // Calculate difference in seconds
        diffSeconds = diff / 1000;

        // Calculate difference in minutes
        diffMinutes = diff / (60 * 1000);

        // Calculate difference in hours
        diffHours = diff / (60 * 60 * 1000);

        // Calculate difference in days
        diffDays = diff / (24 * 60 * 60 * 1000);

        //Calculate difference in week
        diffWeeks = diff / (24 * 60 * 60 * 1000 * 7);

        // Calculate difference in year
        diffyear = diff / (1000L * 60 * 60 * 24 * 365);

        // Calculate difference in month
        diffmonth = diff / (1000L * 60 * 60 * 24 * 30);

        remaingdays =  diff / (24 * 60 * 60 * 1000);


        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(birthdayDate);


        age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        months = today.get(Calendar.MONTH)-dob.get(Calendar.MONTH);

        days = today.get(Calendar.DATE)-dob.get(Calendar.DATE);

   /*  if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            months--;
        }
        if (today.get(Calendar.DATE) < dob.get(Calendar.DATE)) {
            days--;
        }
*/
    }

    public  void  setDataFromTextviews(){
        // Day SEt
        tv_calculation_total_days.setText(""+diffDays);
        tv_calculation_total_weeks.setText(""+diffWeeks);
        tv_calculation_total_months.setText(""+diffmonth);
        tv_calculation_total_years.setText(""+diffyear);
        tv_year.setText(""+age);
        tv_days.setText(""+days);
        tv_month.setText(""+months);
        //Time SEt
        tv_calculation_total_hours.setText(""+diffHours);
        tv_calculation_total_mintues.setText(""+diffMinutes);
        tv_calculation_total_seconds.setText(""+diffSeconds);

    }

    public void ClearDateFromTextviews(){
        et_date_of_birth.setText("");
        tv_month.setText("");
        tv_days.setText("");
        tv_star_name.setText("");
        tv_year.setText("");
        tv_calculation_total_years.setText("");
        tv_calculation_total_months.setText("");
        tv_calculation_total_weeks.setText("");
        tv_calculation_total_days.setText("");
        tv_calculation_total_hours.setText("");
        tv_calculation_total_mintues.setText("");
        tv_calculation_total_seconds.setText("");
        tv_birthdate_day.setText("");

    }

    public void initViews(){
        //TextViews
        tv_star_name = findViewById(R.id.tv_star_name);
        tv_current_day = findViewById(R.id.tv_current_day);
        tv_current_date = findViewById(R.id.tv_current_date);
        tv_year = findViewById(R.id.tv_year);
        tv_month = findViewById(R.id.tv_month);
        tv_days = findViewById(R.id.tv_days);
        tv_birthdate_day = findViewById(R.id.tv_birthdate_day);
        tv_calculation_total_years = findViewById(R.id.tv_calculation_total_years);
        tv_calculation_total_months = findViewById(R.id.tv_calculation_total_months);
        tv_calculation_total_weeks = findViewById(R.id.tv_calculation_total_weeks);
        tv_calculation_total_days = findViewById(R.id.tv_calculation_total_days);
        tv_calculation_total_hours = findViewById(R.id.tv_calculation_total_hours);
        tv_calculation_total_mintues = findViewById(R.id.tv_calculation_total_mintues);
        tv_calculation_total_seconds = findViewById(R.id.tv_calculation_total_seconds);
        tv_next_birthday_days =findViewById(R.id.tv_next_birthday_days);
        tv_next_birthday_month = findViewById(R.id.tv_next_birthday_month);
        tv_first_upcoming_date = findViewById(R.id.tv_first_upcoming_date);
        tv_second_upcoming_date = findViewById(R.id.tv_second_upcoming_date);
        tv_third_upcoming_date = findViewById(R.id.tv_third_upcoming_date);
        tv_fourth_upcoming_date = findViewById(R.id.tv_fourth_upcoming_date);
        tv_fifth_upcoming_date = findViewById(R.id.tv_fifth_upcoming_date);
        tv_first_upcoming_day = findViewById(R.id.tv_first_upcoming_birthday_day);
        tv_second_upcoming_day = findViewById(R.id.tv_second_upcoming_birthday_day);
        tv_third_upcoming_day = findViewById(R.id.tv_third_upcoming_day);
        tv_fourth_upcoming_day = findViewById(R.id.tv_fourth_upcoming_day);
        tv_fifth_upcoming_day = findViewById(R.id.tv_fifth_upcoming_day);

        //ImageViews
        iv_today_calender_icon = findViewById(R.id.iv_today_calender_icon);
        iv_bithdate_calender_icon =findViewById(R.id.iv_bithdate_calender_icon);
        iv_star = findViewById(R.id.iv_star);

        //EditText
        et_date_of_birth = findViewById(R.id.et_date_of_birth);

        //Buttons
        btn_calculate = findViewById(R.id.btn_calculate);
        btn_clear =findViewById(R.id.btn_clear);

        //LinearLayouts
        ll_star_logos = findViewById(R.id.ll_star_logos);
        ll_upcoming_birthdays = findViewById(R.id.ll_upcoming_birthdays);
        ll_star = findViewById(R.id.ll_star);

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btn_calculate.setEnabled(true);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void calculateNextBirthday() {
        // TODO Auto-generated method stub



        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final String strBDay = sdf.format(dt);
        try {

            dt = sdf.parse(strBDay);
        } catch (final java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final Calendar c = Calendar.getInstance();

        c.setTime(dt);
        // c.add(Calendar.DATE, value);
        final Calendar today = Calendar.getInstance();
        // Take your DOB Month and compare it to current
        // month
        final int BMonth = c.get(Calendar.MONTH);
        final int CMonth = today.get(Calendar.MONTH);
        c.set(Calendar.YEAR, today.get(Calendar.YEAR));
        c.set(Calendar.DAY_OF_WEEK,
                today.get(Calendar.DAY_OF_WEEK));
        if (BMonth <= CMonth) {
            c.set(Calendar.YEAR,
                    today.get(Calendar.YEAR) + 1);
        }
        // Result in millis

        final long millis = c.getTimeInMillis()
                - today.getTimeInMillis();
        // Convert to days
        final long days = millis / 86400000; // Precalculated
        // (24 *
        // 60 *
        // 60 *
        // 1000)
        // final String dayOfTheWeek =
        // sdf.format(BDay.getTime());
        sdf = new SimpleDateFormat("EEEE");
        final String dayOfTheWeek = sdf.format(dt);
        tv_next_birthday_days.setText(""+days);

        /* tv.setText("" + days + " days")
        txt10.setText("" + dayOfTheWeek);*/
    }
}
