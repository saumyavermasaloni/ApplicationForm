
import com.google.gson.annotations.SerializedName

    data class UiData(

    @SerializedName("logo-url")
    var logo: String? = null,

    @SerializedName("heading-text")
    var heading: String? = null,

    @SerializedName("uidata")
    var uidata: List<UiDataList>? = null
)