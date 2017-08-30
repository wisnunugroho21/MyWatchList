package com.example.android.moviedb3.LocalStorage;

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

public interface IStorage<Data>
{
    void SaveData(Data data, String fileName);
    Data LoadData(String fileName);
}
