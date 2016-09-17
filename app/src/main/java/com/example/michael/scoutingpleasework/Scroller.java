package com.example.michael.scoutingpleasework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.*;
public class Scroller extends AppCompatActivity {
    //pliz
    EditText teamNumber;
    EditText matchNumber;
    CheckBox parkingZone;
    CheckBox beaconButton;
    RadioGroup climbers;
    RadioGroup mountain;
    RadioGroup zipline;
    RadioGroup teleClimbers;
    EditText floorDebris;
    EditText lowDebris;
    EditText midDebris;
    EditText highDebris;
    RadioGroup endMountain;
    CheckBox allClear;

    String fileName;
    String fileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scouting_scroller);

        teamNumber = (EditText) findViewById(R.id.teamNumber);
        matchNumber = (EditText) findViewById(R.id.matchNumber);
        beaconButton = (CheckBox) findViewById(R.id.beaconButton);
        climbers = (RadioGroup) findViewById(R.id.climbers);
        mountain = (RadioGroup) findViewById(R.id.mountain);
        zipline = (RadioGroup) findViewById(R.id.zipline);
        teleClimbers = (RadioGroup) findViewById(R.id.teleClimbers);
        floorDebris = (EditText) findViewById(R.id.debrisFloor);
        lowDebris = (EditText) findViewById(R.id.debrisLow);
        midDebris = (EditText) findViewById(R.id.debrisMid);
        highDebris = (EditText) findViewById(R.id.debrisHigh);
        endMountain = (RadioGroup) findViewById(R.id.endMountain);
        allClear = (CheckBox) findViewById(R.id.allClear);

        fileContent = new String();
        fileName = new String();

        resetForm();    //Set default values and focus on first field
    }


    private void resetForm() {
        teamNumber = (EditText) findViewById(R.id.teamNumber);
        teamNumber.getText().clear();

        matchNumber = (EditText) findViewById(R.id.matchNumber);
        matchNumber.getText().clear();

        beaconButton = (CheckBox) findViewById(R.id.beaconButton);
        beaconButton.setChecked(false);

        RadioButton tmpButton = (RadioButton) findViewById(R.id.climbers0);
        tmpButton.setChecked(true);

        //Set teleop Climbers to 0 also
        tmpButton = (RadioButton) findViewById(R.id.climbers4);
        tmpButton.setChecked(true);

        tmpButton = (RadioButton) findViewById(R.id.mountainOff);
        tmpButton.setChecked(true);  //Default to Mountain = off

        tmpButton = (RadioButton) findViewById(R.id.zipline0);
        tmpButton.setChecked(true);   //Default to 0 zipline switches

        tmpButton = (RadioButton) findViewById(R.id.teleOff);
        tmpButton.setChecked(true);
        ;      //Default to 0 climbers

        floorDebris = (EditText) findViewById(R.id.debrisFloor);
        floorDebris.getText().clear();

        lowDebris = (EditText) findViewById(R.id.debrisLow);
        lowDebris.getText().clear();

        midDebris = (EditText) findViewById(R.id.debrisMid);
        midDebris.getText().clear();

        highDebris = (EditText) findViewById(R.id.debrisHigh);
        highDebris.getText().clear();

        allClear = (CheckBox) findViewById(R.id.allClear);
        allClear.setChecked(false);

        //Move focus to Team Number
        teamNumber.requestFocus();
    }

    public void save() {
        String text = new String();

        //Verify that Team Number is filled in
        text = teamNumber.getText().toString().trim();
        if (text.equals("")) {
            Toast.makeText(getApplicationContext(), "Team Number must be present", Toast.LENGTH_LONG).show();
            teamNumber.requestFocus();

            return;
        }

        //Verify that MatchNumber is filled in
        text = matchNumber.getText().toString().trim();
        if (text.equals("")) {
            Toast.makeText(getApplicationContext(), "Match Number must be present", Toast.LENGTH_LONG).show();
            matchNumber.requestFocus();

            return;
        }


        fileContent = "";

        if (beaconButton.isChecked()) {
            fileContent = fileContent + "20,";
        } else {
            fileContent = fileContent + "0,";
        }

        fileContent = fileContent + climbers.indexOfChild(climbers.findViewById(climbers.getCheckedRadioButtonId())) * 20 + ",";


        switch (mountain.indexOfChild(mountain.findViewById(mountain.getCheckedRadioButtonId()))) {
            case 0:
                fileContent = fileContent + "0,";
                break;
            case 1:
                fileContent = fileContent + "5,";
                break;
            case 2:
                fileContent = fileContent + "10,";
                break;
            case 3:
                fileContent = fileContent + "20,";
                break;
            case 4:
                fileContent = fileContent + "40,";
                break;
            default:
                fileContent = fileContent + "0,";
                break;
        }

        //Check if radiobutton is on and write score for checkbox, otherwise write only a comma to indicate this
        fileContent = fileContent + zipline.indexOfChild(zipline.findViewById(zipline.getCheckedRadioButtonId())) * 20 + ",";

        fileContent = fileContent + teleClimbers.indexOfChild(teleClimbers.findViewById(teleClimbers.getCheckedRadioButtonId())) * 10 + ",";

        text = floorDebris.getText().toString().trim();

        if (text.isEmpty() || (text == null)) {
            fileContent = fileContent + "0,";
        } else {
            fileContent = fileContent + floorDebris.getText() + ",";
        }

        text = lowDebris.getText().toString().trim();

        if (text.isEmpty() || (text == null)) {
            fileContent = fileContent + "0,";
        } else {
            fileContent = fileContent + Integer.parseInt(lowDebris.getText().toString()) * 5 + ",";
        }

        text = midDebris.getText().toString().trim();

        if (text.isEmpty() || (text == null)) {
            fileContent = fileContent + "0,";
        } else {
            fileContent = fileContent + Integer.parseInt(midDebris.getText().toString()) * 10 + ",";
        }

        text = highDebris.getText().toString().trim();

        if (text.isEmpty() || (text == null)) {
            fileContent = fileContent + "0,";
        } else {
            fileContent = fileContent + Integer.parseInt(highDebris.getText().toString()) * 15 + ",";
        }

        switch (endMountain.indexOfChild(endMountain.findViewById(endMountain.getCheckedRadioButtonId()))) {
            case 0:
                fileContent = fileContent + "0,";
                break;
            case 1:
                fileContent = fileContent + "10,";
                break;
            case 2:
                fileContent = fileContent + "20,";
                break;
            case 3:
                fileContent = fileContent + "40,";
                break;
            case 4:
                fileContent = fileContent + "80,";
                break;
            default:
                fileContent = fileContent + "0,";
                break;
        }

        if (allClear.isChecked()) {
            fileContent = fileContent + "20";
        } else {
            fileContent = fileContent + "0";
        }

        fileName = teamNumber.getText().toString() + "-" + matchNumber.getText().toString() + ".txt";

        File direct = new File(Environment.getExternalStorageDirectory() + "/DeltaRobotics/");

        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/DeltaRobotics/", fileName);

        if (!direct.exists()) {
            direct.mkdir(); //Make the application directory for the scouting app
        }

//        try {

        //RLR For now this works. But, the structure is terrible. I'd like to find a way to put a
        // Yes/No alert in a method that returns boolean indicating the users choice
        //If file exists, prompt user before deleting it to make room for new file
//            if (file.exists()) {
//                //Prompt user to overwrite
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//                builder.setTitle("Overwrite existing file");
//                builder.setMessage("Are you sure?");
//
//                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Do nothing but close the dialog
//
//                        dialog.dismiss();
//
//                        Toast.makeText(getApplicationContext(), "OVERWRITE", Toast.LENGTH_LONG).show();
//                    }
//
//                });
//
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Do nothing
//                        dialog.dismiss();
//
//                        Toast.makeText(getApplicationContext(), "Do NOT overwrite", Toast.LENGTH_LONG).show();
//
//                        return;
//                    }
//                });
//
//                AlertDialog alert = builder.create();
//                alert.show();
//            }
            //if (!file.exists()) {//Should you try to overwrite the file if it does exist? Otherwise the user never has a chance to correct an error
//            else {  //The file didn't exist. So, create it for the first time
        try {
            if (file.exists()) {
                //Delete the existing file to make room for the new one.
                file.delete();
            }

                file.createNewFile();
                FileOutputStream f = new FileOutputStream(file);
                PrintWriter pw = new PrintWriter(f);

                pw.println(fileContent);

                pw.flush();
                pw.close();
                f.close();

                resetForm();

                Toast.makeText(getApplicationContext(), "File saved: " + fileName, Toast.LENGTH_LONG).show();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveClick(View v) {
        save();
    }

    public void clearClick(View v) {
        resetForm();
    }
}