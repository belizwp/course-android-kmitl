package kmitl.lab03.nakarin58070064.simplemydot.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import kmitl.lab03.nakarin58070064.simplemydot.R;

public class ColorPickerDialogFragment extends DialogFragment {

    private static final String COLOR = "color";

    private int color;

    public static ColorPickerDialogFragment newInstance(int color) {
        ColorPickerDialogFragment frag = new ColorPickerDialogFragment();
        frag.color = color;

        Bundle args = new Bundle();
        args.putInt(COLOR, color);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            color = getArguments().getInt(COLOR);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return ColorPickerDialogBuilder
                .with(getContext())
                .setTitle("Choose color")
                .initialColor(color)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(5)
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedColor, Integer[] allColors) {
                        ColorPickerDialogListener listener = getColorPickerDialogListener();
                        if (listener != null) {
                            listener.onColorPicked(selectedColor);
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .build();
    }

    public interface ColorPickerDialogListener {
        void onColorPicked(int color);
    }

    private ColorPickerDialogListener getColorPickerDialogListener() {
        Fragment fragment = getParentFragment();
        try {
            if (fragment != null) {
                return (ColorPickerDialogListener) fragment;
            } else {
                return (ColorPickerDialogListener) getActivity();
            }
        } catch (ClassCastException ignored) {
        }
        return null;
    }
}
