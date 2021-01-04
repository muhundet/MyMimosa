package zw.co.mimosa.mymimosa.data.petty_cash_authorisation_harare_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("requester")
    @Expose
    private Requester requester;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("template")
    @Expose
    private Template template;
    @SerializedName("udf_fields")
    @Expose
    private UdfFields udfFields;
    @SerializedName("resources")
    @Expose
    private Resources resources;

    public Request(String description, Requester requester, String subject, Template template, UdfFields udfFields, Resources resources) {
        this.description = description;
        this.requester = requester;
        this.subject = subject;
        this.template = template;
        this.udfFields = udfFields;
        this.resources = resources;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Requester getRequester() {
        return requester;
    }

    public void setRequester(Requester requester) {
        this.requester = requester;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public UdfFields getUdfFields() {
        return udfFields;
    }

    public void setUdfFields(UdfFields udfFields) {
        this.udfFields = udfFields;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

}