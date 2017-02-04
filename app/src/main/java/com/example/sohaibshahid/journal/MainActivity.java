package com.example.sohaibshahid.journal;

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
