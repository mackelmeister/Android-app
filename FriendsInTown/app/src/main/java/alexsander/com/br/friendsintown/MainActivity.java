package alexsander.com.br.friendsintown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    private LoginButton fbLoginButton;
    private CallbackManager fbCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkUserLoggedWithFacebook();

        fbCallbackManager = CallbackManager.Factory.create();

        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        fbLoginButton.setReadPermissions("public_profile", "email", "user_friends");

        fbLoginButton.registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, loginResult.getAccessToken().getToken());
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Log.d(TAG, object.toString());
                            String name = object.getString("name");
                            String email = object.getString("email");
                            Log.d(TAG, name);
                            Log.d(TAG, email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                new GraphRequest(loginResult.getAccessToken(), "/me/friends", null, HttpMethod.GET, new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.d(TAG, response.getJSONObject().toString());
                    }
                }).executeAsync();



                Bundle parameters = new Bundle();
                parameters.putString("fields", "name, email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Tente novamente", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void checkUserLoggedWithFacebook() {
        if (AccessToken.getCurrentAccessToken() != null) {
            Log.d(TAG, AccessToken.getCurrentAccessToken().getToken());
            // start another activity
        }
    }
}
