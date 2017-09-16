package kmitl.lab03.nakarin58070064.simplemydot;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kmitl.lab03.nakarin58070064.simplemydot.fragment.DotDialogFragment;
import kmitl.lab03.nakarin58070064.simplemydot.fragment.DotFragment;
import kmitl.lab03.nakarin58070064.simplemydot.fragment.EditDotFragment;
import kmitl.lab03.nakarin58070064.simplemydot.model.Dot;

public class MainActivity extends AppCompatActivity implements DotFragment.DotFragmentListener,
        DotDialogFragment.OnDialogListener, EditDotFragment.EditDotFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, DotFragment.newInstance(), DotFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void DotLongPressSelected(Dot dot, int i) {
        DotDialogFragment.newInstance(dot, i)
                .show(getSupportFragmentManager(), "DotDialog");
    }

    @Override
    public void onEditPress(Dot dot, int i) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.fragment_container, EditDotFragment.newInstance(dot, i))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeletePress(Dot dot, int i) {
        DotFragment fragment = findFragmentByTag(DotFragment.TAG);
        if (fragment != null) {
            fragment.removeDot(dot, i);
        }
    }

    @Override
    public void onSave(Dot dot, int i) {
        DotFragment fragment = findFragmentByTag(DotFragment.TAG);
        if (fragment != null) {
            fragment.setDot(dot, i);
        }

        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancel() {
        getSupportFragmentManager().popBackStack();
    }

    private <T> T findFragmentByTag(String tag) {
        return (T) getSupportFragmentManager().findFragmentByTag(tag);
    }
}
