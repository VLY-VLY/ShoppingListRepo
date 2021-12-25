package org.shopping.shoppinglist;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityAdministration extends AppCompatActivity {
    public  static final String   _TAG                              = "ActivityAdministration";
    private static final String   _Tag_Administration_ActivityName  = "Administration_ActivityName";
    private static final String   _Tag_Administration_LocationName  = "Administration_LocationName";
    private static final String   _Tag_Administration_LanguageName  = "Administration_LanguageName";
//modif1-2-3-45
    private              TextView _TextViewLanguageMode             = null;
    private              TextView _TextViewLocationMode             = null;
    private              ListView _ListViewLanguageList             = null;
    private              ListView _ListViewLocationList             = null;
    private ArrayAdapter<String>  _ArrayAdapterLanguageMode         = null;
    private ArrayAdapter<String>  _ArrayAdapterLocation             = null;
    private Context               _Context                          = null;
    private ArrayList<String>     _ListOfLocation                   = new ArrayList <String>();

    private float                 _x1,_x2,_y1,_y2                   = 0;
    private int                   _SelectedLanguageIndex            = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(_TAG,"Administration::onCreate - Begin");
        super.onCreate(savedInstanceState);
        this._Context=this;
        setContentView(R.layout.activity_administration);

        Log.i(_TAG,"Administration::onCreate - Building element for the location");
        this._TextViewLocationMode = (TextView)this.findViewById(R.id.SortMode);
        this._TextViewLocationMode.setTypeface(Typeface.DEFAULT_BOLD);

        this._ListViewLocationList = (ListView)this.findViewById(R.id.SortList);
        this._ListViewLocationList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        this._ListViewLocationList.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent touchEvent) {
                return LaunchActivity(touchEvent," _ListViewLocationList::onTouchEvent");
            }
        }
        );

        this._ListViewLocationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(_TAG, "Administration::onItemClick::Location - Begin");
                CheckedTextView v = (CheckedTextView) view;
                String LocationKind = v.getText().toString();
                Ingredient.SetIngredientSortIndex(position);
                _ServicesSettings.SetSortIndex(position);
                _ServicesSettings.SaveSettings(_Context);
                Log.i(_TAG, "Administration::onItemClick::Location - Item selected at position |"+position+"| is:|" +LocationKind+"|");
                Log.i(_TAG, "Administration::onItemClick::Location - Begin");
            }
        }
        );
        this._ListOfLocation = Ingredient.GetListLocationName();
        this._ArrayAdapterLocation = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked ,this._ListOfLocation);
        this._ListViewLocationList.setAdapter(this._ArrayAdapterLocation);

        int LocationIndex = _ServicesSettings.GetSortType();
        //Ingredient.SetIngredientSortIndex(LocationIndex);
        LocationIndex     = Ingredient.GetIngredientSortIndex();
        Log.i(_TAG, "Administration::onCreate - Location index retrieved from previous run:|"+LocationIndex+"|");
        this._ListViewLocationList.setItemChecked(LocationIndex, true);

        Log.i(_TAG,"Administration::onCreate - Building element for the language");
        this._TextViewLanguageMode = (TextView)this.findViewById(R.id.LanguageMode);
        this._TextViewLanguageMode.setTypeface(Typeface.DEFAULT_BOLD);

        this._ListViewLanguageList = (ListView)this.findViewById(R.id.LanguageList);
        this._ListViewLanguageList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        this._ListViewLanguageList.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent touchEvent) {
                return LaunchActivity(touchEvent," _ListViewLanguageList::onTouchEvent");}});

        this._ListViewLanguageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(_TAG, "Administration::onItemClick::Language - Begin");
                _SelectedLanguageIndex=position;
                CheckedTextView v = (CheckedTextView) view;
                String Language = v.getText().toString();
                _ServicesSettings.SetLanguage(Language);
                setTitle(_ServicesNLS.GetTagValue(ActivityAdministration._Tag_Administration_ActivityName));
                _TextViewLanguageMode.setText(_ServicesNLS.GetTagValue(ActivityAdministration._Tag_Administration_LanguageName));
                _TextViewLocationMode.setText(_ServicesNLS.GetTagValue(ActivityAdministration._Tag_Administration_LocationName));
                _ServicesSettings.SaveSettings(_Context);
                Log.i(_TAG, "Administration::onItemClick::Language - Item selected at position |"+position+"| is:|" +Language+"|");
                Log.i(_TAG, "Administration::onItemClick::Language - End");
            }
        });

        ArrayList <String> ListOfLanguage = _ServicesNLS.GetLanguageList();
        this._ArrayAdapterLanguageMode = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked ,ListOfLanguage);
        this._ListViewLanguageList.setAdapter(this._ArrayAdapterLanguageMode);
        String Str = _ServicesSettings.GetLanguage();
        this._ListViewLanguageList.setItemChecked(_ServicesNLS.GetLanguageIndex(Str), true);

        Log.i(_TAG,"Administration::onCreate - End");
    }

    private boolean LaunchActivity (MotionEvent touchEvent,String iCaller){
        //Log.i(_TAG,"Administration::onTouchEvent - Begin");

        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                _x1 = touchEvent.getX();
                _y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                _x2 = touchEvent.getX();
                _y2 = touchEvent.getY();
                if(_x1 > _x2){
                    this.finish();
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
                Log.i(_TAG,"Administration::LaunchActivity - Begin - End from"+iCaller);
                break;
        }
        //Log.i(_TAG,"Administration::onTouchEvent - End");
        return false;
    }

    @Override
      public boolean onTouchEvent(MotionEvent touchEvent){
        //Log.i(_TAG,"Administration::onTouchEvent - Begin");
        return LaunchActivity(touchEvent," Administration::onTouchEvent");
    }

    @Override
    protected void onStop() {
        Log.i(_TAG,"Administration::onStop - Begin");
        super.onStop();
        Log.i(_TAG,"Administration::onStop - End");
    }

    @Override
    protected void onDestroy() {
        Log.i(_TAG,"Administration::onDestroy - Begin");
        super.onDestroy();
        Log.i(_TAG,"Administration::onDestroy - End");
    }

    @Override
    protected void onPause() {
        Log.i(_TAG,"Administration::onPause - Begin");
        super.onPause();
        Log.i(_TAG,"Administration::onPause - End");
    }

    @Override
    protected void onResume() {
        Log.i(_TAG,"Administration::onResume - Begin");
        super.onResume();
        Log.i(_TAG,"Administration::onResume - End");
    }

    @Override
    protected void onStart() {
        Log.i(_TAG,"Administration::onStart - Begin");
        super.onStart();
        setTitle(_ServicesNLS.GetTagValue(ActivityAdministration._Tag_Administration_ActivityName));
        _TextViewLanguageMode.setText(_ServicesNLS.GetTagValue(ActivityAdministration._Tag_Administration_LanguageName));
        _TextViewLocationMode.setText(_ServicesNLS.GetTagValue(ActivityAdministration._Tag_Administration_LocationName));
        Log.i(_TAG,"Administration::onStart - End");
    }

    @Override
    protected void onRestart() {
        Log.i(_TAG,"Administration::onRestart - Begin");
        super.onRestart();
        Log.i(_TAG,"Administration::onRestart - End");
    }
}