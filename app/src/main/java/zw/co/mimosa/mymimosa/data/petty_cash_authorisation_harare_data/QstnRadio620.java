package zw.co.mimosa.mymimosa.data.petty_cash_authorisation_harare_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QstnRadio620 {

    @SerializedName("name")
    @Expose
    private String name;

    public QstnRadio620(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
