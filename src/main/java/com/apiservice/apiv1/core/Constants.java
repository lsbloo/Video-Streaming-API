package com.apiservice.apiv1.core;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final String TYPE_CSV="csv";



    public static final String VIDEO="/videos";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String CONTENT_LENGTH = "Content-Length";


    public static final String VIDEO_CONTENT="videos/";

    public static final String CONTENT_RANGE = "Content-Range";

    public static final String ACCEPT_RANGES = "Accept-Ranges";

    public static final String BYTES = "bytes";

    public static final int BYTE_RANGE = 1024;
}
