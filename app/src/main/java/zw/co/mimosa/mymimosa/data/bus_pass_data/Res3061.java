package zw.co.mimosa.mymimosa.data.bus_pass_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Res3061 {

    @SerializedName("qstn_text_315")
    @Expose
    private QstnText315 qstnText315;
    @SerializedName("qstn_text_3601")
    @Expose
    private QstnText3601 qstnText3601;
    @SerializedName("qstn_text_3602")
    @Expose
    private QstnText3602 qstnText3602;
    @SerializedName("qstn_select_3908")
    @Expose
    private QstnSelect3908 qstnSelect3908;

    public Res3061(QstnText315 qstnText315, QstnText3601 qstnText3601, QstnText3602 qstnText3602, QstnSelect3908 qstnSelect3908, QstnText3002 qstnText3002) {
        this.qstnText315 = qstnText315;
        this.qstnText3601 = qstnText3601;
        this.qstnText3602 = qstnText3602;
        this.qstnSelect3908 = qstnSelect3908;
        this.qstnText3002 = qstnText3002;
    }

    @SerializedName("qstn_text_3002")
    @Expose
    private QstnText3002 qstnText3002;

    public QstnText315 getQstnText315() {
        return qstnText315;
    }

    public void setQstnText315(QstnText315 qstnText315) {
        this.qstnText315 = qstnText315;
    }

    public QstnText3601 getQstnText3601() {
        return qstnText3601;
    }

    public void setQstnText3601(QstnText3601 qstnText3601) {
        this.qstnText3601 = qstnText3601;
    }

    public QstnText3602 getQstnText3602() {
        return qstnText3602;
    }

    public void setQstnText3602(QstnText3602 qstnText3602) {
        this.qstnText3602 = qstnText3602;
    }

    public QstnSelect3908 getQstnSelect3908() {
        return qstnSelect3908;
    }

    public void setQstnSelect3908(QstnSelect3908 qstnSelect3908) {
        this.qstnSelect3908 = qstnSelect3908;
    }

    public QstnText3002 getQstnText3002() {
        return qstnText3002;
    }

    public void setQstnText3002(QstnText3002 qstnText3002) {
        this.qstnText3002 = qstnText3002;
    }

}