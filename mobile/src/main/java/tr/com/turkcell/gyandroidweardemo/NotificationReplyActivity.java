package tr.com.turkcell.gyandroidweardemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import static tr.com.turkcell.gyandroidweardemo.Constants.EXTRA_VOICE_REPLY;


public class NotificationReplyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_reply);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        int notifcationId = getIntent().getIntExtra(Constants.EXTRA_EVENT_ID, 1);
        notificationManager.cancel(notifcationId);

        EditText edtReply = (EditText)findViewById(R.id.edt_reply);
        edtReply.setText(getMessageText(getIntent()));
    }


    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(Constants.EXTRA_VOICE_REPLY);
        }
        return "";
    }

}
