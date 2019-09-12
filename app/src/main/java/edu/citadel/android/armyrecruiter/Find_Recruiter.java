package edu.citadel.android.armyrecruiter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class Find_Recruiter extends AppCompatActivity {

    private static RecruiterWebCrawler temp = new RecruiterWebCrawler();
    private EditText zipCode;
    private TextView address;
    Button findRecruiter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__recruiter);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        address = findViewById(R.id.address);
        zipCode = findViewById(R.id.zipCode);
        findRecruiter = findViewById(R.id.findRec);

        zipCode.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_STATE_VISIBLE);

        findRecruiter.setOnClickListener((View view) ->{

                Toast toast = Toast.makeText(this, "loading One Moment", Toast.LENGTH_LONG);
                toast.setMargin(100, 100);
                toast.show();

                DownloadRecruitTask task = new DownloadRecruitTask(this);
                task.execute(zipCode.getText().toString());

        });
    }
    private static class DownloadRecruitTask extends AsyncTask<String, Void, RecruiterWebCrawler>
    {

        private WeakReference<Find_Recruiter> activityRef;

        DownloadRecruitTask(Find_Recruiter context)
        {
            activityRef = new WeakReference<>(context);
        }
        protected RecruiterWebCrawler doInBackground(String... symbols)
        {
         int zip = Integer.parseInt(symbols[0]);
         return RecruiterWebCrawler.WebCrawler(zip);
        }

        protected void onPostExecute(RecruiterWebCrawler recruiter)
        {
            Find_Recruiter activity = activityRef.get();
            if(activity == null || activity.isFinishing())
                return;

                temp = recruiter;
                activity.address.setText(recruiter.getAddress());
        }
    }
    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        temp = (RecruiterWebCrawler) savedInstanceState.getSerializable("recruiter");
        this.address.setText(temp.getAddress());

    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putSerializable("recruiter", temp);
    }
}
