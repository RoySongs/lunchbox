package lunchbox.csy.com.lunchbox.item;

public class SearchItem {
    private String restNm;
    private String menuNm;
    private String category;

    public String getRestNm() {
        return restNm;
    }

    public void setRestNm(String restNm) {
        this.restNm = restNm;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public SearchItem(String restNm, String menuNm, String category) {
        this.restNm = restNm;
        this.menuNm = menuNm;
        this.category = category;
    }
}
