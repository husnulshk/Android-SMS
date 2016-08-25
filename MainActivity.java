package com.unpam.sms; 
 
import android.os.Bundle; 
import android.app.Activity; 
import android.app.PendingIntent; 
import android.content.BroadcastReceiver; 
import android.content.Context; 
import android.content.Intent; 
import android.content.IntentFilter; 
import android.telephony.SmsManager; 
import android.view.Menu; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.EditText; 
import android.widget.Toast; 
 
public class MainActivity extends Activity implements OnClickListener{ 
  EditText tujuanEditText; 
  EditText pesanEditText; 
 
  @Override 
  protected void onCreate(Bundle savedInstanceState) { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.activity_main); 
     
    findViewById(R.id.kirimButton).setOnClickListener(this); 
    findViewById(R.id.tutupButton).setOnClickListener(this); 
     
    tujuanEditText = (EditText) 
findViewById(R.id.tujuanEditText); 
    pesanEditText = (EditText) findViewById(R.id.pesanEditText); 
  } 
 
  @Override 
  public boolean onCreateOptionsMenu(Menu menu) { 
    // Inflate the menu; this adds items to the action bar if it 
is present. 
    getMenuInflater().inflate(R.menu.activity_main, menu); 
    return true; 
  } 
 
  @Override 
  public void onClick(View v) { 
    // TODO Auto-generated method stub 
    switch (v.getId()){ 
    case R.id.kirimButton: 
      String tujuan = tujuanEditText.getText().toString(); 
          String pesan = pesanEditText.getText().toString();             
   
            if (tujuan.length()>0 && pesan.length()>0){                 
                sendSMS(tujuan, pesan);                 
            } else{ 
              Toast.makeText(getBaseContext(),"No. tujuan dan pesan 
harus diisi",Toast.LENGTH_SHORT).show(); 
            } 
      break;
	  case R.id.tutupButton: 
      System.exit(0); 
    } 
  } 
 
  private void sendSMS(String phoneNumber, String message){ 
    String SENT = "SMS_SENT"; 
     String DELIVERED = "SMS_DELIVERED"; 
      
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new 
Intent(SENT), 0); 
         
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, 
new Intent(DELIVERED), 0); 
         
        registerReceiver(new BroadcastReceiver(){ 
      @Override 
      public void onReceive(Context arg0, Intent arg1) { 
        switch (getResultCode()) 
        { 
            case Activity.RESULT_OK: 
              Toast.makeText(getBaseContext(), "SMS 
telah dikirim", Toast.LENGTH_SHORT).show(); 
              break; 
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE: 
              Toast.makeText(getBaseContext(), 
"Generic failure", Toast.LENGTH_SHORT).show(); 
              break; 
            case SmsManager.RESULT_ERROR_NO_SERVICE: 
              Toast.makeText(getBaseContext(), "No 
service", Toast.LENGTH_SHORT).show(); 
              break; 
            case SmsManager.RESULT_ERROR_NULL_PDU: 
              Toast.makeText(getBaseContext(), "Null 
PDU", Toast.LENGTH_SHORT).show(); 
              break; 
            case SmsManager.RESULT_ERROR_RADIO_OFF: 
              Toast.makeText(getBaseContext(), "Radio 
off", Toast.LENGTH_SHORT).show(); 
              break; 
        } 
      } 
        }, new IntentFilter(SENT)); 
         
        registerReceiver(new BroadcastReceiver(){ 
      @Override 
      public void onReceive(Context arg0, Intent arg1) { 
        switch (getResultCode()) 
        { 
            case Activity.RESULT_OK: 
              Toast.makeText(getBaseContext(), "SMS 
Terkirim", Toast.LENGTH_SHORT).show(); 
              break; 
            case Activity.RESULT_CANCELED: 
              Toast.makeText(getBaseContext(), "SMS 
Tidak Terkirim", Toast.LENGTH_SHORT).show(); 
              break;               
        } 
      } 
        }, new IntentFilter(DELIVERED));
		SmsManager sms = SmsManager.getDefault(); 
        sms.sendTextMessage(phoneNumber, null, message, sentPI, 
deliveredPI); 
  } 
}