package kmitl.lab03.nakarin58070064.simplemydot.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import kmitl.lab03.nakarin58070064.simplemydot.BuildConfig;
import kmitl.lab03.nakarin58070064.simplemydot.R;
import kmitl.lab03.nakarin58070064.simplemydot.model.Dot;
import kmitl.lab03.nakarin58070064.simplemydot.model.Dots;
import kmitl.lab03.nakarin58070064.simplemydot.util.FileUtils;
import kmitl.lab03.nakarin58070064.simplemydot.util.ScreenUtils;
import kmitl.lab03.nakarin58070064.simplemydot.view.DotView;

public class DotFragment extends Fragment implements View.OnClickListener,
        DotView.DotViewTouchListener, Dots.OnDotsChangeListener {

    public static final String TAG = "DotFragment";

    private static final String KEY_ALL_DOT = "allDot";

    private Dots dots;

    private DotView dotView;
    private Button btnRandomDot;
    private Button btnClearDot;
    private Button btnShare;

    public static DotFragment newInstance() {
        DotFragment fragment = new DotFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initInstances(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(KEY_ALL_DOT,
                (ArrayList<? extends Parcelable>) dots.getAllDot());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dot, container, false);
        setupView(rootView, savedInstanceState);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // restore instance state when all thing created
        if (savedInstanceState != null) {
            ArrayList<Dot> dotArrayList = savedInstanceState.getParcelableArrayList(KEY_ALL_DOT);
            dots.setAllDot(dotArrayList);
        }
    }

    private void initInstances(Bundle savedInstanceState) {
        dots = new Dots();
        dots.setListener(this);
    }

    private void setupView(View rootView, Bundle savedInstanceState) {
        dotView = rootView.findViewById(R.id.dotContainer);
        btnRandomDot = rootView.findViewById(R.id.btnRandDot);
        btnClearDot = rootView.findViewById(R.id.btnClearDot);
        btnShare = rootView.findViewById(R.id.btnShare);

        dotView.setListener(this);
        btnRandomDot.setOnClickListener(this);
        btnClearDot.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    public void randomDot() {
        Random random = new Random();

        int centerX = random.nextInt(dotView.getWidth());
        int centerY = random.nextInt(dotView.getHeight());

        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        dots.addDot(new Dot(centerX, centerY, 75, Color.rgb(r, g, b)));
    }

    public void clearDot() {
        dots.clear();
    }

    public void onShare() {
        Bitmap bitmap = ScreenUtils.captureView(dotView);
        File file = FileUtils.saveBitmap(getContext().getCacheDir(),
                "dotViewImg.png",
                bitmap, Bitmap.CompressFormat.PNG, 100);

        Uri contentUri = FileProvider.getUriForFile(getContext(),
                BuildConfig.APPLICATION_ID + ".fileprovider", file);

        sendFileIntent(contentUri, "image/*", "Share to");
    }

    private void sendFileIntent(Uri contentUri, String type, String message) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        shareIntent.setType(type);
        startActivity(Intent.createChooser(shareIntent, message));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRandDot:
                randomDot();
                break;
            case R.id.btnClearDot:
                clearDot();
                break;
            case R.id.btnShare:
                onShare();
                break;
        }
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

        DotFragmentListener listener = getDotFragmentListener();
        if (listener != null) {
            Dot dot = dots.getAllDot().get(dotIndex);
            listener.DotLongPressSelected(dot, dotIndex);
        }
    }

    @Override
    public boolean onDotViewTap(int x, int y) {
        dots.addDot(new Dot(x, y, 120, Color.RED));
        return true;
    }

    public void removeDot(Dot dot, int i) {
        dots.remove(i);
    }

    public void setDot(Dot dot, int i) {
        dots.set(i, dot);
    }

    public interface DotFragmentListener {
        void DotLongPressSelected(Dot dot, int i);
    }

    private DotFragmentListener getDotFragmentListener() {
        Fragment fragment = getParentFragment();
        try {
            if (fragment != null) {
                return (DotFragmentListener) fragment;
            } else {
                return (DotFragmentListener) getActivity();
            }
        } catch (ClassCastException ignored) {
        }
        return null;
    }
}
