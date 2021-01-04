package zw.co.mimosa.mymimosa.ui.harare_office.petty_cash_authorisation_harare_office;

import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationHelper;

public class PettyCashAuthorisationHarareHelper {
    private static PettyCashAuthorisationHarareHelper mPettyCashAuthorisationHarareHelper = null;

    public static PettyCashAuthorisationHarareHelper getPettyCashAuthorisationInstance(){
        if(mPettyCashAuthorisationHarareHelper== null){
            mPettyCashAuthorisationHarareHelper = new PettyCashAuthorisationHarareHelper();
        }
        return (PettyCashAuthorisationHarareHelper) mPettyCashAuthorisationHarareHelper;
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

    public static PettyCashAuthorisationHarareHelper getmPettyCashAuthorisationHarareHelper() {
        return mPettyCashAuthorisationHarareHelper;
    }

    public static void setmPettyCashAuthorisationHarareHelper(PettyCashAuthorisationHarareHelper mPettyCashAuthorisationHarareHelper) {
        PettyCashAuthorisationHarareHelper.mPettyCashAuthorisationHarareHelper = mPettyCashAuthorisationHarareHelper;
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

    public void setPettyCashAuthorisationApprovers(){
        ApproverStage1 = "$CORPORATE_ACCOUNTING_MANAGER$";
    }
}
