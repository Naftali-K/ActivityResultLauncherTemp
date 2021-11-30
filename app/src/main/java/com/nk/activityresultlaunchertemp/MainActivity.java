package com.nk.activityresultlaunchertemp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST_PROJECT";

    private Button first_layout_btn, second_layout_btn, picture_btn, video_btn, call_btn, mail_btn;
    private TextView printout_tv;
    private ImageView image_from_gallery;
    private VideoView video_from_gallery;

    private ActivityResultLauncher activityResultLauncher, pictureFromGalleryContent, videoFromGalleryContent;
    private Uri numberPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setReferences();

        setLauncher();

        first_layout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FirstActivity.class);
                activityResultLauncher.launch(intent);
            }
        });

        second_layout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityResultLauncher.launch(new Intent(getBaseContext(), SecondActivity.class));
            }
        });

        picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictureFromGalleryContent.launch("image/*");
            }
        });

        video_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoFromGalleryContent.launch("video/*");
            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPhone = Uri.parse("tel: 0123456789");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, numberPhone);
                activityResultLauncher.launch(callIntent);
            }
        });

        mail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mail@mail.ru", "mailmore@mail.tu"});
//                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Title mail");
//                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Text of mail");
//                    emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://com.android.providers.media.documents/document/video%3A40"));
//                activityResultLauncher.launch(emailIntent);

                Intent intent = new Intent(Intent.ACTION_SEND);
                Intent chooser = Intent.createChooser(intent, "Share this with");

                startActivity(chooser);
            }
        });
    }

    private void setReferences(){
        printout_tv = findViewById(R.id.printout_tv);
        first_layout_btn = findViewById(R.id.first_layout_btn);
        second_layout_btn = findViewById(R.id.second_layout_btn);
        picture_btn = findViewById(R.id.picture_btn);
        video_btn = findViewById(R.id.video_btn);
        call_btn = findViewById(R.id.call_btn);
        mail_btn = findViewById(R.id.mail_btn);
        image_from_gallery = findViewById(R.id.image_from_gallery);
        video_from_gallery = findViewById(R.id.video_from_gallery);
    }

    private void setLauncher(){

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == FirstActivity.REQUEST_CODE && result.getData() != null){
//                            Toast.makeText(getBaseContext(), "Result from FIRST layout", Toast.LENGTH_SHORT).show();
                            Intent intent = result.getData();
                            String name = intent.getStringExtra("name");
                            printout_tv.setText(name);
                            return;
                        }
                        if (result.getResultCode() == SecondActivity.REQUEST_CODE && result.getData() != null){
//                            Toast.makeText(getBaseContext(), "Result from SECOND layout", Toast.LENGTH_SHORT).show();
                            printout_tv.setText(result.getData().getStringExtra("age"));
                            return;
                        }
                        if (result.getResultCode() == RESULT_CANCELED){
                            Toast.makeText(getBaseContext(), "CANCELED", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        pictureFromGalleryContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
//                Toast.makeText(getBaseContext(), "Picture Uri: " + result, Toast.LENGTH_SHORT).show();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                    image_from_gallery.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        videoFromGalleryContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Log.d(TAG, "onActivityResult: " + result);
                video_from_gallery.setVideoURI(result);
                video_from_gallery.start();
            }
        });

    }
}