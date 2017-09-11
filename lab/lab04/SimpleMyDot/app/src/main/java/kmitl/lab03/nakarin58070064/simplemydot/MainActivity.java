package kmitl.lab03.nakarin58070064.simplemydot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import kmitl.lab03.nakarin58070064.simplemydot.model.Dot;
import kmitl.lab03.nakarin58070064.simplemydot.model.Dots;
import kmitl.lab03.nakarin58070064.simplemydot.view.DotView;

public class MainActivity extends AppCompatActivity implements Dots.OnDotsChangeListener,
        DotView.DotViewTouchListener, DotDialogFragment.OnDialogListener {

    private static final String KEY_ALL_DOT = "allDot";

    public static final int EDIT_DOT_REQUEST = 1;

    private Dots dots;
    private DotView dotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

        if (savedInstanceState != null) {
            ArrayList<Dot> dotArrayList = savedInstanceState.getParcelableArrayList(KEY_ALL_DOT);
            dots.setAllDot(dotArrayList);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(KEY_ALL_DOT,
                (ArrayList<? extends Parcelable>) dots.getAllDot());
    }

    private void setup() {
        dots = new Dots();
        dots.setListener(this);

        dotView = (DotView) findViewById(R.id.dotContainer);
        dotView.setListener(this);
    }

    public void onRandomDot(View view) {
        Random random = new Random();

        int centerX = random.nextInt(dotView.getWidth());
        int centerY = random.nextInt(dotView.getHeight());

        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        dots.addDot(new Dot(centerX, centerY, 75, Color.rgb(r, g, b)));
    }

    public void onClearDot(View view) {
        dots.clear();
    }

    public void onShare(View view) {
        Bitmap bitmap = captureView(dotView);

        String fileName = "dotViewImg.png";

        saveBitmapToCache(bitmap, 100, fileName);
        File file = new File(getCacheDir(), fileName);
        sendFileIntent(file, "image/jpeg", "Share to");
    }

    private Bitmap captureView(View view) {
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.destroyDrawingCache();
        return bitmap;
    }

    private void saveBitmapToCache(Bitmap bitmap, int quality, String fileName) {
        try {
            FileOutputStream stream = new FileOutputStream(getCacheDir() +
                    File.separator + fileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFileIntent(File file, String type, String message) {
        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(),
                "kmitl.lab03.nakarin58070064.simplemydot.fileprovider", file);

        if (contentUri == null)
            return;

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        shareIntent.setType(type);
        startActivity(Intent.createChooser(shareIntent, message));
    }

    @Override
    public void onDotsChanged(Dots dots) {
        dotView.setDots(dots);
        dotView.invalidate();
    }

    @Override
    public void onDotViewLongPress(int x, int y) {
        final int dotIndex = dots.findDot(x, y);

        if (dotIndex == -1) {
            return;
        }

        DotDialogFragment.newInstance(dotIndex).show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public boolean onDotViewTap(int x, int y) {
        dots.addDot(new Dot(x, y, 120, Color.RED));
        return true;
    }

    @Override
    public void onEditPress(int dotIndex) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("Dot", dots.getAllDot().get(dotIndex));
        intent.putExtra("DotIndex", dotIndex);
        startActivityForResult(intent, EDIT_DOT_REQUEST);
    }

    @Override
    public void onDeletePress(int dotIndex) {
        dots.remove(dotIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_DOT_REQUEST) {
            if (resultCode == RESULT_OK) {
                int dotIndex = data.getIntExtra("DotIndex", -1);

                if (dotIndex == -1)
                    return;

                Dot dot = data.getParcelableExtra("Dot");
                dots.set(dotIndex, dot);
            }
        }
    }
}
