package ro.pub.cs.systems.eim.lab05.startedserviceactivity.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import ro.pub.cs.systems.eim.lab05.startedserviceactivity.general.Constants;

public class StartedServiceBroadcastReceiver extends BroadcastReceiver {

    private TextView messageTextView;
    private Intent startServiceActivityIntent;

    // TODO: exercise 8 - default constructor

    public StartedServiceBroadcastReceiver () {
    }

    public StartedServiceBroadcastReceiver(TextView messageTextView) {
        this.messageTextView = messageTextView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: exercise 7 - get the action and the extra information from the intent
        // and set the text on the messageTextView

        Object data = null;

        String action = intent.getAction();

        switch (action) {
            case Constants.ACTION_STRING :
                data = intent.getStringExtra(Constants.DATA);
                break;
            case Constants.ACTION_INTEGER:
                data = intent.getIntExtra(Constants.DATA, 0);
                break;
            case Constants.ACTION_ARRAY_LIST :
                data = intent.getStringArrayListExtra(Constants.DATA);
                break;
        }

        if (messageTextView != null) {
            if (data != null) {
                StringBuilder stringBuilder = new StringBuilder(messageTextView.getText());
                stringBuilder.append('\n');
                String text = data.toString();
                if (text.length() > 50)
                    text = "";
                stringBuilder.append(text);
                messageTextView.setText(stringBuilder.toString());
            }
        }
        else {
            startServiceActivityIntent = new Intent(context, StartedServiceActivity.class);
            startServiceActivityIntent.putExtra(Constants.MESSAGE, data.toString());
            startServiceActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(startServiceActivityIntent);
        }

        // TODO: exercise 9 - restart the activity through an intent
        // if the messageTextView is not available
        //trebuie sa declar receiverul si in manifest si sa adaug constructor default
    }

}
