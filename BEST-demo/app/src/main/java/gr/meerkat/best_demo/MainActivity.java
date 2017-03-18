package gr.meerkat.best_demo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private final String TAG = this.getClass().getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Switch mSwitch;
    //Accelerometer axis
    private float x = 0;
    private float y = 0;
    private float z = 0;
    private float lastX = 0;
    private float lastY = 0;
    private float lastZ = 0;
    private float force = 0;
    private float forceThreshold = 5.0f;
    //Time deltas
    private long now = 0;
    private long timeDiff = 0;
    private long previous = 0;
    //The vibration object
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        //Initialize Android API sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Initialize the accelerometer sensor
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Initialize the vibrator service
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mSwitch = (Switch) findViewById(R.id.switchview);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        //Before registration the Sensor Events are not getting triggered
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        mSwitch.setChecked(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        //Stop getting sensor events
        mSensorManager.unregisterListener(this, mAccelerometer);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Log.d(TAG,"onSensorChanged");

        //Read x,y,z acceleration
        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];
        now = System.currentTimeMillis();

        if (previous == 0) { //First run of accelerometer
            previous = now;
            lastX = x;
            lastY = y;
            lastZ = z;

        } else {
            timeDiff = now - previous;

            force = Math.abs(x + y + z - lastX - lastY - lastZ);

            if (Float.compare(force, forceThreshold) > 0) {
                Log.d(TAG, "Movement Detected: (" + x + ", " + y + ", " + z + ")" + " force: " + force + " after: " + timeDiff + "ms");
                previous = now;
                lastX = x;
                lastY = y;
                lastZ = z;

                if(mSwitch.isChecked()) {
                    vibrator.vibrate(500);
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged");
    }
}
