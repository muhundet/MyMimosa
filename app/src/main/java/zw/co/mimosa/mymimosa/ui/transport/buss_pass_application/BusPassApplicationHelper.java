package zw.co.mimosa.mymimosa.ui.transport.buss_pass_application;

import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationHelper;

public class BusPassApplicationHelper {

    private static BusPassApplicationHelper mBusPassApplicationHelper = null;

    public static BusPassApplicationHelper getBusPassApplicationInstance(){
        if(mBusPassApplicationHelper== null){
            mBusPassApplicationHelper = new BusPassApplicationHelper();
        }
        return (BusPassApplicationHelper) mBusPassApplicationHelper;
    }

    String firstname;
    String surname;
    String employeeId;
    String department;
    String section;
    String emailId;
    String designation;
    String idNumber;
    String companyName;
    String type;
    String applicant;
    String applyForId;
    String applyForName;
    String applyForRelationship;
    String applyForSchool;
    String applyForLevel;
    String  ApproverStage1;
    String  ApproverStage2;

    public static BusPassApplicationHelper getmBusPassApplicationHelper() {
        return mBusPassApplicationHelper;
    }

    public static void setmBusPassApplicationHelper(BusPassApplicationHelper mBusPassApplicationHelper) {
        BusPassApplicationHelper.mBusPassApplicationHelper = mBusPassApplicationHelper;
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplyForId() {
        return applyForId;
    }

    public void setApplyForId(String applyForId) {
        this.applyForId = applyForId;
    }

    public String getApplyForName() {
        return applyForName;
    }

    public void setApplyForName(String applyForName) {
        this.applyForName = applyForName;
    }

    public String getApplyForRelationship() {
        return applyForRelationship;
    }

    public void setApplyForRelationship(String applyForRelationship) {
        this.applyForRelationship = applyForRelationship;
    }

    public String getApplyForSchool() {
        return applyForSchool;
    }

    public void setApplyForSchool(String applyForSchool) {
        this.applyForSchool = applyForSchool;
    }

    public String getApplyForLevel() {
        return applyForLevel;
    }

    public void setApplyForLevel(String applyForLevel) {
        this.applyForLevel = applyForLevel;
    }

    public String getApproverStage1() {
        return ApproverStage1;
    }

    public void setApproverStage1(String approverStage1) {
        ApproverStage1 = approverStage1;
    }

    public String getApproverStage2() {
        return ApproverStage2;
    }

    public void setApproverStage2(String approverStage2) {
        ApproverStage2 = approverStage2;
    }
}
