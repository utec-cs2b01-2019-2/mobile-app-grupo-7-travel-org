package cs2b01.utec.chat_mobile;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ExperienciasAdapter extends RecyclerView.Adapter<ExperienciasAdapter.ViewHolder> {
    public JSONArray elements;
    private Context context;
    public int userFromId;


    public ExperienciasAdapter(JSONArray elements, Context context, int userFromId){
        this.elements = elements;
        this.context = context;
        this.userFromId = userFromId;
    }

    public void showMessage(String message) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, descripcion, precio;
        RelativeLayout container;
        Button boton;


        public ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            descripcion = itemView.findViewById(R.id.descripcion);
            precio = itemView.findViewById(R.id.precio);
            container = itemView.findViewById(R.id.element_view_container);
            boton = itemView.findViewById(R.id.btnAdd);
        }
    }

    @NonNull
    @Override
    public ExperienciasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_view_add,parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ExperienciasAdapter.ViewHolder holder, int position) {
        try {
            JSONObject element = elements.getJSONObject(position);
            final int id_exp = element.getInt("id");
            final String titulo = element.getString("titulo");
            final String descripcion = element.getString("descripcion");
            final String precio = element.getString("precio")+" soles";
            holder.titulo.setText(titulo);
            holder.descripcion.setText(descripcion);
            holder.precio.setText(precio);



            holder.boton.setOnClickListener(new View.OnClickListener(){


                @Override
                public void onClick(View v) {
                    AddExperiencia(id_exp, titulo,userFromId);
                }
            });



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return elements.length();
    }


    public void AddExperiencia(int id_experiencia, final String titulo, int user_id){

        //2.  Creating a message using user input
        Map<String, Integer> message = new HashMap<>();
        message.put("id_experiencia", id_experiencia);
        message.put("id_viajero", user_id);
        message.put("id_guia", 0);
        //3.  Converting the message object to JSON string (jsonify)
        JSONObject jsonMessage = new JSONObject(message);
        //Toast.makeText(this,jsonMessage.toString(),Toast.LENGTH_LONG).show();

        //4.  Sending json message to the server
        //4.1. Install volley
        //4.2. Create request object
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://travelorg2.herokuapp.com/itinerarioAdd",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO Qué hacer cuando el server responda
                        showMessage(titulo + " agregado correctamente!");
                        try {
                            String mensaje = response.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO Qué hacer cuando ocurra un error
                        showMessage("Ya está agregado "+titulo+", escoge otra!!!");
                    }
                }
        );

        //5. Send Request to the Server
        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.add(request);

    }


}

