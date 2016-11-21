package com.tactlab.cloudamendsample.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Support {

    public static final String BASE_FOLDER_PATH = "CloudAmend";

    public static final String TEMP_PROFILE_PIC = "temp001.dat";

    public static String getBaseFolderPath(Context con) throws IOException {
        String folderName = BASE_FOLDER_PATH;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            folderName = Environment.getExternalStorageDirectory() + "/"
                    + folderName;
        } else {
            folderName = con.getFilesDir() + "/" + folderName;
        }
        File f = new File(folderName);
        if (!f.exists()) {
            f.mkdirs();
            File nomedia = new File(folderName + "/" + ".NOMEDIA");
            try {
                nomedia.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return folderName;
    }


    public static void showAlertDialog(Context con, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setTitle("Alert");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //Toast.makeText(con,"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public static File getTempFile(Context con, String fileName) throws IOException {
        File mFileTemp;
        mFileTemp = new File(getBaseFolderPath(con), fileName + ".jpg");
        return mFileTemp;
    }


    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
}