package com.amical.ard.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryUtil {
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dqkeyxb16",
            "api_key", "491694223638775",
            "api_secret", "RsMoufKjDrWrY8-QVr--t7x50io"
    ));

    public static Cloudinary getCloudinary() {
        return cloudinary;
    }
}