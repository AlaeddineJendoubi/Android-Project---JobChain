package esprit.tn.jobchain;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


import java.util.HashMap;
import java.util.Map;

import esprit.tn.Entities.User;
import esprit.tn.utile.Url;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener , GoogleApiClient.OnConnectionFailedListener{

   private EditText edit_mail, edit_pass;
    TextView linkreg;
    TextView loginButton;
    private static String URL_LOGIN = "https://jobchain-tn.000webhostapp.com/jobscript/loginNew.php";
    private ProgressBar loading;
    private SignInButton SignIn;
    private GoogleApiClient googleApiClient;
    private static  final int REQ_CODE= 9001 ;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).
                enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();


        edit_mail= findViewById(R.id.email_input);
        edit_pass= findViewById(R.id.pass);
        loginButton= findViewById(R.id.LoginBtn);
        linkreg= findViewById(R.id.link);
        loading= findViewById(R.id.loadinglog);
        SignIn = (SignInButton) findViewById(R.id.bn_login) ;
        SignIn.setOnClickListener(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String memail = edit_mail.getText().toString().trim();
                String mpass = edit_pass.getText().toString().trim();

                if (!memail.isEmpty()|| !mpass.isEmpty()){
                    Login(memail,mpass);
                }else {
                    edit_mail.setError("Please insert email");
                    edit_pass.setError("Please insert password");

                }



            }
        });

        linkreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void Login(final String email , final String password) {
        loading.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                response -> {
                    try {
                        JSONObject jsonObject =  new JSONObject(response);
                        String success =jsonObject.getString("success");
                        JSONArray jsonArray  = jsonObject.getJSONArray("login");

                        if(success.equals("1")){
                            for (int i = 0 ; i< jsonArray.length();i++){
                                JSONObject object =jsonArray.getJSONObject(i);
                               String nomt = object.getString("nom").trim();
                               String email1 = object.getString("email").trim();
                                String id = object.getString("id").trim();

                                sessionManager.createSession(nomt, email1,id);
                                Intent inte = new Intent(LoginActivity.this, HomeActivity.class);
                                inte.putExtra("nomUserConnected",nomt);
                                inte.putExtra("idUserConnected",id);
                                startActivity(inte);
                                finish();
                               /*Shered pref getting user name and id*/
                                Context context =this;
                                SharedPreferences settings;
                                SharedPreferences.Editor editor;
                                settings = context.getSharedPreferences("info", Context.MODE_PRIVATE); //1
                               // settings = context.getSharedPreferences("nomUserCon", Context.MODE_PRIVATE); //1
                                editor = settings.edit(); //2

                                editor.putString("idUserCon", id); //3
                                ((Constants) LoginActivity.this.getApplication()).setIdConnectedUser(String.valueOf(id));
                                editor.putString("nomUserCon", nomt); //3
                                editor.commit(); //4

                             // Toast.makeText(LoginActivity.this,"Success Login \nYour Name : "+nomt+"\nYour Email : "+email,Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        loading.setVisibility(View.GONE);
                        loginButton.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this,"Connection error pleas make sure you have acces to internet",Toast.LENGTH_SHORT).show();

                    }
                },
                error -> {
                    loading.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);

                    Toast.makeText(LoginActivity.this,"Connection error pleas make sure you have acces to internet ",Toast.LENGTH_SHORT).show();


                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bn_login:
                singIn();
                break;
        }
    }

    private  void singIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);

    }
    private void singOut(){

    }
    private void handleResult(GoogleSignInResult result ){

        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String imgurl = account.getPhotoUrl().toString();
            updateUI(true);
        } else {

            updateUI(false);
        }

    }
    private void updateUI(boolean islogin){
        if(islogin){
            Intent inte = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(inte);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
