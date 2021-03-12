package ru.awawa.permissionschecker.main

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ru.awawa.permissionschecker.*
import ru.awawa.permissionschecker.details.AppDetailsActivity
import ru.awawa.permissionschecker.helper.AppModel
import ru.awawa.permissionschecker.helper.PermissionsHelper
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    private var adapter = AppsRecyclerAdapter(object :
        RecyclerItemEventListener {
        override fun onClick(v: View) {
            if (v is AppView) {
                val intent = Intent(
                    this@MainActivity,
                    AppDetailsActivity::class.java
                )
                intent.putExtra(
                    AppDetailsActivity.EXTRA_APP_NAME,
                    v.model.name
                )
                intent.putExtra(
                    AppDetailsActivity.EXTRA_PACKAGE_NAME,
                    v.model.packageName
                )
                intent.putExtra(
                    AppDetailsActivity.EXTRA_INDEX,
                    v.model.index
                )
                intent.putExtra(
                    AppDetailsActivity.EXTRA_PERMISSIONS,
                    v.model.permissions.toTypedArray()
                )
                startActivity(intent)
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvApps = findViewById<RecyclerView>(R.id.rvApps)

        val layoutManager = CustomLayoutManager(this)
        rvApps.layoutManager = layoutManager
        rvApps.adapter = adapter

        val data = LinkedList<AppModel>()
        val packages = getInstalledPackages()

        var id = 1
        packages.forEach {
            val permissions = getPermissionForPackage(it.key)
            val index = calculateIndexForPermissions(permissions)
            val appModel =
                AppModel(id, it.value, it.key, permissions, index)
            id++
            data.add(appModel)
        }

        data.sortByDescending { it.index }
        adapter.dataList = data
    }

    private fun getInstalledPackages(): HashMap<String, String> {
        val packageManager = packageManager

        // Initialize a new intent
        val intent = Intent(Intent.ACTION_MAIN, null)

        // Set the intent category
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        // Set the intent flags
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)

        // Initialize a new list of resolve info
        val resolveInfoList = getPackageManager().queryIntentActivities(intent, 0)

        // Initialize a new hash map of package names and application label
        val map: HashMap<String, String> = HashMap()

        // Loop through the resolve info list
        for (resolveInfo in resolveInfoList) {
            // Get the activity info from resolve info
            val activityInfo = resolveInfo.activityInfo

            // Get the package name
            val packageName = activityInfo.applicationInfo.packageName

            val info = packageManager.getApplicationInfo(packageName, 0)
            if (info.flags and ApplicationInfo.FLAG_SYSTEM != 0) continue

            // Get the application label
            val label = packageManager.getApplicationLabel(activityInfo.applicationInfo) as String


            // Put the package name and application label to hash map
            map[packageName] = label
        }

        return map
    }

    private fun getPermissionForPackage(packageName: String): List<String> {
        // Initialize a new string builder instance
        val result = ArrayList<String>()

        try {
            // Get the package info
            val packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)

            // Permissions counter
            var counter = 1

            // Loop through the package info requested permissions
            for (i in packageInfo.requestedPermissions.indices) {
                if (packageInfo.requestedPermissionsFlags[i] and PackageInfo.REQUESTED_PERMISSION_GRANTED != 0) {
                    val permission = packageInfo.requestedPermissions[i]
                    result.add(permission)
                    counter++
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result.toList()
    }

    private fun calculateIndexForPermissions(permissions: List<String>): Int {
        var result = 0
        for (p in permissions) {
            result += PermissionsHelper.getPermissionWeight(p)
        }
        return result
    }
}