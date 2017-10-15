package bms;

import com.opensymphony.xwork2.ActionSupport;

import java.sql.Date;

public class UpdateBook extends ActionSupport {
    private String ISBN;
    private String Title;
    private Integer AuthorID;
    private String Publisher;
    private Date PublishDate;
    private Double Price;
    private String message, errorMessage;
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public Integer getAuthorID() {
        return AuthorID;
    }
    public void setAuthorID(Integer authorID) {
        AuthorID = authorID;
    }
    public String getPublisher() {
        return Publisher;
    }
    public void setPublisher(String publisher) {
        Publisher = publisher;
    }
    public Date getPublishDate() {
        return PublishDate;
    }
    public void setPublishDate(Date publishDate) {
        if (publishDate == null)
            PublishDate = null;
        else
            PublishDate = publishDate;
    }
    public Double getPrice() {
        return Price;
    }
    public void setPrice(Double price) {
        if (price == null)
            Price = null;
        else
            Price = price;
    }
    public String getMessage() {
        return message;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public String execute() {
        message = "更新图书信息成功";
        errorMessage = "更新图书信息失败";
        if (ISBN != null) {
            ISBN = ISBN.replaceAll("[^0-9xX]", "").trim();
            if (ISBN.length() == 0)
                ISBN = null;
        }
        if (Title != null) {
            Title = Title.trim();
            if (Title.length() == 0)
                Title = null;
        }
        if (Publisher != null) {
            Publisher = Publisher.trim();
            if (Publisher.length() == 0)
                Publisher = null;
        }
        if (ISBN == null) {
            errorMessage = errorMessage + "，ISBN中有效字符不能为空";
            return ERROR;
        }
        if (ISBN.length() > DBHelper.ISBN_maxlen) {
            errorMessage = errorMessage + "，ISBN过长";
            return ERROR;
        }
        if (Title == null) {
            errorMessage = errorMessage + "，书名不能为空";
            return ERROR;
        }
        if (Title.length() > DBHelper.Title_maxlen) {
            errorMessage = errorMessage + "，书名过长";
            return ERROR;
        }
        if (Publisher != null && Publisher.length() > DBHelper.Publisher_maxlen) {
            errorMessage = errorMessage + "，出版社过长";
            return ERROR;
        }
        if (Price == null) {
            errorMessage = errorMessage + "，单价不能为空";
            return ERROR;
        }
        if (!DBHelper.updateBookByISBN(ISBN, Title, AuthorID, Publisher, PublishDate, Price))
            return ERROR;
        return SUCCESS;
    }
}
