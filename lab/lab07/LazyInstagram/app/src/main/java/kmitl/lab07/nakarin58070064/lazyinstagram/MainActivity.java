package kmitl.lab07.nakarin58070064.lazyinstagram;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import kmitl.lab07.nakarin58070064.lazyinstagram.adapter.PostAdapter;
import kmitl.lab07.nakarin58070064.lazyinstagram.fragment.ProgressFragment;
import kmitl.lab07.nakarin58070064.lazyinstagram.model.FollowRequest;
import kmitl.lab07.nakarin58070064.lazyinstagram.model.FollowResponse;
import kmitl.lab07.nakarin58070064.lazyinstagram.model.UserProfile;
import kmitl.lab07.nakarin58070064.lazyinstagram.service.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        OnTabSelectListener {

    private UserProfile userProfile;
    private PostAdapter adapter;

    private Toolbar toolbar;
    private RecyclerView list;

    private TextView textUser;
    private TextView textPost;
    private TextView textFollower;
    private TextView textFollwing;
    private TextView textBio;
    private ImageView imageProfile;
    private Button btnFollow;
    private BottomBar bottomBar;

    private ProgressFragment progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();

        progress.show(getSupportFragmentManager(), null);
        loadUserProfile("android");
    }

    private void initInstances() {
        adapter = new PostAdapter(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        list = (RecyclerView) findViewById(R.id.list);
        textUser = (TextView) findViewById(R.id.textUser);
        textPost = (TextView) findViewById(R.id.textPost);
        textFollower = (TextView) findViewById(R.id.textFollower);
        textFollwing = (TextView) findViewById(R.id.textFollwing);
        textBio = (TextView) findViewById(R.id.textBio);
        imageProfile = (ImageView) findViewById(R.id.imageProfile);
        btnFollow = (Button) findViewById(R.id.btnFollow);
        bottomBar = (BottomBar) findViewById(R.id.navBar);

        setSupportActionBar(toolbar);
        list.setLayoutManager(new GridLayoutManager(this, 3));
        list.setAdapter(adapter);

        btnFollow.setOnClickListener(this);
        bottomBar.setOnTabSelectListener(this);

        progress = new ProgressFragment();
        progress.setCancelable(false);
    }

    private void loadUserProfile(String username) {
        Call<UserProfile> call = ApiManager.getInstance().getApi().getProfile(username);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                progress.dismiss();
                if (response.isSuccessful()) {
                    userProfile = response.body();
                    display(userProfile);
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                progress.dismiss();
            }
        });
    }

    private void onFollow(String username, final boolean isFollow) {
        FollowRequest request = new FollowRequest(username, isFollow);
        Call<FollowResponse> call = ApiManager.getInstance().getApi().follow(request);
        call.enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                progress.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("OK")) {
                        userProfile.setFollow(isFollow);
                        setFollowButton(isFollow);

                        if (isFollow) {
                            Toast.makeText(MainActivity.this, "follow success!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "unfollow success!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                }
                Toast.makeText(MainActivity.this, "action failed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void display(UserProfile user) {
        Glide.with(this).load(user.getUrlProfile()).into(imageProfile);

        textUser.setText("@" + user.getUser());
        textPost.setText("Post\n" + user.getPost());
        textFollower.setText("Follower\n" + user.getFollower());
        textFollwing.setText("Following\n" + user.getFollowing());
        textBio.setText(user.getBio());
        setFollowButton(user.isFollow());

        adapter.setData(user.getPosts());
        adapter.notifyDataSetChanged();
    }

    private void setFollowButton(boolean isFollow) {
        btnFollow.setEnabled(true);
        if (isFollow) {
            btnFollow.setText(R.string.following);
        } else {
            btnFollow.setText(R.string.not_follow);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnFollow) {
            progress.show(getSupportFragmentManager(), null);
            onFollow(userProfile.getUser(), !userProfile.isFollow());
        }
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        if (tabId == R.id.tab_grid) {
            adapter.setItemLayout(PostAdapter.GRID_LAYOUT);
            list.setLayoutManager(new GridLayoutManager(this, 3));
        } else if (tabId == R.id.tab_list) {
            adapter.setItemLayout(PostAdapter.LIST_LAYOUT);
            list.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
