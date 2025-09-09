package DatabaseConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import Graph.*;

public class DatabaseConnect
{
    private Connection conn = null;

    public DatabaseConnect()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");//Specify the SQLite Java driver
            conn = DriverManager.getConnection("jdbc:sqlite:AQA Movie DB.db");//Specify the database, since relative in the main project folder
            conn.setAutoCommit(false);// Important as you want control of when data is written
            System.out.println("Opened database successfully");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    public void close() 
    {
        try
        {
            conn.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean loadGraph(Graph graph)
    {
        boolean success = false;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM AdjacencyList;");
            System.out.println("DB AdjacencyList queried successfully");

            while(rs.next())
            {
                int weight = rs.getInt("Weight");
                System.out.println("Weight: " + weight);
                int sourceNodeID = rs.getInt("ToCustomerID");
                String sourceNodeName = selectCustomer(sourceNodeID).Name();
                System.out.println("ToCustomer name retrieved: " + sourceNodeName);
                int endNodeID = rs.getInt("FromCustomerID");
                String endNodeName = selectCustomer(endNodeID).Name();
                System.out.println("FromCustomer name retrieved: " + endNodeName);
                graph.add(sourceNodeName, endNodeName, weight);

                System.out.println("Adding node: " + sourceNodeName + " " + weight + " " + endNodeName + "\n");
            }

            stmt.close();
            conn.commit();
            success = true;
            System.out.println("Graph loaded successfully");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return success;
    }

    public boolean addEdge(int from, int to, int weight)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO AdjacencyList (FromCustomerID, Weight, ToCustomerID) "
                    //+ "VALUES ('" + from.ID() +"', " + weight + ", '" + to.ID() + "');";
            + "VALUES (" + from + ", " + weight + ", " + to + ");";

            stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            success = true;
            System.out.println("Insert successful");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return success;
    }

    //helper to return a customer object given an int ID //overloaded
    public Customer selectCustomer(int ID)
    {
        Statement stmt = null;
        ResultSet rs = null;
        Customer customer = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Customer WHERE ID = '" + ID + "';");
            String CustomerName = rs.getString("CustomerName");
            String HouseNumber = rs.getString("HouseNumber");
            Integer Postcode = rs.getInt("Postcode");
            String PhoneNumber = rs.getString("PhoneNumber");
            String DateOfBirth = rs.getString("DateOfBirth");

            String CustomerAddress = HouseNumber + ", " + Postcode;
            customer = new Customer(ID, CustomerName, PhoneNumber, DateOfBirth, CustomerAddress);

            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return customer;
    }

    //helper to return a customer object given a string name //overloaded
    public Customer selectCustomer(String name)
    {
        Statement stmt = null;
        ResultSet rs = null;
        Customer customer = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Customer WHERE CustomerName = '" + name + "';");
            Integer ID = rs.getInt("ID");
            String CustomerName = rs.getString("CustomerName");
            String HouseNumber = rs.getString("HouseNumber");
            Integer Postcode = rs.getInt("Postcode");
            String PhoneNumber = rs.getString("PhoneNumber");
            String DateOfBirth = rs.getString("DateOfBirth");

            String CustomerAddress = HouseNumber + ", " + Postcode;
            customer = new Customer(ID, CustomerName, PhoneNumber, DateOfBirth, CustomerAddress);

            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return customer;
    }

    public Movie selectMovie(String title)
    {
        Statement stmt = null;
        ResultSet rs = null;
        Movie movie = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ID FROM Movie WHERE Title = '" + title + "';");
            Integer ID = rs.getInt("ID");

            movie = new Movie(ID, title);

            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return movie;
    }

    public boolean addCustomer(Customer customer)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO Customer (CustomerName,HouseNumber,Postcode,PhoneNumber,DateOfBirth) "
                        + "VALUES ('" + customer.Name() +"', " + customer.HouseNumber() + ", '" + customer.Postcode() + "', '" + customer.PhoneNumber() + "', '" + customer.DateOfBirth() + "');";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            success = true;
            System.out.println("Insert successful");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return success;
    }

    public boolean modifyCustomer(Customer customer)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "UPDATE Customer SET HouseNumber= " + customer.HouseNumber() + ", Postcode = '" + customer.Postcode() + "' WHERE CustomerName = '" + customer.Name()  + "';";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            success = true;
            System.out.println("Update successful");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return success;
    }

    public boolean deleteCustomer(Customer customer)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "DELETE FROM Customer "
                        + "WHERE CustomerName = '" + customer.Name() + "';";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            success = true;
            System.out.println("Delete successful");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return success;
    }

    public boolean addRental(MovieRental rental)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO MovieRental (CustomerID,MovieID,RentalDate) "
                    + "VALUES (" + rental.CustomerID() +", " + rental.MovieID() + ", '" + rental.RentalDate() + "');";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            success = true;
            System.out.println("Insert successful");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return success;
    }

    public boolean displayOverdueFees(Customer customer)
    {
        boolean success = false;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "SELECT CustomerID, MovieID, RentalDate," +
                    "    julianday('now') - julianday(RentalDate) AS DaysRented," +
                    "    BuyPrice AS MoviePrice," +
                    "    Duration AS AllowedDuration," +
                    "    CASE " +
                    "        WHEN (julianday('now') - julianday(RentalDate)) > Duration THEN " +
                    "            ROUND((julianday('now') - julianday(RentalDate) - Duration),0)" +
                    "        ELSE 0" +
                    "    END AS OverdueDays," +
                    "    CASE " +
                    "        WHEN (julianday('now') - julianday(RentalDate) - Duration) < 7 THEN " +
                    "            ROUND((FeeUnderOneWeek * BuyPrice),2)" +
                    "        WHEN (julianday('now') - julianday(RentalDate) - Duration) >= 7 THEN " +
                    "            ROUND((FeeOneWeekOrMore * BuyPrice),2)" +
                    "        ELSE 0" +
                    "    END AS OverdueFee" +
                    "    FROM MovieRental, Movie, OverdueFee, Customer" +
                    "    WHERE MovieID = Movie.ID AND" +
                    "        Movie.Rating = OverdueFee.Rating AND" +
                    "        (julianday('now') - julianday(RentalDate)) > Duration AND" +
                    "        CustomerName = '" + customer.Name() + "';";

            rs = stmt.executeQuery(sql);
            System.out.print("CustomerID" +
                    "  |  " + "MovieID" +
                    "  |  " + "RentalDate" +
                    "  |  " + "MoviePrice" +
                    "  |  " + "AllowedDuration" +
                    "  |  " + "OverdueDays" +
                    "  |  " + "OverdueFee" +
                    "  |  ");
            System.out.println();

            //System.out.println(rs.getInt("ID"));
            System.out.print(rs.getInt("CustomerID") + "           |  ");
            System.out.print(rs.getInt("MovieID") + "        |  ");
            System.out.print(rs.getString("RentalDate") + "  |  ");
            System.out.print(rs.getInt("MoviePrice") + "          |  ");
            System.out.print(rs.getInt("AllowedDuration") + "                |  ");
            System.out.print(rs.getInt("OverdueDays") + "         |  ");
            System.out.print(rs.getInt("OverdueFee") + "           |  ");
            System.out.println();

            stmt.close();
            conn.commit();
            success = true;
            System.out.println("Display successful");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return success;
    }
}
