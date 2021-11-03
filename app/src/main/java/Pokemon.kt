import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pokemon(
    val id:Int? = null,
    val name:String? = null,
    val price:Float? = null,
    val type:List<String>? = null,
    val stats:Stats? = null
) : Serializable

data class Stats(
    val total:Int? = null,
    val hp:Int? = null,
    val attack:Int? = null,
    val defense:Int? = null,
    @SerializedName("sp-atk")
    val spAtk:Int? = null,
    @SerializedName("sp-def")
    val spDef:Int? = null,
    val speed:Int? = null
) : Serializable