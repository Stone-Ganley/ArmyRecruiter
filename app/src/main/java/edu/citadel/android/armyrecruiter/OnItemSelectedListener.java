package edu.citadel.android.armyrecruiter;

import android.view.View;
import android.widget.AdapterView;

class OnItemSelectedListener
        implements AdapterView.OnItemSelectedListener
{
    public void onItemSelected(AdapterView<?> parent,
                               View view, int pos, long id)
    {
        parent.getItemAtPosition(pos);

    }
    public void onNothingSelected(AdapterView<?> parent)
    {
// Do nothing.
    }
}