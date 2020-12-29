package zw.co.mimosa.mymimosa.ui.hr.leave_and_advance;


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
    int  numberOfDays;
    int  daysAcrued ;
    String  reasonForApplication;
    Long  udf_date_347 = 200L;
    Long  udf_date_35 = 300L;
    Long  udf_date_36 = 400L;
    int amount;
    String  modeOfPayment;
    String  ApproverStage1;
    String  ApproverStage2;
    String  ApproverStage3;
    String  ApproverStage4;
    String  ApproverStage5;
    String ApproverHos;
    String ApproverHr;
    String ApproverLine;
    //Leave or advance
    String WhatAreYouApplyingFor;
    //self or on behalf
    String WhoAreYouApplyingFor;
   int numberOfMonths;

   String filepath;
   boolean isSickSelected=false;

    public boolean isSickSelected() {
        return isSickSelected;
    }

    public void setSickSelected(boolean sickSelected) {
        isSickSelected = sickSelected;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getApproverLine() {
        return ApproverLine;
    }

    public void setApproverLine(String approverLine) {
        ApproverLine = approverLine;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public int getDaysAcrued() {
        return daysAcrued;
    }

    public void setDaysAcrued(int daysAcrued) {
        this.daysAcrued = daysAcrued;
    }

    public String getApproverHos() {
        return ApproverHos;
    }

    public void setApproverHos(String approverHos) {
        ApproverHos = approverHos;
    }

    public String getApproverHr() {
        return ApproverHr;
    }

    public void setApproverHr(String approverHr) {
        ApproverHr = approverHr;
    }

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

    public int getNumberOfMonths() {
        return numberOfMonths;
    }

    public void setNumberOfMonths(int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }

    public boolean checkDepartment(){
        return department.equals("Plant") || department.equals("Mobile Equipment") || department.equals("Mining South") || department.equals("Mining North") || department.equals("Materials Handling") || department.equals("logistics / mining");
    }

    public void setAdvanceApprovers(){
        ApproverStage1 = ApproverHos.replaceAll(" ", ".") + "@mimosa.co.zw";
        ApproverStage2 = "$DEPT_HEAD$";
        ApproverStage3 = ApproverHr.replaceAll(" ", ".") + "@mimosa.co.zw";
    }

    public void setApprovers(){

        if(typeOfLeave.trim().equals("UNPAID")){
            System.out.println("UNPAID IF");
            ApproverStage1 = "$REPORTING_TO$";
            ApproverStage2 = ApproverHos.replaceAll(" ", ".") + "@mimosa.co.zw";
            ApproverStage3 = "$DEPT_HEAD$";
            ApproverStage4 = "$GENERAL_MANAGER$";
            ApproverStage5 = ApproverHr.replaceAll(" ", ".") + "@mimosa.co.zw";
        }else if(typeOfLeave.trim().equals("SPECIAL")){
            System.out.println("SPECIAL IF");
            ApproverStage1 = ApproverHr.replaceAll(" ", ".") + "@mimosa.co.zw";
            if(checkDepartment()){
                ApproverStage2 = ApproverLine.replaceAll(" ", ".") + "@mimosa.co.zw";
            }else{
                ApproverStage2 = "$REPORTING_TO$";
            }
            ApproverStage3 = ApproverHos.replaceAll(" ", ".") + "@mimosa.co.zw";
            ApproverStage4 = "$DEPT_HEAD$";
        }else{
            System.out.println("OTHER LEAVE IF");
            ApproverStage1 = "$REPORTING_TO$";
            if(checkDepartment()){
                ApproverStage2 = ApproverLine.replaceAll(" ", ".") + "@mimosa.co.zw";
                ApproverStage3 = ApproverHos.replaceAll(" ", ".") + "@mimosa.co.zw";
                ApproverStage4 = "$DEPT_HEAD$";
                ApproverStage5 = ApproverHr.replaceAll(" ", ".") + "@mimosa.co.zw";
            }else{
                ApproverStage2 = ApproverHos.replaceAll(" ", ".") + "@mimosa.co.zw";
                ApproverStage3 = "$DEPT_HEAD$";
                ApproverStage4 = ApproverHr.replaceAll(" ", ".") + "@mimosa.co.zw";
            }
        }
    }

}
