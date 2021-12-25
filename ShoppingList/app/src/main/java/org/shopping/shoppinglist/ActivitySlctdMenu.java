package org.shopping.shoppinglist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ActivitySlctdMenu extends AppCompatActivity {
    public  static final String   _TAG                               = "ActivitySlctdMenu";
    private static       String   _Tag_MenuSelection_ActivityName    = "MenuSelection_ActivityName";

    ExpLstViewAdp _listAdapter;
    ExpandableListView            _expListView;
    List<String>                  _listDataHeader;
    HashMap<String, List<String>> _listDataChild;
    private ArrayList<String>     _ListOfSelectedMenu  = new ArrayList<String>();
    private float _x1,_x2,_y1,_y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(_TAG, "ActivitySlctdMenu::onCreate - Begin");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_slctdmenu);
        // get the listview
        _expListView = (ExpandableListView) findViewById(R.id.lvExp);

        ArrayList<String> UnsortedListOfSelectedMenu = null;
        Intent intent = getIntent();

        _ListOfSelectedMenu = intent.getStringArrayListExtra(_ServicesConstants._LIST_SELECTED_MENUS);
        Log.i(_TAG, "ActivitySlctdMenu::onCreate - Retreiving data and building hashtables");
        PrepareListData();

        _listAdapter = new ExpLstViewAdp(this, _listDataHeader, _listDataChild);

        _expListView.setAdapter(_listAdapter);

        Log.i(_TAG, "ActivitySlctdMenu::PrepareListData - Expanding all the expandableView to visualize all selections");
        for (int i = 0; i < this._listDataHeader.size(); i++)
            _expListView.expandGroup(i);

        this._expListView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent touchEvent) {
                return LaunchActivity(touchEvent, " _expListView::onTouchEvent");
            }
        });

        _expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.i(_TAG, "ActivitySlctdMenu::onChildClick - Begin");
                Log.i(_TAG, "ActivitySlctdMenu::onChildClick - Selection:|" +
                        _listDataHeader.get(groupPosition) + "|" +
                        _listDataChild.get(_listDataHeader.get(groupPosition)).get(childPosition) + "|");
                /*
                // Create a Intent:
                // (This object contains content that will be sent to ActivityChooseMenu).
                Intent myIntent = new Intent(ShowSelectedMenu.this, ActivityShowReceipe.class);
                Log.i(_TAG,"ShowSelectedMenu::onCreate::onClick - Adding parameters");
                myIntent.putExtra("Menu", _listDataChild.get(_listDataHeader.get(groupPosition)).get(childPosition));
                Log.i(_TAG,"ShowSelectedMenu::Show_Selected_Menu_Button::onClick - Adding parameter");
                ShowSelectedMenu.this.startActivity(myIntent);*/
                Log.i(_TAG, "ActivitySlctdMenu::onChildClick - End");
                return false;
            }
        });

        this._expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.i(_TAG, "ActivitySlctdMenu::onItemLongClick - Begin");
                ExpandableListView listView = (ExpandableListView) adapterView;

                long pos = listView.getExpandableListPosition(position);
                // get type and correct positions
                int itemType = ExpandableListView.getPackedPositionType(pos);
                int groupPos = ExpandableListView.getPackedPositionGroup(pos);
                int childPos = ExpandableListView.getPackedPositionChild(pos);
                Log.i(_TAG, "ActivitySlctdMenu::onItemLongClick - groupPos |" + groupPos + "|");
                Log.i(_TAG, "ActivitySlctdMenu::onItemLongClick - childPos |" + childPos + "|");
                String MenuName = _listDataChild.get(_listDataHeader.get(groupPos)).get(childPos);
                Log.i(_TAG, "Item selected at position |" + position + "| is:|" + MenuName + "|");
                Log.i(_TAG, "ActivitySlctdMenu::onItemLongClick - End");
                return true; //Returning true so the OnItemClickwill not be called
            }
        });
    }
      public boolean onTouchEvent(MotionEvent touchEvent){
         //Log.i(_TAG,"ActivitySlctdMenu::onTouchEvent - Begin");
        LaunchActivity(touchEvent,"ActivitySlctdMenu::onTouchEvent");
        return false;
    }

    private void PrepareListData() {
        Log.i(_TAG, "ActivitySlctdMenu::PrepareListData - Begin");
        this._listDataHeader = new ArrayList<String>();
        _listDataChild = new HashMap<String, List<String>>();

        this._listDataChild = new HashMap<String, List<String>>();
        this._listDataHeader = new ArrayList<String>();
        for (String KeyMenuName:this._ListOfSelectedMenu){
            Log.i(_TAG, "ActivitySlctdMenu::PrepareListData - Working on:|"+KeyMenuName+"|");
            int tmpIndex = KeyMenuName.indexOf(_ServicesConstants._SEMICOLON_CHARACTER, 0);
            String DayName = new String(KeyMenuName.substring(0, tmpIndex));
            if (!this._listDataHeader.contains(DayName))
                this._listDataHeader.add(DayName);
        }
        for (String Day:this._listDataHeader){
            ArrayList <String> ListOfMenuOfTheDay=new ArrayList<String>();
            for(String KeyMenuName:this._ListOfSelectedMenu){
                if(KeyMenuName.contains(Day)){
                    int tmpIndex = KeyMenuName.indexOf(_ServicesConstants._SEMICOLON_CHARACTER, 0);
                    String MenuName= new String(KeyMenuName.substring(tmpIndex+1, KeyMenuName.length()));
                    ListOfMenuOfTheDay.add(MenuName);
                }
            }Log.i(_TAG, "ActivitySlctdMenu::Adding elements in hashtable for:|"+Day+"|");
            Collections.sort(ListOfMenuOfTheDay);
            _listDataChild.put(Day,ListOfMenuOfTheDay);
        }
        Log.i(_TAG,"ActivitySlctdMenu::PrepareListData - End");
    }

    @Override
    protected void onStop() {
        Log.i(_TAG,"ActivitySlctdMenu::onStop - Begin");
        super.onStop();
        Log.i(_TAG,"ActivitySlctdMenu::onStop - End");
    }

    @Override
    protected void onDestroy() {
        Log.i(_TAG,"ActivitySlctdMenu::onDestroy - Begin");
        super.onDestroy();
        Log.i(_TAG,"ActivitySlctdMenu::onDestroy - End");
    }

    @Override
    protected void onPause() {
        Log.i(_TAG,"ActivitySlctdMenu::onPause - Begin");
        super.onPause();
        Log.i(_TAG,"ActivitySlctdMenu::onPause - End");
    }

    @Override
    protected void onResume() {
        Log.i(_TAG,"ActivitySlctdMenu::onResume - Begin");
        super.onResume();
        Log.i(_TAG,"ActivitySlctdMenu::onResume - End");
    }

    @Override
    protected void onStart() {
        Log.i(_TAG,"ActivitySlctdMenu::onStart - Begin");
        super.onStart();
        setTitle(_ServicesNLS.GetTagValue(ActivitySlctdMenu._Tag_MenuSelection_ActivityName));

        Log.i(_TAG,"ActivitySlctdMenu::onStart - End");
    }

    @Override
    protected void onRestart() {
        Log.i(_TAG,"ActivitySlctdMenu::onRestart - Begin");
        super.onRestart();
        Log.i(_TAG,"ActivitySlctdMenu::onRestart - End");
    }

    private boolean LaunchActivity (MotionEvent touchEvent,String iCaller){
        //Log.i(_TAG,"ActivitySlctdMenu::LaunchActivity - Begin");
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                _x1 = touchEvent.getX();
                _y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                _x2 = touchEvent.getX();
                _y2 = touchEvent.getY();
                if(_x1 > _x2){
                    Intent WorkingIntent = new Intent(ActivitySlctdMenu.this, ActivitySlctdIngrdnt.class);
                    WorkingIntent.putStringArrayListExtra(_ServicesConstants._LIST_SELECTED_MENUS,this._ListOfSelectedMenu);
                    startActivity(WorkingIntent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }else
                if(_x1 < _x2) {
                    this.finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                Log.i(_TAG,"ActivitySlctdMenu::LaunchActivity - Begin - End");
                break;
        }
        //Log.i(_TAG,"ActivitySlctdMenu::onTouchEvent - End");
        return false;
    }
}