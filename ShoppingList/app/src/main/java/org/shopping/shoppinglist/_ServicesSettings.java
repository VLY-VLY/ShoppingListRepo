package org.shopping.shoppinglist;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class _ServicesSettings {
    public  static final String _TAG                                    = "_ServicesSettings";

    private static final String                  _SettingsFileName      = "settingsfile.txt";
    private static       String                  _SortingTag            = "SortingValue=";
    private static       String                  _SortingDefault        = "0";
    private static       String                  _LanguageTag           = "Language=";
    private static       String                  _LanguageDefault       = "Francais";
    private static       boolean                 _Witness               = false;
    private static       HashMap<String, String> _SettingHashMapContent = null;

    public static  boolean  LoadSettings(Context iContext){
        Log.i(_TAG, "_ServicesSettings::LoadSettings - Begin");
        boolean Result = true;
        _SettingHashMapContent = _ServicesSettings.GetSettingHashMapFromFile(iContext);
        if(null==_ServicesSettings._SettingHashMapContent) {
            Log.i(_TAG, "_ServicesSettings::LoadSettings - Failed to load the Hashmap settings");
            Result = false;
        }
        Log.i(_TAG, "_ServicesSettings::LoadSettings - End");
        return Result;
    }

    public static  boolean  SetLanguage(String iValue) {
        Log.i(_TAG, "_ServicesSettings::SetLanguage - Begin");
        boolean Res = true;
        if (!_ServicesSettings.SetSetting(_ServicesSettings._LanguageTag,iValue)) {
            Res = false;
            Log.i(_TAG, "_ServicesSettings::SetLanguage - Failed to set the language");
        }
        Log.i(_TAG, "_ServicesSettings::SetLanguage - End");
        return Res;
    }

    public static  String   GetLanguage() {
        Log.i(_TAG, "_ServicesSettings::GetLanguage - Begin");
        String Res = _ServicesSettings.GetSettingValue(_ServicesSettings._LanguageTag);
        if (null==Res)
            Log.i(_TAG, "_ServicesSettings::GetLanguage - Failed to get the language");
        else
            Log.i(_TAG, "_ServicesSettings::GetLanguage - Language found:|"+Res+"|");
        Log.i(_TAG, "_ServicesSettings::GetLanguage - End");
        return Res;
    }

    public static  boolean  SetSortType(int iValue) {
        Log.i(_TAG, "_ServicesSettings::SetSortType - Begin");
        boolean Res = true;
        String Str=Integer.toString(iValue);
        if (!_ServicesSettings.SetSetting(_ServicesSettings._SortingTag,Str)) {
            Res = false;
            Log.i(_TAG, "_ServicesSettings::SetSortType - Failed to set the SortType");
        }
        Log.i(_TAG, "_ServicesSettings::SetSortType - End");
        return Res;
    }

    public static  int      GetSortType() {
        Log.i(_TAG, "_ServicesSettings::GetSortType - Begin");
        String Res = _ServicesSettings.GetSettingValue(_ServicesSettings._LanguageTag);
        if (null==Res)
            Log.i(_TAG, "_ServicesSettings::GetSortType - Failed to get the SortType");
        int ResultConversion = Integer.parseInt(Res);
        Log.i(_TAG, "_ServicesSettings::GetSortType - SortType found:|"+ResultConversion+"|");
        Log.i(_TAG, "_ServicesSettings::GetSortType - End");
        return ResultConversion;
    }

    public static  boolean  SaveSettings(Context iContext){
        Log.i(_TAG, "_ServicesSettings::SaveSettingsFile - Begin");
        ArrayList <String> SettingFileContent = _ServicesTools.BuildLinesFromHashMapStringString(_ServicesSettings._SettingHashMapContent);
        boolean Res = true;
        if (null == SettingFileContent){
            Log.i(_TAG, "_ServicesSettings::SaveSettingsFile - Failed to convert the HasmMap into a string of list");
            Res = false;
        }
        Res = _ServicesFiles.SaveInternalDataFile(iContext,SettingFileContent, _ServicesSettings._SettingsFileName);
        if (!Res){
            Log.i(_TAG, "_ServicesSettings::SaveSettingsFile - Failed to save the setting file");
            Res = false;
        }
        Log.i(_TAG, "_ServicesSettings::SaveSettingsFile - End");
        return Res;
    }

    private static String   GetSettingValue(String iTag) {
        Log.i(_TAG, "_ServicesSettings::GetSettingValue - Begin");
        String Result = _ServicesSettings._SettingHashMapContent.get(iTag);

        if( null == Result) {
         Log.i(_TAG, "_ServicesSettings::GetSettingValue - Settings not found for |"+iTag+"|");
        }else{
            Log.i(_TAG, "_ServicesSettings::GetSettingValue - Settings found for |"+iTag+"|->|"+Result+"|");
        }
        Log.i(_TAG, "_ServicesSettings::GetSettingValue - End");
        return Result;
    }

    private static boolean  SetSetting(String iName,String iValue){
        if ( ! _ServicesSettings._SettingHashMapContent.containsKey(iName))
            return false;
        _ServicesSettings._SettingHashMapContent.remove(iName);
        _ServicesSettings._SettingHashMapContent.put(iName,iValue);
        return true;
    }

    private static HashMap<String, String>  GetSettingHashMapFromFile(Context iContext){
        Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles - Begin");
        HashMap<String, String> ContentFile = new HashMap<String, String>();
        ArrayList <String> SettingFileContent = _ServicesFiles.ReadInternalDataFile(iContext, _ServicesSettings._SettingsFileName);
        if (SettingFileContent == null) {
            Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles - First run, creating a default setting file");
            String DefaultSorting  = new String(_ServicesSettings._SortingTag +_ServicesConstants._EqualString+ _ServicesSettings._SortingDefault);
            String DefaultLanguage = new String(_ServicesSettings._LanguageTag + _ServicesSettings._LanguageDefault);
            SettingFileContent = new ArrayList <String> ();
            SettingFileContent.add(new String(_ServicesSettings._SortingTag + _ServicesSettings._SortingDefault));
            SettingFileContent.add(new String(_ServicesSettings._LanguageTag + _ServicesSettings._LanguageDefault));
            Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles - Content of the file to create:");
            for (String Str : SettingFileContent) {
                Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles - Line :|"+Str+"|");
            }
           if(_ServicesFiles.SaveInternalDataFile(iContext,SettingFileContent, _ServicesSettings._SettingsFileName))
                Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles - First run Settings File saved successfully");
            else {
                Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles - Failed to save first run settings file");
                return null;
            }
            SettingFileContent = _ServicesFiles.LoadFileFromAssets(iContext, _ServicesSettings._SettingsFileName);
            if(null == SettingFileContent) {
                Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles - Failed to read settings file");
                return null;
            }
        }
        Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles - Building the HashMap");

        ContentFile=_ServicesTools.BuildHashMapStringStringFromLines(SettingFileContent);

        if(null == ContentFile){
            Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles - Failed to build HashMap");
            return null;
        }
        Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles - Content of the HashMap");
        for (String TagStr : ContentFile.keySet()) {
            Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles - Name->Value:|"+TagStr+"|->|"+ ContentFile.get(TagStr)+"|");
            SettingFileContent.add(new String(TagStr+ContentFile.get(TagStr)));
        }
        Log.i(_TAG, "_ServicesSettings::ReadSettingsFiles -End");
        return ContentFile;
    }
}
