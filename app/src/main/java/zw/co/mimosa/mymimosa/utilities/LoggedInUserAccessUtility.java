package zw.co.mimosa.mymimosa.utilities;

public class LoggedInUserAccessUtility {
    private static LoggedInUserAccessUtility instance = null;

    public String employeeId;

    private LoggedInUserAccessUtility()
    {

    }


    public static LoggedInUserAccessUtility getInstance()
    {
        if (instance == null)
            instance = new LoggedInUserAccessUtility();

        return instance;
    }

    public void setEmployeeId(String empId){
        employeeId = empId;
    }

   public String getEmployeeId(){
        return employeeId;
    }
}
