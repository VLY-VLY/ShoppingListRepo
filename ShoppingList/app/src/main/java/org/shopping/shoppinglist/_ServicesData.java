package org.shopping.shoppinglist;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
//MNU_0000;Day;Menu;;;;;;
//MNU_0001;Samedi;Accompagnement Tristan;;;;;;
//IGD_0000;MealDisplayNameAndKey;Ingredient;Quantity;Unit;Caterory;#Location#Alphabétique#;#Location#Super-U Bihorel#;#Location#Carrefour MSA#
//IGD_0001;Accompagnement Tristan;Carotte;3;Unité(s);Fruits et légumes;0;7;5
//IGD_0002;Accompagnement Tristan;Gingembre;1;CAS;Cuisine du monde;0;5;9

public class _ServicesData {
    public  static final String _TAG                                                      = "_ServicesData";
    // Internal files have to be in low caps otherwise an exception is raised
    private static final String                                 _DatabaseFileNameAssetStorage = "ingredientsdatabasefile.csv";
    private static final String                                 _RecipesFileNameAssetStorage  = "recettedecuisine.txt";
    //External files can have upper case
    private static final String                                 _DatabaseFileNameExterStorage = "IngredientsDatabaseFile.csv";
    private static final String                                 _RecipesFileNameExterStorage  = "RecetteDeCuisine.txt";
    //Data member definition
    private static       ArrayList<String>                      _CSVContentFile               = null;
    private static       ArrayList<String>                      _RecipesContentFile           = null;
    private static       ArrayList<String>                      _OrderedCategory              = new ArrayList <String>();
    private static       HashMap<String, ArrayList<String>>     _CategoryMenuListHashMap      = new HashMap<String,ArrayList<String>>();
    private static       HashMap<String, ArrayList<Ingredient>> _RecipeListHashMap            = new HashMap<String,ArrayList<Ingredient>>();

    public static ArrayList<Ingredient> GetIngredientList(String iRecipeName){
        return _RecipeListHashMap.get(iRecipeName);
    }

    public static boolean LoadDataFiles(Context iContext){
        Log.i(_TAG, "_ServicesData::LoadDataFiles - Begin");

        Log.i(_TAG, "_ServicesData::LoadDataFiles - Loading file:|"+_ServicesData._DatabaseFileNameExterStorage+"|");
        _ServicesData._CSVContentFile = _ServicesFiles.LoadFileFromExternalOrAssetLocation(
                iContext,
                _ServicesData._DatabaseFileNameAssetStorage,
                _ServicesData._DatabaseFileNameExterStorage);

        if (null == _ServicesData._CSVContentFile) {
            Log.i(_TAG, "_ServicesData::LoadDataFiles - End");
            return false;
        }
        Log.i(_TAG, "_ServicesData::LoadDataFiles - File:|"
                +_ServicesData._DatabaseFileNameExterStorage
                +"| successfully loaded with number of lines :|"
                +_ServicesData._CSVContentFile.size()
                +"|");

        Log.i(_TAG, "_ServicesData::LoadDataFiles - Loading file:|"+_ServicesData._RecipesFileNameExterStorage+"|");
        _ServicesData._RecipesContentFile = _ServicesFiles.LoadFileFromExternalOrAssetLocation(
                iContext,
                _ServicesData._RecipesFileNameAssetStorage,
                _ServicesData._RecipesFileNameExterStorage);

        if (null == _ServicesData._RecipesContentFile) {
            Log.i(_TAG, "_ServicesData::LoadDataFiles - End");
            return false;
        }
        Log.i(_TAG, "_ServicesData::LoadDataFiles - File:|"
                +_ServicesData._RecipesFileNameExterStorage
                +"| successfully loaded");
        return true;
    }

    public static boolean LoadDataInList(Context iContext) {
    Log.i(_TAG, "_ServicesData::LoadDataInList - Begining");
    boolean  Result = true;
    int NumberOfMenusLoaded       = 0;
    int NumberOfIngredientsLoaded = 0;
        for (String Line:_CSVContentFile){
            if (Line.length() == 0 ||
                Line.startsWith(_ServicesConstants._SEMICOLONS_STRING) ||
                Line.startsWith(_ServicesConstants._HEADER_MENU))
            {
                Log.i(_TAG, "_ServicesData::LoadDataInList - Skipping empty line");
                continue;
            }

            if(Line.startsWith(_ServicesConstants._HEADER_INGREDIENT)) {
                Log.i(_TAG, "_ServicesData::LoadDataInList - Processing the ingredient header line");
                //IGD_0000;MealDisplayNameAndKey;Ingredient;Quantity;Unit;Caterory;#Location#Alphabétique#;#Location#Super-U Bihorel#;#Location#Carrefour MSA#
                Ingredient.InitializeIngredientLocations(Line);
                continue;
            }
            if(Line.startsWith(_ServicesConstants._PREFIX_MENU)) {
                //MNU_0001;Samedi;Accompagnement Tristan;;;;;;
                NumberOfMenusLoaded++;
                int StartIndex      = Line.indexOf(_ServicesConstants._SEMICOLON_CHARACTER)+1;
                int EndIndex        = Line.indexOf(_ServicesConstants._SEMICOLON_CHARACTER,StartIndex);
                String CategoryName = new String(Line.substring(StartIndex, EndIndex)); //Samedi
                StartIndex          = EndIndex+1;
                EndIndex            = Line.indexOf(_ServicesConstants._SEMICOLON_CHARACTER,StartIndex);
                String MenuName     = new String(Line.substring(StartIndex, EndIndex)); //Accompagnement Tristan
                ArrayList<String> MenuList        = _ServicesData._CategoryMenuListHashMap.get(CategoryName);
                if (null == MenuList){
                    Log.i(_TAG, "_ServicesData::LoadDataInList - (1) Loading menu key :|"+CategoryName+"| adding :|"+MenuName+"|");
                    MenuList = new ArrayList<String>();
                    MenuList.add(new String (MenuName));
                    _ServicesData._CategoryMenuListHashMap.put(CategoryName,MenuList);
                    _ServicesData._OrderedCategory.add(CategoryName);
                }else {
                Log.i(_TAG, "_ServicesData::LoadDataInList - (2) Loading menu key :|"+CategoryName+"| adding :|"+MenuName+"|");
                MenuList.add(new String (MenuName));
            }
        }
        if(Line.startsWith(_ServicesConstants._PREFIX_INGREDIENT)) {
            //IGD_0001;Accompagnement Tristan;Carotte;3;Unité(s);Fruits et légumes;0;7;5
            NumberOfIngredientsLoaded++;
            Ingredient  WorkingIngredient = new Ingredient(Line,iContext);
            String RecipeName     = WorkingIngredient.GetRecipeName(); //Accompagnement Tristan
            String IngredientName = WorkingIngredient.GetIngredientStringToDisplay();//Carotte 3 Unité(s)
            ArrayList<Ingredient> IngredientList = _ServicesData._RecipeListHashMap.get(RecipeName);
            if (null == IngredientList){
                IngredientList = new ArrayList<Ingredient>();
                IngredientList.add(WorkingIngredient);
                _ServicesData._RecipeListHashMap.put(RecipeName,IngredientList);
                Log.i(_TAG, "_ServicesData::LoadDataInList - (1) Loading ingredient key :|"+RecipeName+"| adding :|"+IngredientName+"|");
            }else {
                Log.i(_TAG, "_ServicesData::LoadDataInList - (2) Loading ingredient key :|"+RecipeName+"| adding :|"+IngredientName+"|");
                IngredientList.add(WorkingIngredient);
            }
        }
    }
        Log.i(_TAG, "_ServicesData::LoadDataInList - Number of menus loaded        :"+NumberOfMenusLoaded);
        Log.i(_TAG, "_ServicesData::LoadDataInList - Number of ingredients loaded  :"+NumberOfIngredientsLoaded);
        //_ServicesData.CategoryMenuListHashMap();
    Log.i(_TAG, "_ServicesData::LoadDataInList - End");
    return Result;
    }

    public static ArrayList <String> GetListOrderedCategory(){
        return _ServicesData._OrderedCategory;
    }

    public static HashMap<String, ArrayList<String>> GetListCategoryHashmap(){
        return _ServicesData._CategoryMenuListHashMap;
    }

    private static void  CategoryMenuListHashMap(){
        Log.i(_TAG, "_ServicesData::CategoryMenuListHashMap - Begin");
        for (String TagStr : _ServicesData._OrderedCategory) {
            ArrayList<String>  ListString = _ServicesData._CategoryMenuListHashMap.get(TagStr);
            for(String WorkingString:ListString){
                Log.i(_TAG, "_ServicesData::CategoryMenuListHashMap - Printing Key->Value|" + TagStr + "|->|" + WorkingString + "|");
            }
        }
        Log.i(_TAG, "_ServicesData::CategoryMenuListHashMap - End");
    }
}
