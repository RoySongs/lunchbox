package lunchbox.csy.com.lunchbox.model;

import lunchbox.csy.com.lunchbox.item.SearchItem;

public class SearchResult {
    public SearchItem searchItem;
    public String message;
    public boolean result;
    public int code;


    public String getMessage() {
        return message;
    }

    public boolean isResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "searchItem=" + searchItem +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", code=" + code +
                '}';
    }
}
