package kmitl.lab03.nakarin58070064.simplemydot;

import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import kmitl.lab03.nakarin58070064.simplemydot.model.Dot;
import kmitl.lab03.nakarin58070064.simplemydot.view.DotView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Dot.OnDotChangeListener {

    Button btnRandDot;

    Random random;

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRandDot = (Button) findViewById(R.id.btnRandDot);
        btnRandDot.setOnClickListener(this);

        random = new Random();

        constraintLayout = (ConstraintLayout) findViewById(R.id.constLayout);
    }

    private void randomDot() {

        int centerX = random.nextInt(constraintLayout.getWidth());
        int centerY = random.nextInt(constraintLayout.getHeight());

        DotView dotView = new DotView(this);
        dotView.setDot(new Dot(this, centerX, centerY, 50));

        constraintLayout.addView(dotView);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnRandDot) {
            randomDot();
        }
    }

    @Override
    public void onDotChange(Dot dot) {
//        dotView.setDot(dot);
//        dotView.invalidate();
    }
}
