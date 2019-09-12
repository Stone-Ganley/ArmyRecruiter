package edu.citadel.android.armyrecruiter;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PTcalculator implements Serializable
{
    private static final long serialVersionUID = 46827221313596640L;

    private String gender;
    private String age;
    private int pushUps;
    private int sitUps;
    private int runTime;
    private int score;
    private int pushUpScore;
    private int sitUpScore;
    private int runScore;


    public static PTcalculator calculate(int PushUps, int SitUps, int run, String Gender, String ageRange)
    {
        PTcalculator Temp = new PTcalculator(PushUps, SitUps, run, Gender, ageRange);
        try
        {

            //URL is a script created to turn a google sheets int JSON object
            //Essentially a cloud database
            URL url = new URL(("https://script.google.com/macros/s/" +
                    "AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/" +
                    "exec?id=18TpOVmEJ7FUa1iJGyzTsIRgmNVA05etbsrwvrWNhcNw&sheet=PTStandards"));;
            InputStreamReader isReader = new InputStreamReader(url.openStream());
            BufferedReader reader = new BufferedReader(isReader);

            StringBuilder builder = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null)
                builder.append(line + "\n");

            return new PTcalculator(builder.toString(), Temp);
        }
        catch (Exception ex)
        {
            return new PTcalculator();
        }


}

    public PTcalculator()
    {
        this.gender = "";
        this.age = "";
        this.pushUps = 0;
        this.sitUps = 0;
        this.runTime = 0;
        this.score = -1;
        this.pushUpScore = 0;
        this.sitUpScore = 0;
        this.runScore = 0;

    }

    public PTcalculator(int pushUps, int sitUps, int run, String gender, String age)
    {
         setPushUps(pushUps);
        setSitUps(sitUps);
        setRunTime(run);
        setGender(gender);
        setAge(age);

    }

    private PTcalculator(String PTCalcJsonStr, PTcalculator calc)
    {
        try
        {
            JSONObject PTCALCJson = new JSONObject(PTCalcJsonStr);
            JSONArray rows = PTCALCJson.getJSONArray("PTStandards");
            ScoreSearch(rows, calc);
        }
        catch (JSONException ex)
        {
            this.gender = "";
            this.age = "";
            this.pushUps = 0;
            this.sitUps = 0;
            this.runTime = 0;
            this.score = -1;
            this.pushUpScore = 0;
            this.sitUpScore = 0;
            this.runScore = 0;
        }
    }

    private void ScoreSearch(JSONArray JSONArg, PTcalculator calc){
    try {
        if(calc.getGender().equalsIgnoreCase("male")) {
            //find score for push ups
            for (int i = 0; i < 258; i++) {
                JSONObject jsonArr = JSONArg.getJSONObject(i);
                if (jsonArr.getInt("reps") == calc.getPushUps() &&
                        jsonArr.getString("exercise").equalsIgnoreCase("push ups")) {
                    this.pushUpScore += jsonArr.getInt(calc.getAge());
                } else if (jsonArr.getInt("reps") == calc.getSitUps() &&
                        jsonArr.getString("exercise").equalsIgnoreCase("sit ups")) {
                    this.sitUpScore += jsonArr.getInt(calc.getAge());
                } else if (jsonArr.getInt("reps") == calc.getRunTime() &&
                        jsonArr.getString("exercise").equalsIgnoreCase("run")) {
                    this.runScore += jsonArr.getInt(calc.getAge());
                }

            }
            this.score += this.runScore + this.pushUpScore + this.sitUpScore;
        }else if (calc.getGender().equalsIgnoreCase("female")){
            for (int c = 258; c < 489; c++) {
                JSONObject jsonArr1 = JSONArg.getJSONObject(c);
                if (jsonArr1.getInt("reps") == calc.getPushUps() &&
                        jsonArr1.getString("exercise").equalsIgnoreCase("push ups")) {
                    this.pushUpScore += jsonArr1.getInt(calc.getAge());
                } else if (jsonArr1.getInt("reps") == calc.getSitUps() &&
                        jsonArr1.getString("exercise").equalsIgnoreCase("sit ups")) {
                    this.sitUpScore += jsonArr1.getInt(calc.getAge());
                } else if (jsonArr1.getInt("reps") == calc.getRunTime() &&
                        jsonArr1.getString("exercise").equalsIgnoreCase("run")) {
                    this.runScore += jsonArr1.getInt(calc.getAge());
                }

            }
            this.score += this.runScore + this.pushUpScore + this.sitUpScore;
        }


    }catch (JSONException ex){
        this.gender = "";
        this.age = "";
        this.pushUps = 0;
        this.sitUps = 0;
        this.runTime = 0;
        this.score = -1;
        this.pushUpScore = 0;
        this.sitUpScore = 0;
        this.runScore = 0;

    }
    }

    public int getPushUps() {
        return pushUps;
    }

    public void setPushUps(int pushUps) {
        this.pushUps = pushUps;
    }

    public int getSitUps() {
        return sitUps;
    }

    public void setSitUps(int sitUps) {
        this.sitUps = sitUps;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPushUpScore() {
        return pushUpScore;
    }

    public void setPushUpScore(int pushUpScore) {
        this.pushUpScore = pushUpScore;
    }

    public int getSitUpScore() {
        return sitUpScore;
    }

    public void setSitUpScore(int sitUpScore) {
        this.sitUpScore = sitUpScore;
    }

    public int getRunScore() {
        return runScore;
    }

    public void setRunScore(int runScore) {
        this.runScore = runScore;
    }
}



