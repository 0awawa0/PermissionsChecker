package ru.awawa.permissionschecker.details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import ru.awawa.permissionschecker.R
import ru.awawa.permissionschecker.helper.PermissionModel


class PermissionView(context: Context, attributes: AttributeSet?): ConstraintLayout(context, attributes) {

    private val view = LayoutInflater.from(context).inflate(R.layout.permission_view, this, false)

    private val tvConstant = view.findViewById<TextView>(R.id.tvConstant)
    private val tvWeight = view.findViewById<TextView>(R.id.tvWeight)
    private val tvDescription = view.findViewById<TextView>(R.id.tvDescription)

    var model: PermissionModel = PermissionModel("", 0, "")
        set(value) {
            field = value
            tvConstant.text = value.constant
            tvWeight.text = value.weight.toString()
            tvDescription.text = value.description

            when {
                value.weight > 15 -> tvWeight.setTextColor(ContextCompat.getColor(context, R.color.red_secret_data))
                value.weight > 10 -> tvWeight.setTextColor(
                    ContextCompat.getColor(context, R.color.orange_sensitive_data)
                )
                value.weight > 0 -> tvWeight.setTextColor(ContextCompat.getColor(context, R.color.green_not_secret_data))
                else -> tvWeight.setTextColor(ContextCompat.getColor(context, R.color.blue_no_data))
            }
        }

    constructor(context: Context): this(context, null)

    constructor(context: Context, model: PermissionModel): this(context) {
        this.model = model
    }

    init {
        this.addView(view)
    }
}