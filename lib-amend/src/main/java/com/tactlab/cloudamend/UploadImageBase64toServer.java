package com.tactlab.cloudamend;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by deepak on 10-10-2016.
 */

public class UploadImageBase64toServer extends AsyncTask<Void, Void, String> {

    public static final String ID = "id";
    private int statusCode;

    private String serviceUrl;
    private Properties properties;
    private AmendUploadListener amendListener;

    private int reqCode;

    private Exception exception;
    private static final String IMAGE_NAME = "ImageName";
    private static final String RESOLUTION = "Resolution";
    private static final String SIZE = "Size";
    private static final String MIME_TYPE = "MimeType";

    private static final String HEIGHT = "Height";
    private static final String WIDTH = "Width";
    private static final String CREATED_DATE = "CreatedDate";
    private static final String CDN_URL = "CDNUrl";
    private static final String SECURE_CDN_URL = "SecureCDNUrl";


    public UploadImageBase64toServer(String serviceUrl, Properties properties, final AmendUploadListener amendListener, final int reqCode) {
        this.serviceUrl = serviceUrl;

        this.reqCode = reqCode;

        this.amendListener = amendListener;

        this.properties = properties;

    }


    @Override
    protected String doInBackground(Void... params) {
        String data = "";
        HttpURLConnection urlConnection = null;

        try {
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);


            String inputdata = "";
            if (properties != null) {
                inputdata = properties.getProperty(ID);

            }

            urlConnection.setRequestProperty("Accept", "application/json;odata=verbose");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(inputdata.getBytes().length));
            urlConnection.setRequestProperty("Content-Type", "application/json;odata=verbose");
            urlConnection.setRequestProperty("AccessKey", Amend.accessKey);
            urlConnection.setRequestProperty("AccessSecret", Amend.accessSecret);

            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            dataOutputStream.write(inputdata.getBytes(), 0, inputdata.getBytes().length);
            dataOutputStream.flush();
            dataOutputStream.close();


            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(100000);
            // handle issues
            statusCode = urlConnection.getResponseCode();

            // create JSON object from content
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                data = getResponseText(in);
            } else {

                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                if (in != null) {
                    data = getResponseText(in);
                }
            }

        } catch (Exception ex) {
            exception = ex;
        } finally {
            if (urlConnection != null) {
                try {
                    urlConnection.disconnect();
                } catch (Exception e) {

                }
            }
        }
        return data;

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (amendListener != null) {
            if (statusCode == 200) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String imgId = obj.getString(IMAGE_NAME);
                    int wt = obj.getInt(WIDTH);
                    int ht = obj.getInt(HEIGHT);
                    int size = obj.getInt(SIZE);
                    String mime_type = obj.getString(MIME_TYPE);
                    String cr_date = obj.getString(CREATED_DATE);
                    String res = obj.getString(RESOLUTION);
                    String url = obj.getString(CDN_URL);
                    String sc_url = obj.getString(SECURE_CDN_URL);
                    AmendResponse ar = new AmendResponse(imgId, wt, ht, size, res, mime_type, cr_date, url, sc_url);
                    amendListener.onSuccess(statusCode, reqCode, ar);
                } catch (Exception e) {
                    amendListener.onError(500, reqCode, e);
                }
            } else {
                if (exception != null) {
                    amendListener.onError(statusCode, reqCode, exception);
                } else {
                    try {
                        JSONObject obj = new JSONObject(result);
                        String msg = obj.getString(Amend.MESSAGE);
                        Exception e = new Exception(msg);
                        amendListener.onError(statusCode, reqCode, e);
                    } catch (Exception e) {
                        amendListener.onError(500, reqCode, e);
                    }
                }
            }
        }
    }

    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }
}
