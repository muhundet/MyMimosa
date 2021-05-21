package zw.co.mimosa.mymimosa.data.overtime_authorisation_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Res3606 {

    @SerializedName("qstn_text_2101")
    @Expose
    private QstnText2101 qstnText2101;

    public QstnText2101 getQstnText2101() {
        return qstnText2101;
    }

    public Res3606(QstnText2101 qstnText2101) {
        this.qstnText2101 = qstnText2101;
    }

    public void setQstnText2101(QstnText2101 qstnText2101) {
        this.qstnText2101 = qstnText2101;
    }

}
