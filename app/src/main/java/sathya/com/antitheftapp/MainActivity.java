package sathya.com.antitheftapp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity  implements  LocationListener{

    TextView tv1, tv2;
    LocationManager locationManager;

    String mess = "dummy", cno = "dummy";
    double lat,alt;
    int  clat,calt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("AntiTheft App");
        mess = "dummy";
        cno = "dummy";

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);


        //Toast.makeText(this, ""+alt+"\n"+lat, Toast.LENGTH_SHORT).show();
        try {
            Bundle b = getIntent().getExtras();
            if ((b != null)) {
                cno = b.getString("k1");
                mess = b.getString("k2");
                tv1.setText(cno);
                tv2.setText(mess);


                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if (mess.equalsIgnoreCase("RING")) {
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                if (mess.equalsIgnoreCase("VIBRATE")) {
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                }

                if (mess.equalsIgnoreCase("SILENT")) {
                    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                int i;

                        if (mess.equalsIgnoreCase("Location")) {
                            sendMessage(mess, cno);
                        }

            } else {

            }
        } catch (Exception e) {
            //Toast.makeText(this, "Message send", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    public void sendMessage(String mess, String cno) {
        Log.e("Send Sms", "");


        location();
        String link="http://www.google.com/maps/place/"+lat+","+alt;
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(cno, null, "your location is "+link, null, null);
    }

    public void location()
    {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }catch (SecurityException se){
            Toast.makeText(this, "Security error", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLocationChanged(Location location)
    {
        lat=location.getLatitude();
        alt=location.getAltitude();
        calt=(int)lat;
        clat=(int)alt;
        Toast.makeText(this, ""+lat+"/n"+alt, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

        Toast.makeText(this, "Turn on GPS", Toast.LENGTH_SHORT).show();
    }
}

