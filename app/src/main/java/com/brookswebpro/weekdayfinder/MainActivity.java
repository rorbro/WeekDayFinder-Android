package com.brookswebpro.weekdayfinder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

String[] monthsArray = {"January", "February", "March", "April", "May", "June", "July", "August",
"September", "October", "November", "December"};
    ArrayList<Integer> daysArray = new ArrayList<Integer>();
    TextView label;
    RelativeLayout layoutR;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    ArrayAdapter<Integer> adapter2;
    ImageView whitebgd;
    EditText yearEntered;
    TextView resultTextView;
    Button enterButton;
    Button startOverButton;
    boolean isMonthSelected = false;
    boolean isDaySelected = false;
    boolean isYearSelected = false;
    String month;
    String day;
    String year;
    String date;
    int daysToAdd;
    int monthNum;
    int addMonthDays;
    int dayNumber;
    int yearNumber;

    public void submitPressed (View view) {

        if (!isMonthSelected) {
            isMonthSelected = true;
            date = month;
            switch (month) {
                case "January":
                    addDaysToMonth(2);
                    monthNum = 1;
                    addMonthDays = 0;
                    break;
                case "February":
                    monthNum = 2;
                    addMonthDays = 31;
                    break;
                case "March":
                    addDaysToMonth(2);
                    monthNum = 3;
                    addMonthDays = 59;
                    break;
                case "April":
                    addDaysToMonth(1);
                    monthNum = 4;
                    addMonthDays = 90;
                    break;
                case "May":
                    addDaysToMonth(2);
                    monthNum = 5;
                    addMonthDays = 120;
                    break;
                case "June":
                    addDaysToMonth(1);
                    monthNum = 6;
                    addMonthDays = 151;
                    break;
                case "July":
                    addDaysToMonth(2);
                    monthNum = 7;
                    addMonthDays = 181;
                    break;
                case "August":
                    addDaysToMonth(2);
                    monthNum = 8;
                    addMonthDays = 212;
                    break;
                case "September":
                    addDaysToMonth(1);
                    monthNum = 9;
                    addMonthDays = 243;
                    break;
                case "October":
                    addDaysToMonth(2);
                    monthNum = 10;
                    addMonthDays = 273;
                    break;
                case "November":
                    addDaysToMonth(1);
                    monthNum = 11;
                    addMonthDays = 303;
                    break;
                case "December":
                    addDaysToMonth(2);
                    monthNum = 12;
                    addMonthDays = 334;
                    break;
            }
            adapter2 = new ArrayAdapter<Integer>(this, R.layout.spinner_item, daysArray);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            label.setText("Day: ");
            spinner.setAdapter(adapter2);
            resultTextView.setText("You entered: " + date);
            enterButton.setText("Submit Day");


        } else if (!isDaySelected) {
            isDaySelected = true;
            date += " " + day;
            dayNumber = Integer.parseInt(day);
            //activate edit text field
            yearEntered.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
            resultTextView.setText("You entered: " + date + "\n\n ENTER YEAR");
            label.setText("Year: ");
            enterButton.setText("Submit Year");

        } else if (!isYearSelected) {
            if (TextUtils.isEmpty(yearEntered.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_LONG).show();
                yearEntered.setText("");
            } else {

                try {
                    year = yearEntered.getText().toString();
                    yearNumber = Integer.parseInt(year);
                    if (yearNumber > 1000000) {
                        Toast.makeText(getApplicationContext(), "Please enter a smaller number", Toast.LENGTH_LONG).show();
                    } else if (((monthNum == 2 && dayNumber == 29) && (((yearNumber - 2000) % 4 != 0) || ((yearNumber - 2000) % 100 == 0) && ((yearNumber - 2000) % 400 != 0)))) {
                        Toast.makeText(getApplicationContext(), year + " is not a leap year. Please try again.", Toast.LENGTH_LONG).show();
                        resetApp();
                    } else {
                        date += ", " + yearEntered.getText().toString();
                        resultTextView.setText("You entered: " + date);
                        yearEntered.setText("");
                        yearEntered.setVisibility(View.INVISIBLE);
                        enterButton.setText("Calculate WeekDay");
                        isYearSelected = true;
                        label.setVisibility(View.INVISIBLE);
                        whitebgd.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    resultTextView.setText("Please enter a smaller number");
                    yearEntered.setText("");
                }
            }
        } else {
            calculateWeekDay(dayNumber, yearNumber);

        }
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        layoutR = (RelativeLayout) findViewById(R.id.relativeLayout);
        layoutR.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
/*        adapter = ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
*/
        yearEntered = (EditText) findViewById(R.id.editTextYear);
        yearEntered.setVisibility(View.INVISIBLE);

        whitebgd = (ImageView) findViewById(R.id.imageView2);

        resultTextView = (TextView) findViewById(R.id.textView);
        resultTextView.setMovementMethod(new ScrollingMovementMethod());

        label = (TextView) findViewById(R.id.label);

        enterButton  = (Button) findViewById(R.id.button1);
        startOverButton  = (Button) findViewById(R.id.button2);
        startOverButton.setVisibility(View.INVISIBLE);

        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, monthsArray);
    //NEW ADAPTER CODE
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(this);

        for (int i=1; i <= 29; i++) {
            daysArray.add(i);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    if (!isMonthSelected) {
        month = parent.getItemAtPosition(position).toString();
    } else if (!isDaySelected) {
        day = parent.getItemAtPosition(position).toString();

    }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addDaysToMonth (int daysToAdd) {
        for (int i=1; i <= daysToAdd; i++) {
            daysArray.add(29+i);
        }
    }

    public int calculateLeapDays (int earlyYear, int futureYear) {

        int leapDays = 0;
        int counter = 0;
        int leapYear = 0;
        int extraLeapDays = 0;

        if ((futureYear - earlyYear) < 4) {
            counter = earlyYear;
            while (counter <= futureYear) {
                if (counter % 4 == 0) {
                    leapDays += 1;
                    if ((counter % 100 == 0) && (counter % 400 != 0)) {
                        leapDays -= 1;
                    }
                }
                counter += 1;
            }
        } else {
            for (int i = 1; i <= 4; i++) {
                if ((earlyYear + i - 1) % 4 == 0) {
                    leapDays += 1;
                    if (((earlyYear + i - 1) % 100 == 0) && ((earlyYear + i - 1) % 400 != 0)) {
                        leapDays -= 1;
                    }
                    leapYear = earlyYear + i - 1;
                }
            }
            leapDays += (futureYear - leapYear) / 4;
            extraLeapDays += leapDays/100;
            leapDays -= leapDays/25;
            leapDays += extraLeapDays;
        }
        return leapDays;
    }

    public int getDaysOfMonth (int month) {
        int returnDays = 0;

        switch (month) {
            case 1:case 3:case 5:case 7:case 8:case 10:case 12:
                returnDays = 31;
                break;
            case 4:case 6:case 9:case 11:
                returnDays = 30;
                break;
            case 2:
                returnDays = 28;
                break;
        }
        return returnDays;
    }

    public int adjustDaysOfMonth (int month, int year) {

        int adjustDays = 0;
        switch (month) {
            case 1:case 3:case 5:case 7:case 8:case 10:case 12:
                adjustDays = 1;
            break;
            case 4:case 6:case 9:case 11:
                adjustDays = 0;
            break;
            case 2:
                if (year % 4 == 0) {
                    adjustDays = -1;
                } else {
                    adjustDays = -2;
                }
                break;
        }

        return adjustDays;
    }

    public void calculateWeekDay (int day1, int year) {
        enterButton.setVisibility(View.INVISIBLE);

        int finalDay = 6;
        int daySum = 0;
        int yearDifference = 0;
        boolean yearPlus = true;
        int leapDays = 0;
        int dayDifference = 0;
        int finalDifference = 0;
        String finalDayString = "Saturday";

        if (year > 2000) {
            yearDifference = year - 2000;
            yearPlus = true;
            leapDays += 1;

            if (yearDifference >= 3) {
                leapDays += calculateLeapDays(2000 + 1, year - 1);
            }
            if ((year % 4 == 0) && (monthNum > 2)) {
                leapDays += 1;
                if ((year % 100 == 0) && (year % 400 != 0)) {
                    leapDays -= 1;
                }
            }
        } else if (year < 2000) {
            yearDifference = 2000 - year;
            yearPlus = false;
            if (yearDifference >= 3) {
                leapDays += calculateLeapDays((year + 1), (2000 - 1));
            }
            if ((year % 4 == 0) && (monthNum < 3)) {
                leapDays += 1;
                if ((year % 100 == 0) && (year % 400 != 0)) {
                    leapDays -= 1;
                }
            }
        } else {
            if (monthNum > 2) {
                leapDays += 1;
            }
        }

        daySum += yearDifference;
        dayDifference += monthNum;

        if (yearPlus) {
            finalDifference = addMonthDays + (day1 - 1) + yearDifference + leapDays;
        } else {
            finalDifference = addMonthDays + (day1 - 1) - yearDifference - leapDays;
        }

        finalDay = finalDifference  % 7;

        switch (finalDay) {
            case 0:
                finalDayString = "Saturday";
                break;
            case -1:case 6:
                finalDayString = "Friday";
                break;
            case -2:case 5:
                finalDayString = "Thursday";
                break;
            case -3:case 4:
                finalDayString = "Wednesday";
                break;
            case -4:case 3:
                finalDayString = "Tuesday";
                break;
            case -5:case 2:
                finalDayString = "Monday";
                break;
            case -6:case 1:
                finalDayString = "Sunday";
                break;
        }

        resultTextView.setText("The week day for " + date + " is " + finalDayString);
        startOverButton.setVisibility(View.VISIBLE);
    }
    public void startOver (View view) {

        resetApp();
        startOverButton.setVisibility(View.INVISIBLE);
        enterButton.setVisibility(View.VISIBLE);
        resultTextView.setText("");
        whitebgd.setVisibility(View.VISIBLE);

    }

    public void resetApp () {

        resultTextView.setText("Select Month");
        enterButton.setText("Enter Month");
        yearEntered.setText("");
        yearEntered.setVisibility(View.INVISIBLE);
        label.setText("Month: ");
        label.setVisibility(View.VISIBLE);
        date = "";
        isMonthSelected = false;
        isDaySelected = false;
        isYearSelected = false;
        month = "";
        day = "";
        year = "";

        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);

        if (daysArray.size() > 29) {
            int tempNum = daysArray.size();
            for (int i = 1; i <= (tempNum - 29); i++) {
                daysArray.remove(tempNum - i);
            }
        }
    }
}
