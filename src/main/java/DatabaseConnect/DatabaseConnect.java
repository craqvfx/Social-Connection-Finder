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
                int sourceNodeID = rs.getInt("SourceID");
                String sourceNodeName = getUser(sourceNodeID).Name();
                int endNodeID = rs.getInt("TargetID");
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

    //helper to return a customer object given an int id
    @Override
    public User getUser(int id)
    {
        Statement stmt = null;
        ResultSet rs = null;
        User user = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM User WHERE ID = '" + id + "';");
            int ID = rs.getInt("ID");
            String Name = rs.getString("Name");
            int CompanyID = rs.getInt("CompanyID");
            user = new User(ID, Name, CompanyID);
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
    public User getUser(String Email, String Password)
    {
        Statement stmt = null;
        ResultSet rs = null;
        User user = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM User, LoginInfo"
            + " WHERE User.ID = LoginInfo.ID"
            + "   AND LoginInfo.Email = '" + Email + "'"
            + "   AND LoginInfo.Password = '" + Password + "';");
            int ID = rs.getInt("ID");
            String Name = rs.getString("Name");
            int CompanyID = rs.getInt("CompanyID");
            user = new User(ID, Name, CompanyID);
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
    public boolean addUser(User user) // TODO: update to work regardless of what fields there are
    {
        boolean success = false;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO User (Name, CompanyID) "
                        + "VALUES ('" + user.Name() +"', '" + user.CompanyID() + "');";
            stmt.executeUpdate(sql);

            // Get the last inserted ID
            rs = stmt.executeQuery("SELECT last_insert_rowid() AS ID;");
            int id = -1;
            if (rs.next()) {
                id = rs.getInt("ID");
            }
            rs.close();

            // Insert into LoginInfo using the retrieved ID
            sql = "INSERT INTO LoginInfo (ID, Email, Password) "
                + "VALUES (" + id + ", '" + user.Email() + "', '" + user.Password() + "');";
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
        int ID = user.ID();

        try
        {
            stmt = conn.createStatement();
            String sql = "DELETE FROM Connections WHERE SourceID = '" + ID + "' OR To = '" + ID + "';";
            sql += "DELETE FROM LoginInfo WHERE ID = '" + ID + "';";
            sql += "DELETE FROM User WHERE ID = '" + ID + "';";
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
    public boolean addConnection(int sourceID, int targetID, int weight)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO Connections (SourceID, TargetID, RelationshipStrength)"
            + " VALUES ('" + sourceID + "', '" + targetID + "', '" + weight + "');";

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
    public boolean deleteConnection(int sourceID, int targetID)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "DELETE FROM Connections WHERE SourceID = '" + sourceID + "' AND TargetID = '" + targetID + "';";
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
    public boolean modifyPassword(int id, String NewPassword)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "UPDATE LoginInfo"
            + " SET Password = '" + NewPassword + "'"
            + " WHERE ID = '" + id + "';";
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
    public boolean modifyEmail(int id, String NewEmail)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "UPDATE LoginInfo"
            + " SET Email = '" + NewEmail + "'"
            + " WHERE ID = '" + id + "';";
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
    public boolean modifyName(int id, String NewName)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "UPDATE User"
            + " SET Name = '" + NewName + "'"
            + " WHERE ID = '" + id + "';";
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

    public boolean modifyCompanyID(int id, int companyID)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "UPDATE User"
            + " SET CompanyID = '" + companyID + "'"
            + " WHERE ID = '" + id + "';";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            success = true;
            System.out.println("Update successful");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName()+ ": " + e.getMessage());
        }
    
        return success;
    }



    @Override
    public boolean addCompany(Company company)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO Company (ID,Name) "
                    + "VALUES (" + company.ID() +", '" + company.Name() + "');";
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
    public ArrayList<User> getUsersByIndustry(int IndustryID)
    {
        Statement stmt = null;
        ResultSet rs = null;
        User user = null;
        ArrayList <User> userList = new ArrayList<User>();

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT User.ID, User.Name, User.CompanyID"
            + " FROM User, Company, Industry"
            + " WHERE User.CompanyID = Company.ID"
            + " AND Company.IndustryID = '" + IndustryID + "';");

            while(rs.next())
            {
                int ID = rs.getInt("ID");
                String Name = rs.getString("Name");
                int CompanyID = rs.getInt("CompanyID");
                user = new User(ID, Name, CompanyID);
                userList.add(user);
            }
            
            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return userList;
    }

    @Override
    public ArrayList<User> getUsersByCompany(int CompanyID)
    {
        Statement stmt = null;
        ResultSet rs = null;
        User user = null;
        ArrayList <User> userList = new ArrayList<User>();

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT User.ID, User.Name, User.CompanyID"
            + " FROM User, Company"
            + " WHERE User.CompanyID = '" + CompanyID + "';");

            while(rs.next())
            {
                int ID = rs.getInt("ID");
                String Name = rs.getString("Name");
                user = new User(ID, Name, CompanyID);
                userList.add(user);
            }
            
            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return userList;
    }

    @Override
    public String[] getCompanyList()
    {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<String> companyList = new ArrayList<>();


        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Company;");
            boolean found = false;
            while (rs.next()) {
                found = true;
                companyList.add(rs.getInt("ID") + " | " + rs.getString("Name"));
            }
            if (!found) {
                companyList.add("No companies found");
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return companyList.toArray(new String[0]);
    }

    @Override
    public String[] getIndustryList()
    {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<String> industryList = new ArrayList<>();

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Industry;");
            boolean found = false;
            while (rs.next()) {
                found = true;
                industryList.add(rs.getInt("ID") + " | " + rs.getString("Industry"));
            }
            if (!found) {
                industryList.add("No industries found");
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return industryList.toArray(new String[0]);
    }

    @Override
    public int getMostRecentID(String tableName)
    {
        /*
         * This method retrieves the most recent ID from a specified table.
         * It assumes that the most recent entry is determined by the highest value in the specified column, as new rowids are always one greater than the largest entry *at time of insertion*.
         * Autoincremented primary keys are not needed, as this makes newly inserted primary keys one greater than the largest entry *ever*.
         */
        Statement stmt = null;
        ResultSet rs = null;
        int mostRecentID = -1;
        String idColumn = null;

        try {
            stmt = conn.createStatement();
            // Check if table has an ID column
            rs = stmt.executeQuery("PRAGMA table_info('" + tableName + "');");
            while (rs.next()) {
                String colName = rs.getString("name");
                if (colName.equalsIgnoreCase("ID")) {
                    idColumn = "ID";
                    break;
                }
            }
            rs.close();

            String query;
            String colToGet;
            if (idColumn != null) {
                query = "SELECT * FROM " + tableName + " ORDER BY ID DESC LIMIT 1;";
                colToGet = "ID";
            } else {
                query = "SELECT * FROM " + tableName + " ORDER BY rowid DESC LIMIT 1;";
                colToGet = "rowid";
            }

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                mostRecentID = rs.getInt(colToGet);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return mostRecentID;
    }

    public boolean IDExists(String tableName, int ID)
    {
        Statement stmt = null;
        ResultSet rs = null;
        boolean exists = false;
        String idColumn = null;

        try
        {
            stmt = conn.createStatement();

            // Check if table has an ID column
            rs = stmt.executeQuery("PRAGMA table_info('" + tableName + "');"); // Gets metadata about the table's columns
            while (rs.next()) 
            {
                String colName = rs.getString("name");
                if (colName.equalsIgnoreCase("ID")) // equalsIgnoreCase checks for equality while avoiding case sensitivity issues
                {
                    idColumn = "ID";
                    break;
                }
            }
            rs.close();

            String query;
            if (idColumn != null) {
                query = "SELECT * FROM " + tableName + " WHERE ID = " + ID + ";";
            } else {
                query = "SELECT * FROM " + tableName + " WHERE rowid = " + ID + ";";
            }

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                exists = true;
            }

            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName()   + ": " + e.getMessage());
        }

        return exists;
    }
}
