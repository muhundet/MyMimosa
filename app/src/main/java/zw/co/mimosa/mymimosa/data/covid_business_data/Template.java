package zw.co.mimosa.mymimosa.data.covid_business_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Template {

    @SerializedName("name")
    @Expose
    private String name;

    public Template(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
