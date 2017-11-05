package kmitl.lab09.nakarin58070064.moneyflow.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kmitl.lab09.nakarin58070064.moneyflow.R;

public class TransactionHolder extends RecyclerView.ViewHolder {

    public View rootLayout;
    public TextView sign, textDesribe, textAmount;

    public TransactionHolder(View itemView) {
        super(itemView);

        rootLayout = itemView.findViewById(R.id.rootLayout);
        sign = itemView.findViewById(R.id.sign);
        textDesribe = itemView.findViewById(R.id.textDescribe);
        textAmount = itemView.findViewById(R.id.textAmount);
    }
}
