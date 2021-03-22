package ru.awawa.permissionschecker.helper


data class AppModel(
    val id: Int,
    val name: String,
    val packageName: String,
    val permissions: List<String>,
    val index1: Int,
    val index2: Float
)
