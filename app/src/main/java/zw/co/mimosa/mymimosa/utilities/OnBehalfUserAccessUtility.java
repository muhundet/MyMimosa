package zw.co.mimosa.mymimosa.utilities;

public class OnBehalfUserAccessUtility {
    private static OnBehalfUserAccessUtility instance = null;

    public String employeeId;

    private OnBehalfUserAccessUtility()
    {

    }


    public static OnBehalfUserAccessUtility getInstance()
    {
        if (instance == null)
            instance = new OnBehalfUserAccessUtility();

        return instance;
    }

    public void setEmployeeId(String empId){
        employeeId = empId;
    }

    public String getEmployeeId(){
        return employeeId;
    }
}
