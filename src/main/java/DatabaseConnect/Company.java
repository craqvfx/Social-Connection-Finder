package DatabaseConnect;

/*
 * Company class representing Company table in DB
 * For database storage and retrieval
 * Stores ID, Name, and IndustryID
 */

public class Company
{
    private Integer ID;
    private String Name;
    private Integer IndustryID;

    public Company(String Name, Integer IndustryID)
    {
        this.Name = Name;
        this.IndustryID = IndustryID;
    }

    public Company(Integer ID, String Name, Integer IndustryID)
    {
        this.ID = ID;
        this.Name = Name;
        this.IndustryID = IndustryID;
    }

    public Integer ID(){ return  ID; }

    public String Name() { return Name; }

    public Integer IndustryID() { return IndustryID; }
}
