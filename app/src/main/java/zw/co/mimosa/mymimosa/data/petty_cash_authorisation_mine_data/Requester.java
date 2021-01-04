package zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Requester {

    @SerializedName("name")
    @Expose
    private String name;

    public Requester(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
