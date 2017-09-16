package kmitl.lab03.nakarin58070064.simplemydot.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import kmitl.lab03.nakarin58070064.simplemydot.R;
import kmitl.lab03.nakarin58070064.simplemydot.model.Dot;

public class EditDotFragment extends Fragment implements View.OnClickListener,
        ColorPickerDialogFragment.ColorPickerDialogListener {

    private static final String INDEX = "index";
    private static final String DOT = "dot";

    private Dot dot;
    private int index;

    private View colorBar;
    private EditText etCenterX;
    private EditText etCenterY;
    private EditText etRadius;

    private Button btnSave;
    private Button btnCancel;

    public static EditDotFragment newInstance(Dot dot, int i) {
        EditDotFragment fragment = new EditDotFragment();
        fragment.index = i;

        Bundle args = new Bundle();
        args.putParcelable(DOT, dot);
        args.putInt(INDEX, i);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dot = getArguments().getParcelable(DOT);
            index = getArguments().getInt(INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_dot, container, false);
        setupView(rootView);
        return rootView;
    }

    private void setupView(View rootView) {
        colorBar = rootView.findViewById(R.id.colorBar);
        etCenterX = rootView.findViewById(R.id.etCenterX);
        etCenterY = rootView.findViewById(R.id.etCenterY);
        etRadius = rootView.findViewById(R.id.etRadius);
        btnSave = rootView.findViewById(R.id.btnSave);
        btnCancel = rootView.findViewById(R.id.btnCancel);

        colorBar.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        colorBar.setBackgroundColor(dot.getColor());
        etCenterX.setText(String.valueOf(dot.getCenterX()));
        etCenterY.setText(String.valueOf(dot.getCenterY()));
        etRadius.setText(String.valueOf(dot.getRadius()));
    }

    private void onSave() {
        int color = ((ColorDrawable) colorBar.getBackground()).getColor();
        int centerX = Integer.parseInt(etCenterX.getText().toString());
        int centerY = Integer.parseInt(etCenterY.getText().toString());
        int radius = Integer.parseInt(etRadius.getText().toString());

        Dot newDot = new Dot(centerX, centerY, radius, color);

        EditDotFragmentListener listener = getEditDotFragmentListener();
        if (listener != null) {
            listener.onSave(newDot, index);
        }
    }

    private void onCancel() {
        EditDotFragmentListener listener = getEditDotFragmentListener();
        if (listener != null) {
            listener.onCancel();
        }
    }

    private void callColorPickerDialog() {
        ColorPickerDialogFragment.newInstance(dot.getColor())
                .show(getChildFragmentManager(), "ColorPickerDialog");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.colorBar:
                callColorPickerDialog();
                break;
            case R.id.btnSave:
                onSave();
                break;
            case R.id.btnCancel:
                onCancel();
                break;
        }
    }

    @Override
    public void onColorPicked(int color) {
        colorBar.setBackgroundColor(color);
    }

    public interface EditDotFragmentListener {
        void onSave(Dot dot, int i);

        void onCancel();
    }

    private EditDotFragmentListener getEditDotFragmentListener() {
        Fragment fragment = getParentFragment();
        try {
            if (fragment != null) {
                return (EditDotFragmentListener) fragment;
            } else {
                return (EditDotFragmentListener) getActivity();
            }
        } catch (ClassCastException ignored) {
        }
        return null;
    }
}
