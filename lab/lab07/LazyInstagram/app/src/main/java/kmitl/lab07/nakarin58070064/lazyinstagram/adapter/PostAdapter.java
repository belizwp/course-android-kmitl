package kmitl.lab07.nakarin58070064.lazyinstagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kmitl.lab07.nakarin58070064.lazyinstagram.R;
import kmitl.lab07.nakarin58070064.lazyinstagram.model.PostModel;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Holder> {

    public static final int LIST_LAYOUT = 0;
    public static final int GRID_LAYOUT = 1;

    private Context mContext;
    private List<PostModel> data;
    private int itemLayout;

    public PostAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
        itemLayout = GRID_LAYOUT; // default
    }

    public void setData(List<PostModel> data) {
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == GRID_LAYOUT) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, null);
        } else if (viewType == LIST_LAYOUT) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        } else {
            throw new NullPointerException("no support viewType");
        }

        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        String imageUrl = data.get(position).getUrl();
        Glide.with(mContext).load(imageUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemLayout;
    }

    public void setItemLayout(int itemLayout) {
        this.itemLayout = itemLayout;
    }

    static class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}
