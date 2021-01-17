package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.admin.Admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.myapplication.MainActivity.link;

public class FirstFragment extends Fragment {
    public static final int CROP_PHOTO = 2;
    public static final int REQUEST_CODE_SIGN_IN = 3;
    public static File outputImage;
    public static Path path;
    public static Bitmap bitmap;
    private ImageView showImage;

    //Drive googleDriveService;

    //圖片路徑
    private Uri imageUri;

    //圖片名稱
    private String filename;
    private TextView lbl_imgpath;

    private Credentials credentials;
    private String CONTRACT_ADDRESS;
    private String ACCOUNT_ADDRESS;
    private Admin web3j;
    DriveServiceHelper mDriveServiceHelper;
    private FileOutputStream b;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filename = "tmp";
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 定義呼叫相機並取回圖片的Intent意圖
                //Log.i("path",Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
                startActivityForResult(intent, CROP_PHOTO);
            }
        });
        view.findViewById(R.id.getURL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckedTextView t2 = getView().findViewById(R.id.url);

                t2.setText(link);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {

            if(requestCode == CROP_PHOTO) {
                bitmap = (Bitmap) data.getExtras().get("data");
                //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(""));
                //File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);


                try {
                    outputImage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()+"/Camera/"+filename+".jpg");

                    try {
                        if(outputImage.exists()) {
                            outputImage.delete();
                        }

                        outputImage.createNewFile();

                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                    b = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()+"/Camera/"+filename+".jpg");

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把資料寫入檔案,其中第一個引數表示圖片格式,
                    //第二個引數指壓縮率。100表示不壓縮

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        b.flush();
                        b.close();
                        //Log.i("tmp","DONE");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                ImageView ic = (ImageView) getView().findViewById(R.id.imageView);

                ic.setImageBitmap(bitmap);

            }
        }
    }
}