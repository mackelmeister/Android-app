package alexsander.com.br.friendsintown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        email = (EditText) findViewById(R.id.registerEmail);
        password = (EditText) findViewById(R.id.registerPassword);
        password2 = (EditText) findViewById(R.id.registerPassword2);

        Button sendButton = (Button) findViewById(R.id.button4);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.equals(password2)){
                  // TODO: 23/06/2016 Send registration to server
                    Toast.makeText(getApplicationContext(), "We have yet to code the server conection", Toast.LENGTH_LONG);
                } else {
                    password.setText("");
                    password2.setText("");
                    Toast.makeText(getApplicationContext() , "The password confirmation did not match the password", Toast.LENGTH_LONG);
                }
            }
        });


    }
}
