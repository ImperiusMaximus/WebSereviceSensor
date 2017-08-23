package service.webservice.Kalu.Sensor.com.SensorWebService;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Button  buttonGETandPOST;

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    int whip=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGETandPOST = (Button) findViewById(R.id.button_getandpost);


       buttonGETandPOST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GetAndPostActivity.class);
                startActivity(intent);

                sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
                sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

                if(sensor==null)
                    finish();
                sensorEventListener=new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        float x=sensorEvent.values[0];
                        System.out.println("valor giro"+x);
                        if (x<-5 && whip==0){
                            whip++;
                            getWindow().getDecorView().setBackgroundColor(Color.GREEN);

                        }else if (x>5 && whip==1){
                            whip++;

                            getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                        }
                        if(whip==2){
                            whip=0;
                        }

                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    }
                };start();

        };


        private void start(){
            sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }


});
}
}

