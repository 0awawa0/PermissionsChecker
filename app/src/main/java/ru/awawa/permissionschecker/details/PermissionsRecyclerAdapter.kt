package ru.awawa.permissionschecker.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.awawa.permissionschecker.R
import ru.awawa.permissionschecker.helper.PermissionModel


class PermissionsRecyclerAdapter: RecyclerView.Adapter<PermissionsRecyclerAdapter.PermissionViewHolder>() {

    var dataList: List<PermissionModel>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: PermissionViewHolder, position: Int) {
        val permission = dataList?.get(position) ?: return
        holder.view.model = permission
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermissionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.permission_view_row, parent, false) as PermissionView
        return PermissionViewHolder(view)
    }
    inner class PermissionViewHolder(val view: PermissionView): RecyclerView.ViewHolder(view)
}