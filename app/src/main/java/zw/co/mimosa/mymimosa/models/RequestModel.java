package zw.co.mimosa.mymimosa.models;

public class RequestModel {
    String requestSubject;
    String requestStatus;
    String requestId, pendingApprover, dateRaised;


    public RequestModel(String requestSubject, String requestStatus, String requestId, String pendingApprover, String dateRaised) {
        this.requestSubject = requestSubject;
        this.requestStatus = requestStatus;
        this.requestId = requestId;
        this.pendingApprover = pendingApprover;
        this.dateRaised = dateRaised;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPendingApprover() {
        return pendingApprover;
    }

    public void setPendingApprover(String pendingApprover) {
        this.pendingApprover = pendingApprover;
    }

    public String getDateRaised() {
        return dateRaised;
    }

    public void setDateRaised(String dateRaised) {
        this.dateRaised = dateRaised;
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
