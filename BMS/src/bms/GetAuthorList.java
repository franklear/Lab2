package bms;

import com.opensymphony.xwork2.ActionSupport;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetAuthorList extends ActionSupport {
    private String name;
    private List<Map<String, Object>> authorListRaw;
    private List<Pair<Integer, String>> authorList;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Pair<Integer, String>> getAuthorList() {
        return authorList;
    }
    public String execute() {
        authorListRaw = DBHelper.findAuthorByNameFuzzily(name);
        authorList = new ArrayList<>();
        for (Map<String, Object> m : authorListRaw) {
            StringBuilder sb = new StringBuilder();
            sb.append(m.get("Name"));
            if (m.containsKey("Age")) {
                Integer t = (Integer)m.get("Age");
                if (t != null) {
                    sb.append(", ");
                    sb.append(t);
                    sb.append("岁");
                }
            }
            if (m.containsKey("Country")) {
                String t = (String)m.get("Country");
                if (t != null) {
                    sb.append(", ");
                    sb.append(t);
                }
            }
            authorList.add(new Pair<>((Integer)m.get("AuthorID"), sb.toString()));
        }
        return SUCCESS;
    }
}
