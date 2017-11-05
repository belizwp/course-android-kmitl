package kmitl.lab09.nakarin58070064.moneyflow;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import kmitl.lab09.nakarin58070064.moneyflow.database.MoneyFlowDatabase;
import kmitl.lab09.nakarin58070064.moneyflow.model.Transaction;
import kmitl.lab09.nakarin58070064.moneyflow.task.AddTransactionTask;
import kmitl.lab09.nakarin58070064.moneyflow.task.DeleteTransactionTask;
import kmitl.lab09.nakarin58070064.moneyflow.task.UpdateTransactionTask;

public class TransactionActivity extends AppCompatActivity implements View.OnClickListener {

    private MoneyFlowDatabase database;
    private Transaction mTransaction;

    private RadioGroup radioType;
    private EditText etDescribe, etAmount;
    private Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        initInstances();
    }

    private void initInstances() {
        initDB();

        radioType = findViewById(R.id.radioType);
        etDescribe = findViewById(R.id.etDescribe);
        etAmount = findViewById(R.id.etAmount);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        radioType.check(R.id.income); // default
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        mTransaction = getIntent().getParcelableExtra(Transaction.class.getSimpleName());

        setupInfo();
    }

    private void initDB() {
        database = Room.databaseBuilder(getApplicationContext(), MoneyFlowDatabase.class, "DB")
                .fallbackToDestructiveMigration()
                .build();
    }

    private void setupInfo() {
        if (mTransaction != null) {
            setTitle(getString(R.string.title_edit));
            if (mTransaction.getType().equals(getString(R.string.type_income))) {
                radioType.check(R.id.income);
            } else {
                radioType.check(R.id.outcome);
            }
            etDescribe.setText(mTransaction.getDescribe());
            etAmount.setText(String.valueOf(mTransaction.getAmount()));
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            setTitle(getString(R.string.title_add));
            btnDelete.setVisibility(View.GONE);
        }
    }

    private void save() {
        String type;
        String describe;
        int amount;

        switch (radioType.getCheckedRadioButtonId()) {
            case R.id.income:
                type = getString(R.string.type_income);
                break;
            case R.id.outcome:
                type = getString(R.string.type_outcome);
                break;
            default:
                type = "";
        }

        describe = etDescribe.getText().toString();

        try {
            amount = Integer.parseInt(etAmount.getText().toString());
        } catch (IllegalArgumentException ignore) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            return;
        }

        Transaction transaction = new Transaction(type, describe, amount);

        if (mTransaction != null) {
            mTransaction.updateInfo(transaction);
            new UpdateTransactionTask(database, new UpdateTransactionTask.OnUpdateSuccessListener() {
                @Override
                public void onUpdateSuccess() {
                    finish();
                }
            }).execute(mTransaction);

        } else {
            new AddTransactionTask(database, new AddTransactionTask.OnAddSuccessListener() {
                @Override
                public void onAddSuccess() {
                    finish();
                }
            }).execute(transaction);
        }
    }

    private void delete() {
        new DeleteTransactionTask(database, new DeleteTransactionTask.OnDeleteSuccessListener() {
            @Override
            public void onDeleteSuccess() {
                finish();
            }
        }).execute(mTransaction);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSave) {
            save();
        } else if (view.getId() == R.id.btnDelete) {
            delete();
        }
    }
}
