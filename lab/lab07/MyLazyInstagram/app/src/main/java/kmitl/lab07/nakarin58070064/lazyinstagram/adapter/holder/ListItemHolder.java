package kmitl.lab07.nakarin58070064.lazyinstagram.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kmitl.lab07.nakarin58070064.lazyinstagram.R;

public class ListItemHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView like;
    public TextView comment;

    public ListItemHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        like = itemView.findViewById(R.id.like);
        comment = itemView.findViewById(R.id.comment);
    }
}
