package DatabaseConnect;

public class User
{
    private Integer FriendCode;
    private String Name;
    private int CompanyID;
    private String Email;
    private String Password;

    public User(Integer friendCode, String name)
    {
        FriendCode = friendCode;
        Name = name;
    }

    public User(Integer friendCode, String name, int companyID)
    {
        FriendCode = friendCode;
        Name = name;
        CompanyID = companyID;
    }

    public User(Integer friendCode, String name, int companyID, String email, String password)
    {
        FriendCode = friendCode;
        Name = name;
        CompanyID = companyID;
        Email = email;
        Password = password;
    }

    public int FriendCode(){ return FriendCode; }

    public String Name() { return Name; }
    public void setName(String name) { this.Name = name; }

    public int CompanyID() { return CompanyID; }
    public void setCompanyID(int companyID) { this.CompanyID = companyID; }

    public String Email() { return Email; }
    public void setEmail(String email) { this.Email = email; }

    public String Password() { return Password; }
    public void setPassword(String password) { this.Password = password; }
}
