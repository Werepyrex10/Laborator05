package ro.pub.cs.systems.eim.lab05.boundedserviceactivity.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

import ro.pub.cs.systems.eim.lab05.boundedserviceactivity.general.Constants;

public class BoundedService extends Service {

    final private IBinder boundedServiceBinder = new BoundedServiceBinder();
    final private Random random = new Random();
    final private int MAX_RANGE = Constants.MESSAGES.size();

    // TODO: exercise 10a - implement a IBinder public class to provide a reference
    // to the service instance through a getService() method
    public class BoundedServiceBinder extends Binder {

        public BoundedService getService() {
            return BoundedService.this;
        }
    }

    // TODO: exercise 9f - override the lifecycle callback methods and log a message
    // of the moment they are invoked

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Constants.TAG, "onBind() method was invoked");
        return boundedServiceBinder;
    }

    public String getMessage() {
        // TODO: exercise 10b - return a random value from the Constants.MESSAGES array list
        int randValue = random.nextInt(MAX_RANGE);
        return Constants.MESSAGES.get(randValue);
    }
}
