package com.test.login.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;

public class ImageUtil {
    public static int MAX_DIMENSION = 300;

    public static Bitmap scaleImageDown(Context context, Uri uri){
        //Uri(이미지의 주소)의 이미지를 MAX_DIMENSION 미만의 크기로 줄여서 비트맵으로 반환
        Bitmap bitmap = null;
        try {
            bitmap = scaleBitmapDown(
                            MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static Bitmap scaleBitmapDown(Bitmap bitmap) {
        //파라미터로 들어온 비트맵을 MAX_DIMENSION 미만의 크기로 줄여서 비트맵을 반환
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = MAX_DIMENSION;
        int resizedHeight = MAX_DIMENSION;

        if (originalHeight > originalWidth) {
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        }

        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

}
