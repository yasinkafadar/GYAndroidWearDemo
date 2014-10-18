package tr.com.turkcell.gyandroidweardemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.view.Menu;
import android.view.MenuItem;


public class NotificationResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_result);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        int notifcationId = getIntent().getIntExtra(Constants.EXTRA_EVENT_ID, 1);
        notificationManager.cancel(notifcationId);
    }


}
