package org.shopping.shoppinglist;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class Ingredient implements Comparable <Ingredient> {
    public  static final String _TAG                                   = "Ingredient";
    private static final String _LOCATIONTAG                           = "#Location#";
    private static final String _Tag_Ingredient_IntTranslationFailure  = "Ingredient_IntTranslationFailure";


    private static       ArrayList<String>  _ListIgdFeatureStatic      = new ArrayList<String>();
    public  static       ArrayList<String>  _ListLocationNameStatic    = new ArrayList<String>();
    private static       int                _SortIndex                 = 0;

    private static final int                _INGREDIENTINDEX           = 0;
    private static final int                _RECIPENAME                = 1;
    private static final int                _INGREDIENT                = 2;
    private static final int                _QUANTITY                  = 3;
    private static final int                _UNIT                      = 4;

    public               ArrayList<String>  _ListIgdFeatureInstance    = null;
    public               ArrayList<Integer> _ListLocationInstance    = null;

    public static  boolean InitializeIngredientLocations(String iString) {
        Log.i(_TAG, "Ingredient::InitializeIngredientLocations - Begin");
        Ingredient._SortIndex = _ServicesSettings.GetSortType();

        //IGD_0000;MealDisplayNameAndKey;Ingredient;Quantity;Unit;Caterory;#Location#Alphabétique#;#Location#Super-U Bihorel#;#Location#Carrefour MSA#
        int StartIndex = 0;
        int EndIndex = iString.indexOf(_ServicesConstants._SEMICOLON_CHARACTER, StartIndex);
        String WorkingString;
        Log.i(_TAG, "Ingredient::InitializeIngredientLocations - Processing:|" + iString + "|");

        for (StartIndex = 0; StartIndex < iString.length(); EndIndex = iString.indexOf(_ServicesConstants._SEMICOLON_CHARACTER, StartIndex)) {
            if (EndIndex == -1)
                EndIndex = iString.length();
            WorkingString = iString.substring(StartIndex, EndIndex);
            Log.i(_TAG, "Ingredient::InitializeIngredientLocations - Substring found:|" + WorkingString + "|");
            if (WorkingString.startsWith(Ingredient._LOCATIONTAG)) {
                int start = Ingredient._LOCATIONTAG.length();
                int end = WorkingString.indexOf(_ServicesConstants._HASH_CHARACTER, start + 1);
                String Location = WorkingString.substring(start, end);
                Log.i(_TAG, "Ingredient::InitializeIngredientLocations - Location found:|" + Location + "|");
                Ingredient._ListLocationNameStatic.add(Location);
            } else
                _ListIgdFeatureStatic.add(WorkingString);
            StartIndex = EndIndex + 1;
        }
        for (String Str : Ingredient._ListIgdFeatureStatic) {
            Log.i(_TAG, "Ingredient::InitializeIngredientLocations - _ListIngredientFeature item:|" + Str + "|");
        }
        for (String Str : Ingredient._ListLocationNameStatic) {
            Log.i(_TAG, "Ingredient::InitializeIngredientLocations - _ListLocationName:|" + Str + "|");
        }
        Log.i(_TAG, "Ingredient::InitializeIngredientLocations - End");
        return true;
    }
    public static  ArrayList<String> GetListLocationName(){
        return Ingredient._ListLocationNameStatic;
    }
    public static  void    SetIngredientSortIndex(int iIndex){
        Ingredient._SortIndex = iIndex;
    }
    public static  int     GetIngredientSortIndex(){
        return Ingredient._SortIndex;
    }
    Ingredient(String iString, Context iContext) {
        //Log.i(_TAG, "Ingredient::Ingredient(String iString) - Begin");
        //IGD_0001;Accompagnement Tristan;Carotte;3;Unité(s);Fruits et légumes;0;7;5
        this._ListIgdFeatureInstance = new ArrayList<String>();
        this._ListLocationInstance = new ArrayList<Integer>();
        int StartIndex = 1;
        int EndIndex = iString.indexOf(_ServicesConstants._SEMICOLON_CHARACTER, StartIndex);
        String WorkingString;
        //Log.i(_TAG, "Ingredient::Ingredient - Processing input:|" + iString + "|");
        for (StartIndex = 0; StartIndex < iString.length(); EndIndex = iString.indexOf(_ServicesConstants._SEMICOLON_CHARACTER, StartIndex)) {
            if (EndIndex == -1) {
                EndIndex = iString.length();
                //continue;
            }
            WorkingString = iString.substring(StartIndex, EndIndex);
            //Log.i(_TAG, "Ingredient::Ingredient - Substring found:|" + WorkingString + "|");
            if(this._ListIgdFeatureInstance.size()<Ingredient._ListIgdFeatureStatic.size())
                this._ListIgdFeatureInstance.add(WorkingString);
            else {
                Integer LocationNumber = 0;
                try {
                    LocationNumber = Integer.parseInt(WorkingString);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    String Message = _ServicesNLS.GetTagValue(Ingredient._Tag_Ingredient_IntTranslationFailure);
                    Message+=_ServicesConstants._SPACE_CHARACTER+ WorkingString;
                    Message+=_ServicesConstants._ARROW+this._ListIgdFeatureInstance.get(0);
                    //Log.i(_TAG, "Ingredient::Ingredient - Conversion error:|" + Message + "|");
                    Toast.makeText(iContext,
                            Message,
                            Toast.LENGTH_LONG).show();
                }
                this._ListLocationInstance.add(LocationNumber);
            }
            StartIndex = EndIndex + 1;
        }
        //Log.i(_TAG, "Ingredient::Ingredient(String iString) - End");
    }
    public         int     compareTo(final Ingredient iIngredient) {
        int LocationThis  = this._ListLocationInstance.get(Ingredient._SortIndex);
        int LocationOther = iIngredient._ListLocationInstance.get(Ingredient._SortIndex);
        int res = Integer.compare((int) LocationThis, (int) LocationOther);
        if (res == 0) {
            //res = String.CASE_INSENSITIVE_ORDER.compare(this.GetMealDisplayNameAndKey(),iIngredient.GetMealDisplayNameAndKey());
            res = this.GetIngredientName().compareTo(iIngredient.GetIngredientName());
        }
        return (res);
    }
    public         void    Display() {
        Log.i(_TAG, "Ingredient::DisplayIngredient() - Begin");
        String DisplayString;
        for (String TmpString : this._ListIgdFeatureInstance)
            Log.i(_TAG, "Ingredient::DisplayIngredient() - Component|" + TmpString.toString() + "|");
        for (int Location : this._ListLocationInstance)
            Log.i(_TAG, "Ingredient::DisplayIngredient() - Location |" + Location + "|");
         Log.i(_TAG, "Ingredient::DisplayIngredient() - End");
    }
    public         String  GetIngredientStringToDisplay(){
        String TmpString = new String();
        TmpString = TmpString + this._ListIgdFeatureInstance.get(Ingredient._INGREDIENT)+_ServicesConstants._SPACE_CHARACTER;
        TmpString = TmpString + this._ListIgdFeatureInstance.get(Ingredient._QUANTITY)+_ServicesConstants._SPACE_CHARACTER;
        TmpString = TmpString + this._ListIgdFeatureInstance.get(Ingredient._UNIT);
        return TmpString;
    }
    public         String  GetIngredientName(){
        return this._ListIgdFeatureInstance.get(Ingredient._INGREDIENT);
    }
    public         String  GetRecipeName(){
        return this._ListIgdFeatureInstance.get(Ingredient._RECIPENAME);
    }

}

/*


    Ingredient(String iMealDisplayNameAndKey, String iIngredient, String iQuantity, String iUnit, ArrayList<Integer> iLocations, int iSortIndex) {
        this._ListIngredientFeature= new ArrayList<String>();
        this.AddField(iMealDisplayNameAndKey);
        this.AddField(iIngredient);
        this.AddField(iQuantity);
        this.AddField(iUnit);
        this._SortIndex = iSortIndex;
        this._ListOfLocations = new ArrayList<Integer>(iLocations);
    }

    private void AddField(String iField) {
        //Log.i(_TAG, "Ingredient::AddField(String iField) - Begining");
        this._ListIngredientFeature.add(iField);
        //Log.i(_TAG, "Ingredient::AddField(String iField) - End");
    }


    ArrayList<Integer> GetListOfLocation() {
        //Log.i(_TAG, "Ingredient::GetListOfLocation() - Begining");
        return this._ListOfLocations;
        //Log.i(_TAG, "Ingredient::GetListOfLocation() - End");
    }

    void DisplayIngredient() {
        //Log.i(_TAG, "Ingredient::DisplayIngredient() - Begining");
        //for (String TmpString : _ListIngredientFeature)
        //    Log.i(_TAG, "|" + TmpString.toString() + "|");
        String TmpString = new String("Ingredient:|");
        TmpString = TmpString + this.GetMealDisplayNameAndKey()+"|";
        TmpString = TmpString + this.GetIngredient()+"|";
        TmpString = TmpString + this.GetQuantity()+"|";
        TmpString = TmpString + this.GetUnit()+"|";
        for(int i=0;i<this._ListOfLocations.size();i++)
            TmpString = TmpString + this._ListOfLocations.get(i)+"|";
        Log.i(_TAG, "Ingredient::DisplayIngredient() - "+TmpString.toString());
        //Log.i(_TAG, "Ingredient::DisplayIngredient() - End");
    }

    String GetMealDisplayNameAndKey(){
        return this.GetField(Ingredient._MEALDISPLAYNAMEANDKEY);
    }

    String GetIngredient(){
        return this.GetField(Ingredient._INGREDIENT);
    }





}*/
