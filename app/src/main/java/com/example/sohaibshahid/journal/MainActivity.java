package com.example.sohaibshahid.journal;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText editTextDW, editTextDB, editTextTIL;
    private Spinner spinnerDG;
    Button mainButton;

    private static final String[] spinnerDayinGeneral = {"Day in a Word", "YAY!", "nay", "M'eh"};
    private String DayinGeneral;
    public String fileNameText = "Journal.txt";
    public String fileNameExcel = "Journal-Data.xls";
    private String Folder = "Life";
    String date = new SimpleDateFormat("dd-MM-yyy").format(new Date());


//    private GoogleApiClient mGoogleApiClient;

    File mFile;
    FileOutputStream fileoutputStream;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Drive.API)
//                .addScope(Drive.SCOPE_FILE)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();

        editTextDB = (EditText) findViewById(R.id.editTextDB);
        editTextDW = (EditText) findViewById(R.id.editTextDW);
        editTextTIL = (EditText) findViewById(R.id.editTextTIL);
        spinnerDG = (Spinner) findViewById(R.id.spinnerDG);
        mainButton = (Button) findViewById(R.id.mainButton);

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Calendar calendarDaily = Calendar.getInstance();
        calendarDaily.set(Calendar.HOUR_OF_DAY, 21);
        calendarDaily.set(Calendar.MINUTE, 0);
        calendarDaily.set(Calendar.SECOND, 0);

        Calendar calendarYearly = Calendar.getInstance();
        calendarYearly.set(Calendar.MONTH, 12);
        calendarYearly.set(Calendar.DAY_OF_MONTH, 31);
        calendarYearly.set(Calendar.HOUR_OF_DAY, 21);
        calendarYearly.set(Calendar.MINUTE, 0);
        calendarYearly.set(Calendar.SECOND, 0);

        Intent intentDaily = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntentDaily = PendingIntent.getBroadcast(MainActivity.this, 0,intentDaily, 0);
        AlarmManager daily = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        daily.setRepeating(AlarmManager.RTC_WAKEUP, calendarDaily.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntentDaily);

        Intent intentYearly = new Intent(MainActivity.this, YearlyReminder.class);
        PendingIntent pendingIntentYearly = PendingIntent.getBroadcast(MainActivity.this, 0,intentYearly, 0);
        AlarmManager yearly = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        yearly.set(AlarmManager.RTC_WAKEUP, calendarYearly.getTimeInMillis(), pendingIntentYearly);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,spinnerDayinGeneral);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDG.setAdapter(adapter);
        spinnerDG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Day in a Word")) {
                    DayinGeneral = "M'eh";
                } else {
                    DayinGeneral = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                DayinGeneral = "M'eh";
            }
        });

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mFile = new File(getExternalFilesDir(Folder), fileNameText);
                    fileoutputStream = new FileOutputStream(mFile, true);
                    PrintText();
                    fileoutputStream.close();
                    Toast.makeText(MainActivity.this, "Logged Text", Toast.LENGTH_SHORT).show();
//            mGoogleApiClient.connect();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    mFile = new File(getExternalFilesDir(Folder), fileNameExcel);
                    fileoutputStream = new FileOutputStream(mFile, true);
                    PrintData();
                    fileoutputStream.close();
                    Toast.makeText(MainActivity.this, "Logged Data", Toast.LENGTH_SHORT).show();
//            mGoogleApiClient.connect();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void PrintData() {
        try {
            //This is for the excel sheet
            fileoutputStream.write(date.getBytes());
            fileoutputStream.write("\t".getBytes());
            fileoutputStream.write(DayinGeneral.getBytes());
            fileoutputStream.write("\t".getBytes());
            fileoutputStream.write(editTextDW.getText().toString().getBytes());
            fileoutputStream.write("\t".getBytes());
            fileoutputStream.write(editTextTIL.getText().toString().getBytes());
            fileoutputStream.write("\t".getBytes());
            fileoutputStream.write(editTextDB.getText().toString().getBytes());
            fileoutputStream.write("\n".getBytes());
            fileoutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PrintText() {
        try {
            fileoutputStream.write("\n".getBytes());
            fileoutputStream.write(date.getBytes());
            fileoutputStream.write("  -  ".getBytes());
            fileoutputStream.write(DayinGeneral.getBytes());
            fileoutputStream.write("  -  ".getBytes());
            fileoutputStream.write(editTextDW.getText().toString().getBytes());
            fileoutputStream.write("  -  ".getBytes());
            fileoutputStream.write(editTextTIL.getText().toString().getBytes());
            fileoutputStream.write("  -  ".getBytes());
            fileoutputStream.write(editTextDB.getText().toString().getBytes());
            fileoutputStream.write("\n".getBytes());
            fileoutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
