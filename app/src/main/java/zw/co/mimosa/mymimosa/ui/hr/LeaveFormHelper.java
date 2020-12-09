package zw.co.mimosa.mymimosa.ui.hr;


public class LeaveFormHelper {
    private static LeaveFormHelper mLeaveFomHelper = null;

    public static LeaveFormHelper getLeaveFormHelperInstance(){
        if(mLeaveFomHelper == null){
            mLeaveFomHelper = new LeaveFormHelper();
        }
        return (LeaveFormHelper) mLeaveFomHelper;
    }

    String firstname;
    String surname;
    String employeeId;
    String department;
    String section;
    String emailId;
    String designation;
    Long udf_date_324 = 100L;
    String  typeOfLeave;
    int  udf_long_345 = 7;
    int  udf_long_346 = 30;
    String  reasonForApplication;
    Long  udf_date_347 = 200L;
    Long  udf_date_35 = 300L;
    Long  udf_date_36 = 400L;
    String  modeOfPayment;
    String  ApproverStage1;
    String  ApproverStage2;
    String  ApproverStage3;
    String  ApproverStage4;
    String  ApproverStage5;
    //Leave or advance
    String WhatAreYouApplyingFor;
    //self or on behalf
    String WhoAreYouApplyingFor;
    String  ToBeDeductedInFullAt;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public static LeaveFormHelper getmLeaveFomHelper() {
        return mLeaveFomHelper;
    }

    public static void setmLeaveFomHelper(LeaveFormHelper mLeaveFomHelper) {
        LeaveFormHelper.mLeaveFomHelper = mLeaveFomHelper;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getUdf_date_324() {
        return udf_date_324;
    }

    public void setUdf_date_324(Long udf_date_324) {
        this.udf_date_324 = udf_date_324;
    }

    public String getTypeOfLeave() {
        return typeOfLeave;
    }

    public void setTypeOfLeave(String typeOfLeave) {
        this.typeOfLeave = typeOfLeave;
    }

    public int getUdf_long_345() {
        return udf_long_345;
    }

    public void setUdf_long_345(int udf_long_345) {
        this.udf_long_345 = udf_long_345;
    }

    public int getUdf_long_346() {
        return udf_long_346;
    }

    public void setUdf_long_346(int udf_long_346) {
        this.udf_long_346 = udf_long_346;
    }

    public String getReasonForApplication() {
        return reasonForApplication;
    }

    public void setReasonForApplication(String reasonForApplication) {
        this.reasonForApplication = reasonForApplication;
    }

    public Long getUdf_date_347() {
        return udf_date_347;
    }

    public void setUdf_date_347(Long udf_date_347) {
        this.udf_date_347 = udf_date_347;
    }

    public Long getUdf_date_35() {
        return udf_date_35;
    }

    public void setUdf_date_35(Long udf_date_35) {
        this.udf_date_35 = udf_date_35;
    }

    public Long getUdf_date_36() {
        return udf_date_36;
    }

    public void setUdf_date_36(Long udf_date_36) {
        this.udf_date_36 = udf_date_36;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
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

    public String getApproverStage3() {
        return ApproverStage3;
    }

    public void setApproverStage3(String approverStage3) {
        ApproverStage3 = approverStage3;
    }

    public String getApproverStage4() {
        return ApproverStage4;
    }

    public void setApproverStage4(String approverStage4) {
        ApproverStage4 = approverStage4;
    }

    public String getApproverStage5() {
        return ApproverStage5;
    }

    public void setApproverStage5(String approverStage5) {
        ApproverStage5 = approverStage5;
    }

    public String getWhatAreYouApplyingFor() {
        return WhatAreYouApplyingFor;
    }

    public void setWhatAreYouApplyingFor(String whatAreYouApplyingFor) {
        WhatAreYouApplyingFor = whatAreYouApplyingFor;
    }

    public String getWhoAreYouApplyingFor() {
        return WhoAreYouApplyingFor;
    }

    public void setWhoAreYouApplyingFor(String whoAreYouApplyingFor) {
        WhoAreYouApplyingFor = whoAreYouApplyingFor;
    }

    public String getToBeDeductedInFullAt() {
        return ToBeDeductedInFullAt;
    }

    public void setToBeDeductedInFullAt(String toBeDeductedInFullAt) {
        ToBeDeductedInFullAt = toBeDeductedInFullAt;
    }


}
