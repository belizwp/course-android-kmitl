package kmitl.lab09.nakarin58070064.moneyflow.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import kmitl.lab09.nakarin58070064.moneyflow.dao.TransactionDAO;
import kmitl.lab09.nakarin58070064.moneyflow.model.Transaction;

@Database(entities = Transaction.class, version = 1)
public abstract class MoneyFlowDatabase extends RoomDatabase {

    public abstract TransactionDAO transactionDAO();

}
