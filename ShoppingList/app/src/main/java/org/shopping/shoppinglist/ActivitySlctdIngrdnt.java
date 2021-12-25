package org.shopping.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivitySlctdIngrdnt extends AppCompatActivity {
    public  static final String     _TAG                                    = "ActivitySlctdIngrdnt";
    private static       String     _Tag_SelectedIngredients_ActivityName   = "SelectedIngredients_ActivityName";
    private ArrayList<Ingredient>   _IngredientList                         = null;

    private float _x1,_x2,_y1,_y2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(_TAG,"ActivitySlctdIngrdnt::onCreate - Begin");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slctdingrdnt);

        this._IngredientList = new ArrayList<Ingredient>();

        ArrayList<String> ListOfSelectedMenu = null;
        Intent intent = getIntent();
        ListOfSelectedMenu = intent.getStringArrayListExtra(_ServicesConstants._LIST_SELECTED_MENUS);
        Log.i(_TAG,"ActivitySlctdIngrdnt::onCreate - Retrieving list of all ingredients");
        for (String Str:ListOfSelectedMenu){
            int tmpIndex = Str.indexOf(_ServicesConstants._SEMICOLON_CHARACTER, 0);
            String MenuName= new String(Str.substring(tmpIndex+1, Str.length()));
            Log.i(_TAG,"ActivitySlctdIngrdnt::onCreate - Retrieving ingredients for:|"+MenuName+"|");
            this._IngredientList.addAll(_ServicesData.GetIngredientList(MenuName));
        }
        Log.i(_TAG,"ActivitySlctdIngrdnt::onCreate - List of retrieved ingredients");
        for(Ingredient Ing:this._IngredientList) {
            Log.i(_TAG,"ActivitySlctdIngrdnt::onCreate - Menu->Ingredient:|"+Ing.GetRecipeName()+"|->|"+Ing.GetIngredientStringToDisplay()+"|");
        }

        Log.i(_TAG,"ActivitySlctdIngrdnt::onCreate - End");
    }

      public boolean onTouchEvent(MotionEvent touchEvent){
        //Log.i(_TAG,"SelectedIngredients::onTouchEvent - Begin");

        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                _x1 = touchEvent.getX();
                _y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                _x2 = touchEvent.getX();
                _y2 = touchEvent.getY();
                if(_x1 < _x2){
                    this.finish();
                    //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }
                Log.i(_TAG,"ActivitySlctdIngrdnt::onTouchEvent - Begin - End");
                break;
        }
        //Log.i(_TAG,"SelectedIngredients::onTouchEvent - End");
        return false;
    }
    @Override
    protected void onStop() {
        Log.i(_TAG,"ActivitySlctdIngrdnt::onStop - Begin");
        super.onStop();
        Log.i(_TAG,"ActivitySlctdIngrdnt::onStop - End");
    }

    @Override
    protected void onDestroy() {
        Log.i(_TAG,"ActivitySlctdIngrdnt::onDestroy - Begin");
        super.onDestroy();
        Log.i(_TAG,"ActivitySlctdIngrdnt::onDestroy - End");
    }

    @Override
    protected void onPause() {
        Log.i(_TAG,"ActivitySlctdIngrdnt::onPause - Begin");
        super.onPause();
        Log.i(_TAG,"ActivitySlctdIngrdnt::onPause - End");
    }

    @Override
    protected void onResume() {
        Log.i(_TAG,"ActivitySlctdIngrdnt::onResume - Begin");
        super.onResume();
        Log.i(_TAG,"ActivitySlctdIngrdnt::onResume - End");
    }

    @Override
    protected void onStart() {
        Log.i(_TAG,"ActivitySlctdIngrdnt::onStart - Begin");
        super.onStart();
        setTitle(_ServicesNLS.GetTagValue(ActivitySlctdIngrdnt._Tag_SelectedIngredients_ActivityName));
        Log.i(_TAG,"ActivitySlctdIngrdnt::onStart - End");
    }

    @Override
    protected void onRestart() {
        Log.i(_TAG,"ActivitySlctdIngrdnt::onRestart - Begin");
        super.onRestart();
        Log.i(_TAG,"ActivitySlctdIngrdnt::onRestart - End");
    }
}