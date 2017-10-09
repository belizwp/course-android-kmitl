package kmitl.lab07.nakarin58070064.lazyinstagram.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import kmitl.lab07.nakarin58070064.lazyinstagram.R;

public class GridItemHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;

    public GridItemHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
    }
}