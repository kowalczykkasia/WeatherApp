package com.example.weatheracc.models

import androidx.room.Embedded
import androidx.room.Entity;
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "autocomplete")
data class AutocompleteCities(
    @PrimaryKey
    @SerializedName("id") val id : String,
    @SerializedName("description") val description : String,
    @Embedded
    @SerializedName("structured_formatting") val structured_formatting : Name
)

data class AutocompleteCitiesResponse(
    @SerializedName("predictions") val list : List<AutocompleteCities>
)

data class Name(
    @SerializedName("main_text") val main_text : String,
    @SerializedName("secondary_text") val secondary_text :String
)
