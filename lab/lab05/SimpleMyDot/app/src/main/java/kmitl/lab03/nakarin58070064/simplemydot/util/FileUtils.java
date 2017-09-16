package kmitl.lab03.nakarin58070064.simplemydot.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static File saveBitmap(File directory, String fileName, Bitmap bitmap,
                                  Bitmap.CompressFormat format, int quality) {
        try {
            FileOutputStream stream = new FileOutputStream(directory +
                    File.separator + fileName);
            bitmap.compress(format, quality, stream);
            stream.close();

            return new File(directory, fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
