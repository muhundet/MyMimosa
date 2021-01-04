package zw.co.mimosa.mymimosa.data.petty_cash_authorisation_harare_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Res2431 {

    @SerializedName("qstn_text_307")
    @Expose
    private QstnText307 qstnText307;
    @SerializedName("qstn_text_308")
    @Expose
    private QstnText308 qstnText308;
    @SerializedName("qstn_radio_620")
    @Expose
    private QstnRadio620 qstnRadio620;

    public Res2431(QstnText307 qstnText307, QstnText308 qstnText308, QstnRadio620 qstnRadio620) {
        this.qstnText307 = qstnText307;
        this.qstnText308 = qstnText308;
        this.qstnRadio620 = qstnRadio620;
    }

    public QstnText307 getQstnText307() {
        return qstnText307;
    }

    public void setQstnText307(QstnText307 qstnText307) {
        this.qstnText307 = qstnText307;
    }

    public QstnText308 getQstnText308() {
        return qstnText308;
    }

    public void setQstnText308(QstnText308 qstnText308) {
        this.qstnText308 = qstnText308;
    }

    public QstnRadio620 getQstnRadio620() {
        return qstnRadio620;
    }

    public void setQstnRadio620(QstnRadio620 qstnRadio620) {
        this.qstnRadio620 = qstnRadio620;
    }

}
