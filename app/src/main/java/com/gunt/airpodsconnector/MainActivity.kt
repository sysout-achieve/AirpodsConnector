package com.gunt.airpodsconnector

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gunt.airpodsconnector.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BluetoothConnector {

    lateinit var binding: ActivityMainBinding

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private var itemList = ArrayList<BluetoothDeviceModel>()

    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        viewAdapter = BluetoothListAdapter(itemList){
            Toast.makeText(baseContext, "model num : ${it.macNumber}", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = viewAdapter
        }

        binding.btnStart.setOnClickListener { getBluetoothDeviceList() }
    }


    override fun getBluetoothDeviceList() {
        itemList.clear()
        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
            itemList.add(BluetoothDeviceModel(deviceName, deviceHardwareAddress))
        }
        viewAdapter.notifyDataSetChanged()
    }
}