package cs2b01.utec.chat_mobile;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public Activity getActivity(){
        return this;
    }


    public void Registrarse(View view) {

        // 1. Getting username and password inputs from view
        EditText txtUsername = (EditText) findViewById(R.id.r_usuario);
        EditText txtEmail = (EditText) findViewById(R.id.r_mail);
        EditText txtName = (EditText) findViewById(R.id.r_Name);
        EditText txtLastname = (EditText) findViewById(R.id.r_Apellido);
        EditText txt = (EditText) findViewById(R.id.r_pais);
        EditText txtPassword = (EditText) findViewById(R.id.r_Password);
        EditText txtEdad = (EditText) findViewById(R.id.r_edad);

        final String username = txtUsername.getText().toString();
        final String email = txtEmail.getText().toString();
        String name = txtName.getText().toString();
        String lastname = txtLastname.getText().toString();
        String country = txt.getText().toString();
        String password = txtPassword.getText().toString();
        String Sdad = txtEdad.getText().toString();
        int edad = Integer.parseInt(Sdad);

        // 2. Creating a message from user input data
        Map<String, Object> message = new HashMap<>();
        message.put("usuario", username);
        message.put("contrasena", password);
        message.put("correo", email);
        message.put("nombre", name);
        message.put("apellido", lastname);
        message.put("pais", country);
        message.put("edad",edad);

        // 3. Converting the message object to JSON string (jsonify)
        JSONObject jsonMessage = new JSONObject(message);

        // 4. Sending json message to Server
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://travelorg2.herokuapp.com/registrar",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO
                        try {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            showMessage("Usuario "+username+" registrado con exito!!!");

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        if( error instanceof AuthFailureError){
                            showMessage(email+" ya exite, intente con otro");
                        }
                        else {
                            showMessage(error.getMessage());
                        }
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }



}

