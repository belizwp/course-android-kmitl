package kmitl.lab03.nakarin58070064.simplemydot.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import kmitl.lab03.nakarin58070064.simplemydot.model.Dot;

public class DotView extends View {

    /**
     * Interface
     */
    public interface OnDotLongClickListener {
        void onDotLongClick(Dot dot);
    }

    public interface OnSingleTapListener {
        boolean onSingleTap(MotionEvent e);
    }

    private OnDotLongClickListener onDotLongClickListener;
    private OnSingleTapListener onSingleTapListener;

    public void setOnDotLongClickListener(OnDotLongClickListener l) {
        this.onDotLongClickListener = l;
    }

    public void setOnSingleTapListener(OnSingleTapListener l) {
        this.onSingleTapListener = l;
    }

    /**
     * View
     */
    private Paint paint;
    private GestureDetectorCompat mDetector;
    private List<Dot> dots;

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
        mDetector = new GestureDetectorCompat(context, listener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (dots == null)
            return;

        for (Dot dot : dots) {
            paint.setColor(dot.getColor());
            canvas.drawCircle(dot.getCenterX(), dot.getCenterY(), dot.getRadius(), paint);
        }
    }

    public void setDot(List<Dot> dots) {
        this.dots = dots;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    private boolean isTouchDot(Dot dot, int x, int y) {
        double distance = Math.pow(Math.pow((dot.getCenterX() - x), 2) +
                Math.pow((dot.getCenterY() - y), 2), 0.5);

        if (distance <= dot.getRadius()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Listener
     */
    GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);

            if (dots == null)
                return;

            int x = (int) e.getX();
            int y = (int) e.getY();

            // reverse loop to handle top stack first
            for (int i = dots.size() - 1; i >= 0; i--) {

                Dot dot = dots.get(i);

                if (isTouchDot(dot, x, y)) {
                    onDotLongClickListener.onDotLongClick(dot);
                    return;
                }
            }
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return onSingleTapListener.onSingleTap(e);
        }
    };

}
