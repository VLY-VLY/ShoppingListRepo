package org.shopping.shoppinglist;


import android.util.Log;

import java.util.ArrayList;

public class Element {
    public static final String             _TAG = "Element";
                  ArrayList<String>        _ListFields;
                  ArrayList<Integer>       _ListOfLocations;

    Element() {
        //Log.i(_TAG, "Element::Element() - Begining");
        this._ListFields      = new ArrayList<String>();
        this._ListOfLocations = new ArrayList<Integer>();

        //Log.i(_TAG, "Element::Element() - End");
    }
    void AddField(String iField) {
        //Log.i(_TAG, "Element::AddField(String iField) - Begining");
        this._ListFields.add(iField);
        //Log.i(_TAG, "Element::AddField(String iField) - End");
    }

    int GetNumberOfFields(){
        return this._ListFields.size();
    }

    void AddLocation(Integer iLocation){
        this._ListOfLocations.add(iLocation);
    }

    String GetField(int iIndex) {
        String TmpString = this._ListFields.get(iIndex);
        return TmpString;
    }

    ArrayList <Integer>  GetLocations() {
        return this._ListOfLocations;
    }

    void DisplayElement() {
        //Log.i(_TAG, "Element::DisplayElement() - Begining");
        //for (String TmpString : _ListFields)
        //    Log.i(_TAG, "|" + TmpString.toString() + "|");
        String TmpString = new String("Element:|");
        for(int i = 0 ; i < _ListFields.size(); i++) {
            TmpString=TmpString+this.GetField(i)+"|";
        }
        Log.i(_TAG, "Element::DisplayElement() - "+TmpString.toString());
        //Log.i(_TAG, "Element::DisplayElement() - End");
    }

};
