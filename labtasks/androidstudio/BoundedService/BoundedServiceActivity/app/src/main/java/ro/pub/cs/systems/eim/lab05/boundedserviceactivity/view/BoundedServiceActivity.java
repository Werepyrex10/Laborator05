package ro.pub.cs.systems.eim.lab05.boundedserviceactivity.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Timestamp;

import ro.pub.cs.systems.eim.lab05.boundedserviceactivity.R;
import ro.pub.cs.systems.eim.lab05.boundedserviceactivity.general.Constants;
import ro.pub.cs.systems.eim.lab05.boundedserviceactivity.service.BoundedService;

public class BoundedServiceActivity extends AppCompatActivity {

    private TextView messageFromServiceTextView;
    private Button getMessageFromServiceButton;

    private BoundedService boundedService;
    private int boundedServiceStatus;

    // TODO: exercise 10c - create a ServiceConnection object
    // override methods onServiceConnected() and onServiceDisconnected()
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundedService.BoundedServiceBinder boundedServiceBinder =
                    (BoundedService.BoundedServiceBinder)service;
            boundedService = boundedServiceBinder.getService();
            boundedServiceStatus = Constants.SERVICE_STATUS_BOUND;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            boundedService = null;
            boundedServiceStatus = Constants.SERVICE_STATUS_UNBOUND;
        }
    };

    // TODO: exercise 10e - implement a button click listener for getMessageFromServiceButton

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (boundedService != null && boundedServiceStatus == Constants.SERVICE_STATUS_BOUND) {
                Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
                messageFromServiceTextView.setText("[" + timeStamp
                        + "] " + boundedService.getMessage() + "\n"
                        + messageFromServiceTextView.getText().toString());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounded_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messageFromServiceTextView = (TextView)findViewById(R.id.message_from_service_text_view);
        getMessageFromServiceButton = (Button)findViewById(R.id.get_message_from_service_button);
        // TODO: exercise 10e - set an instance of the button click listener to handle click events
        // for getMessageFromServiceButton
        messageFromServiceTextView = (TextView) findViewById(R.id.message_from_service_text_view);
        getMessageFromServiceButton = (Button) findViewById(R.id.get_message_from_service_button);

        getMessageFromServiceButton.setOnClickListener(new ButtonClickListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // TODO: exercise 10d - bind the service through an intent
        Intent intent = new Intent(this, BoundedService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO: exercise 10d - unbind the service
        if (boundedServiceStatus == Constants.SERVICE_STATUS_BOUND) {
            unbindService(serviceConnection);
            boundedServiceStatus = Constants.SERVICE_STATUS_UNBOUND;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bounded, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
