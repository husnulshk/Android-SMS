package com.unpam.sms; 
 
import android.content.BroadcastReceiver; 
import android.content.Context; 
import android.content.Intent; 
import android.os.Bundle; 
import android.telephony.SmsMessage; 
import android.widget.Toast; 
 
public class SmsReceiver extends BroadcastReceiver{ 
 
  @Override
  public void onReceive(Context arg0, Intent arg1) { 
    // TODO Auto-generated method stub 
    Bundle bundle = arg1.getExtras();         
        SmsMessage[] msgs = null; 
        String str = "";    
         
        if (bundle != null){ 
          Object[] pdus = (Object[]) bundle.get("pdus"); 
            msgs = new SmsMessage[pdus.length];             
            for (int i=0; i<msgs.length; i++){ 
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);              
                str += "SMS dari " + msgs[i].getOriginatingAddress();             
                str += " :"; 
                str += msgs[i].getMessageBody().toString(); 
                str += "\n";         
            } 
             
            Toast.makeText(arg0, str, Toast.LENGTH_SHORT).show(); 
        } 
  } 
 
}