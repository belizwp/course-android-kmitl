package kmitl.lab07.nakarin58070064.lazyinstagram.network.request;

public class FollowRequest {

    private String user;
    private boolean isFollow;

    public FollowRequest(String user, boolean isFollow) {
        this.user = user;
        this.isFollow = isFollow;
    }
}