package zw.co.mimosa.mymimosa.data.overtime_authorisation_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Res306 {

    @SerializedName("qstn_text_315")
    @Expose
    private QstnText315 qstnText315;
    @SerializedName("qstn_text_658")
    @Expose
    private QstnText658 qstnText658;
    @SerializedName("qstn_text_309")
    @Expose
    private QstnText309 qstnText309;
    @SerializedName("qstn_radio_316")
    @Expose
    private QstnRadio316 qstnRadio316;
    @SerializedName("qstn_text_310")
    @Expose
    private QstnText310 qstnText310;

    public Res306(QstnText315 qstnText315, QstnText658 qstnText658, QstnText309 qstnText309, QstnRadio316 qstnRadio316, QstnText310 qstnText310, QstnText312 qstnText312, QstnText314 qstnText314, QstnText313 qstnText313) {
        this.qstnText315 = qstnText315;
        this.qstnText658 = qstnText658;
        this.qstnText309 = qstnText309;
        this.qstnRadio316 = qstnRadio316;
        this.qstnText310 = qstnText310;
        this.qstnText312 = qstnText312;
        this.qstnText314 = qstnText314;
        this.qstnText313 = qstnText313;
    }

    @SerializedName("qstn_text_312")
    @Expose
    private QstnText312 qstnText312;
    @SerializedName("qstn_text_314")
    @Expose
    private QstnText314 qstnText314;
    @SerializedName("qstn_text_313")
    @Expose
    private QstnText313 qstnText313;

    public QstnText315 getQstnText315() {
        return qstnText315;
    }

    public void setQstnText315(QstnText315 qstnText315) {
        this.qstnText315 = qstnText315;
    }

    public QstnText658 getQstnText658() {
        return qstnText658;
    }

    public void setQstnText658(QstnText658 qstnText658) {
        this.qstnText658 = qstnText658;
    }

    public QstnText309 getQstnText309() {
        return qstnText309;
    }

    public void setQstnText309(QstnText309 qstnText309) {
        this.qstnText309 = qstnText309;
    }

    public QstnRadio316 getQstnRadio316() {
        return qstnRadio316;
    }

    public void setQstnRadio316(QstnRadio316 qstnRadio316) {
        this.qstnRadio316 = qstnRadio316;
    }

    public QstnText310 getQstnText310() {
        return qstnText310;
    }

    public void setQstnText310(QstnText310 qstnText310) {
        this.qstnText310 = qstnText310;
    }

    public QstnText312 getQstnText312() {
        return qstnText312;
    }

    public void setQstnText312(QstnText312 qstnText312) {
        this.qstnText312 = qstnText312;
    }

    public QstnText314 getQstnText314() {
        return qstnText314;
    }

    public void setQstnText314(QstnText314 qstnText314) {
        this.qstnText314 = qstnText314;
    }

    public QstnText313 getQstnText313() {
        return qstnText313;
    }

    public void setQstnText313(QstnText313 qstnText313) {
        this.qstnText313 = qstnText313;
    }

}

