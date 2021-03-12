package ru.awawa.permissionschecker.main

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import ru.awawa.permissionschecker.helper.AppModel
import ru.awawa.permissionschecker.R
import ru.awawa.permissionschecker.helper.PermissionsHelper


class AppView(context: Context, attributes: AttributeSet?): ConstraintLayout(context, attributes) {

    private val view = LayoutInflater.from(context).inflate(R.layout.app_view, this, false)

    private val tvAppName = view.findViewById<TextView>(R.id.tvAppName)
    private val tvIndex = view.findViewById<TextView>(R.id.tvIndex)

    var model: AppModel =
        AppModel(0, "", "", emptyList(), 0)
        set(value) {
            field = value
            id = value.id
            tvAppName.text = value.name
            tvIndex.text = value.index.toString()
            var dangerousCount = 0
            var sensitiveCount = 0
            var regularCount = 0

            value.permissions.forEach {
                val weight = PermissionsHelper.getPermissionWeight(it)
                when {
                    weight > 15 -> dangerousCount++
                    weight > 10 -> sensitiveCount++
                    weight > 0 -> regularCount++
                }
            }

            val danger = 1.5 * dangerousCount + 0.25 * sensitiveCount + 0.075 * regularCount
            when {
                danger > 5 -> tvIndex.setTextColor(ContextCompat.getColor(context, R.color.red_secret_data))
                danger > 3 -> tvIndex.setTextColor(ContextCompat.getColor(context, R.color.orange_sensitive_data))
                danger > 1 -> tvIndex.setTextColor(ContextCompat.getColor(context, R.color.green_not_secret_data))
                else -> tvIndex.setTextColor(ContextCompat.getColor(context, R.color.blue_no_data))
            }
        }

    constructor(context: Context): this(context, null)

    constructor(context: Context, model: AppModel): this(context) {
        this.model = model
    }

    init {
        this.addView(view)
    }
}