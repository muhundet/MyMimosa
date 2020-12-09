package zw.co.mimosa.mymimosa.utilities;

public class LeaveFormTrackerUtility {
    private static LeaveFormTrackerUtility instance = null;
    public String employeeId;
    public String WhatAreYouApplyingFor, WhoAreYouApplyingFor;

    private LeaveFormTrackerUtility()
    {

    }

    public static LeaveFormTrackerUtility getInstance()
    {
        if (instance == null)
            instance = new LeaveFormTrackerUtility();

        return instance;
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
}

