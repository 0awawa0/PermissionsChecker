package ru.awawa.permissionschecker.details


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.awawa.permissionschecker.CustomLayoutManager
import ru.awawa.permissionschecker.R
import ru.awawa.permissionschecker.helper.PermissionModel
import ru.awawa.permissionschecker.helper.PermissionsHelper
import java.util.*

class AppDetailsActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_APP_NAME = "APP_NAME"
        const val EXTRA_PACKAGE_NAME = "PACKAGE_NAME"
        const val EXTRA_INDEX = "INDEX"
        const val EXTRA_PERMISSIONS = "PERMISSIONS"
        const val EXTRA_DANGER_LEVEL = "DANGER_LEVEL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        val appName = intent.getStringExtra(EXTRA_APP_NAME)
        val packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME)
        val index = intent.getIntExtra(EXTRA_INDEX, 0)
        val permissions = intent.getStringArrayExtra(EXTRA_PERMISSIONS) ?: emptyArray()
        val danger = intent.getDoubleExtra(EXTRA_DANGER_LEVEL, 0.0)

        findViewById<TextView>(R.id.tvAppName).text = appName
        findViewById<TextView>(R.id.tvPackageName).text = packageName
        findViewById<TextView>(R.id.tvIndex).text = index.toString()

        findViewById<TextView>(R.id.tvIndex).setTextColor(
            ContextCompat.getColor(this, when {
                danger > 5 -> R.color.red_secret_data
                danger > 3 -> R.color.orange_sensitive_data
                danger > 1 -> R.color.green_not_secret_data
                else -> R.color.blue_no_data
            })
        )
        val rvPermissions = findViewById<RecyclerView>(R.id.rvPermissions)
        rvPermissions.layoutManager = CustomLayoutManager(this)

        val adapter = PermissionsRecyclerAdapter()
        rvPermissions.adapter = adapter

        val data = LinkedList<PermissionModel>()
        permissions.forEach {
            val model = PermissionsHelper.getModelForPermission(it)
                ?: PermissionModel(
                    it,
                    0,
                    "Permission unknown, not important or belongs to system permissions"
                )
            data.add(model)
        }

        data.sortByDescending { it.weight }
        adapter.dataList = data
    }
}