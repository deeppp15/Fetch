package com.example.fetch.data

import com.google.gson.annotations.SerializedName

data class ItemData(
    @SerializedName("id")
    val itemId: Int,
    @SerializedName("listId")
    val listId: Int,
    @SerializedName("name")
    val name: String?

    )
