package cosw.eci.edu.camera.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import cosw.eci.edu.camera.R;
import cosw.eci.edu.camera.model.Post;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewPostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPostFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;


    public static final String EXTRA_MESSAGE = "cosw.eci.edu.camera.MESSAGE";
    private static final String TAKE_PHOTO = "Take Photo";
    private static final int TAKE_PHOTO_OPTION = 1;

    private static final String CHOOSE_GALLERY = "Choose from Gallery";
    private static final int CHOOSE_GALLERY_OPTION = 2;

    private static final String CANCEL = "Cancel";
    private boolean isImagenValid = false;
    Context context;

    public NewPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPostFragment newInstance(String param1, String param2) {
        NewPostFragment fragment = new NewPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_post, container, false);
        context = v.getContext();
        Button btn_addPhoto = (Button) v.findViewById(R.id.btn_addPhoto);
        btn_addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhoto();
            }
        });
        Button btn_save = (Button) v.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
        return v;


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void addPhoto() {
        final CharSequence[] options = {TAKE_PHOTO, CHOOSE_GALLERY, CANCEL};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView image = (ImageView) getView().findViewById(R.id.imageView);

        if (resultCode == MainActivity.RESULT_OK) {
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
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }


    public boolean validatePostForm(EditText editText){
        if (editText.length() == 0) {
            editText.setError("Please enter either a message");
            return false;
        }
        else if(editText.length() <= 50){
            editText.setError("The messaage should have a length longer than 50 characters.");
            return false;
        }
        else if(!isImagenValid) {
            editText.setError("Please select an image");
            return false;
        }
        else {
            return true;
        }
    }


    public void post() {
        EditText editText = (EditText) getView().findViewById(R.id.txt_message);
        ImageView image = (ImageView) getView().findViewById(R.id.imageView);
        if(validatePostForm(editText)){
            Intent intent = new Intent(getContext(), PostActivity.class);
            Post post = new Post();
            post.setMessage(editText.getText().toString());

            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            String photo = encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 0);
            post.setPhoto(photo);

            intent.putExtra(EXTRA_MESSAGE, post);
            startActivity(intent);
        }
    }
}
