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
}
