package zw.co.mimosa.mymimosa.ui.harare_office.fuel_request;

import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationHelper;

public class FuelRequestHelper {
    private static FuelRequestHelper mFueRequestHelper = null;

    public static FuelRequestHelper getRequestInstance(){
        if(mFueRequestHelper== null){
            mFueRequestHelper = new FuelRequestHelper();
        }
        return (FuelRequestHelper) mFueRequestHelper;
    }

    String firstname;
    String surname;
    String employeeId;
    String department;
    String section;
    String emailId;
    String designation;
    String grade;
    String requestType;
    String vehicleReg;
    String Account;
    String Reason;

    public static FuelRequestHelper getmFueRequestHelper() {
        return mFueRequestHelper;
    }

    public static void setmFueRequestHelper(FuelRequestHelper mFueRequestHelper) {
        FuelRequestHelper.mFueRequestHelper = mFueRequestHelper;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getVehicleReg() {
        return vehicleReg;
    }

    public void setVehicleReg(String vehicleReg) {
        this.vehicleReg = vehicleReg;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public void setFuelRequestApprovers(){

    }
}
