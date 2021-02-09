package zw.co.mimosa.mymimosa.models;

public class RequestModel {
    String requestSubject;
    String requestStatus;

    public RequestModel(String requestSubject, String requestStatus) {
        this.requestSubject = requestSubject;
        this.requestStatus = requestStatus;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestSubject() {
        return requestSubject;
    }

    public void setRequestSubject(String requestSubject) {
        this.requestSubject = requestSubject;
    }

    public RequestModel(String requestSubject) {
        this.requestSubject = requestSubject;
    }
}
