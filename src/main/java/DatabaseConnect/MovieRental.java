package DatabaseConnect;

public class MovieRental
{
    private Integer ID;
    private Integer CustomerID;
    private Integer MovieID;
    private String RentalDate;

    public MovieRental(Integer RentalID, Integer CustomerID, Integer MovieID, String RentalDate)
    {
        this.ID = RentalID;
        this.CustomerID = CustomerID;
        this.MovieID = MovieID;
        this.RentalDate = RentalDate; //assumes is given in correct format
    }

    public Integer ID(){ return  ID; }

    public Integer CustomerID() { return CustomerID; }

    public Integer MovieID() { return MovieID; }

    public String RentalDate() { return RentalDate; }
}
