package kmitl.lab07.nakarin58070064.lazyinstagram;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import kmitl.lab07.nakarin58070064.lazyinstagram.adapter.PostAdapter;
import kmitl.lab07.nakarin58070064.lazyinstagram.fragment.ProgressFragment;
import kmitl.lab07.nakarin58070064.lazyinstagram.network.ApiManager;
import kmitl.lab07.nakarin58070064.lazyinstagram.network.request.FollowRequest;
import kmitl.lab07.nakarin58070064.lazyinstagram.network.response.FollowResponse;
import kmitl.lab07.nakarin58070064.lazyinstagram.network.response.UserProfile;
import kmitl.lab07.nakarin58070064.lazyinstagram.view.ProfileView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ProfileView.OnFollowClickListener,
        OnTabSelectListener, AdapterView.OnItemSelectedListener {

    private UserProfile userProfile;
    private ArrayAdapter<CharSequence> userAdapter;
    private PostAdapter postAdapter;
    private ProgressFragment progress;

    private Toolbar toolbar;
    private Spinner userSpinner;
    private ProfileView profileView;
    private BottomBar navBar;
    private RecyclerView rvPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        userSpinner = (Spinner) findViewById(R.id.userSpinner);
        userAdapter = ArrayAdapter.createFromResource(this, R.array.user,
                R.layout.spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);
        userSpinner.setOnItemSelectedListener(this);

        postAdapter = new PostAdapter(this);
        progress = new ProgressFragment();
        progress.setCancelable(false);

        profileView = (ProfileView) findViewById(R.id.userProfile);
        navBar = (BottomBar) findViewById(R.id.navBar);
        rvPost = (RecyclerView) findViewById(R.id.rvPost);

        profileView.setOnFollowClickListener(this);
        navBar.setOnTabSelectListener(this);
        rvPost.setAdapter(postAdapter);
    }

    private void loadUserProfile(String user) {
        progress.show(getSupportFragmentManager(), null);
        Call<UserProfile> call = ApiManager.getInstance().getApi().getProfile(user);
        call.enqueue(new MyCallback(MyCallback.ACTION_LOAD_PROFILE));
    }

    @Override
    public void onFollowClick() {
        progress.show(getSupportFragmentManager(), null);
        FollowRequest body = new FollowRequest(userProfile.getUser(), !userProfile.isFollow());
        Call<FollowResponse> call = ApiManager.getInstance().getApi().follow(body);
        call.enqueue(new MyCallback(MyCallback.ACTION_FOLLOW));
    }

    private void display(UserProfile user) {
        Glide.with(this).load(user.getUrlProfile()).into(profileView.getProfileImageView());

        profileView.setUser(user.getUser());
        profileView.setPost(user.getPost());
        profileView.setFollower(user.getFollower());
        profileView.setFollowing(user.getFollowing());
        profileView.setBio(user.getBio());
        profileView.setFollow(user.isFollow());

        postAdapter.setData(user.getPosts());
        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        if (tabId == R.id.tab_grid) {
            postAdapter.setItemLayout(PostAdapter.GRID_LAYOUT);
            rvPost.setLayoutManager(new GridLayoutManager(this, 3));
        } else if (tabId == R.id.tab_list) {
            postAdapter.setItemLayout(PostAdapter.LIST_LAYOUT);
            rvPost.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String user = getResources().getStringArray(R.array.user)[position];
        loadUserProfile(user);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class MyCallback implements Callback {

        static final int ACTION_LOAD_PROFILE = 1;
        static final int ACTION_FOLLOW = 2;

        int action;

        public MyCallback(int action) {
            this.action = action;
        }

        @Override
        public void onResponse(Call call, Response response) {
            progress.dismiss();
            if (response.isSuccessful()) {
                switch (action) {
                    case ACTION_LOAD_PROFILE:
                        userProfile = (UserProfile) response.body();
                        display(userProfile);
                        return;
                    case ACTION_FOLLOW:
                        FollowResponse followResponse = (FollowResponse) response.body();
                        if (followResponse.getMessage().equals("OK")) {
                            userProfile.setFollow(!userProfile.isFollow());
                            profileView.setFollow(userProfile.isFollow());
                            if (userProfile.isFollow()) {
                                showToast("follow success!");
                            } else {
                                showToast("unfollow success!");
                            }
                            return;
                        }
                        break;
                }
            }
            showToast("action failed!");
        }

        @Override
        public void onFailure(Call call, Throwable t) {
            progress.dismiss();
            showToast(t.getMessage());
        }
    }
}
