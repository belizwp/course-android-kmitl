package kmitl.lab07.nakarin58070064.lazyinstagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kmitl.lab07.nakarin58070064.lazyinstagram.R;
import kmitl.lab07.nakarin58070064.lazyinstagram.adapter.holder.GridItemHolder;
import kmitl.lab07.nakarin58070064.lazyinstagram.adapter.holder.ListItemHolder;
import kmitl.lab07.nakarin58070064.lazyinstagram.network.response.PostModel;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == GRID_LAYOUT) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, null);
            return new GridItemHolder(itemView);
        } else if (viewType == LIST_LAYOUT) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
            return new ListItemHolder(itemView);
        } else {
            throw new NullPointerException("no support viewType");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PostModel post = data.get(position);

        if (holder instanceof GridItemHolder) {
            setupGridItem((GridItemHolder) holder, post);
        } else if (holder instanceof ListItemHolder) {
            setupListItem((ListItemHolder) holder, post);
        }
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

    private void setupGridItem(GridItemHolder holder, PostModel postModel) {
        Glide.with(mContext).load(postModel.getUrl()).into(holder.imageView);
    }

    private void setupListItem(ListItemHolder holder, PostModel postModel) {
        Glide.with(mContext).load(postModel.getUrl()).into(holder.imageView);
        holder.like.setText(String.valueOf(postModel.getLike()));
        holder.comment.setText(String.valueOf(postModel.getComment()));
    }

}
