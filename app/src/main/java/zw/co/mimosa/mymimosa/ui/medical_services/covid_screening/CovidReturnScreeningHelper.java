package zw.co.mimosa.mymimosa.ui.medical_services.covid_screening;

public class CovidReturnScreeningHelper {
    private static CovidReturnScreeningHelper mCovidReturnScreeningHelper = null;

    public static CovidReturnScreeningHelper getCovidReturnToWorkHelperInstance(){
        if(mCovidReturnScreeningHelper == null){
            mCovidReturnScreeningHelper = new CovidReturnScreeningHelper();
        }
        return (CovidReturnScreeningHelper) mCovidReturnScreeningHelper;
    }

    String firstname;
    String surname;
    String employeeId;
    String department;
    String section;
    String emailId;
    String designation;
    long dateOfEngagement;
    long dateOfBirth;
    String gender;
    String companyName;
    String IdNumber;
    String cellNumber;
    String nextOfKin;
    String address;
    String template;
    String description;


    public static CovidReturnScreeningHelper getmCovidReturnScreeningHelper() {
        return mCovidReturnScreeningHelper;
    }

    public static void setmCovidReturnScreeningHelper(CovidReturnScreeningHelper mCovidReturnScreeningHelper) {
        CovidReturnScreeningHelper.mCovidReturnScreeningHelper = mCovidReturnScreeningHelper;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
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

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIdNumber() {
        return IdNumber;
    }

    public void setIdNumber(String idNumber) {
        IdNumber = idNumber;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
