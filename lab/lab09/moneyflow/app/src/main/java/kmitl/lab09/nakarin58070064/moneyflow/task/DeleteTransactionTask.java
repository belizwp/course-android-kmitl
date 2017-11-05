package kmitl.lab09.nakarin58070064.moneyflow.task;

import android.os.AsyncTask;

import kmitl.lab09.nakarin58070064.moneyflow.database.MoneyFlowDatabase;
import kmitl.lab09.nakarin58070064.moneyflow.model.Transaction;

public class DeleteTransactionTask extends AsyncTask<Transaction, Void, Void> {

    private MoneyFlowDatabase database;
    private OnDeleteSuccessListener listener;

    public DeleteTransactionTask(MoneyFlowDatabase db, OnDeleteSuccessListener l) {
        this.database = db;
        this.listener = l;
    }

    @Override
    protected Void doInBackground(Transaction... transactions) {
        for (int i = 0; i < transactions.length; i++) {
            database.transactionDAO().delete(transactions[i]);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onDeleteSuccess();
    }

    public interface OnDeleteSuccessListener {
        void onDeleteSuccess();
    }
}
