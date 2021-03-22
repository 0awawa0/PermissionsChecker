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
        AppModel(0, "", "", emptyList(), 0, 0.0f)
        set(value) {
            field = value
            id = value.id
            tvAppName.text = value.name
            tvIndex.text = value.index1.toString()

            when {
                value.index2 > 5.5 -> tvIndex.setTextColor(ContextCompat.getColor(context, R.color.red_secret_data))
                value.index2 > 3 -> tvIndex.setTextColor(ContextCompat.getColor(context, R.color.orange_sensitive_data))
                else -> tvIndex.setTextColor(ContextCompat.getColor(context, R.color.green_not_secret_data))
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
