package ru.awawa.permissionschecker.main

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ru.awawa.permissionschecker.helper.AppModel
import ru.awawa.permissionschecker.R


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
        }

    constructor(context: Context): this(context, null)

    constructor(context: Context, model: AppModel): this(context) {
        this.model = model
    }

    init {
        this.addView(view)
    }
}