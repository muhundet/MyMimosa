package zw.co.mimosa.mymimosa.ui.hr.overtime_authorisation;

import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidReturnScreeningHelper;

public class OvertimeAuthorisationHelper {
    private static OvertimeAuthorisationHelper mOvertimeAuthorisationHelper = null;

    public static OvertimeAuthorisationHelper getOvertimeAuthorisationInstance(){
        if(mOvertimeAuthorisationHelper == null){
            mOvertimeAuthorisationHelper = new OvertimeAuthorisationHelper();
        }
        return (OvertimeAuthorisationHelper) mOvertimeAuthorisationHelper;
    }

    String firstname;
    String surname;
    String employeeId;
    String department;
    String section;
    String emailId;
    String designation;
    long dateOfEngagement;

    public static OvertimeAuthorisationHelper getmOvertimeAuthorisationHelper() {
        return mOvertimeAuthorisationHelper;
    }

    public static void setmOvertimeAuthorisationHelper(OvertimeAuthorisationHelper mOvertimeAuthorisationHelper) {
        OvertimeAuthorisationHelper.mOvertimeAuthorisationHelper = mOvertimeAuthorisationHelper;
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
}
