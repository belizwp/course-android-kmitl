package kmitl.lab09.nakarin58070064.moneyflow.task;

import android.os.AsyncTask;

import java.util.List;

import kmitl.lab09.nakarin58070064.moneyflow.database.MoneyFlowDatabase;
import kmitl.lab09.nakarin58070064.moneyflow.model.Transaction;

public class FetchTransactionTask extends AsyncTask<Void, Void, List<Transaction>> {

    private MoneyFlowDatabase database;
    private OnFetchSuccessListener listener;

    public FetchTransactionTask(MoneyFlowDatabase db, OnFetchSuccessListener l) {
        this.database = db;
        this.listener = l;
    }

    @Override
    protected List<Transaction> doInBackground(Void... voids) {
        return database.transactionDAO().getTransactions();
    }

    @Override
    protected void onPostExecute(List<Transaction> transactionList) {
        super.onPostExecute(transactionList);
        listener.onFetchSuccess(transactionList);
    }

    public interface OnFetchSuccessListener {
        void onFetchSuccess(List<Transaction> transactionList);
    }

}
