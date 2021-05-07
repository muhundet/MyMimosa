package zw.co.mimosa.mymimosa.data.bus_pass_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QstnSelect3908 {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;

    public String getName() {
        return name;
    }

    public QstnSelect3908(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
