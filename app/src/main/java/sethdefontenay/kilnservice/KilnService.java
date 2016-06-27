package sethdefontenay.kilnservice;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

public class KilnService extends Service {
    public BluetoothAdapter adapter;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        //Connect to Bluetooth here
        ConnectBluetoothDevice();
        //Get maxtemp from website

        //Post temp to website

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    public void ConnectBluetoothDevice(){
        String status = "";
        String address = "test mac address";
        UUID MY_UUID = new UUID(10,30);

        adapter = BluetoothAdapter.getDefaultAdapter();
        if(adapter != null) {
            if (adapter.isEnabled()) {
                String mydeviceaddress = adapter.getAddress();
                String mydevicename = adapter.getName();
                status = mydevicename + " : " + mydeviceaddress;
            }
            else
            {
                status = "Bluetooth is not Enabled.";
            }

            Toast.makeText(this, status, Toast.LENGTH_LONG).show();
        }

        BluetoothDevice device = adapter.getRemoteDevice(address);
        BluetoothSocket tmp = null;
        BluetoothSocket mmSocket = null;

        // Get a BluetoothSocket for a connection with the
        // given BluetoothDevice
        try {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
            tmp = (BluetoothSocket) m.invoke(device, 1);
        } catch (IOException e) {
            Log.e(TAG, "create() failed", e);
        }
        mmSocket = tmp;

    }
}
