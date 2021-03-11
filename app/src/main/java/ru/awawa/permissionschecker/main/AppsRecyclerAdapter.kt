package ru.awawa.permissionschecker.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.awawa.permissionschecker.helper.AppModel
import ru.awawa.permissionschecker.R
import ru.awawa.permissionschecker.RecyclerItemEventListener

class AppsRecyclerAdapter(private val eventListener: RecyclerItemEventListener)
    : RecyclerView.Adapter<AppsRecyclerAdapter.AppViewHolder>()
{

    var dataList: List<AppModel>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = dataList?.get(position) ?: return
        holder.view.model = app
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.app_view_row, parent, false) as AppView
        return AppViewHolder(view)
    }

    inner class AppViewHolder(val view: AppView): RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener { eventListener.onClick(view) }
        }
    }
}