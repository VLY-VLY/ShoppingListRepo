package org.shopping.shoppinglist;

import java.util.ArrayList;

//public static final String    _TAG                          = "_ServicesConstants";


public class MenuForCategory {
    private ArrayList<String>   _CategoryData;
    private static final int    _CategoryName = 0;
    private static final int    _MenuName     = 1;

    MenuForCategory(String iString){
        //MNU_0001;Samedi;Accompagnement Tristan;;;;;

        this._CategoryData  = new ArrayList<String>();
        int StartIndex      = iString.indexOf(_ServicesConstants._SEMICOLON_CHARACTER)+1;
        int EndIndex        = iString.indexOf(_ServicesConstants._SEMICOLON_CHARACTER,StartIndex);
        String CategoryName = new String(iString.substring(StartIndex, EndIndex));

        StartIndex          = EndIndex+1;
        EndIndex            = iString.indexOf(_ServicesConstants._SEMICOLON_CHARACTER,StartIndex);
        String MenuName     = new String(iString.substring(StartIndex, EndIndex));

        this._CategoryData.add(CategoryName);
        this._CategoryData.add(MenuName);
    }
    public String GetCategoryName(){
        String CategoryName = new String (this._CategoryData.get(MenuForCategory._CategoryName));
        return CategoryName;
    }
    public String GetMenuName(){
        String MenuName     = new String (this._CategoryData.get(MenuForCategory._MenuName));
        return MenuName;
    }
}
