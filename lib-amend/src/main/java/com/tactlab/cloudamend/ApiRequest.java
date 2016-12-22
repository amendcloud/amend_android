package com.tactlab.cloudamend;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;


class ApiRequest extends AsyncTask<Void, Void, String> {
    private String serviceUrl;
    private int statusCode;
    private String inputData;
    private OnApiRequestDoneListener listener;
    private int reqCode;

    ApiRequest(String url, String inputData, OnApiRequestDoneListener listener, int requestCode) {
        this.serviceUrl = url;
        this.inputData = inputData == null ? "" : inputData;
        this.listener = listener;
        this.reqCode = requestCode;
    }

    @Override
    protected String doInBackground(Void... params) {
        String data = "";

        HttpURLConnection urlConnection = null;

        try {
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection) urlToRequest
                    .openConnection();


            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);


            urlConnection.setRequestProperty("Accept", "application/json;odata=verbose");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(inputData.getBytes().length));

            urlConnection.setRequestProperty("Content-Type", "application/json;odata=verbose");
            urlConnection.setRequestProperty("AccessKey", Amend.accessKey);
            urlConnection.setRequestProperty("AccessSecret", Amend.accessSecret);

            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            dataOutputStream.writeBytes(inputData);
            if (dataOutputStream != null) {
                dataOutputStream.flush();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            dataOutputStream = null;


            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(100000);
            // handle issues
            statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Log.d("URL Data ", "HTTP_UNAUTHORIZED");
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                Log.d("URL Data ", "HTTP_NOTOK " + statusCode);
            }

            // create JSON object from content
            if (statusCode == HttpURLConnection.HTTP_OK || (statusCode > 200 && statusCode < 209)) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                data = getResponseText(in);
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                data = getResponseText(in);
            }
        } catch (MalformedURLException e) {
            data = "";
            Log.e("Error ", "MalformedURLException");
        } catch (SocketTimeoutException e) {
            data = "";
            Log.e("Error ", "SocketTimeoutException");
        } catch (IOException e) {

            for (int i = 0; i < e.getStackTrace().length; i++) {

                Log.d("Error ", "IOException " + data + e.getStackTrace()[i].toString());
            }
            data = "";

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }


        }
        return data;

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (listener != null) {
            listener.onApiRequestDone(statusCode, result, reqCode);
        }
    }

    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    interface OnApiRequestDoneListener {
        void onApiRequestDone(int statusCode, String result, int requestCode);
    }
}
