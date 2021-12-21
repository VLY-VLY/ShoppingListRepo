package org.shopping.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class SelectedIngredients extends AppCompatActivity {
    public  static final String _TAG                 = "SelectedIngredients";
    private static        String  _Tag_SelectedIngredients_ActivityName  = "SelectedIngredients_ActivityName";

    private float _x1,_x2,_y1,_y2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(_TAG,"SelectedIngredients::onCreate - Begin");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectedingredients);
        Log.i(_TAG,"SelectedIngredients::onCreate - End");
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
                    /*Intent i = new Intent(SelectedIngredients.this, MenuSelection.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    */
                    this.finish();
                    //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }
                Log.i(_TAG,"SelectedIngredients::onTouchEvent - Begin - End");
                break;
        }
        //Log.i(_TAG,"SelectedIngredients::onTouchEvent - End");
        return false;
    }
    @Override
    protected void onStop() {
        Log.i(_TAG,"SelectedIngredients::onStop - Begin");
        super.onStop();
        Log.i(_TAG,"SelectedIngredients::onStop - End");
    }

    @Override
    protected void onDestroy() {
        Log.i(_TAG,"SelectedIngredients::onDestroy - Begin");
        super.onDestroy();
        Log.i(_TAG,"SelectedIngredients::onDestroy - End");
    }

    @Override
    protected void onPause() {
        Log.i(_TAG,"SelectedIngredients::onPause - Begin");
        super.onPause();
        Log.i(_TAG,"SelectedIngredients::onPause - End");
    }

    @Override
    protected void onResume() {
        Log.i(_TAG,"SelectedIngredients::onResume - Begin");
        super.onResume();
        Log.i(_TAG,"SelectedIngredients::onResume - End");
    }

    @Override
    protected void onStart() {
        Log.i(_TAG,"SelectedIngredients::onStart - Begin");
        super.onStart();
        setTitle(_ServicesNLS.GetTagValue(SelectedIngredients._Tag_SelectedIngredients_ActivityName));
        Log.i(_TAG,"SelectedIngredients::onStart - End");
    }

    @Override
    protected void onRestart() {
        Log.i(_TAG,"SelectedIngredients::onRestart - Begin");
        super.onRestart();
        Log.i(_TAG,"SelectedIngredients::onRestart - End");
    }
}