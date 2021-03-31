package zw.co.mimosa.mymimosa.ui.hr.educational_assistance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EducationalAssistanceHelper {
    private static EducationalAssistanceHelper mEducationalAssistanceHelper = null;

    public static EducationalAssistanceHelper getEducationalAssistanceInstance(){
        if(mEducationalAssistanceHelper == null){
            mEducationalAssistanceHelper = new EducationalAssistanceHelper();
        }
        return (EducationalAssistanceHelper) mEducationalAssistanceHelper;
    }

    String firstname;
    String surname;
    String employeeId;
    String department;
    String section;
    String emailId;
    String designation;
    String jobGrade;
    String childFirstName;
    String childSurname;
    long childDob;
    String childSchoolName;
    String childLevel;
    String term;
    String applicationType;
    String approver;

    public String getApproverStage1() {
        return ApproverStage1;
    }

    public void setApproverStage1(String approverStage1) {
        ApproverStage1 = approverStage1;
    }

    long dateOfEngagement;
    String currency;
    String[] attachements;
    int invoiceValue;
    int receiptValue;
    String receiptFilePath;
    String invoiceFilePath;
    boolean isReceiptSelected=false;
    boolean isInvoiceSelected=false;
    List<File> list = new ArrayList<>();

    String  ApproverStage1;

    public List<File> getList() {
        return list;
    }

    public void setList(List<File> list) {
        this.list = list;
    }

    public static EducationalAssistanceHelper getmEducationalAssistanceHelper() {
        return mEducationalAssistanceHelper;
    }

    public static void setmEducationalAssistanceHelper(EducationalAssistanceHelper mEducationalAssistanceHelper) {
        EducationalAssistanceHelper.mEducationalAssistanceHelper = mEducationalAssistanceHelper;
    }

    public boolean isReceiptSelected() {
        return isReceiptSelected;
    }

    public void setReceiptSelected(boolean receiptSelected) {
        isReceiptSelected = receiptSelected;
    }

    public boolean isInvoiceSelected() {
        return isInvoiceSelected;
    }

    public void setInvoiceSelected(boolean invoiceSelected) {
        isInvoiceSelected = invoiceSelected;
    }

    public String getReceiptFilePath() {
        return receiptFilePath;
    }

    public void setReceiptFilePath(String receiptFilePath) {
        this.receiptFilePath = receiptFilePath;
    }

    public String getInvoiceFilePath() {
        return invoiceFilePath;
    }

    public void setInvoiceFilePath(String invoiceFilePath) {
        this.invoiceFilePath = invoiceFilePath;
    }


    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getJobGrade() {
        return jobGrade;
    }

    public void setJobGrade(String jobGrade) {
        this.jobGrade = jobGrade;
    }

    public String getChildFirstName() {
        return childFirstName;
    }

    public void setChildFirstName(String childFirstName) {
        this.childFirstName = childFirstName;
    }

    public String getChildSurname() {
        return childSurname;
    }

    public void setChildSurname(String childSurname) {
        this.childSurname = childSurname;
    }

    public long getChildDob() {
        return childDob;
    }

    public void setChildDob(long childDob) {
        this.childDob = childDob;
    }

    public String getChildSchoolName() {
        return childSchoolName;
    }

    public void setChildSchoolName(String childSchoolName) {
        this.childSchoolName = childSchoolName;
    }

    public String getChildLevel() {
        return childLevel;
    }

    public void setChildLevel(String childLevel) {
        this.childLevel = childLevel;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public long getDateOfEngagement() {
        return dateOfEngagement;
    }

    public void setDateOfEngagement(long dateOfEngagement) {
        this.dateOfEngagement = dateOfEngagement;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String[] getAttachements() {
        return attachements;
    }

    public void setAttachements(String[] attachements) {
        this.attachements = attachements;
    }

    public int getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(int invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public int getReceiptValue() {
        return receiptValue;
    }

    public void setReceiptValue(int receiptValue) {
        this.receiptValue = receiptValue;
    }

    public void setEAApprovers(){
        ApproverStage1 = approver.replaceAll(" ", ".") + "@mimosa.co.zw";

    }
}


