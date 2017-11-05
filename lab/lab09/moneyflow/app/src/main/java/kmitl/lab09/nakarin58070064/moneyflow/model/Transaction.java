package kmitl.lab09.nakarin58070064.moneyflow.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Transaction implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String type;
    private String describe;
    private int amount;

    public Transaction(String type, String describe, int amount) {
        this.type = type;
        this.describe = describe;
        this.amount = amount;
    }

    protected Transaction(Parcel in) {
        id = in.readInt();
        type = in.readString();
        describe = in.readString();
        amount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(describe);
        dest.writeInt(amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void updateInfo(Transaction transaction) {
        this.type = transaction.getType();
        this.describe = transaction.getDescribe();
        this.amount = transaction.getAmount();
    }
}
