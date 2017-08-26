package kmitl.lab03.nakarin58070064.simplemydot;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kmitl.lab03.nakarin58070064.simplemydot.model.Dot;
import kmitl.lab03.nakarin58070064.simplemydot.view.DotView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        Dot.OnDotChangeListener, DotView.OnDotLongClickListener, DotView.OnSingleTapListener {

    private Random random;
    private List<Dot> dots;

    private DotView dotView;
    private Button btnRandDot;
    private Button btnClearDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
    }

    private void initInstances() {
        random = new Random();
        dots = new ArrayList<>();

        dotView = (DotView) findViewById(R.id.dotContainer);
        btnRandDot = (Button) findViewById(R.id.btnRandDot);
        btnClearDot = (Button) findViewById(R.id.btnClearDot);

        dotView.setOnDotLongClickListener(this);
        dotView.setOnSingleTapListener(this);
        btnRandDot.setOnClickListener(this);
        btnClearDot.setOnClickListener(this);
    }

    private void randDot() {
        int centerX = random.nextInt(dotView.getWidth());
        int centerY = random.nextInt(dotView.getHeight());

        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        dots.add(new Dot(this, centerX, centerY, 75, Color.rgb(r, g, b)));
    }

    private void clearDot() {
        dots.clear();
        dotView.invalidate();
    }

    private void removeDot(Dot dot) {
        dots.remove(dot);
        dotView.invalidate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRandDot:
                randDot();
                break;
            case R.id.btnClearDot:
                clearDot();
                break;
        }
    }

    @Override
    public void onDotChange(Dot dot) {
        dotView.setDot(dots);
        dotView.invalidate();
    }

    @Override
    public void onDotLongClick(final Dot dot) {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Edit Dot")
                .initialColor(dot.getColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(5)
                .lightnessSliderOnly()
                .setPositiveButton("Select", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedColor,
                                        Integer[] allColors) {
                        dot.setColor(selectedColor);
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeDot(dot);
                    }
                })
                .build()
                .show();
    }

    @Override
    public boolean onSingleTap(MotionEvent e) {
        dots.add(new Dot(this, (int) e.getX(), (int) e.getY(), 120, Color.RED));
        return true;
    }
}
