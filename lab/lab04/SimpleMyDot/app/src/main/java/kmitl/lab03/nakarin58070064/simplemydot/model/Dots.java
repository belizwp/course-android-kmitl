package kmitl.lab03.nakarin58070064.simplemydot.model;

import java.util.ArrayList;
import java.util.List;

public class Dots {

    public interface OnDotsChangeListener {
        void onDotsChanged(Dots dots);
    }

    private OnDotsChangeListener listener;

    public void setListener(OnDotsChangeListener listener) {
        this.listener = listener;
    }

    private List<Dot> allDot = new ArrayList<>();

    public void addDot(Dot dot) {
        this.allDot.add(dot);
        this.listener.onDotsChanged(this);
    }

    public void remove(int i) {
        this.allDot.remove(i);
        this.listener.onDotsChanged(this);
    }

    public void clear() {
        this.allDot.clear();
        this.listener.onDotsChanged(this);
    }

    public void set(int i, Dot dot) {
        this.allDot.set(i, dot);
        this.listener.onDotsChanged(this);
    }

    public int findDot(int x, int y) {
        for (int i = allDot.size() - 1; i >= 0; i--) {

            Dot dot = allDot.get(i);

            double distance = Math.pow(Math.pow((dot.getCenterX() - x), 2) +
                    Math.pow((dot.getCenterY() - y), 2), 0.5);

            if (distance <= dot.getRadius()) {
                return i;
            }
        }
        return -1;
    }

    public List<Dot> getAllDot() {
        return allDot;
    }

    public void setAllDot(List<Dot> allDot) {
        this.allDot = allDot;
        this.listener.onDotsChanged(this);
    }
}
