package cosw.eci.edu.camera.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Juan Pablo Arévalo on 27/03/2018.
 */

public class Post implements Serializable {
    private String message;
    private String photo;

    public String getMessage() {
        return message;
    }

    public String getPhoto() {
        return photo;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
