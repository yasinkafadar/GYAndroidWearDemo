package tr.com.turkcell.gyandroidweardemo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;


public class MyActivity extends Activity {

    private static final String TAG = "MyActivity";

    private GoogleApiClient mGoogleAppiClient;
    private EditText edtSendData;
    private Button btnSyncData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        edtSendData = (EditText) findViewById(R.id.edt_send_data);
        btnSyncData = (Button) findViewById(R.id.btn_sync_data);
        btnSyncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        createGoogleApiClient();
    }

    public void showNotification(View view){
        int id = view.getId();
        switch (id){
            case R.id.btn_create_notification:
                createNotification();
                break;
            case R.id.btn_create_notification_large_icon:
                createNotificationWithLargeIcon();
                break;
            case R.id.btn_create_notification_with_action:
                createNotificationWithAction();
                break;
            case R.id.btn_create_notification_wear_extender:
                createNotificationWithWearAction();
                break;
            case R.id.btn_create_big_text:
                createBigTextNotification();
                break;
            case R.id.btn_create_choice_notification:
                createChoiceNotification();
                break;
        }
    }

    // show classical notification only title and content with app icon
    private void createNotification() {
        int notificationId = 1;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, NotificationResultActivity.class);
        viewIntent.putExtra(Constants.EXTRA_EVENT_ID, notificationId);
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getString(R.string.title))
                        .setContentText(getString(R.string.content))
                        .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    // show classical notification title, content, large icon and app icon
    private void createNotificationWithLargeIcon() {
        int notificationId = 2;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, NotificationResultActivity.class);
        viewIntent.putExtra(Constants.EXTRA_EVENT_ID, notificationId);
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.drawable.ic_sun)) // set large icon to show bg image on wear
                        .setContentTitle(getString(R.string.weather_title))
                        .setContentText(getString(R.string.weather_content))
                        .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    // show classical notification title, content, large icon, app icon and action
    private void createNotificationWithAction() {
        int notificationId = 3;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, NotificationReplyActivity.class);
        viewIntent.putExtra(Constants.EXTRA_EVENT_ID, notificationId);
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.drawable.avatar))
                        .setContentTitle(getString(R.string.title))
                        .setContentText(getString(R.string.content))
                        .addAction(R.drawable.ic_action_reply, getString(R.string.reply), viewPendingIntent)
                        .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    // show classical notification title, content, large icon, app icon, action and wear specific action
    private void createNotificationWithWearAction() {
        int notificationId = 4;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, NotificationResultActivity.class);
        viewIntent.putExtra(Constants.EXTRA_EVENT_ID, notificationId);
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create wear specific action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_action_remove,
                        getString(R.string.remove), viewPendingIntent)
                        .build();

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.drawable.avatar))
                        .setContentTitle(getString(R.string.title))
                        .setContentText(getString(R.string.content))
                        .addAction(R.drawable.ic_action_reply, getString(R.string.reply), viewPendingIntent)
                        .extend(new NotificationCompat.WearableExtender().addAction(action))
                        .setContentIntent(viewPendingIntent);

        notificationBuilder.setAutoCancel(true);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    // show classical notification only title and content with app icon
    private void createBigTextNotification() {
        int notificationId = 5;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, NotificationResultActivity.class);
        viewIntent.putExtra(Constants.EXTRA_EVENT_ID, notificationId);
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText(getString(R.string.big_text_content));

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getString(R.string.title))
                        .setContentText(getString(R.string.content))
                        .setStyle(bigStyle)
                        .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private void createChoiceNotification() {
        int notificationId = 6;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, NotificationReplyActivity.class);
        viewIntent.putExtra(Constants.EXTRA_EVENT_ID, notificationId);
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput remoteInput = new RemoteInput.Builder(Constants.EXTRA_VOICE_REPLY)
                .setLabel(getString(R.string.reply_title))
                .setChoices(getResources().getStringArray(R.array.reply_choices))
                .build();

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_action_reply,
                        getString(R.string.reply), viewPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.drawable.avatar))
                        .setContentTitle(getString(R.string.title))
                        .setContentText(getString(R.string.content))
                        .extend(new NotificationCompat.WearableExtender().addAction(action))
                        .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private void createGoogleApiClient(){
        mGoogleAppiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "onConnected: " + connectionHint);
                        // Now you can use the data layer API
                        edtSendData.setVisibility(View.VISIBLE);
                        btnSyncData.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                    }
                })
                .addApi(Wearable.API)
                .build();
    }

    private void sendData(){
        PutDataMapRequest dataMap = PutDataMapRequest.create("/count");
        dataMap.getDataMap().putString("count_key", edtSendData.getText().toString());
        PutDataRequest request = dataMap.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi
                .putDataItem(mGoogleAppiClient, request);

        // Asynchronously waiting
        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(final DataApi.DataItemResult result) {
                if(result.getStatus().isSuccess()) {
                    Log.d(TAG, "Data item set: " + result.getDataItem().getUri());
                }
            }
        });

        // Synchronously waiting
        /*DataApi.DataItemResult result = pendingResult.await();
        if(result.getStatus().isSuccess()) {
            Log.d(TAG, "Data item set: " + result.getDataItem().getUri());
        } */
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleAppiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleAppiClient.disconnect();
    }
}
