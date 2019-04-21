package lunchbox.csy.com.lunchbox.model;

import java.util.Arrays;
import java.util.List;

import lunchbox.csy.com.lunchbox.item.SearchItem;

public class SearchResult {
    //public List<SearchItem> searchItem;
    public String[] restNm;
    public String message;
    public boolean result;
    public int code;

    @Override
    public String toString() {
        return "SearchResult{" +
                "restNm=" + Arrays.toString(restNm) +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", code=" + code +
                '}';
    }

    public String[] getRestNm() {
        return restNm;
    }

    public void setRestNm(String[] restNm) {
        this.restNm = restNm;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
