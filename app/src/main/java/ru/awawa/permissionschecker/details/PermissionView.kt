package ru.awawa.permissionschecker.details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ru.awawa.permissionschecker.R
import ru.awawa.permissionschecker.helper.AppModel
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
        }

    constructor(context: Context): this(context, null)

    constructor(context: Context, model: PermissionModel): this(context) {
        this.model = model
    }

    init {
        this.addView(view)
    }
}