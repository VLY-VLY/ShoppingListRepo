package org.shopping.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivityMenuChooser extends AppCompatActivity {
    public  static final String  _TAG                            = "MainActivityMenuChooser";
    private static        String  _Tag_MenuChooser_ActivityName  = "MenuChooser_ActivityName";
    //  ExpandableListAdapter listAdapter;
    ExpLstViewAdpChkbox            _listAdapter;
    ExpandableListView             _expListView;
    ArrayList<String>              _listDataHeader;
    HashMap<String, List<String>>  _listDataChild;
    private              float     _x1,_x2,_y1,_y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(_TAG,"MenuChooser::onCreate - Begin");

        super.onCreate(savedInstanceState);
        Log.i(_TAG, "MenuChooser::onCreate - Loading settings");
        if (_ServicesSettings.LoadSettings((Context) this))
            Log.i(_TAG, "MenuChooser::onCreate - Settings loaded successfully");
        else
            Log.i(_TAG, "MenuChooser::onCreate - Failed to load settings");

        Log.i(_TAG, "MenuChooser::onCreate - Loading languages file name");

        if (_ServicesNLS.LoadLanguageFile((Context) this))
            Log.i(_TAG, "MenuChooser::onCreate - language file loaded successfully");
        else
            Log.i(_TAG, "MenuChooser::onCreate - Failed to load language file");

        if (_ServicesData.LoadDataFiles((Context) this))
            Log.i(_TAG, "MenuChooser::onCreate - Data files loaded successfully");
        else
            Log.i(_TAG, "MenuChooser::onCreate - Failed to load data files");

        setContentView(R.layout.activity_menuchooser);
        // get the listview
        _expListView = (ExpandableListView) findViewById(R.id.lvExp);
        // preparing list data
        prepareListData();
        _listAdapter = new ExpLstViewAdpChkbox(this, _listDataHeader, _listDataChild);
        // setting list adapter
        _expListView.setAdapter(_listAdapter);

        this._expListView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                return LaunchActivity(touchEvent," _ListViewSortList::onTouchEvent");
            }
        });

        Log.i(_TAG,"MenuChooser::onCreate - End ");
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent){
        //Log.i(_TAG,"MenuChooser::onTouchEvent - Begin");
        this.LaunchActivity(touchEvent,"MenuChooser::onTouchEvent");
        //Log.i(_TAG,"MenuChooser::onTouchEvent - End");
        return false;
    }

    private boolean LaunchActivity (MotionEvent touchEvent,String iCaller){
        //Log.i(_TAG,"Administration::LaunchActivity - Begin");
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                _x1 = touchEvent.getX();
                _y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                _x2 = touchEvent.getX();
                _y2 = touchEvent.getY();
                if(_x1 > _x2){
                    Log.i(_TAG,"MenuChooser::LaunchActivity - Begin");
                    ArrayList <String> tmpLs=this._listAdapter.GetSelectedElement();
                    for (String Str:tmpLs){
                        Log.i(_TAG,"Administration::LaunchActivity - Str=|"+Str+"|");
                    }
                    Intent i = new Intent(MainActivityMenuChooser.this, MenuSelected.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    Log.i(_TAG,"MenuChooser::LaunchActivity - End");
                }else
                if(_x1 < _x2){
                    Log.i(_TAG,"MenuChooser::LaunchActivity - Begin");
                    ArrayList <String> tmpLs=this._listAdapter.GetSelectedElement();
                    for (String Str:tmpLs){
                        Log.i(_TAG,"Administration::LaunchActivity - Str=|"+Str+"|");
                    }
                    Intent i = new Intent(MainActivityMenuChooser.this, Administration.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    Log.i(_TAG,"MenuChooser::LaunchActivity - End");

                }
                break;
        }
        //Log.i(_TAG,"Administration::onTouchEvent - End");
        return false;
    }

    @Override
    protected void onStop() {
        Log.i(_TAG,"MenuChooser::onStop - Begin");
        super.onStop();
        Log.i(_TAG,"MenuChooser::onStop - End");
    }

    @Override
    protected void onDestroy() {
        Log.i(_TAG,"MenuChooser::onDestroy - Begin");
        super.onDestroy();
        Log.i(_TAG,"MenuChooser::onDestroy - End");
    }

    @Override
    protected void onPause() {
        Log.i(_TAG,"MenuChooser::onPause - Begin");
        super.onPause();
        Log.i(_TAG,"MenuChooser::onPause - End");
    }

    @Override
    protected void onResume() {
        Log.i(_TAG,"MenuChooser::onResume - Begin");
        super.onResume();
        Log.i(_TAG,"MenuChooser::onResume - End");
    }

    @Override
    protected void onStart() {
        Log.i(_TAG,"MenuChooser::onStart - Begin");
        super.onStart();
        setTitle(_ServicesNLS.GetTagValue(MainActivityMenuChooser._Tag_MenuChooser_ActivityName));

        Log.i(_TAG,"MenuChooser::onStart - End");
    }

    @Override
    protected void onRestart() {
        Log.i(_TAG,"MenuChooser::onRestart - Begin");
        super.onRestart();
        Log.i(_TAG,"MenuChooser::onRestart - End");
    }

    private void prepareListData() {
        _listDataHeader = new ArrayList<String>();
        _listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        _listDataHeader.add("Top 250");
        _listDataHeader.add("Now Showing");
        _listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        _listDataChild.put(_listDataHeader.get(0), top250); // Header, Child data
        _listDataChild.put(_listDataHeader.get(1), nowShowing);
        _listDataChild.put(_listDataHeader.get(2), comingSoon);
    }
}