package kmitl.lab03.nakarin58070064.simplemydot.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import kmitl.lab03.nakarin58070064.simplemydot.R;
import kmitl.lab03.nakarin58070064.simplemydot.model.Dot;

public class DotDialogFragment extends DialogFragment {

    public interface OnDialogListener {
        void onEditPress(Dot dot, int i);

        void onDeletePress(Dot dot, int i);
    }

    private static final String INDEX = "index";
    private static final String DOT = "dot";

    private static final int EDIT = 0;
    private static final int DELETE = 1;

    public static DotDialogFragment newInstance(Dot dot, int i) {
        DotDialogFragment frag = new DotDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(DOT, dot);
        args.putInt(INDEX, i);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dot dot = getArguments().getParcelable(DOT);
        final int dotIndex = getArguments().getInt(INDEX);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.dot_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case EDIT:
                        if (getOnDialogListener() != null) {
                            OnDialogListener listener = getOnDialogListener();
                            if (listener != null) {
                                listener.onEditPress(dot, dotIndex);
                            }
                        }
                        break;
                    case DELETE:
                        OnDialogListener listener = getOnDialogListener();
                        if (listener != null) {
                            listener.onDeletePress(dot, dotIndex);
                        }
                        break;
                    default:
                        dialogInterface.dismiss();
                }
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
