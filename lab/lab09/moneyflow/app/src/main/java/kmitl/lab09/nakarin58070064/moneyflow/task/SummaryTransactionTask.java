package kmitl.lab09.nakarin58070064.moneyflow.task;

import android.os.AsyncTask;

import kmitl.lab09.nakarin58070064.moneyflow.database.MoneyFlowDatabase;
import kmitl.lab09.nakarin58070064.moneyflow.model.Summary;

public class SummaryTransactionTask extends AsyncTask<Void, Void, Summary> {

    private MoneyFlowDatabase database;
    private OnSummarySuccessListener listener;

    public SummaryTransactionTask(MoneyFlowDatabase db, OnSummarySuccessListener l) {
        this.database = db;
        this.listener = l;
    }

    @Override
    protected Summary doInBackground(Void... voids) {
        return database.transactionDAO().getSummary();
    }

    @Override
    protected void onPostExecute(Summary summary) {
        super.onPostExecute(summary);
        listener.onSummarySuccess(summary);
    }

    public interface OnSummarySuccessListener {
        void onSummarySuccess(Summary summary);
    }
}
