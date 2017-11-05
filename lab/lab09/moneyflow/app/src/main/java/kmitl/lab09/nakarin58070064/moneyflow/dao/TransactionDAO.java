package kmitl.lab09.nakarin58070064.moneyflow.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import kmitl.lab09.nakarin58070064.moneyflow.model.Summary;
import kmitl.lab09.nakarin58070064.moneyflow.model.Transaction;

@Dao
public interface TransactionDAO {

    @Query("SELECT * FROM `Transaction` ORDER BY id ASC")
    List<Transaction> getTransactions();

    @Insert
    void insert(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("SELECT total_income, total_outcome FROM " +
            "(SELECT SUM(amount) AS total_income FROM `Transaction` WHERE type == 'income')" +
            "JOIN " +
            "(SELECT SUM(amount) AS total_outcome FROM `Transaction` WHERE type == 'outcome')")
    Summary getSummary();
}
