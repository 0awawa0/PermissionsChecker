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
                    AppDetailsActivity.EXTRA_INDEX1,
                    v.model.index1
                )
                intent.putExtra(
                    AppDetailsActivity.EXTRA_INDEX2,
                    v.model.index2
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
                AppModel(id, it.value, it.key, permissions, index.first, index.second)
            id++
            data.add(appModel)
        }


        data.sortByDescending { it.index1 }
        adapter.dataList = data
    }

    private fun getInstalledPackages(): HashMap<String, String> {
        val packageManager = packageManager

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        val resolveInfoList = getPackageManager().queryIntentActivities(intent, 0)
        val map: HashMap<String, String> = HashMap()
        for (resolveInfo in resolveInfoList) {
            val activityInfo = resolveInfo.activityInfo
            val packageName = activityInfo.applicationInfo.packageName
            val info = packageManager.getApplicationInfo(packageName, 0)
            if (info.flags and ApplicationInfo.FLAG_SYSTEM != 0) continue
            val label = packageManager.getApplicationLabel(activityInfo.applicationInfo) as String
            map[packageName] = label
        }

        return map
    }

    private fun getPermissionForPackage(packageName: String): List<String> {
        val result = ArrayList<String>()

        try {
            val packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            var counter = 1

            if (packageInfo.requestedPermissions == null) return emptyList()
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

    private fun calculateIndexForPermissions(permissions: List<String>): Pair<Int, Float> {
        var dangerousCount = 0
        var sensitiveCount = 0
        var regularCount = 0

        var index1 = 0
        for (p in permissions) {
            val weight = PermissionsHelper.getPermissionWeight(p)
            when {
                weight > 15 -> dangerousCount++
                weight > 10 -> sensitiveCount++
                weight > 0 -> regularCount++
            }
            index1 += weight
        }

        val index2 = 1.35f * dangerousCount + 0.25f * sensitiveCount + 0.075f * regularCount
        return Pair(index1, index2)
    }
}