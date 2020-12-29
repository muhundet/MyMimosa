package zw.co.mimosa.mymimosa.data.educational_assistance_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("requester")
    @Expose
    private Requester requester;
    @SerializedName("template")
    @Expose
    private Template template;
    @SerializedName("udf_fields")
    @Expose
    private UdfFields udfFields;

    public Request(String subject, String description, Requester requester, Template template, UdfFields udfFields) {
        this.subject = subject;
        this.description = description;
        this.requester = requester;
        this.template = template;
        this.udfFields = udfFields;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
}
