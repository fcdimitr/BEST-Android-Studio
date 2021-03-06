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
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private final String TAG = this.getClass().getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    // View related
    private Switch mSwitch;
    private TextView forceValue;

    // Sensor related
    private float lastX = 0;
    private float lastY = 0;
    private float lastZ = 0;
    private static final float forceThreshold = 70.0f;
    private long previous = 0;

    //The vibration object
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        // Initialize Android API sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Initialize the accelerometer sensor
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Initialize the vibrator service
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Find the switch in the view
        mSwitch = (Switch) findViewById(R.id.switchview);

        // Find the force value TextField
        forceValue = (TextView) findViewById(R.id.force_value);

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
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        long now = System.currentTimeMillis();

        if (previous == 0) { //First run of accelerometer
            previous = now;
            lastX = x;
            lastY = y;
            lastZ = z;

        } else {

            // get time difference
            long timeDiff = now - previous;

            // find force value by F = sqrt( (dx)^2 + (dy)^2 + (dz)^2 )
            float force = Math.abs( (x - lastX) * (x - lastX) +
                                    (y - lastY) * (y - lastY) +
                                    (z - lastZ) * (z - lastZ) );

            // update text view
            forceValue.setText( String.format( Locale.US, "%.02f", force) );

            // update last values
            lastX = x;
            lastY = y;
            lastZ = z;

            if (Float.compare(force, forceThreshold) > 0) {

                String movementLog =
                        String.format( Locale.US,
                                "Movement Detected: (%.2f, %.2f, %.2f) force: %.2f after: %d ms",
                                x, y, z, force, timeDiff);

                Log.d(TAG, movementLog);
                previous = now;

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
