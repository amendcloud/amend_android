package com.tactlab.cloudamend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Properties;

/**
 * Created by deepak on 26-09-2016.
 */

public class RequestManager {
    private static final String IMAGE_NAME = "ImageName";
    private static final String NEW_NAME = "NewName";
    private Context context;
    private String imageName;
    private String imageUrl;
    private String options;
    private Util u = new Util();
    private int placeHolderId = 0;
    private RequestListener listener;
    private boolean isFetchCalled = false;

    RequestManager(Context context) {
        this.context = context;
    }

    private static boolean isNetworkOnline(Context con) {
        boolean status;
        try {

            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);

            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);

                status = netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return status;
    }

    public void rename(String imageName, final String newName, int requestCode, final AmendRenameListener amendListener) {
        try {
            if (Amend.accessKey == null || Amend.accessKey.equals("")) {
                if (amendListener != null) {
                    Exception e = new Exception("Access key required");
                    amendListener.onError(400, requestCode, e);
                }
                Log.e(Amend.TAG, "Access key required");
                return;
            }
            if (Amend.accessSecret == null || Amend.accessSecret.equals("")) {
                if (amendListener != null) {
                    Exception e = new Exception("Access Secret required");
                    amendListener.onError(400, requestCode, e);
                }
                Log.e(Amend.TAG, "Access Secret required");
                return;
            }

            if (Amend.amendName == null || Amend.amendName.equals("")) {
                if (amendListener != null) {
                    Exception e = new Exception("Amend name required");
                    amendListener.onError(400, requestCode, e);
                }
                Log.e(Amend.TAG, "Amend name required");
                return;
            }

            if (isNetworkOnline(context)) {
                JSONObject obj = new JSONObject();


                if (imageName != null && !imageName.isEmpty()) {
                    obj.put(IMAGE_NAME, imageName);
                } else {
                    Exception e = new Exception("Image Name can not be null or empty");
                    if (amendListener != null) {
                        amendListener.onError(400, requestCode, e);
                    }
                    Log.e(Amend.TAG, "Image Name can not be null or empty");
                    return;
                }

                if (newName != null && !newName.isEmpty()) {
                    obj.put(NEW_NAME, newName);
                } else {
                    Exception e = new Exception("New Name can not be null or empty");
                    if (amendListener != null) {
                        amendListener.onError(400, requestCode, e);
                    }
                    Log.e(Amend.TAG, "New Name can not be null or empty");
                    return;
                }

                String url = u.BASE_URL + Amend.amendName + "/" + u.RENAME;

                new ApiRequest(url, obj.toString(), new ApiRequest.OnApiRequestDoneListener() {
                    @Override
                    public void onApiRequestDone(int statusCode, String result, int requestCode) {
                        if (statusCode == 200) {
                            if (amendListener != null) {
                                amendListener.onSuccess(statusCode, requestCode, newName);
                            }
                        } else {
                            try {
                                JSONObject obj = new JSONObject(result);
                                if (obj.has(Amend.MESSAGE)) {
                                    String message = obj.getString(Amend.MESSAGE);
                                    Exception e = new Exception(message);
                                    if (amendListener != null) {
                                        amendListener.onError(statusCode, requestCode, e);
                                    }
                                } else {
                                    Exception e = new Exception("Something went wrong");
                                    if (amendListener != null) {
                                        amendListener.onError(statusCode, requestCode, e);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (amendListener != null) {
                                    amendListener.onError(500, requestCode, e);
                                }
                            }
                        }
                    }
                }, requestCode).execute();
            } else {
                if (amendListener != null) {
                    Exception e = new Exception("Internet not reachable");
                    amendListener.onError(500, requestCode, e);
                }
                Log.e(Amend.TAG, "Internet not reachable");
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (amendListener != null) {
                amendListener.onError(500, requestCode, e);
            }
        }
    }

    public void destroy(String imageName, int requestCode, final AmendDestroyListener amendListener) {
        try {
            if (Amend.accessKey == null || Amend.accessKey.equals("")) {
                if (amendListener != null) {
                    Exception e = new Exception("Access key required");
                    amendListener.onError(400, requestCode, e);
                }
                Log.e(Amend.TAG, "Access key required");
                return;
            }
            if (Amend.accessSecret == null || Amend.accessSecret.equals("")) {
                if (amendListener != null) {
                    Exception e = new Exception("Access Secret required");
                    amendListener.onError(400, requestCode, e);
                }
                Log.e(Amend.TAG, "Access Secret required");
                return;
            }

            if (Amend.amendName == null || Amend.amendName.equals("")) {
                if (amendListener != null) {
                    Exception e = new Exception("Amend name required");
                    amendListener.onError(400, requestCode, e);
                }
                Log.e(Amend.TAG, "Amend name required");
                return;
            }

            if (isNetworkOnline(context)) {
                JSONObject obj = new JSONObject();


                if (imageName != null && !imageName.equals("")) {
                    obj.put(IMAGE_NAME, imageName);
                } else {
                    Exception e = new Exception("Image Name can not be null or empty");
                    if (amendListener != null) {
                        amendListener.onError(400, requestCode, e);
                    }
                    Log.d(Amend.TAG, "Image Name can not be null or empty");
                    return;
                }


                String url = u.BASE_URL + Amend.amendName + "/" + u.DESTROY;

                new ApiRequest(url, obj.toString(), new ApiRequest.OnApiRequestDoneListener() {
                    @Override
                    public void onApiRequestDone(int statusCode, String result, int requestCode) {
                        if (statusCode == 200) {
                            if (amendListener != null) {
                                amendListener.onSuccess(statusCode, requestCode, "Image destroyed successfully");
                            }
                        } else {
                            try {
                                JSONObject obj = new JSONObject(result);
                                if (obj.has(Amend.MESSAGE)) {
                                    String message = obj.getString(Amend.MESSAGE);
                                    Exception e = new Exception(message);
                                    if (amendListener != null) {
                                        amendListener.onError(statusCode, requestCode, e);
                                    }
                                } else {
                                    Exception e = new Exception("Something went wrong");
                                    if (amendListener != null) {
                                        amendListener.onError(statusCode, requestCode, e);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (amendListener != null) {
                                    amendListener.onError(500, requestCode, e);
                                }
                            }
                        }
                    }
                }, requestCode).execute();
            } else {
                if (amendListener != null) {
                    Exception e = new Exception("Internet not reachable");
                    amendListener.onError(500, requestCode, e);
                }
                Log.e(Amend.TAG, "Internet not reachable");
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (amendListener != null) {
                amendListener.onError(500, requestCode, e);
            }
        }
    }

    public void upload(int requestCode, File file, AmendUploadListener amendListener) {
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            String imageBase64 = Base64.encodeToString(b, Base64.DEFAULT);
            uploadImage(requestCode, imageBase64, null, amendListener);
        } else {
            Exception e = new Exception("File doest not exist");
            amendListener.onError(404, requestCode, e);
        }

    }

    public void upload(int requestCode, File file, String imageName, AmendUploadListener amendListener) {
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            String imageBase64 = Base64.encodeToString(b, Base64.DEFAULT);
            uploadImage(requestCode, imageBase64, imageName, amendListener);
        } else {
            Exception e = new Exception("File doest not exist");
            amendListener.onError(404, requestCode, e);
        }

    }

    public void upload(int requestCode, String imageBase64, AmendUploadListener amendListener) {

        uploadImage(requestCode, imageBase64, null, amendListener);
    }

    public void upload(int requestCode, String imageBase64, String imageName, AmendUploadListener amendListener) {
        uploadImage(requestCode, imageBase64, imageName, amendListener);
    }

    private void uploadImage(int requestCode, String imageBase64, String imageName, AmendUploadListener amendListener) {
        try {
            if (Amend.accessKey == null || Amend.accessKey.equals("")) {
                if (amendListener != null) {
                    Exception e = new Exception("Access key required");
                    amendListener.onError(400, requestCode, e);
                }
                Log.e(Amend.TAG, "Access key required");
                return;
            }
            if (Amend.accessSecret == null || Amend.accessSecret.equals("")) {
                if (amendListener != null) {
                    Exception e = new Exception("Access Secret required");
                    amendListener.onError(400, requestCode, e);
                }
                Log.e(Amend.TAG, "Access Secret required");
                return;
            }

            if (Amend.amendName == null || Amend.amendName.equals("")) {
                if (amendListener != null) {
                    Exception e = new Exception("Amend name required");
                    amendListener.onError(400, requestCode, e);
                }
                Log.e(Amend.TAG, "Amend name required");
                return;
            }

            if (isNetworkOnline(context)) {
                JSONObject obj = new JSONObject();
                obj.put(u.IMAGE_BASE_64, imageBase64);
                if (imageName != null && !imageName.equals("")) {
                    obj.put(u.IMAGE_NAME, imageName);
                }

                Properties p = new Properties();
                p.put(UploadImageBase64toServer.ID, obj.toString());

                String url = u.BASE_URL + Amend.amendName + "/" + u.UPLOAD;

                new UploadImageBase64toServer(url, p, amendListener, requestCode).execute();
            } else {
                if (amendListener != null) {
                    Exception e = new Exception("Internet not reachable");
                    amendListener.onError(500, requestCode, e);
                }
                Log.e(Amend.TAG, "Internet not reachable");
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (amendListener != null) {
                amendListener.onError(500, requestCode, e);
            }
        }
    }


    public RequestManager load(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public RequestManager fetch(String imageUrl) {
        isFetchCalled = true;
        this.imageUrl = imageUrl;
        return this;
    }

    public RequestManager fetch(String imageUrl, AmendOptions options) {
        isFetchCalled = true;
        this.imageUrl = imageUrl;
        this.options = options.getOptions();

        return this;
    }

    public RequestManager load(String imageName, AmendOptions options) {
        this.imageName = imageName;
        this.options = options.getOptions();
        return this;
    }

    public RequestManager placeHolder(int id) {
        placeHolderId = id;
        return this;
    }

    public RequestManager listener(RequestListener listener) {
        this.listener = listener;
        return this;
    }

    public void into(final ImageView iv) {
        String url = "";

        if (isFetchCalled) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                url = u.BASE_URL + Amend.amendName + "/fetch";
                if (options != null) {
                    url += "/" + options;
                }
                url += "/" + imageUrl;
            } else {
                if (listener != null) {
                    Exception e = new Exception("Image Url required");
                    listener.onError(e);
                }
                Log.e(Amend.TAG, "Image Url required");
                return;
            }
        } else {
            if (imageName != null && !imageName.isEmpty()) {
                url = u.BASE_URL + Amend.amendName + "/image";
                if (options != null) {
                    url += "/" + options;
                }
                url += "/" + imageName;

            } else {
                if (listener != null) {
                    Exception e = new Exception("Image name required");
                    listener.onError(e);
                }
                Log.e(Amend.TAG, "Image name required");
                return;
            }
        }


        if (Amend.amendName == null || Amend.amendName.equals("")) {
            if (listener != null) {
                Exception e = new Exception("Amend name required");
                listener.onError(e);
            }
            Log.e(Amend.TAG, "Amend name required");
            return;
        }
        if (listener != null) {
            listener.onStarted();
        }


        // Get the ImageLoader through your singleton class.
        ImageLoader mImageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();

        mImageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    iv.setImageBitmap(response.getBitmap());
                    if (listener != null) {
                        listener.onSuccess(response.getBitmap());
                    }
                } else if (placeHolderId != 0) {
                    iv.setImageResource(placeHolderId);
                    if (listener != null) {
                        Exception e = new Exception("Image not found");
                        listener.onError(e);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (placeHolderId != 0) {
                    iv.setImageResource(placeHolderId);
                    if (listener != null) {
                        listener.onError(error);
                        error.printStackTrace();
                    }
                }
            }
        });

    }
}
