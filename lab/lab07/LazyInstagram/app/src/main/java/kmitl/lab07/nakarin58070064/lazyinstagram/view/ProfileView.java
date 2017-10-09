package kmitl.lab07.nakarin58070064.lazyinstagram.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import kmitl.lab07.nakarin58070064.lazyinstagram.R;

public class ProfileView extends FrameLayout implements View.OnClickListener {

    private ImageView imageProfile;
    private TextView tvUser;
    private TextView tvPost;
    private TextView tvFollower;
    private TextView tvFollowing;
    private TextView tvBio;
    private Button btnFollow;

    private OnFollowClickListener listener;

    public ProfileView(@NonNull Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public ProfileView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
    }

    public ProfileView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProfileView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
    }

    private void initInflate() {
        inflate(getContext(), R.layout.layout_userprofile, this);
    }

    private void initInstances() {
        imageProfile = findViewById(R.id.imageProfile);
        tvUser = findViewById(R.id.tvUser);
        tvPost = findViewById(R.id.tvPost);
        tvFollower = findViewById(R.id.tvFollwer);
        tvFollowing = findViewById(R.id.tvFollowing);
        tvBio = findViewById(R.id.tvBio);
        btnFollow = findViewById(R.id.btnFollow);

        btnFollow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnFollow) {
            if (listener != null) {
                listener.onFollowClick();
            }
        }
    }

    public ImageView getProfileImageView() {
        return imageProfile;
    }

    public void setUser(String user) {
        tvUser.setText(String.format("@%s", user));
    }

    public void setPost(int i) {
        tvPost.setText(String.valueOf(i));
    }

    public void setFollower(int i) {
        tvFollower.setText(String.valueOf(i));
    }

    public void setFollowing(int i) {
        tvFollowing.setText(String.valueOf(i));
    }

    public void setBio(String bio) {
        tvBio.setText(bio);
    }

    public void setFollow(boolean isFollow) {
        btnFollow.setEnabled(true);
        if (isFollow) {
            btnFollow.setText(R.string.following);
        } else {
            btnFollow.setText(R.string.not_follow);
        }
    }

    public void setOnFollowClickListener(OnFollowClickListener l) {
        this.listener = l;
    }

    public interface OnFollowClickListener {
        void onFollowClick();
    }
}
