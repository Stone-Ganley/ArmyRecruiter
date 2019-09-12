package edu.citadel.android.armyrecruiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.PT_Calc:
                Intent intent1 = new Intent(this, PTcalcActivity.class);
                this.startActivity(intent1);
                return true;
            case R.id.Recruiter_Option:
                Intent intent2 = new Intent(this, Find_Recruiter.class);
                this.startActivity(intent2);
                return true;
            case R.id.About_Us:
                Intent intent3 = new Intent(this, About_Us.class);
                this.startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
