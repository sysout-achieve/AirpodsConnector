package com.gunt.airpodsconnector

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.tlgbltcn.library.BluetoothHelper
import c.tlgbltcn.library.BluetoothHelperListener
import com.gunt.airpodsconnector.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BluetoothHelperListener {

    lateinit var binding: ActivityMainBinding

    private lateinit var bluetoothHelper: BluetoothHelper
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private var itemList = ArrayList<BluetoothDeviceModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        bluetoothHelper = BluetoothHelper(this@MainActivity, this@MainActivity)
            .setPermissionRequired(true)
            .create()

        if (bluetoothHelper.isBluetoothEnabled()) binding.enableDisable.text = "Bluetooth State Off"
        else binding.enableDisable.text = "Bluetooth State On"

        if (bluetoothHelper.isBluetoothScanning()) binding.startStop.text = "Stop discovery"
        else binding.startStop.text = "Start discovery"


        binding.enableDisable.setOnClickListener {
            if (bluetoothHelper.isBluetoothEnabled()) {
                bluetoothHelper.disableBluetooth()
            } else {
                bluetoothHelper.enableBluetooth()
            }
        }

        binding.startStop.setOnClickListener {
            if (bluetoothHelper.isBluetoothScanning()) {
                bluetoothHelper.stopDiscovery()
            } else {
                bluetoothHelper.startDiscovery()
            }
        }

        viewAdapter = BluetoothListAdapter(itemList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = viewAdapter
        }
    }

    override fun onStartDiscovery() {
        binding.startStop.text = "Stop discovery"
    }

    override fun onFinishDiscovery() {
        binding.startStop.text = "Start discovery"
        itemList.clear()
    }

    override fun onEnabledBluetooth() {
        binding.enableDisable.text = "Bluetooth State Off"
    }

    override fun onDisabledBluetooh() {
        binding.enableDisable.text = "Bluetooth State On"
    }

    override fun getBluetoothDeviceList(device: BluetoothDevice) {
        itemList.add(BluetoothDeviceModel(device.name, device.address))
        viewAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        bluetoothHelper.registerBluetoothStateChanged()
    }

    override fun onStop() {
        super.onStop()
        bluetoothHelper.unregisterBluetoothStateChanged()
    }
}