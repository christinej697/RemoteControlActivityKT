package com.example.remotecontrolactivity

import androidx.appcompat.app.AppCompatActivity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
//import android.os.Handler
import android.os.Message
import android.os.AsyncTask
import android.content.Context
import android.content.Intent
import android.content.BroadcastReceiver

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket

import android.telephony.TelephonyManager
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.remotecontrolactivitykt.R

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Timer
import java.util.TimerTask
import java.util.ArrayList
import java.util.UUID
import java.util.concurrent.*
import java.util.logging.Handler
//import javax.swing.text.html.ListView

class MainActivity : AppCompatActivity() {

    //internal var forBtn: Button
    //internal var backBtn: Button
    //internal var leftBtn: Button
    //internal var rightBtn: Button
    //internal var stopBtn: Button
    internal var mBluetoothAdapter: BluetoothAdapter? = null
    internal var mBluetoothDevice: BluetoothDevice? = null
    internal var mBluetoothSocket: BluetoothSocket? = null
    internal var lv_paired_devices: ListView? = null
    //ConnectedThread c;

    private var connected = false
    private val myHandler: android.os.Handler? = null
    // String buffer for outgoing messages
    private val mOutStringBuffer: StringBuffer? = null
    internal var bluetoothMSG: String? = null

    private var registered = false
    private val mAddress: String? = null

    internal var bluetoothdata = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()  //get a handle to default local Bluetooth adapter
        //if the adpater is null then bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Device doesn't support Bluetooth", Toast.LENGTH_LONG).show()
            finish()
        }
        /*/  else if (!mBluetoothAdapter.isEnabled()) {
            //Ask user to turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, BT_ACTIVATE_REQUEST);
            System.out.println("Bluetooth Enabled");
        }*/

        try {
            if (mBluetoothSocket == null || !connected) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()//get the mobile bluetooth device
                val device = mBluetoothAdapter!!.getRemoteDevice(mAddress)//connects to the device's address and checks if it's available
                //TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                //String uuid = tManager.getDeviceId();
                mBluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID)//create a RFCOMM (SPP) connection
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                mBluetoothSocket!!.connect()//start connection
            }
        } catch (e: IOException) {
            connected = false//if the try failed, you can check the exception here
        }

        //Handler handler = new myOwnHandler(bluetoothMSG,bluetoothdata);

        //private static class myOwnHandler extends Handler{

        val myHandler = object : android.os.Handler() {
            //@Override
            fun handleMsg(msg: Message) {
                if (msg.what === MESSAGE_READ) {
                    val recilvdata = msg.obj as String
                    bluetoothdata.append(recilvdata)
                }
                bluetoothdata.delete(0, bluetoothdata.length)
            }
        }

        var forBtn = findViewById(R.id.forButton) as Button           //these are onTouch
        var backBtn = findViewById(R.id.backButton) as Button
        var leftBtn = findViewById(R.id.leftButton) as Button
        var rightBtn = findViewById(R.id.rightButton) as Button
        var stopBtn = findViewById(R.id.stopButton) as Button          //this is onClick

        forBtn.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.getAction() === android.view.MotionEvent.ACTION_DOWN) {
                    v.setBackgroundColor(Color.GREEN)
                    //c.write("F");
                } else if (event.getAction() === android.view.MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(Color.RED)
                }
                return false
            }
        })

        backBtn.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.getAction() === android.view.MotionEvent.ACTION_DOWN) {
                    v.setBackgroundColor(Color.GREEN)
                    //c.write("B");
                } else if (event.getAction() === android.view.MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(Color.RED)
                }
                return false
            }
        })

        leftBtn.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.getAction() === android.view.MotionEvent.ACTION_DOWN) {
                    v.setBackgroundColor(Color.GREEN)
                } else if (event.getAction() === android.view.MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(Color.RED)
                }
                return false
            }
        })

        rightBtn.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.getAction() === android.view.MotionEvent.ACTION_DOWN) {
                    v.setBackgroundColor(Color.GREEN)
                } else if (event.getAction() === android.view.MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(Color.RED)
                }
                return false
            }
        })

        stopBtn.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.getAction() === android.view.MotionEvent.ACTION_DOWN) {
                    v.setBackgroundColor(Color.GREEN)
                } else if (event.getAction() === android.view.MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(Color.RED)
                }
                return false
            }
        })


    }

    private class myOwnHandler : android.os.Handler() {
        //myHandler = new Handler() {
        //   //@Override
        fun myOwnHandler(msg: Message, bluetoothdata: StringBuilder) {
            if (msg.what === MESSAGE_READ) {
                val recilvdata = msg.obj as String
                bluetoothdata.append(recilvdata)
            }
            bluetoothdata.delete(0, bluetoothdata.length)
        }
    }

    override fun onStart() {
        super.onStart()
        if (!mBluetoothAdapter!!.isEnabled()) {
            //Ask user to turn the bluetooth on
            val turnBTon = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(turnBTon, BT_ACTIVATE_REQUEST)
            println("Bluetooth Enabled")
        }
    }

    private fun ensureDiscoverable() {
        if (mBluetoothAdapter!!.getScanMode() !== BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            startActivity(discoverableIntent)
        }
    }

    private inner class ConnectedThread(private val mmSocket: BluetoothSocket) : Thread() {
        private val mmInStream: InputStream?
        private val mmOutStream: OutputStream?

        /* Call this from the main activity to shutdown the connection
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }*/
        private val mReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.getAction()

                if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                    val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                    val intent1 = Intent(this@MainActivity, MainActivity::class.java)

                    when (state) {
                        BluetoothAdapter.STATE_OFF -> {
                            if (registered) {
                                unregisterReceiver(this)
                                registered = false
                            }
                            startActivity(intent1)
                            finish()
                        }
                        BluetoothAdapter.STATE_TURNING_OFF -> {
                            if (registered) {
                                unregisterReceiver(this)
                                registered = false
                            }
                            startActivity(intent1)
                            finish()
                        }
                    }
                }
            }
        }

        init {
            var tmpIn: InputStream? = null
            var tmpOut: OutputStream? = null

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = mmSocket.getInputStream()
                tmpOut = mmSocket.getOutputStream()
            } catch (e: IOException) {
            }

            mmInStream = tmpIn
            mmOutStream = tmpOut
        }

        override fun run() {

            val buffer = ByteArray(1024)  // buffer store for the stream
            var bytes: Int // bytes returned from read()


            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream!!.read(buffer)

                    val btdata = String(buffer, 0, bytes)

                    // Send the obtained bytes to the UI activity
                    myHandler!!.obtainMessage(MESSAGE_READ, bytes, -1, btdata).sendToTarget()
                } catch (e: IOException) {
                    break
                }

            }
        }

        /* Call this from the main activity to send data to the remote device */
        fun write(output: String) {
            val msgBuffer = output.toByteArray()
            try {
                mmOutStream!!.write(msgBuffer)
            } catch (e: IOException) {
            }

        }
    }

    companion object {
        val myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        //intent request codes
        private val BT_ACTIVATE_REQUEST = 1
        private val BT_CONNECT_REQUEST = 2
        private val MESSAGE_READ = 3
        private val MESSAGE_WRITE = 0
        val CONNECTING = 4
        val CONNECTED = 5
        val NO_SOCKET_FOUND = 6
        // Key names received from the BluetoothChatService Handler
        val DEVICE_NAME = "device_name"
        val TOAST = "toast"
    }

}
