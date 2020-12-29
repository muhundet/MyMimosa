package zw.co.mimosa.mymimosa.ui.hr.acting_allowance;

import zw.co.mimosa.mymimosa.ui.hr.educational_assistance.EducationalAssistanceHelper;

public class ActingAllowanceHelper {
    private static ActingAllowanceHelper mActingAllowanceHelper = null;

    public static ActingAllowanceHelper getActingAllowanceInstance(){
        if(mActingAllowanceHelper == null){
            mActingAllowanceHelper = new ActingAllowanceHelper();
        }
        return (ActingAllowanceHelper) mActingAllowanceHelper;
    }

    String firstname;
    String surname;
    String employeeId;
    String department;
    String section;
    String emailId;
    String designation;
    long dateOfEngagement;
    String approver;
    String actingPosition;
    String currentGrade;
    String actingGrade;
    long startDate;
    long endDate;
    long effectiveDate;
    String numberOfDaysActedWords;
    long numberOfDaysActedFigures;
    long overtime15Times;
    long overtime20Times;
    long nighShiftAllowance;
    long standbyWeeks;
    String approverHr;
    String  ApproverStage1;
    String  ApproverStage2;
    String  ApproverStage3;
    String  ApproverStage4;
    String  ApproverStage5;

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

    public static ActingAllowanceHelper getmActingAllowanceHelper() {
        return mActingAllowanceHelper;
    }

    public static void setmActingAllowanceHelper(ActingAllowanceHelper mActingAllowanceHelper) {
        ActingAllowanceHelper.mActingAllowanceHelper = mActingAllowanceHelper;
    }

    public String getCurrentGrade() {
        return currentGrade;
    }

    public void setCurrentGrade(String currentGrade) {
        this.currentGrade = currentGrade;
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

    public long getDateOfEngagement() {
        return dateOfEngagement;
    }

    public void setDateOfEngagement(long dateOfEngagement) {
        this.dateOfEngagement = dateOfEngagement;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getActingPosition() {
        return actingPosition;
    }

    public void setActingPosition(String actingPosition) {
        this.actingPosition = actingPosition;
    }

    public String getActingGrade() {
        return actingGrade;
    }

    public void setActingGrade(String actingGrade) {
        this.actingGrade = actingGrade;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public long getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(long effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getNumberOfDaysActedWords() {
        return numberOfDaysActedWords;
    }

    public void setNumberOfDaysActedWords(String numberOfDaysActedWords) {
        this.numberOfDaysActedWords = numberOfDaysActedWords;
    }

    public long getNumberOfDaysActedFigures() {
        return numberOfDaysActedFigures;
    }

    public void setNumberOfDaysActedFigures(long numberOfDaysActedFigures) {
        this.numberOfDaysActedFigures = numberOfDaysActedFigures;
    }

    public long getOvertime15Times() {
        return overtime15Times;
    }

    public void setOvertime15Times(long overtime15Times) {
        this.overtime15Times = overtime15Times;
    }

    public long getOvertime20Times() {
        return overtime20Times;
    }

    public void setOvertime20Times(long overtime20Times) {
        this.overtime20Times = overtime20Times;
    }

    public long getNighShiftAllowance() {
        return nighShiftAllowance;
    }

    public void setNighShiftAllowance(long nighShiftAllowance) {
        this.nighShiftAllowance = nighShiftAllowance;
    }

    public long getStandbyWeeks() {
        return standbyWeeks;
    }

    public void setStandbyWeeks(long standbyWeeks) {
        this.standbyWeeks = standbyWeeks;
    }

    public String getApproverHr() {
        return approverHr;
    }

    public void setApproverHr(String approverHr) {
        this.approverHr = approverHr;
    }

    public void setActingAllowanceApprovers(){
        ApproverStage1 = "$REPORTING_TO$";
        ApproverStage2 = approver.replaceAll(" ", ".") + "@mimosa.co.zw";
        ApproverStage3 = "$DEPT_HEAD$";
        ApproverStage4=approverHr.replaceAll(" ", ".") + "@mimosa.co.zw";
    }
}
