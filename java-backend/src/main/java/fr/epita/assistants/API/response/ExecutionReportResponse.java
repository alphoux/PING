package fr.epita.assistants.API.response;

public class ExecutionReportResponse {
    private boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ExecutionReportResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }    
}
