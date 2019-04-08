package esprit.tn.jobchain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
public TextView login ;
public TextView Register ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        login = findViewById(R.id.login);
        Register = findViewById(R.id.Register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(v);
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register(v);
            }
        });

    }

public void Login(View view){

    Intent intent = new Intent(this , LoginActivity.class);

    startActivity(intent);

}

    public void Register(View view){

        Intent intent = new Intent(this , RegisterActivity.class);

        startActivity(intent);

    }
}
