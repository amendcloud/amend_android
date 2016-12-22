package com.tactlab.cloudamend;

import java.io.Serializable;

/**
 * Created by deepak on 22-12-2016.
 */

public class AmendResponse implements Serializable {

    private String ImageName;
    private int Size;
    private String Resolution;
    private String MimeType;
    private int Width;
    private int Height;


    private String CreatedDate;
    private String CDNUrl;
    private String SecureCDNUrl;

    public AmendResponse(String imageName, int width, int height, int size, String resolution, String mimeType, String createdDate, String CDNUrl, String secureCDNUrl) {
        this.CDNUrl = CDNUrl;
        CreatedDate = createdDate;
        Height = height;
        ImageName = imageName;
        MimeType = mimeType;
        Resolution = resolution;
        SecureCDNUrl = secureCDNUrl;
        Size = size;
        Width = width;
    }

    public int getSize() {
        return Size;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }


    public String getImageName() {
        return ImageName;
    }

    public String getMimeType() {
        return MimeType;
    }

    public String getSecureCDNUrl() {
        return SecureCDNUrl;
    }

    public String getCDNUrl() {
        return CDNUrl;
    }

    public int getHeight() {
        return Height;
    }

    public int getWidth() {
        return Width;
    }
}
