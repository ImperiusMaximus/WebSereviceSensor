package service.webservice.Kalu.Sensor.com.SensorWebService;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GetAndPostActivity extends AppCompatActivity {

    Button botonGet;
    TextView textView;
    ProgressBar progressBar;
    String method;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_and_post);

        botonGet = (Button) findViewById(R.id.botonget);
        //botonPost = (Button) findViewById(R.id.botonpost);

        textView = (TextView) findViewById(R.id.textview);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        final SensorManager[] sensorManager = new SensorManager[1];
        final Sensor[] sensor = new Sensor[1];
        final SensorEventListener[] sensorEventListener = new SensorEventListener[1];
        final int[] whip = {0};
        sensorManager[0] =(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor[0] = sensorManager[0].getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        botonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(sensor[0] ==null)
                    finish();
                sensorEventListener[0] =new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        float x=sensorEvent.values[0];
                        System.out.println("valor giro"+x);
                        if (x<-5 && whip[0] ==0){
                            whip[0]++;
                            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                            method= "POST";
                            pedirDatosPost("http://maloschistes.com/maloschistes.com/jose/webservicesend.php"); // Web service enviando GET y POST

                        }else if (x>5 && whip[0] ==1){
                            whip[0]++;
                            method = "GET";
                           // pedirDatosGet("http://maloschistes.com/maloschistes.com/jose/webservicesend.php"); // Web service enviando GET y POST
                            pedirDatosGet("https://www.facebook.com"); // Web service enviando GET y POST
                            getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                        }
                        if(whip[0] ==2){
                            whip[0] =0;
                        }

                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    }
                };sensorManager[0].registerListener(sensorEventListener[0], sensor[0],SensorManager.SENSOR_DELAY_NORMAL);



                if(isOnLine()){


                }else{
                    Toast.makeText(getApplicationContext(), "Sin conexión", Toast.LENGTH_SHORT).show();
                }






    }

    public void cargarDatos(String datos){
        textView.append(datos+"\n"); // Unimos los datos (lo que sería la URL con los parametros GET)

    }

    public boolean isOnLine(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }

    public void pedirDatosGet(String uri){
        MyTask task = new MyTask();
        //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
       // task.execute(uri);
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod(method);
        requestPackage.setUri(uri);

        requestPackage.setParam("parametro2", "Izquierda");


        task.execute(requestPackage);

    }
            public void pedirDatosPost(String uri){
                MyTask task = new MyTask();
           //     task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
         //       task.execute(uri);
                RequestPackage requestPackage = new RequestPackage();
                requestPackage.setMethod(method);
                requestPackage.setUri(uri);
                requestPackage.setParam("parametro1", "Derecha");


                task.execute(requestPackage);

            }
    class MyTask extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(RequestPackage... params) { // Recibimos un RequestPackage

            String content = HttpManager.getData(params[0]); //Cadena de contenido. Sin seguridad
            return content;

        }

        @Override
        protected void onPostExecute(String result) { // result es el resultado de doInBackground
            super.onPostExecute(result);

            if(result == null){

                Toast.makeText(GetAndPostActivity.this, "No se pudo conectar", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                return;

            }

            cargarDatos(result);
            progressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            // nothing
        }
    }


        });

    }
}
