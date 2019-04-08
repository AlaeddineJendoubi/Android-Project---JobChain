package esprit.tn.jobchain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import esprit.tn.utile.Url;

public class RegisterActivity extends AppCompatActivity {

    private  EditText nomText ,prenomText,telText,emailText,passwordText;
    Button registerBtn ;
    Spinner address ;
    private ProgressBar loading;
    private Bitmap bitmap ;
    CircleImageView profile_imageR;
    Button imagebtnR;
    String getId;





    TextView linkreg;

    String URL_REGIST = "https://jobchain-tn.000webhostapp.com/jobscript/adduser.php";
    private  static  String URL_UPLOAD ="http://https://jobchain-tn.000webhostapp.com/jobscript/image_upload.php";


    // public static final  String USERNAME="USERNAME" ;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        nomText = (EditText) findViewById(R.id.username_input);
        prenomText =(EditText) findViewById(R.id.prenom);
        telText =(EditText) findViewById(R.id.tel);
        emailText =(EditText) findViewById(R.id.email);
        passwordText =(EditText) findViewById(R.id.password);
        address = (Spinner)findViewById(R.id.spinner);
        registerBtn =(Button) findViewById(R.id.register);
        loading = (ProgressBar) findViewById(R.id.loading) ;
        linkreg= findViewById(R.id.link);

        linkreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist ();

            }
        });}





    private void regist (){

        if (!validate()) {
            onSignupFailed();
            startActivity(new Intent(RegisterActivity.this,RegisterActivity.class));
            return;
        }

        loading.setVisibility(View.VISIBLE);
        registerBtn.setVisibility(View.GONE);


        final String nom = this.nomText.getText().toString().trim();
        final String prenom = this.prenomText.getText().toString().trim();
        final String tel = this.telText.getText().toString().trim();
        final String email = this.emailText.getText().toString().trim();
        final String password = this.passwordText.getText().toString().trim();
        final String add = this.address.getSelectedItem().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject =  new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                if (!nom.isEmpty()|| !prenom.isEmpty() || !email.isEmpty() || !password.isEmpty()) {
                                    Toast.makeText(RegisterActivity.this,"Register Success!",Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(i);
                                }else {
                                    nomText.setError("Please insert your first name");
                                    passwordText.setError("Please insert your last name");
                                    emailText.setError("Please insert your email");
                                    passwordText.setError("Please insert your password");


                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this,"Register Error! "+e.toString(),Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            registerBtn.setVisibility(View.VISIBLE);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this,"Register Error! "+error.toString(),Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        registerBtn.setVisibility(View.VISIBLE);

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nom",nom);
                params.put("prenom",prenom);
                params.put("numtel",tel);
                params.put("password",password);
                params.put("email",email);
                params.put("address",add);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Erreur Login", Toast.LENGTH_LONG).show();

        registerBtn.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String nom = nomText.getText().toString();
        String prenom = prenomText.getText().toString();
        String email = emailText.getText().toString();
        String tel = telText.getText().toString();
        String motdepasse = passwordText.getText().toString();

        if (nom.isEmpty() || nom.length() < 3) {
            nomText.setError("au mois 3 caractères");
            valid = false;
        } else {
            nomText.setError(null);
        }

        if (prenom.isEmpty() || prenom.length() < 3) {
            prenomText.setError("au mois 3 caractères");
            valid = false;
        } else {
            prenomText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Adresse mail invalide");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (motdepasse.isEmpty() || motdepasse.length() < 2 || motdepasse.length() > 15) {
            passwordText.setError("entre 2 et 15 caractères");
            valid = false;
        } else {//passwordText.setError(null);
        }


        return valid;
    }





}