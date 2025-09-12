package DatabaseConnect;

public class Company
{
    private Integer ID;
    private Integer Name;    // TO-DO: Link to Industry table and return string name
    private Integer IndustryID;

    public Company(Integer ID, Integer Name, Integer IndustryID)
    {
        this.ID = ID;
        this.Name = Name;
        this.IndustryID = IndustryID;
    }

    public Integer ID(){ return  ID; }

    public Integer Name() { return Name; }

    public Integer IndustryID() { return IndustryID; }
}
