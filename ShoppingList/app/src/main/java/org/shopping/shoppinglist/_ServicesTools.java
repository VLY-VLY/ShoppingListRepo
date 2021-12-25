package org.shopping.shoppinglist;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class _ServicesTools {
    public static final String _TAG = "_ServicesTools";

    public static HashMap<String, String> BuildHashMapStringStringFromLines(ArrayList<String> iLines) {
        Log.i(_TAG, "_ServicesFiles::BuildHashMapStringStringFromLines - Begin");
        HashMap<String, String> ContentFile = new HashMap<String, String>();
        for (String Str : iLines) {
            Log.i(_TAG, "_ServicesFiles::BuildHashMapStringStringFromLines - Filling Hashtable with:|" + Str + "|");
            String Name  = new String(Str.substring(0, Str.indexOf(_ServicesConstants._EQUAL_CHARACTER) + 1));
            String Value = new String(Str.substring(Str.indexOf(_ServicesConstants._EQUAL_CHARACTER) + 1, Str.length()));
            Log.i(_TAG, "_ServicesFiles::BuildHashMapStringStringFromLines - Adding Name-Value:|" + Value + "|->|" + Name + "|");
            ContentFile.put(Name, Value);
        }
        Log.i(_TAG, "_ServicesFiles::BuildHashMapStringStringFromLines - Begin");
        return ContentFile;
    }

    public static ArrayList <String>      BuildLinesFromHashMapStringString(HashMap<String, String> iHashMap) {
        ArrayList <String> Lines = new ArrayList <String> ();
        for (String TagStr : iHashMap.keySet()) {
            String Value = iHashMap.get(TagStr);
            Log.i(_TAG, "_ServicesFiles::SaveSettingsFile - Processing Name->Value|" + TagStr + "|->|" + Value + "|");
            Lines.add(new String(TagStr + Value));
        }
        Log.i(_TAG, "_ServicesSettings::SaveSettingsFile - End");
        return Lines;
    }
}

