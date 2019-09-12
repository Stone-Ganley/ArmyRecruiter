package edu.citadel.android.armyrecruiter;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.ref.WeakReference;
import java.util.Objects;

public class PTcalcActivity extends AppCompatActivity {

    private static PTcalculator temp;
    private EditText pushUps, sitUps, runTime;
    private TextView finalScore, sitUpScore, pushUpScore, runScore, passFail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptcalc);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Button calcScore = findViewById(R.id.Calc_Score);
        pushUps = findViewById(R.id.pushUps);
        sitUps = findViewById(R.id.sitUps);
        runTime = findViewById(R.id.runTime);
        finalScore = findViewById(R.id.score);
        sitUpScore = findViewById(R.id.SitUpScore);
        pushUpScore = findViewById(R.id.PushUpScore);
        runScore = findViewById(R.id.RunScore);
        passFail = findViewById(R.id.passfail);

        // initialize Gender Spinner
        Resources genderRes = getResources();
        String[] genderArray
                = genderRes.getStringArray(R.array.gender_array);
        Spinner gender = findViewById(R.id.gender);
        ArrayAdapter<String> genderAdapter =
                new ArrayAdapter<>(this,  R.layout.spinner_entry, genderArray);
        genderAdapter.setDropDownViewResource(R.layout.spinner_entry);
        gender.setAdapter(genderAdapter);
        gender.setOnItemSelectedListener(new OnItemSelectedListener());

        // initialize Age Spinner
        Resources ageGroupRes = getResources();
        String[] ageRange
                = ageGroupRes.getStringArray(R.array.age_groups);
        Spinner age =  findViewById(R.id.age);
        ArrayAdapter<String> ageRangeAdapter =
                new ArrayAdapter<>(this,  R.layout.spinner_entry, ageRange);
        ageRangeAdapter.setDropDownViewResource(R.layout.spinner_entry);
        age.setAdapter(ageRangeAdapter);
        age.setOnItemSelectedListener(new OnItemSelectedListener());

        //runs on background thread to calculate APFT Score
        calcScore.setOnClickListener((View v) ->
        {

            if (gender.getSelectedItem().toString().equals("")){
                Toast genderError = Toast.makeText(this , "Missing Information", Toast.LENGTH_LONG);
                genderError.setMargin(100,100);
                genderError.show();
            }else if(age.getSelectedItem().toString().equals("")){
                Toast ageError = Toast.makeText(this , "Missing Information", Toast.LENGTH_LONG);
                ageError.setMargin(100,100);
                ageError.show();
            }else if (pushUps.getText().toString().equals("")){
                Toast pushUpError = Toast.makeText(this , "Missing Information", Toast.LENGTH_LONG);
                pushUpError.setMargin(100,100);
                pushUpError.show();
            }else if(sitUps.getText().toString().equals("")){
                Toast sitUpError = Toast.makeText(this , "Missing Information", Toast.LENGTH_LONG);
                sitUpError.setMargin(100,100);
                sitUpError.show();
            }else if(runTime.getText().toString().equals("")){
                Toast runError = Toast.makeText(this, "Missing Information",Toast.LENGTH_LONG);
                runError.setMargin(100,100);
                runError.show();
            }else {
                Toast toast = Toast.makeText(this, "loading One Moment", Toast.LENGTH_LONG);
                toast.setMargin(100, 100);
                toast.show();
                DownloadPTcalcClass BackgroundTask = new DownloadPTcalcClass(PTcalcActivity.this);
                BackgroundTask.execute(gender.getSelectedItem().toString(), age.getSelectedItem().toString(),
                        pushUps.getText().toString(), sitUps.getText().toString(), runTime.getText().toString());
            }

        });

    }

    private static class DownloadPTcalcClass extends AsyncTask<String, Void, PTcalculator>
    {

        private WeakReference<PTcalcActivity> activityRef;

        DownloadPTcalcClass(PTcalcActivity context)
        {
            activityRef = new WeakReference<>(context);
        }
        protected PTcalculator doInBackground(String... symbols)
        {
            String gender = symbols[0];
            String ageRange = symbols[1];
            int pushUps = Integer.parseInt(symbols[2]);
            int sitUps = Integer.parseInt(symbols[3]);
            int runTime = Integer.parseInt(symbols[4]);
            return PTcalculator.calculate(pushUps, sitUps, runTime, gender, ageRange);
        }

        protected void onPostExecute(PTcalculator calc)
        {
            PTcalcActivity activity = activityRef.get();
            if(activity == null || activity.isFinishing())
                return;

            temp = calc;
            activity.pushUpScore.setText("(" + Integer.toString(calc.getPushUpScore()) + ")");
            activity.sitUpScore.setText("(" + Integer.toString(calc.getSitUpScore()) + ")");
            activity.runScore.setText("(" + Integer.toString(calc.getRunScore()) + ")");
            if(calc.getPushUpScore() < 60 || calc.getSitUpScore() < 60 || calc.getRunScore() < 60) {
                if (calc.getScore() == -1) {
                    Toast toast1 = Toast.makeText(activity, "something went wrong", Toast.LENGTH_LONG);
                    toast1.setMargin(100,100);
                    toast1.show();
                        } else{
                            activity.finalScore.setText(" Failed");
                            activity.passFail.setText("(" + Integer.toString(calc.getScore()) + ")");
                        }

                } else if (calc.getPushUpScore() >= 60 && calc.getSitUpScore() >= 60 && calc.getRunScore() >= 60) {
                activity.finalScore.setText(" Passed");
                activity.passFail.setText("(" + Integer.toString(calc.getScore()) + ")");
            }
        }
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        temp = (PTcalculator) savedInstanceState.getSerializable("calculator");
        pushUpScore.setText("(" + Integer.toString(temp.getPushUpScore()) + ")");
        sitUpScore.setText("(" + Integer.toString(temp.getSitUpScore()) + ")");
        runScore.setText("(" + Integer.toString(temp.getRunScore()) + ")");

        if(temp.getPushUpScore() < 60 || temp.getSitUpScore() < 60 || temp.getRunScore() < 60) {

            finalScore.setText(" Failed");
            passFail.setText("(" + Integer.toString(temp.getScore()) + ")");


        } else if (temp.getPushUpScore() >= 60 && temp.getSitUpScore() >= 60 && temp.getRunScore() >= 60) {
            finalScore.setText(" Passed");
            passFail.setText("(" + Integer.toString(temp.getScore()) + ")");
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putSerializable("calculator", temp);
    }
}
