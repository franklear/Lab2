package bms;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.Map;

public class FindBook extends ActionSupport {
    private List<Map<String, Object>> tableContent;
    private String authorName;
    public List<Map<String, Object>> getTableContent() {
        return tableContent;
    }
    public void setTableContent(List<Map<String, Object>> tableContent) {
        this.tableContent = tableContent;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String execute() {
        tableContent = DBHelper.findBookByAuthorNameFuzzily(authorName);
        return SUCCESS;
    }
}
