package kmitl.lab03.nakarin58070064.simplemydot.model;

public class Dot {

    public interface OnDotChangeListener {
        void onDotChange(Dot dot);
    }

    private OnDotChangeListener listener;

    public void setOnDotChangeListener(OnDotChangeListener listener) {
        this.listener = listener;
    }

    private int centerX;
    private int centerY;
    private int radius;

    public Dot(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public Dot(OnDotChangeListener listener, int centerX, int centerY, int radius) {
        this.listener = listener;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;

        listener.onDotChange(this);
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
        this.listener.onDotChange(this);
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
        this.listener.onDotChange(this);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        this.listener.onDotChange(this);
    }
}