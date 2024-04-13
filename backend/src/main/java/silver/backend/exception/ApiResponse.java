package silver.backend.exception;


public class ApiResponse {

    private String msg;
    private String url;

    public ApiResponse(String msg, String url){
        this.msg=msg;
        this.url=url;
        //url.replace("uri=", "");
    }
}
