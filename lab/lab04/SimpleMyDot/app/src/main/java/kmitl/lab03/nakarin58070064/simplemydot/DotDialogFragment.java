package kmitl.lab03.nakarin58070064.simplemydot;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

public class DotDialogFragment extends DialogFragment {

    public interface OnDialogListener {
        void onEditPress(int dotIndex);

        void onDeletePress(int dotIndex);
    }

    public static DotDialogFragment newInstance(int dotIndex) {
        DotDialogFragment frag = new DotDialogFragment();
        Bundle args = new Bundle();
        args.putInt("dotIndex", dotIndex);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int dotIndex = getArguments().getInt("dotIndex");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.edit_dot)
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        OnDialogListener listener = getOnDialogListener();
                        if (listener != null) {
                            listener.onEditPress(dotIndex);
                        }
                    }
                })
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        OnDialogListener listener = getOnDialogListener();
                        if (listener != null) {
                            listener.onDeletePress(dotIndex);
                        }
                    }
                })
                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    private OnDialogListener getOnDialogListener() {
        Fragment fragment = getParentFragment();
        try {
            if (fragment != null) {
                return (OnDialogListener) fragment;
            } else {
                return (OnDialogListener) getActivity();
            }
        } catch (ClassCastException ignored) {
        }
        return null;
    }
}
