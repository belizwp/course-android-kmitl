package kmitl.lab03.nakarin58070064.simplemydot.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import kmitl.lab03.nakarin58070064.simplemydot.model.Dot;
import kmitl.lab03.nakarin58070064.simplemydot.model.Dots;

public class DotView extends View {

    public interface DotViewTouchListener {
        void onDotViewLongPress(int x, int y);

        boolean onDotViewTap(int x, int y);
    }

    private DotViewTouchListener listener;

    public void setListener(DotViewTouchListener listener) {
        this.listener = listener;
    }

    private Paint paint;
    private GestureDetectorCompat mDetector;

    private Dots dots;

    public DotView(Context context) {
        super(context);
        initInstances(context, null, 0);
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInstances(context, attrs, 0);
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInstances(context, attrs, defStyleAttr);
    }

    private void initInstances(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        paint = new Paint();
        mDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return listener.onDotViewTap((int) e.getX(), (int) e.getY());
            }

            @Override
            public void onLongPress(MotionEvent e) {
                listener.onDotViewLongPress((int) e.getX(), (int) e.getY());
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (this.dots != null) {
            for (Dot dot : dots.getAllDot()) {
                paint.setColor(dot.getColor());
                canvas.drawCircle(dot.getCenterX(), dot.getCenterY(), dot.getRadius(), paint);
            }
        }
    }

    public void setDots(Dots dots) {
        this.dots = dots;
    }
}
