package org.shopping.shoppinglist;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class _ServicesData {
    public  static final String _TAG                                     = "_ServicesData";
    // Internal files have to be in low caps otherwise an exception is raised
    private static final String            _DatabaseFileNameAssetStorage = "ingredientsdatabasefile.csv";
    private static final String            _RecipesFileNameAssetStorage = "recettedecuisine.txt";

    //External files can have upper case
    private static final String            _DatabaseFileNameExterStorage = "IngredientsDatabaseFile.csv";
    private static final String            _RecipesFileNameExterStorage = "RecetteDeCuisine.txt";

    private static       ArrayList<String> _CSVContentFile;
    private static       ArrayList<String> _RecipesContentFile;



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
}
