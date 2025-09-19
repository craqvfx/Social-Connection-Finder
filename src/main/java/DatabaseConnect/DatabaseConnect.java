package DatabaseConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Graph.*;

public class DatabaseConnect implements IDatabaseConnect
{
    private Connection conn = null;

    public DatabaseConnect()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");//Specify the SQLite Java driver
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/connections.db");//Specify the database, since relative in the main project folder
            conn.setAutoCommit(false);// Important as you want control of when data is written
            System.out.println("Opened database successfully");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    @Override
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

    @Override
    public boolean loadGraph(Graph graph)
    {
        boolean success = false;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Connections;");

            while(rs.next())
            {
                int weight = rs.getInt("RelationshipStrength");
                int sourceNodeID = rs.getInt("FriendCodeFrom");
                String sourceNodeName = getUser(sourceNodeID).Name();
                int endNodeID = rs.getInt("FriendCodeTo");
                String endNodeName = getUser(endNodeID).Name();

                graph.add(sourceNodeName, endNodeName, weight);
                System.out.println("Adding node: " + sourceNodeName + " " + weight + " " + endNodeName + "\n");
            }

            stmt.close();
            conn.commit();
            success = true;
            System.out.println("Graph loaded successfully");
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return success;
    }

    //helper to return a customer object given an int FriendCode //overloaded
    @Override
    public User getUser(int FriendCode)
    {
        Statement stmt = null;
        ResultSet rs = null;
        User user = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM User WHERE FriendCode = '" + FriendCode + "';");
            String Name = rs.getString("Name");
            int CompanyID = rs.getInt("CompanyID");
            
            user = new User(FriendCode, Name, CompanyID);
            
            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return user;
    }

    //helper to return a user given a string email and password //overloaded
    public User getUser(String Email, String Password) // TODO
    {
        Statement stmt = null;
        ResultSet rs = null;
        User user = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM User WHERE Email = '" + Email + "';");// TODO: make sql query
            Integer FriendCode = rs.getInt("FriendCode");
            String Name = rs.getString("Name");
            int companyID = rs.getInt("FriendCode");

            user = new User(FriendCode, Name, companyID);

            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return user;
    }

    @Override
    public boolean addUser(User user)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO User (FriendCode,Name,CompanyID) "
                        + "VALUES ('" + user.FriendCode() +"', '" + user.Name() + "', '" + user.CompanyID() + "');";
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

    @Override
    public boolean deleteUser(User user)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "DELETE FROM User "// TODO: make query // TODO: make sql clean up email/password entries aswell
                        + "WHERE CustomerName = '" + user.Name() + "';";
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

    @Override
    public boolean addConnection(int from, int to, int weight)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO Connections (FriendCodeFrom, FriendCodeTo, RelationshipStrength)"// TODO: update sql, documentation
            + "VALUES (" + from + ", " + to + ", " + weight + ");";

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

    @Override
    public boolean deleteConnection(int from, int to)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "DELETE FROM Connections WHERE FriendCodeFrom = " + from + ", FriendCodeTo = " + to + ";";
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

    @Override
    public boolean modifyPassword(int FriendCode, String NewPassword)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "UPDATE LoginInfo"
            + " SET Password = " + NewPassword
            + " WHERE FriendCode = " + FriendCode + ";";
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

    @Override
    public boolean modifyEmail(int FriendCode, String newEmail) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyEmail'");
    }

    @Override
    public boolean modifyName(int FriendCode, String newName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyName'");
    }

    @Override
    public boolean addCompany(Company company)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO Company (ID,Name,IndustryID) "
                    + "VALUES (" + company.ID() +", " + company.Name() + ", '" + company.IndustryID() + "');";
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

    @Override
    public ArrayList<User> getUsers(String industry) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsers'");
    }

    @Override
    public ArrayList<User> getUsers(Integer companyID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsers'");
    }
}
