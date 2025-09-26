package DatabaseConnect;

public class User
{
    private Integer ID;
    private String Name;
    private int CompanyID;
    private String Email;
    private String Password;

    public User(Integer id, String name)
    {
        ID = id;
        Name = name;
    }

    public User(Integer id, String name, int companyID)
    {
        ID = id;
        Name = name;
        CompanyID = companyID;
    }

    public User(String name, String email, String password)
    {
        Name = name;
        Email = email;
        Password = password;
    }

    public User(Integer id, String name, int companyID, String email, String password)
    {
        ID = id;
        Name = name;
        CompanyID = companyID;
        Email = email;
        Password = password;
    }

    public int ID(){ return ID; }

    public String Name() { return Name; }
    public void setName(String name) { this.Name = name; }

    public int CompanyID() { return CompanyID; }
    public void setCompanyID(int companyID) { this.CompanyID = companyID; }

    public String Email() { return Email; }
    public void setEmail(String email) { this.Email = email; }

    public String Password() { return Password; }
    public void setPassword(String password) { this.Password = password; }
}
