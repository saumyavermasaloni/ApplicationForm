import com.google.gson.annotations.SerializedName

data class UiDataList(

    @SerializedName("uitype")
    var uitype: String? = null,

    @SerializedName("value")
    var value: String? = null,

    @SerializedName("key")
    var key: String? = null,

    @SerializedName("hint")
    var hint: String? = null
)