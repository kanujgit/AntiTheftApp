package sathya.com.antitheftapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by Anuj on 22-Aug-17.
 */

public class ReadMessage extends BroadcastReceiver{
    String cno,mess;
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        if (bundle != null)
        {
            Object object[] = (Object[])bundle.get("pdus");

            for (int i=0;i<object.length;i++)
            {
                byte b[] = (byte[])object[i];
                SmsMessage sms = SmsMessage.createFromPdu(b);
                cno = sms.getDisplayOriginatingAddress();
                mess = sms.getDisplayMessageBody();
            }

            Intent i = new Intent();
            i.setClassName("sathya.com.antitheftapp","sathya.com.antitheftapp.MainActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("k1",cno);
            i.putExtra("k2",mess);
            context.startActivity(i);
        }
    }
}
