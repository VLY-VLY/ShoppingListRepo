package org.shopping.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class MenuSelected extends AppCompatActivity {
    public  static final String _TAG                 = "MenuSelection";
    private static        String  _Tag_MenuSelection_ActivityName  = "MenuSelection_ActivityName";

    private float _x1,_x2,_y1,_y2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(_TAG,"MenuSelection::onCreate - Begin");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuselection);
        Log.i(_TAG,"MenuSelection::onCreate - End");
    }

      public boolean onTouchEvent(MotionEvent touchEvent){
          //Log.i(_TAG,"MenuSelection::onTouchEvent - Begin");

        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                _x1 = touchEvent.getX();
                _y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                _x2 = touchEvent.getX();
                _y2 = touchEvent.getY();
                if(_x1 > _x2){
                    Intent i = new Intent(MenuSelected.this, SelectedIngredients.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }else
                if(_x1 < _x2) {
                    this.finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                Log.i(_TAG,"MenuSelection::onTouchEvent - Begin - End");
                break;
        }
          //Log.i(_TAG,"MenuSelection::onTouchEvent - End");
        return false;
    }
    @Override
    protected void onStop() {
        Log.i(_TAG,"MenuSelection::onStop - Begin");
        super.onStop();
        Log.i(_TAG,"MenuSelection::onStop - End");
    }

    @Override
    protected void onDestroy() {
        Log.i(_TAG,"MenuSelection::onDestroy - Begin");
        super.onDestroy();
        Log.i(_TAG,"MenuSelection::onDestroy - End");
    }

    @Override
    protected void onPause() {
        Log.i(_TAG,"MenuSelection::onPause - Begin");
        super.onPause();
        Log.i(_TAG,"MenuSelection::onPause - End");
    }

    @Override
    protected void onResume() {
        Log.i(_TAG,"MenuSelection::onResume - Begin");
        super.onResume();
        Log.i(_TAG,"MenuSelection::onResume - End");
    }

    @Override
    protected void onStart() {
        Log.i(_TAG,"MenuSelection::onStart - Begin");
        super.onStart();
        setTitle(_ServicesNLS.GetTagValue(MenuSelected._Tag_MenuSelection_ActivityName));

        Log.i(_TAG,"MenuSelection::onStart - End");
    }

    @Override
    protected void onRestart() {
        Log.i(_TAG,"MenuSelection::onRestart - Begin");
        super.onRestart();
        Log.i(_TAG,"MenuSelection::onRestart - End");
    }
}