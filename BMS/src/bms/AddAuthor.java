package bms;

import com.opensymphony.xwork2.ActionSupport;

public class AddAuthor extends ActionSupport {
    private String Name;
    private Integer Age;
    private String Country;
    private String message, errorMessage;
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public Integer getAge() {
        return Age;
    }
    public void setAge(Integer age) {
        if (age == null || age <= 0)
            Age = null;
        else
            Age = age;
    }
    public String getCountry() {
        return Country;
    }
    public void setCountry(String country) {
        Country = country;
    }
    public String getMessage() {
        return message;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public String execute() {
        errorMessage = "添加作者失败";
        message = "添加作者成功";
        if (Name != null){
            Name = Name.trim();
            if (Name.length() == 0)
                Name = null;
        }
        if (Country != null){
            Country = Country.trim();
            if (Country.length() == 0)
                Country = null;
        }
        if (Name == null) {
            errorMessage = errorMessage + "，姓名不能为空";
            return ERROR;
        }
        if (Name.length() > DBHelper.Name_maxlen) {
            errorMessage = errorMessage + "，姓名过长";
            return ERROR;
        }
        if (Country != null && Country.length() > DBHelper.Country_maxlen) {
            errorMessage = errorMessage + "，国籍过长";
            return ERROR;
        }
        if (!DBHelper.addAuthor(0, Name, Age, Country))
            return ERROR;
        return SUCCESS;
    }
}
