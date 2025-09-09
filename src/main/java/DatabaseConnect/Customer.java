package DatabaseConnect;

public class Customer
{
    private Integer ID;
    private String CustomerName;
    private String PhoneNumber;
    private String DateOfBirth;
    private Integer HouseNumber;
    private String Postcode;

    public Customer(Integer id, String name, String phoneNumber, String dateOfBirth, String address)
    {
        ID = id;
        CustomerName = name;
        PhoneNumber = phoneNumber; //assumes is given in correct format
        DateOfBirth = dateOfBirth; //assumes is given in correct format

        String[] parts = address.split(",");//split address by commas
        this.HouseNumber = Integer.parseInt(parts[0].trim());//get house number assuming it comes first
        this.Postcode = parts[parts.length - 1].trim(); //get postcode assuming it comes last

    }

    public int ID(){ return  ID; }

    public String Name() { return CustomerName; }

    public Integer HouseNumber() { return HouseNumber; }

    public String Postcode() { return Postcode; }

    public String PhoneNumber() { return PhoneNumber; }

    public String DateOfBirth() { return DateOfBirth; }
}
