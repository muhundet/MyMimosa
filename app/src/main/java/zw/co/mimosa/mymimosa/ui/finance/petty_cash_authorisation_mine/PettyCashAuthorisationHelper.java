package zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine;

import zw.co.mimosa.mymimosa.ui.hr.acting_allowance.ActingAllowanceHelper;

public class PettyCashAuthorisationHelper {
    private static PettyCashAuthorisationHelper mPettyCashAuthorisationHelper = null;

    public static PettyCashAuthorisationHelper getPettyCashAuthorisationInstance(){
        if(mPettyCashAuthorisationHelper== null){
            mPettyCashAuthorisationHelper = new PettyCashAuthorisationHelper();
        }
        return (PettyCashAuthorisationHelper) mPettyCashAuthorisationHelper;
    }

    String firstname;
    String surname;
    String employeeId;
    String department;
    String section;
    String emailId;
    String designation;
    String currency;
    String amountInWords;
    long amountInFigures;
    String costCentre;
    String managementAccountingApprover;
    String  ApproverStage1;
    String  ApproverStage2;
    String  ApproverStage3;
    String  ApproverStage4;

    public static PettyCashAuthorisationHelper getmPettyCashAuthorisationHelper() {
        return mPettyCashAuthorisationHelper;
    }

    public static void setmPettyCashAuthorisationHelper(PettyCashAuthorisationHelper mPettyCashAuthorisationHelper) {
        PettyCashAuthorisationHelper.mPettyCashAuthorisationHelper = mPettyCashAuthorisationHelper;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public long getAmountInFigures() {
        return amountInFigures;
    }

    public void setAmountInFigures(long amountInFigures) {
        this.amountInFigures = amountInFigures;
    }

    public String getCostCentre() {
        return costCentre;
    }

    public void setCostCentre(String costCentre) {
        this.costCentre = costCentre;
    }

    public String getManagementAccountingApprover() {
        return managementAccountingApprover;
    }

    public void setManagementAccountingApprover(String managementAccountingApprover) {
        this.managementAccountingApprover = managementAccountingApprover;
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

    public void setPettyCashAuthorisationApprovers(){
        ApproverStage1 = "$DEPT_HEAD$";
        ApproverStage2 = "$SECTION_HEAD_FINANCE$";
        ApproverStage3 = "$FINANCIAL_ACCOUNTANT$";
        ApproverStage4 = managementAccountingApprover.replaceAll(" ", ".") + "@mimosa.co.zw";
    }
}
