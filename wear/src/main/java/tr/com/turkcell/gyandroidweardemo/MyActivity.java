package tr.com.turkcell.gyandroidweardemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MyActivity extends Activity {

    private static final String SYNC_ACTION = "android_wear_sync";

    private MyReceiver myReceiver;
    private IntentFilter filter;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        filter = new IntentFilter(SYNC_ACTION);
        myReceiver = new MyReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myReceiver);
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mTextView.setText(intent.getStringExtra("sync_data"));
        }

    }
}
