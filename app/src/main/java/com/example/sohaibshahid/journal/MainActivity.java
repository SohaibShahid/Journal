package com.example.sohaibshahid.journal;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText editTextDW, editTextDB;
    private Spinner spinnerDG;
    Button mainButton;

    private static final String[] spinnerDayinGeneral = {"Day in a Word", "YAY!", "nay", "M'eh"};
    private String DayinGeneral;
    private String fileName = "Journal.txt";
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
        spinnerDG = (Spinner) findViewById(R.id.spinnerDG);
        mainButton = (Button) findViewById(R.id.mainButton);

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

//        long when = System.currentTimeMillis();
//
//        Notification mNotification = new Notification.Builder(this)
//                .setSmallIcon(R.drawable.ic_stat_name)
//                .setContentTitle("Daily Log")
//                .setContentText("You seem to have forgotten to log in today. Want to do it now?")
//                .setVibrate(new long[]{1000, 1000})
//                .build();
//
//        notificationManager.notify(0,mNotification);
        Intent intent1 = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, 0);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

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
                    mFile = new File(getExternalFilesDir(Folder), fileName);
                    fileoutputStream = new FileOutputStream(mFile, true);
                    Print();
                    fileoutputStream.close();
//            mGoogleApiClient.connect();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void Print() {
        try {
            fileoutputStream.write("\n".getBytes());
            fileoutputStream.write(date.getBytes());
            fileoutputStream.write("  -  ".getBytes());
            fileoutputStream.write(DayinGeneral.getBytes());
            fileoutputStream.write("  -  ".getBytes());
            fileoutputStream.write(editTextDW.getText().toString().getBytes());
            fileoutputStream.write("  -  ".getBytes());
            fileoutputStream.write(editTextDB.getText().toString().getBytes());
            fileoutputStream.write("\n".getBytes());
            fileoutputStream.close();
//            mGoogleApiClient.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
