package com.example.android.moviedb3.LocalStorage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nugroho on 23/08/17.
 */

public class ImageStorage implements IStorage<Bitmap>
{
    Context context;
    String directoryName;

    public ImageStorage(String directoryName, Context context)
    {
        this.context = context;
    }

    public void SaveData(Bitmap bitmap, String fileName)
    {
        File file = new File(context.getDir(directoryName, MODE_PRIVATE), fileName);

        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

            fileOutputStream.close();

        } catch (IOException e)
        {
            Log.e("Image Error" , e.getMessage());
            Toast.makeText(context, "Error when save image", Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap LoadData(String fileName)
    {
        File file = new File(context.getDir(directoryName, MODE_PRIVATE), fileName);

        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);

            fileInputStream.close();

            return bitmap;

        } catch (IOException e) {
            Log.e("Image Error", e.getMessage());
            Toast.makeText(context, "Error when load image", Toast.LENGTH_SHORT).show();

            return null;
        }
    }


}
