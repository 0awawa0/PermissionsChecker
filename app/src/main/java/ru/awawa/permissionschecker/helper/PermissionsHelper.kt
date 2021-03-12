package ru.awawa.permissionschecker.helper

object PermissionsHelper {

    val permissions = listOf(
        PermissionModel(
            "android.permission.ACCESS_BACKGROUND_LOCATION",
            20,
            "Allows an app to access location in the background. Applications with this permissions can " +
                    "access your location even if you are not using the particular application."
        ),
        PermissionModel(
            "android.permission.ACCESS_COARSE_LOCATION",
            14,
            "Allows an app to access approximate location."
        ),
        PermissionModel(
            "android.permission.ACCESS_FINE_LOCATION",
            16,
            "Allows an app to access precise location."
        ),
        PermissionModel(
            "android.permission.ACCESS_MEDIA_LOCATION",
            12,
            "Allows an application to access any geographic locations persisted in the user's shared " +
                    "collection. That means the application with this permission can access information about " +
                    "where was made particular photos and videos stored in the phone storage."
        ),
        PermissionModel(
            "android.permission.ACCESS_NETWORK_STATE",
            4,
            "Allows applications to access information about networks. This is not dangerous permission."
        ),
        PermissionModel(
            "android.permission.ACCESS_WIFI_STATE",
            4,
            "Allows applications to access information about Wi-Fi networks."
        ),
        PermissionModel(
            "android.permission.ACTIVITY_RECOGNITION",
            14,
            "Allows an application to recognize physical activity such as walking, jogging, sleeping etc."
        ),
        PermissionModel(
            "com.android.voicemail.permission.ADD_VOICEMAIL",
            9,
            "Allows an application to add voicemails into the system."
        ),
        PermissionModel(
            "android.permission.ANSWER_PHONE_CALLS",
            11,
            "Allows the app to answer an incoming phone call."
        ),
        PermissionModel(
            "android.permission.BLUETOOTH",
            4,
            "Allows applications to connect to paired bluetooth devices."
        ),
        PermissionModel(
            "android.permission.BLUETOOTH_ADMIN",
            6,
            "Allows applications to discover and pair bluetooth devices. "
        ),
        PermissionModel(
            "android.permission.BODY_SENSORS",
            16,
            "Allows an application to access data from sensors that the user uses to measure what is " +
                    "happening inside their body, such as heart rate."
        ),
        PermissionModel(
            "android.permission.CALL_PHONE",
            10,
            "Allows an application to initiate a phone call without going through the Dialer user interface " +
                    "for the user to confirm the call."
        ),
        PermissionModel(
            "android.permission.CAMERA",
            14,
            "Required to be able to access the camera device. Allows application to take photos and videos"
        ),
        PermissionModel(
            "android.permission.CHANGE_NETWORK_STATE",
            5,
            "Allows applications to change network connectivity state."
        ),
        PermissionModel(
            "android.permission.CHANGE_WIFI_STATE",
            5,
            "Allows applications to change Wi-Fi connectivity state."
        ),
        PermissionModel(
            "android.permission.DISABLE_KEYGUARD",
            4,
            "Allows applications to disable the keyguard if it is not secure."
        ),
        PermissionModel(
            "android.permission.FOREGROUND_SERVICE",
            7,
            "Allows application to run foreground service, so it may do work when user is not interacting " +
                    "with it at the moment."
        ),
        PermissionModel(
            "android.permission.INTERNET",
            3,
            "Allows applications to open network sockets. Common permission for every application that " +
                    "needs to send and receive data from the internet."
        ),
        PermissionModel(
            "android.permission.MODIFY_AUDIO_SETTINGS",
            5,
            "Allows an application to modify global audio settings."
        ),
        PermissionModel(
            "android.permission.NFC",
            4,
            "Allows applications to perform I/O operations over NFC."
        ),
        PermissionModel(
            "android.permission.QUERY_ALL_PACKAGES",
            1,
            "Allows query of any normal app on the device."
        ),
        PermissionModel(
            "android.permission.READ_CALENDAR",
            19,
            "Allows an application to read the user's calendar data."
        ),
        PermissionModel(
            "android.permission.READ_CALL_LOG",
            18,
            "Allows an application to read the user's call log."
        ),
        PermissionModel(
            "android.permission.READ_CONTACTS",
            19,
            "Allows an application to read the user's contacts data."
        ),
        PermissionModel(
            "android.permission.READ_EXTERNAL_STORAGE",
            13,
            "Allows an application to read from device's storage."
        ),
        PermissionModel(
            "android.permission.READ_PHONE_NUMBERS",
            15,
            "Allows read access to the device's phone number(s)."
        ),
        PermissionModel(
            "android.permission.READ_PHONE_STATE",
            17,
            "Allows read only access to phone state, including the current cellular network information," +
                    " the status of any ongoing calls, and a list of any PhoneAccounts registered on the device."
        ),
        PermissionModel(
            "android.permission.READ_SMS",
            18,
            "Allows an application to read SMS messages."
        ),
        PermissionModel(
            "android.permission.RECEIVE_MMS",
            19,
            "Allows an application to monitor incoming MMS messages."
        ),
        PermissionModel(
            "android.permission.RECEIVE_SMS",
            19,
            "Allows an application to receive SMS messages."
        ),
        PermissionModel(
            "android.permission.RECEIVE_WAP_PUSH",
            19,
            "Allows an application to receive WAP push messages."
        ),
        PermissionModel(
            "android.permission.RECORD_AUDIO",
            20,
            "Allows an application to record audio."
        ),
        PermissionModel(
            "android.permission.SEND_SMS",
            16,
            "Allows an application to send SMS messages."
        ),
        PermissionModel(
            "android.permission.SYSTEM_ALERT_WINDOW",
            12,
            "Allows application to show its windows on top of other applications."
        ),
        PermissionModel(
            "android.permission.USE_SIP",
            9,
            "Allows an application to use SIP service."
        ),
        PermissionModel(
            "android.permission.WRITE_CALENDAR",
            10,
            "Allows an application to write the user's calendar data."
        ),
        PermissionModel(
            "android.permission.WRITE_CALL_LOG",
            10,
            "Allows an application to write (but not read) the user's call log data."
        ),
        PermissionModel(
            "android.permission.WRITE_CONTACTS",
            10,
            "Allows an application to write the user's contacts data."
        ),
        PermissionModel(
            "android.permission.WRITE_EXTERNAL_STORAGE",
            10,
            "Allows an application to write to device's storage. "
        ),
        PermissionModel(
            "android.permission.WRITE_SETTINGS",
            17,
            "Allows an application to read or write the system settings."
        ),
        PermissionModel(
            "com.android.voicemail.permission.WRITE_VOICEMAIL",
            10,
            "Allows an application to modify and remove existing voicemails in the system."
        )
    )

    fun getPermissionWeight(permission: String): Int {
        return permissions.find { it.constant == permission }?.weight ?: 0
    }

    fun getModelForPermission(permission: String): PermissionModel? {
        return permissions.find { it.constant == permission }
    }
}