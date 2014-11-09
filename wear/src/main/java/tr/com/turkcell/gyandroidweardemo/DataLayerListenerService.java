package tr.com.turkcell.gyandroidweardemo;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by TTYKAFADAR on 18.10.2014.
 */
public class DataLayerListenerService extends WearableListenerService {

    private static final String TAG = "DataLayerListenerService";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED &&
                    event.getDataItem().getUri().getPath().equals("/count")) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                String countValue = dataMapItem.getDataMap().getString("count_key");

                // do something this value
                Log.d(TAG, "Count value:" + countValue);

                // Send value to activity
                Intent intent = new Intent();
                intent.setAction("android_wear_sync");
                intent.putExtra("sync_data", countValue);
                sendBroadcast(intent);
            }
        }
    }
}
