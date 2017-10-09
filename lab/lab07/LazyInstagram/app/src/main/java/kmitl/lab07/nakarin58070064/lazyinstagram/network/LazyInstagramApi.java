package kmitl.lab07.nakarin58070064.lazyinstagram.network;

import kmitl.lab07.nakarin58070064.lazyinstagram.network.request.FollowRequest;
import kmitl.lab07.nakarin58070064.lazyinstagram.network.response.FollowResponse;
import kmitl.lab07.nakarin58070064.lazyinstagram.network.response.UserProfile;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LazyInstagramApi {

    String BASE = "https://us-central1-retrofit-course.cloudfunctions.net";

    @GET("/getProfile")
    Call<UserProfile> getProfile(@Query("user") String user);

    @POST("/follow")
    Call<FollowResponse> follow(@Body FollowRequest request);
}
