package bms;

import com.opensymphony.xwork2.ActionSupport;

public class DeleteBook extends ActionSupport {
    private String ISBN;
    private String message, errorMessage;
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public String getMessage() {
        return message;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public String execute() {
        message = "删除图书成功";
        errorMessage = "删除图书失败";
        if (ISBN != null) {
            ISBN = ISBN.replaceAll("[^0-9xX]", "").trim();
            if (ISBN.length() == 0)
                ISBN = null;
        }
        if (ISBN == null || ISBN.length() > DBHelper.ISBN_maxlen) {
            errorMessage = errorMessage + "，ISBN不合法";
            return ERROR;
        }
        if (DBHelper.findBookByISBN(ISBN).isEmpty()) {
            errorMessage = errorMessage + "，本书不存在";
            return ERROR;
        }
        if (!DBHelper.deleteBookByISBN(ISBN))
            return ERROR;
        return SUCCESS;
    }
}
