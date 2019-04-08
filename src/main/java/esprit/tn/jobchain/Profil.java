package esprit.tn.jobchain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import esprit.tn.utile.Url;

public class Profil extends AppCompatActivity {
    EditText nom , prenom , num , password , email ;
    Spinner adress ;
    TextView imagebtn , save;
    private Bitmap bitmap ;
    CircleImageView profile_image;

    String getId;
    private  static  String URL_READ ="https://jobchain-tn.000webhostapp.com/jobscript/read_detail.php";

    private  static  String URL_EDIT ="https://jobchain-tn.000webhostapp.com/edit_detail.php";

    private  static  String URL_UPLOAD ="https://jobchain-tn.000webhostapp.com/jobscript/image_upload.php";

    private static  final String TAG= HomeActivity.class.getSimpleName();   //getting the info
    SessionManager sessionManager;
    private  Menu action ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        sessionManager = new SessionManager(this);
        // sessionManager.checkLogin();
        HashMap<String,String> user = sessionManager.getUserDetail();
        getId= user.get(sessionManager.ID);
        profile_image = findViewById(R.id.profile_image);
        imagebtn = findViewById(R.id.uploadImage);
        save =  findViewById(R.id.savveEdit);

        nom = (EditText) findViewById(R.id.username_inputProfil);
        prenom = (EditText) findViewById(R.id.prenomProfil);
        num = (EditText) findViewById(R.id.telProfil);
        email= (EditText) findViewById(R.id.emailProfil);
        password = (EditText) findViewById(R.id.passwordProfil);
        adress = (Spinner) findViewById(R.id.spinnerProfil);

        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseFile();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveEditDetail();
            }
        });
    }

    private void  getUserDetail( ){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if( success.equals("1")){
                                System.out.println("fil 1 jaw");

                                for (int i =0 ; i < jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String  strnom = object.getString("nom").trim();
                                    String  strprenom = object.getString("prenom").trim();
                                    String  strnum = object.getString("numtel").trim();
                                    String  stremail = object.getString("email").trim();
                                    String  strpassword = object.getString("password").trim();
                                    String  straddress = object.getString("address").trim();
                                    String  strphoto = object.getString("photo").trim();

                                    nom.setText(strnom);
                                    prenom.setText(strprenom);
                                    num.setText(strnum);
                                    email.setText(stremail);
                                    password.setText(strpassword);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            System.out.println("fil e");


                            Toast.makeText(Profil.this,"Error Reading .... "+e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        System.out.println("fil Error");

                        Toast.makeText(Profil.this,"Error Reading "+error.toString(),Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>  params = new HashMap<>();
                params.put("id",getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action ,menu);
        action = menu ;
        action.findItem(R.id.menu_save).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menu_edit:
                System.out.println( "fil item Selected ... ");
                nom.setFocusableInTouchMode(true);
                prenom.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);
                num.setFocusableInTouchMode(true);
                password.setFocusableInTouchMode(true);
                adress.setFocusableInTouchMode(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(nom,InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case  R.id.menu_save:

                SaveEditDetail();

                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);
                System.out.println( "fil save  ... ");

                nom.setFocusableInTouchMode(false);
                prenom.setFocusableInTouchMode(false);
                email.setFocusableInTouchMode(false);
                num.setFocusableInTouchMode(false);
                password.setFocusableInTouchMode(false);
                adress.setFocusableInTouchMode(false);

                nom.setFocusable(false);
                prenom.setFocusable(false);
                email.setFocusable(false);
                num.setFocusable(false);
                password.setFocusable(false);
                adress.setFocusable(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }


    }

    private void SaveEditDetail() {
        final  String id = getId;
        final  String nom = this.nom.getText().toString().trim();
        final  String prenom = this.prenom.getText().toString().trim();
        final  String num = this.num.getText().toString().trim();
        final  String email = this.email.getText().toString().trim();
        final  String password = this.password.getText().toString().trim();
        final  String address = this.adress.getSelectedItem().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();
        System.out.println( "fil save edit ... ");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            System.out.println( "lol"+success);
                            if (success.equals("1")){
                                System.out.println("fil iiif 1");
                                Toast.makeText(Profil.this , "Success ! ", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(nom,email,id);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("erreeeur e");

                            Toast.makeText(Profil.this , "Error "+e.toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        System.out.println("Errroooooooor");


                        Toast.makeText(Profil.this , "Error "+error.toString(), Toast.LENGTH_SHORT).show();


                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);

                params.put("nom",nom);
                params.put("prenom",prenom);
                params.put("numtel",num);
                params.put("email",email);
                params.put("password",password);
                params.put("address",address);
                System.out.println("cccvvvv");



                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void ChooseFile (){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data !=null &&data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadPicture (getId,getStringImage(bitmap));

        }
    }

    private void UploadPicture(final String id, final String  photo) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(Profil.this,"Success!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Profil.this,"Try Again! "+e.toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Profil.this,"Try Again! "+error.toString(), Toast.LENGTH_SHORT).show();


                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("photo",photo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    //convert bitmap to string
    static public String getStringImage (Bitmap bitmap){
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG , 100,byteArrayInputStream);
        byte [] imageByteArray = byteArrayInputStream.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageByteArray, android.util.Base64.DEFAULT);
        return encodedImage;
    }

}