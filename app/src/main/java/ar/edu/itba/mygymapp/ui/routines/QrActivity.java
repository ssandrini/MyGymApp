package ar.edu.itba.mygymapp.ui.routines;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.view.MenuInflater;
import ar.edu.itba.mygymapp.BuildConfig;
import ar.edu.itba.mygymapp.R;
import java.util.Date;
import ar.edu.itba.mygymapp.databinding.QrActivityBinding;
import android.view.MenuItem;
import android.view.View;
import java.io.FileOutputStream;
import android.widget.TextView;
import android.widget.Toast;
import net.glxn.qrgen.android.QRCode;
import java.io.File;
import java.io.IOException;
import android.text.format.DateFormat;

public class QrActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_activity);
        int id = getIntent().getIntExtra(RoutineActivity.EXTRA_ID,0);
        String title = getIntent().getStringExtra(RoutineActivity.EXTRA_TITLE);
        if(id == 0)
            System.out.println("esto ta mal");
        TextView titleTextView = findViewById(R.id.qrRoutineName);
        titleTextView.setText(title);
        Button backButton = findViewById(R.id.qr_back);
        Button shareButton = findViewById(R.id.qr_share);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyStoragePermission(QrActivity.this);
                takeScreenShot(getWindow().getDecorView());
            }
        });

        Bitmap myBitmap = QRCode.from("http://mygym.com/routine?id="+ id).bitmap();
        ImageView myImage = findViewById(R.id.qr_code);
        myImage.setImageBitmap(myBitmap);

    }

    private void takeScreenShot(View view) {

        //This is used to provide file name with Date a format
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        //It will make sure to store file to given below Directory and If the file Directory dosen't exist then it will create it.
        try {
            File mainDir = new File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }

            //Providing file name along with Bitmap to capture screenview
            String path = mainDir + "/" + "MyGym" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            //This logic is used to save file at given location with the given filename and compress the Image Quality.
            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            //Create New Method to take ScreenShot with the imageFile.
            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Share ScreenShot
    private void shareScreenShot(File imageFile) {
        //Using sub-class of Content provider
        Uri uri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + "." + getLocalClassName() + ".provider", imageFile);

        //Explicit intent
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        //It will show the application which are available to share Image; else Toast message will throw.
        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getText(R.string.no_app), Toast.LENGTH_SHORT).show();
        }
    }

    public static void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

}