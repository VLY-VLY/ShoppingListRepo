package org.shopping.shoppinglist;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class _ServicesNLS {
    //#File format
    //#LanguageName.Tag=TagValueForTheReferredLanguage
    //# Hash character means the line is to skip

    public  static final String            _TAG                          = "_ServicesNLS";

    private static ArrayList<String>       _LanguagesList                = new ArrayList<String>();
    private static final String            _LanguageFileName             = "NLSData.txt";
    private static ArrayList<String>       _Lines                        = null;
    private static HashMap<String, String> _TagsValues                   = new HashMap<String, String>();

    public static boolean            LoadLanguageFile(Context iContext){
        Log.i(_TAG, "_ServicesNLS::LoadLanguageFile - Begin");
        boolean Result = true;
        _ServicesNLS._Lines = _ServicesFiles.LoadFileFromAssets(iContext, _ServicesNLS._LanguageFileName);

        if (_ServicesNLS._Lines==null) {
            Log.i(_TAG, "_ServicesNLS::LoadLanguageFile - Failed to load NLS file:|"+_ServicesNLS._LanguageFileName+"|");
            Result = false;
        }
        else
           for (String Line: _ServicesNLS._Lines){
               Log.i(_TAG, "_ServicesNLS::LoadLanguageFile - Processing :|"+Line+"|");
               if(Line.startsWith(_ServicesConstants._HashString)     ||
                        Line.isEmpty()                          ||
                       !Line.contains(_ServicesConstants._EqualString)||
                       !Line.contains(_ServicesConstants._DotString)
               ){
                   Log.i(_TAG, "_ServicesNLS::LoadLanguageFile - Skipping   :|"+Line+"|");
                   continue;
               }
               String Language        = new String (Line.substring(0,Line.indexOf(_ServicesConstants._DotString)));
               String LanguagePlusTag = new String (Line.substring(0,Line.indexOf(_ServicesConstants._EqualString)));
               String Value      = new String (Line.substring(Line.indexOf(_ServicesConstants._EqualString)+1,Line.length()));
               Log.i(_TAG, "_ServicesNLS::LoadLanguageFile - Language   :|"+Language+"|");
               Log.i(_TAG, "_ServicesNLS::LoadLanguageFile - Tag        :|"+LanguagePlusTag+"|");
               Log.i(_TAG, "_ServicesNLS::LoadLanguageFile - Value      :|"+Value+"|");
               if (!_ServicesNLS._LanguagesList.contains(Language)) {
                    _ServicesNLS._LanguagesList.add(Language);
                }
               _ServicesNLS._TagsValues.put(LanguagePlusTag,Value);
        }
        for (String LanguageStr: _ServicesNLS._LanguagesList) {
            Log.i(_TAG, "_ServicesNLS::LoadLanguageFile - Language in List :|"+LanguageStr+"|");
        }
        for (String TagStr : _ServicesNLS._TagsValues.keySet()) {
            Log.i(_TAG, "_ServicesNLS::LoadLanguageFile - TagStr:|"+TagStr+"| has value :|"+ _ServicesNLS._TagsValues.get(TagStr)+"|");
        }
        Log.i(_TAG, "_ServicesNLS::LoadLanguageFile - End");
        return Result;
    }

    public static ArrayList <String> GetLanguageList(){
        Log.i(_TAG, "_ServicesNLS::GetLanguageList - Begin");
        Log.i(_TAG, "_ServicesNLS::GetLanguageList - End");
        ArrayList <String> Result = new ArrayList<>(_ServicesNLS._LanguagesList);
        return Result;
    }

    public static int                GetLanguageIndex(String iLanguage){
        Log.i(_TAG, "_ServicesNLS::GetLanguageIndex - Begin");
        int Res = _LanguagesList.indexOf(_ServicesSettings.GetLanguage());
        Log.i(_TAG, "_ServicesNLS::GetLanguageIndex - Current language index:|"+Res+"|");
        Log.i(_TAG, "_ServicesNLS::GetLanguageIndex - Begin");
        return Res;
    }

    public static String             GetTagValue(String iTag){
        Log.i(_TAG, "_ServicesNLS::GetTagValue - Begin");
        String Result = null;
        String WorkingTag= new String(_ServicesSettings.GetLanguage()+_ServicesConstants._DotString+iTag);
        Log.i(_TAG, "_ServicesNLS::GetTagValue - Retreiving value for Tag:|"+WorkingTag+"|");
        String Str = _ServicesNLS._TagsValues.get(WorkingTag);
        if (null == Str ){
            Log.i(_TAG, "_ServicesNLS::GetTagValue - Failed to get value for Tag:|"+iTag+"|");
            Result = new String (iTag);
        }
        else {
            Log.i(_TAG, "_ServicesNLS::GetTagValue - Value for Tag:|" + iTag + "| is |" + Str+"|");
            Result = new String(Str);
        }
            Log.i(_TAG, "_ServicesNLS::GetTagValue - End");
        return Str;
    }
}
