package bms;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.sql.Date;
import java.util.Map;

public class ShowBook extends ActionSupport {
    private String ISBN;
    private String Title;
    private Integer AuthorID;
    private String Publisher;
    private Date PublishDate;
    private Double Price;
    private String Name;
    private Integer Age;
    private String Country;
    private String errorMessage;
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public String getTitle() {
        return Title;
    }
    public Integer getAuthorID() {
        return AuthorID;
    }
    public String getPublisher() {
        return Publisher;
    }
    public Date getPublishDate() {
        return PublishDate;
    }
    public Double getPrice() {
        return Price;
    }
    public String getName() {
        return Name;
    }
    public Integer getAge() {
        return Age;
    }
    public String getCountry() {
        return Country;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
   public String execute() {
       errorMessage = "查询图书详细信息失败";
       if (ISBN != null) {
           ISBN = ISBN.replaceAll("[^0-9xX]", "").trim();
           if (ISBN.length() == 0)
               ISBN = null;
       }
       if (ISBN == null) {
           errorMessage = errorMessage + "，ISBN中有效字符不能为空";
           return ERROR;
       }
       if (ISBN.length() > DBHelper.ISBN_maxlen) {
           errorMessage = errorMessage + "，ISBN过长";
           return ERROR;
       }
       Map<String, Object> bookInfo = DBHelper.findBookByISBN(ISBN);
       if (bookInfo.isEmpty()) {
           errorMessage = errorMessage + "，本书不存在";
           return ERROR;
       }
       Title = (String)bookInfo.get("Title");
       AuthorID = (Integer) bookInfo.get("AuthorID");
       Publisher = (String)bookInfo.get("Publisher");
       PublishDate = (Date) bookInfo.get("PublishDate");
       Price = (Double) bookInfo.get("Price");
       Map<String, Object> authorInfo = DBHelper.findAuthorByAuthorID(AuthorID);
       if (authorInfo.isEmpty()) {
           errorMessage = errorMessage + "，作者不存在";
           return ERROR;
       }
       Name = (String)authorInfo.get("Name");
       Age = (Integer)authorInfo.get("Age");
       Country = (String)authorInfo.get("Country");
       ActionContext.getContext().put("PublishDate", PublishDate);
       return SUCCESS;
   }
}
