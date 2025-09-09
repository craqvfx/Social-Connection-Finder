package DatabaseConnect;

public class Movie
{
    private Integer ID;
    private String Title;

    public Movie(Integer ID, String Title)
    {
        this.ID = ID;
        this.Title = Title;
    }

    public Integer ID(){ return  ID; }

    public String Title() { return Title; }
}
