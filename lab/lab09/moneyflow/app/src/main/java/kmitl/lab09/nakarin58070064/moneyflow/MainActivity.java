package kmitl.lab09.nakarin58070064.moneyflow;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import kmitl.lab09.nakarin58070064.moneyflow.adapter.RecyclerItemClickListener;
import kmitl.lab09.nakarin58070064.moneyflow.adapter.TransactionAdapter;
import kmitl.lab09.nakarin58070064.moneyflow.database.MoneyFlowDatabase;
import kmitl.lab09.nakarin58070064.moneyflow.model.Summary;
import kmitl.lab09.nakarin58070064.moneyflow.model.Transaction;
import kmitl.lab09.nakarin58070064.moneyflow.task.FetchTransactionTask;
import kmitl.lab09.nakarin58070064.moneyflow.task.SummaryTransactionTask;

public class MainActivity extends AppCompatActivity {

    private MoneyFlowDatabase database;

    private TextView textMoney, textEmpty;
    private RecyclerView list;
    private TransactionAdapter adapter;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

    private void initInstances() {
        initDB();

        textMoney = findViewById(R.id.textMoney);
        textEmpty = findViewById(R.id.textEmpty);
        list = findViewById(R.id.list);
        btnAdd = findViewById(R.id.btnAdd);

        adapter = new TransactionAdapter(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        list.addOnItemTouchListener(new RecyclerItemClickListener(this, list,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startTransactionActivity(adapter.getTransactionList().get(position));
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTransactionActivity(null);
            }
        });
    }

    private void initDB() {
        database = Room.databaseBuilder(getApplicationContext(), MoneyFlowDatabase.class, "DB")
                .fallbackToDestructiveMigration()
                .build();
    }

    private void startTransactionActivity(@Nullable Transaction transaction) {
        Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
        intent.putExtra(Transaction.class.getSimpleName(), transaction);
        startActivity(intent);
    }

    private void fetchData() {
        new FetchTransactionTask(database, new FetchTransactionTask.OnFetchSuccessListener() {
            @Override
            public void onFetchSuccess(List<Transaction> transactionList) {
                updateList(transactionList);
            }
        }).execute();

        new SummaryTransactionTask(database, new SummaryTransactionTask.OnSummarySuccessListener() {
            @Override
            public void onSummarySuccess(Summary summary) {
                updateMoney(summary);
            }
        }).execute();
    }

    private void updateList(List<Transaction> transactionList) {
        if (transactionList.size() == 0) {
            list.setVisibility(View.GONE);
            textEmpty.setVisibility(View.VISIBLE);
        } else {
            list.setVisibility(View.VISIBLE);
            textEmpty.setVisibility(View.GONE);
            adapter.setTransactionList(transactionList);
            adapter.notifyDataSetChanged();
        }
    }

    private void updateMoney(Summary summary) {
        int sum = summary.getSum();
        int totalIncome = summary.getTotalIncome();

        if ((float) sum / totalIncome <= 0.25) {
            textMoney.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light));
        } else if ((float) sum / totalIncome <= 0.5) {
            textMoney.setTextColor(ContextCompat.getColor(this, android.R.color.holo_orange_light));
        } else {
            textMoney.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_light));
        }
        textMoney.setText(NumberFormat.getNumberInstance().format(sum));
    }
}
