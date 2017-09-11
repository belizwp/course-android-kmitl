package kmitl.lab03.nakarin58070064.simplemydot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.flask.colorpicker.ColorPickerView;

import kmitl.lab03.nakarin58070064.simplemydot.model.Dot;

public class EditActivity extends AppCompatActivity {

    private Dot dot;
    private ColorPickerView colorPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setTitle(getString(R.string.edit_dot_title));

        dot = getIntent().getParcelableExtra("Dot");

        colorPickerView = (ColorPickerView) findViewById(R.id.color_picker_view);
        colorPickerView.setColor(dot.getColor(), false);
    }

    public void onSelect(View view) {
        dot.setColor(colorPickerView.getSelectedColor());

        Intent returnIntent = new Intent();
        returnIntent.putExtra("Dot", dot);
        returnIntent.putExtra("DotIndex", getIntent().getIntExtra("DotIndex", -1));
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void onCancel(View view) {
        finish();
    }
}
