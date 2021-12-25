package org.shopping.shoppinglist;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class _ServicesFiles {
    public  static final String   _TAG                                = "_ServicesFiles";
    private static final String   _Tag_ServicesFiles_FileSaved        = "ServicesFiles_FileSaved";
    private static final String   _Tag_ServicesFiles_FileSavedError   = "ServicesFiles_FileSavedError";
    private static final String   _Tag_ServicesFiles_FileOpenError    = "ServicesFiles_FileOpenError";
    private static final String   _Tag_ServicesFiles_FileReadError    = "ServicesFiles_FileReadError";


    public  static ArrayList<String>  LoadFileFromAssets(Context iContext,String iFileName) {
        Log.i(_TAG, "_ServicesFiles::LoadDataFromAssets - Begin");
        Scanner  Reader                       = null;
        ArrayList <String> ListLines = new ArrayList<String>();
        try {
            // To create teh file in Androidstudio:
            // 1 - Create an asset directory: File>New>Folder>Assets folder
            // 2 - Put the file there
            InputStream in = iContext.getAssets().open(iFileName);
            String AssetPath = iContext.getAssets().toString();
            Log.i(_TAG, "_ServicesFiles::LoadDataFromAssets - AssetPath:|"+AssetPath+"|");
            Reader = new Scanner(in, _ServicesConstants._CHAR_SET);
            while (Reader.hasNextLine()) {
                String TmpString=Reader.nextLine();
                ListLines.add(TmpString);
                Log.i(_TAG, "|" + TmpString.toString() + "|");
            }
        } catch (IOException e) {
            Log.i(_TAG, "_ServicesFiles::LoadDataFromAssets - Read error (catch)" + e.getMessage());
            Toast.makeText(iContext.getApplicationContext(),
                    _ServicesNLS.GetTagValue(_ServicesFiles._Tag_ServicesFiles_FileReadError) + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } finally {
            try {
                Reader.close();
            } catch (Exception e) {
                Log.i(_TAG, "_ServicesFiles::LoadDataFromAssets - Read error (finally)" + e.getMessage());
                Toast.makeText(iContext.getApplicationContext(),
                        _ServicesNLS.GetTagValue(_ServicesFiles._Tag_ServicesFiles_FileReadError) + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
        Log.i(_TAG, "_ServicesFiles::LoadDataFromAssets - End");
        return ListLines;
    }

    public  static ArrayList<String>  LoadFileFromExternalOrAssetLocation(Context iParentActivity,String iFileNameAsset,String iFileNameExternalStorage){
        Log.i(_TAG, "_ServicesFiles::LoadFileFromExternalOrAssetLocation - Begin");
        ArrayList <String> CSVFileContent     = null;
        ArrayList <String> ReceipeFileContent = null;
        File extStore = GetAppExternalFilesDir(iParentActivity);
        Boolean Result = true;
        Log.i(_TAG, "_ServicesFiles::LoadFileFromExternalOrAssetLocation - iFileNameAsset          :|" + iFileNameAsset + "|");
        Log.i(_TAG, "_ServicesFiles::LoadFileFromExternalOrAssetLocation - iFileNameExternalStorage:|" + iFileNameExternalStorage + "|");

        String DestinationPath = extStore.getAbsolutePath() + _ServicesConstants._SLASH + iFileNameExternalStorage;
        Log.i(_TAG, "_ServicesFiles::LoadFileFromExternalOrAssetLocation - Working on file:|" + DestinationPath + "|");
        File myFile = new File(DestinationPath);
        if(!myFile.exists()) {
            Log.i(_TAG, "_ServicesFiles::LoadFileFromExternalOrAssetLocation - Reading file from Assets");
            CSVFileContent = _ServicesFiles.LoadFileFromAssets(iParentActivity,iFileNameAsset);
            Log.i(_TAG, "_ServicesFiles::LoadFileFromExternalOrAssetLocation - Number of lines for file read:" + CSVFileContent.size());
            Log.i(_TAG, "_ServicesFiles::LoadFileFromExternalOrAssetLocation - Writting the file in external location");
            Result = _ServicesFiles.WriteFileInExternalLocation(iParentActivity,CSVFileContent,iFileNameExternalStorage);

            if(Result)
                Log.i(_TAG, "_ServicesFiles::LoadFileFromExternalOrAssetLocation - Writting file completed successfully");
            else
                Log.i(_TAG, "_ServicesFiles::LoadFileFromExternalOrAssetLocation - Failed to write the file");
        }
        Log.i(_TAG, "_ServicesFiles::LoadFileFromExternalOrAssetLocation - Loading database file from external location");
        CSVFileContent = _ServicesFiles.ReadFileInExternalLocation(iParentActivity,iFileNameExternalStorage);
        Log.i(_TAG, "_ServicesFiles::LoadFileFromExternalOrAssetLocation - End");
        return CSVFileContent;
    }

    public  static boolean            SaveInternalDataFile(Context iContext,ArrayList <String> iLinesToSave,String iFileName) {
        Log.i(_TAG, "_ServicesFiles::SaveInternalDataFile - Begin");
        boolean Result = true;
        try {
            // Open Stream to write file.
            FileOutputStream out = iContext.openFileOutput(iFileName,Context.MODE_PRIVATE);
            if(out == null) {
                Log.i(_TAG, "_ServicesFiles::SaveInternalDataFile - openFileOutput returned null");
                Log.i(_TAG, "_ServicesFiles::SaveInternalDataFile - End (1)");
                return false;
            }
            for (String TmpString:iLinesToSave) {
                TmpString=TmpString+ _ServicesConstants._END_OF_LINE;
                Log.i(_TAG, "_ServicesFiles::SaveInternalDataFile - Writing line:"+TmpString);
                out.write(TmpString.getBytes());
            }
            out.close();
        } catch (Exception e) {
            Toast.makeText(iContext,
                    _ServicesNLS.GetTagValue(_ServicesFiles._Tag_ServicesFiles_FileSavedError)+ e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            Result = false;
        }
        Log.i(_TAG, "_ServicesFiles::SaveInternalDataFile - End");
        return Result;
    }

    public  static ArrayList<String>   ReadInternalDataFile(Context iContext, String iFileName) {
        Log.i(_TAG, "_ServicesFiles::ReadInternalDataFile - Begin");
        ArrayList <String> ResultList = new ArrayList<String>();
        try {
            // Open stream to read file.
            FileInputStream in = iContext.openFileInput(iFileName);
            BufferedReader br= new BufferedReader(new InputStreamReader(in));
            StringBuilder sb= new StringBuilder();
            String s= null;
            while((s= br.readLine())!= null)  {
                Log.i(_TAG, "_ServicesFiles::ReadInternalDataFile - LineRead:|"+s+"|");
                ResultList.add(s);
            }
        } catch (Exception e) {
            Log.i(_TAG, "_ServicesFiles::ReadInternalDataFile - Internal file not found.");
            Log.i(_TAG, "_ServicesFiles::ReadInternalDataFile - Error caught:|"+e.getMessage().toString()+"|");
        }
        if (ResultList.size()==0) {
            Log.i(_TAG, "_ServicesFiles::ReadInternalDataFile - No data available");
            ResultList = null;
        }
        else{
            Log.i(_TAG, "_ServicesFiles::ReadInternalDataFile - Returning a lis of "+ResultList.size()+" lines");
        }
        Log.i(_TAG, "_ServicesFiles::ReadInternalDataFile - End ");
        return ResultList;
    }

    private static boolean            WriteFileInExternalLocation(Context iContext,ArrayList <String> iLinesToWrite, String iFileName) {
        boolean Result = true;
        try {
            Log.i(_TAG, "_ServicesFiles::WriteFileInExternalLocation - Begin");
            File extStore = _ServicesFiles.GetAppExternalFilesDir(iContext);
            String path = extStore.getAbsolutePath() + "/" +iFileName;
            Log.i(_TAG, "_ServicesFiles::WriteFileInExternalLocation - File will be saved in :|" + path + "|");
            File myFile = new File(path);
            FileOutputStream fOut = new FileOutputStream(myFile);
            for (String Line:iLinesToWrite){
                Line = Line + _ServicesConstants._END_OF_LINE;
                fOut.write(Line.getBytes(_ServicesConstants._CHAR_SET));
            }
            fOut.close();
            Toast.makeText(iContext.getApplicationContext(),
                    iFileName + _ServicesNLS.GetTagValue(_ServicesFiles._Tag_ServicesFiles_FileSaved),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(iContext.getApplicationContext(),
                    _ServicesNLS.GetTagValue(_ServicesFiles._Tag_ServicesFiles_FileSavedError) + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            Log.i(_TAG, "Write Error: " + e.getMessage());
            e.printStackTrace();
            Result = false;
        }
        Log.i(_TAG, "_ServicesFiles::WriteFileInExternalLocation - Begin");
        return Result;
    }

    private static ArrayList <String> ReadFileInExternalLocation(Context iContext,String iFileName) {
        ArrayList <String> FileContent = new ArrayList <String> ();
        try {
            Log.i(_TAG, "_ServicesFiles::ReadFileInExternalLocation - Begin");
            Scanner Reader;
            File extStore = _ServicesFiles.GetAppExternalFilesDir(iContext);
            String path = extStore.getAbsolutePath() + _ServicesConstants._SLASH + iFileName;
            Log.i(_TAG, "_ServicesFiles::ReadFileInExternalLocation - Reading file:|" + path +"|");
            InputStream in = new FileInputStream(path);
            Reader = new Scanner(in, _ServicesConstants._CHAR_SET);
            while (Reader.hasNextLine()) {
                String TmpString=Reader.nextLine();
                FileContent.add(TmpString);
                //Log.i(_TAG, "|" + TmpString.toString() + "|");
            }
        }catch (IOException e) {
            Toast.makeText(iContext.getApplicationContext(),
                    _ServicesNLS.GetTagValue(_ServicesFiles._Tag_ServicesFiles_FileOpenError)+ e.getMessage(),
                    Toast.LENGTH_LONG).show();
            Log.e(_TAG, "Read Error: " + e.getMessage());
            e.printStackTrace();
        }
        Log.i(_TAG, "_ServicesFiles::ReadFileInExternalLocation - End");
        return FileContent;
    }

    private static File               GetAppExternalFilesDir(Context iParentActivity)  {
        Log.i(_TAG, "_ServicesFiles::getAppExternalFilesDir - Begin - End");

        if (android.os.Build.VERSION.SDK_INT >= 29) {
            // /storage/emulated/0/Android/data/org.o7planning.externalstoragedemo/files
            return iParentActivity.getExternalFilesDir(null);
        } else {
            // @Deprecated in API 29.
            // /storage/emulated/0
            return Environment.getExternalStorageDirectory();
        }
    }


}
