package yaohf.com.model.bean;

import java.io.Serializable;

/**
 * Created by viqgd on 2017/2/23.
 */

public class Result implements Serializable{


    private int resultcode;
    private String reason;
    private String result;

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultcode=" + resultcode +
                ", reason='" + reason + '\'' +
                ", result='" + result + '\'' +
                ", error_code=" + error_code +
                '}';
    }

    private int error_code;
}
