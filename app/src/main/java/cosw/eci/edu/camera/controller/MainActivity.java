package cosw.eci.edu.camera.controller;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import cosw.eci.edu.camera.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;


import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;


import cosw.eci.edu.camera.model.Post;

public class MainActivity extends AppCompatActivity implements NewPostFragment.OnFragmentInteractionListener{

    /*public static final String EXTRA_MESSAGE = "cosw.eci.edu.camera.MESSAGE";
    private static final String TAKE_PHOTO = "Take Photo";
    private static final int TAKE_PHOTO_OPTION = 1;

    private static final String CHOOSE_GALLERY = "Choose from Gallery";
    private static final int CHOOSE_GALLERY_OPTION = 2;

    private static final String CANCEL = "Cancel";
    private boolean isImagenValid = false;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment(new NewPostFragment(), true);

    }

   /* public void addPhoto(View view) {
        final CharSequence[] options = {TAKE_PHOTO, CHOOSE_GALLERY, CANCEL};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(TAKE_PHOTO)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, TAKE_PHOTO_OPTION);
                } else if (options[item].equals(CHOOSE_GALLERY)) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_GALLERY_OPTION);
                } else if (options[item].equals(CANCEL)) {
                    dialog.dismiss();
                }
            }
        });
        builder.create();
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView image = (ImageView) findViewById(R.id.imageView);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO_OPTION:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(photo);
                    isImagenValid = true;
                    break;
                case CHOOSE_GALLERY_OPTION:
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        String path = getPathFromURI(selectedImageUri);
                        image.setImageURI(selectedImageUri);
                        isImagenValid = true;
                    }
                    break;
            }
        }
    }


    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void post(View view) {
        EditText editText = (EditText) findViewById(R.id.txt_message);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        if (editText.length() == 0) {
            editText.setError("Please enter either a message");
        }
        else if(editText.length() <= 50){
            editText.setError("The messaage should have a length longer than 50 characters.");
        }
        else if(!isImagenValid) {
            editText.setError("Please select an image");
        }
        else{
            Log.i("INFO", "ENTRO ACA");
            Intent intent = new Intent(this, PostActivity.class);
            Post post = new Post();
            post.setMessage(editText.getText().toString());

            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            String photo = encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 0);
            post.setPhoto(photo);

            intent.putExtra(EXTRA_MESSAGE, post);
            startActivity(intent);
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }*/



    public void showFragment(Fragment fragment, boolean addToBackStack){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        String tag = fragment.getClass().getSimpleName();
        if ( addToBackStack )
        {
            transaction.addToBackStack( tag );
        }
        transaction.replace( R.id.fragment_container, fragment, tag );
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
