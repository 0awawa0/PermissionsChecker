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
        const val EXTRA_INDEX1 = "INDEX1"
        const val EXTRA_INDEX2 = "INDEX2"
        const val EXTRA_PERMISSIONS = "PERMISSIONS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        val appName = intent.getStringExtra(EXTRA_APP_NAME)
        val packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME)
        val index1 = intent.getIntExtra(EXTRA_INDEX1, 0)
        val index2 = intent.getFloatExtra(EXTRA_INDEX2, 0.0f)
        val permissions = intent.getStringArrayExtra(EXTRA_PERMISSIONS) ?: emptyArray()

        findViewById<TextView>(R.id.tvAppName).text = appName
        findViewById<TextView>(R.id.tvPackageName).text = packageName
        findViewById<TextView>(R.id.tvIndex).text = index1.toString()

        findViewById<TextView>(R.id.tvIndex).setTextColor(
            ContextCompat.getColor(this, when {
                index2 > 5.5 -> R.color.red_secret_data
                index2 > 3 -> R.color.orange_sensitive_data
                else -> R.color.green_not_secret_data
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