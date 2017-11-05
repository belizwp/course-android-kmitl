package kmitl.lab09.nakarin58070064.moneyflow.model;

import android.arch.persistence.room.ColumnInfo;

public class Summary {

    @ColumnInfo(name = "total_income")
    private int totalIncome;

    @ColumnInfo(name = "total_outcome")
    private int totalOutcome;

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getTotalOutcome() {
        return totalOutcome;
    }

    public void setTotalOutcome(int totalOutcome) {
        this.totalOutcome = totalOutcome;
    }

    public int getSum() {
        return totalIncome - totalOutcome;
    }
}
