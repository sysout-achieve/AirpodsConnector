package com.gunt.airpodsconnector

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gunt.airpodsconnector.databinding.ItemBluetoothDeviceBinding

class BluetoothListAdapter(private var item: ArrayList<BluetoothDeviceModel>) :
    RecyclerView.Adapter<BluetoothListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBluetoothDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            deviceName.text = item[position].name
            macAddress.text = item[position].macNumber
        }
    }

    class ViewHolder(var binding: ItemBluetoothDeviceBinding) : RecyclerView.ViewHolder(binding.root)
}
