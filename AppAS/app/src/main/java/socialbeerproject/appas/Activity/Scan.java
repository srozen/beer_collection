package socialbeerproject.appas.Activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.OutputStream;

import socialbeerproject.appas.R;

/**
 * Created by Pierret on 17-11-15.
 */
public class Scan extends Activity implements View.OnClickListener {
    //keep track of camera capture intent
    final int CAMERA_CAPTURE = 1;
    //captured picture uri
    protected Uri picUri;
    //keep track of cropping intent
    final int PIC_CROP = 2;

    protected Button captureBtn;
    protected Button scanBtn;
    protected Button retour;

    protected Bitmap photoScan;

    protected OutputStream scanUpload;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        captureBtn = (Button)findViewById(R.id.capture_btn);
        scanBtn = (Button)findViewById(R.id.scan_btn);
        retour = (Button)findViewById(R.id.button_retour_scan);

        //handle buttons clicks
        captureBtn.setOnClickListener(this);
        retour.setOnClickListener(this);
        scanBtn.setOnClickListener(this);


        scanBtn.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.capture_btn) {
            try {
                //use standard intent to capture an image
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //we will handle the returned data in onActivityResult
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
                scanBtn.setVisibility(View.VISIBLE);
            }
            catch(ActivityNotFoundException anfe){
                //display an error message
                String errorMessage = "Whoops - votre appareil ne supporte pas l'application";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }

        }

        if (v.getId() == R.id.button_retour_scan) {
            finish();
        }

        if (v.getId() == R.id.scan_btn) {
            // TODO : ENVOIE DE L IMAGE AU SERVEUR A PARTIR DE scanUpload
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //user is returning from capturing an image using the camera
            if(requestCode == CAMERA_CAPTURE){
                //get the Uri for the captured image
                picUri = data.getData();
                //carry out the crop operation
                performCrop();

                scanBtn.setVisibility(View.VISIBLE);
            }//user is returning from cropping the image
            else if(requestCode == PIC_CROP){
                //get the returned data
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                photoScan = extras.getParcelable("data");
                //retrieve a reference to the ImageView
                ImageView picView = (ImageView)findViewById(R.id.picture);
                //display the returned cropped image
                picView.setImageBitmap(photoScan);

                // TODO : REGLER LA COMPRESSEION ICI 75
                /*photoScan.compress(Bitmap.CompressFormat.JPEG, 75, scanUpload);

               File file = new File(getRealPathFromURI(this,picUri));
                boolean deleted = file.delete();

                if (!deleted) {
                    //display an error message
                    String errorMessage = "Whoops - la photo n'a pas été supprimé de votre appareil.";
                    Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }*/

            }
        }
    }



    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }




    private void performCrop(){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);

        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}