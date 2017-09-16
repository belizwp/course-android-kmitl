package kmitl.lab03.nakarin58070064.simplemydot.util;

import android.graphics.Bitmap;
import android.view.View;

public class ScreenUtils {

    public static Bitmap captureView(View view) {
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.destroyDrawingCache();
        return bitmap;
    }

}
