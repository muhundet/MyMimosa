package zw.co.mimosa.mymimosa.data.overtime_authorisation_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UdfFields {

    @SerializedName("udf_pick_38")
    @Expose
    private String udfPick38;
    @SerializedName("udf_sline_307")
    @Expose
    private String udfSline307;
    @SerializedName("udf_sline_349")
    @Expose
    private String udfSline349;
    @SerializedName("udf_pick_1202")
    @Expose
    private String udfPick1202;
    @SerializedName("udf_sline_350")
    @Expose
    private String udfSline350;
    @SerializedName("udf_pick_639")
    @Expose
    private String udfPick639;
    @SerializedName("udf_date_306")
    @Expose
    private UdfDate306 udfDate306;

    public UdfFields(String udfPick38, String udfSline307, String udfSline349, String udfPick1202, String udfSline350, String udfPick639, UdfDate306 udfDate306) {
        this.udfPick38 = udfPick38;
        this.udfSline307 = udfSline307;
        this.udfSline349 = udfSline349;
        this.udfPick1202 = udfPick1202;
        this.udfSline350 = udfSline350;
        this.udfPick639 = udfPick639;
        this.udfDate306 = udfDate306;
    }

    public String getUdfPick38() {
        return udfPick38;
    }

    public void setUdfPick38(String udfPick38) {
        this.udfPick38 = udfPick38;
    }

    public String getUdfSline307() {
        return udfSline307;
    }

    public void setUdfSline307(String udfSline307) {
        this.udfSline307 = udfSline307;
    }

    public String getUdfSline349() {
        return udfSline349;
    }

    public void setUdfSline349(String udfSline349) {
        this.udfSline349 = udfSline349;
    }

    public String getUdfPick1202() {
        return udfPick1202;
    }

    public void setUdfPick1202(String udfPick1202) {
        this.udfPick1202 = udfPick1202;
    }

    public String getUdfSline350() {
        return udfSline350;
    }

    public void setUdfSline350(String udfSline350) {
        this.udfSline350 = udfSline350;
    }

    public String getUdfPick639() {
        return udfPick639;
    }

    public void setUdfPick639(String udfPick639) {
        this.udfPick639 = udfPick639;
    }

    public UdfDate306 getUdfDate306() {
        return udfDate306;
    }

    public void setUdfDate306(UdfDate306 udfDate306) {
        this.udfDate306 = udfDate306;
    }

}
