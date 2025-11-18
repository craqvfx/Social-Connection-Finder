
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

/*
 * DatabaseConnect class for connecting to and interacting with the database
 * Implements IDatabaseConnect interface
 */

public class DatabaseConnect implements IDatabaseConnect
{
    private Connection conn = null; // JDBC connection instance

    // Constructor to establish database connection
    public DatabaseConnect()
    {
        try
        {
            Class.forName("org.sqlite.JDBC"); // Specify the SQLite Java driver
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/connections.db"); // Specify the database, since relative in the main project folder
            conn.setAutoCommit(false); // Sets auto commit to false. Important as you want control of when data is written
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); // Print error
            System.exit(0); // Exit if connection cannot be established
        }
    }
    
    // Close the database connection
    @Override
    public void close()
    {
        try // Attempt to close connection
        {
            conn.close(); // Close JDBC connection
        }
        catch (SQLException ex) // Handle SQL errors on close
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex); // Log severe error
        }
    }

    // Load graph data from Connections table into provided Graph object
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
                    int sourceNodeID = rs.getInt("SourceID"); // Source user ID from DB
                    String sourceNodeName = getUser(sourceNodeID).Name(); // Lookup source user name
                    int endNodeID = rs.getInt("TargetID"); // Target user ID from DB
                    String endNodeName = getUser(endNodeID).Name(); // Lookup target user name

                    graph.add(sourceNodeName, endNodeName, weight); // Add relationship to graph
            }

            stmt.close();
            conn.commit();
            success = true;
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return success;
    }

    // Helper to return a customer object given an int id // Overloaded
    @Override
    public User getUser(int id)
    {
        Statement stmt = null;
        ResultSet rs = null;
        User user = null;

        try
        {
            stmt = conn.createStatement(); // Create statement for user lookup
            rs = stmt.executeQuery("SELECT * FROM User WHERE ID = '" + id + "';"); // Query user by ID
            int ID = rs.getInt("ID"); // Read ID column
            String Name = rs.getString("Name"); // Read Name column
            int CompanyID = rs.getInt("CompanyID"); // Read CompanyID column
            user = new User(ID, Name, CompanyID); // Construct User object
            rs.close(); // Close result set
            stmt.close(); // Close statement
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return user;
    }

    // Helper to return a user given a string email and password // Overloaded
    @Override
    public User getUser(String Email, String Password)
    {
        Statement stmt = null;
        ResultSet rs = null;
        User user = null;

        try
        {
            stmt = conn.createStatement(); // Create statement for auth lookup
            rs = stmt.executeQuery("SELECT * FROM User, LoginInfo"
            + " WHERE User.ID = LoginInfo.ID"
            + "   AND LoginInfo.Email = '" + Email + "'"
            + "   AND LoginInfo.Password = '" + Password + "';"); // Query user by email/password
            int ID = rs.getInt("ID"); // Read ID
            String Name = rs.getString("Name"); // Read Name
            int CompanyID = rs.getInt("CompanyID"); // Read CompanyID
            user = new User(ID, Name, CompanyID); // Construct User
            rs.close(); // Close result set
            stmt.close(); // Close statement
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return user;
    }

    // Add a new user to the database
    @Override
    public boolean addUser(User user)
    {
        boolean success = false;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.createStatement(); // Create statement for insertion
            String sql = "INSERT INTO User (Name, CompanyID) "
                        + "VALUES ('" + user.Name() +"', '" + user.CompanyID() + "');"; // Insert user SQL
            stmt.executeUpdate(sql); // Execute insert

            // Get the last inserted ID
            rs = stmt.executeQuery("SELECT last_insert_rowid() AS ID;"); // Get last insert ID
            int id = -1; // Default if not found
            if (rs.next()) {
                id = rs.getInt("ID"); // Retrieve generated ID
            }
            rs.close(); // Close result set

            // Insert into LoginInfo using the retrieved ID
            sql = "INSERT INTO LoginInfo (ID, Email, Password) "
                + "VALUES (" + id + ", '" + user.Email() + "', '" + user.Password() + "');"; // Insert login info
            stmt.executeUpdate(sql); // Execute insert

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

    // Delete a user from the database
    @Override
    public boolean deleteUser(User user)
    {
        boolean success = false;
        Statement stmt = null;
        int ID = user.ID();

        try
        {
            stmt = conn.createStatement(); // Create statement for delete operations
            String sql = "DELETE FROM Connections WHERE SourceID = '" + ID + "' OR To = '" + ID + "';"; // Delete connections
            sql += "DELETE FROM LoginInfo WHERE ID = '" + ID + "';"; // Delete login info
            sql += "DELETE FROM User WHERE ID = '" + ID + "';"; // Delete user
            stmt.executeUpdate(sql); // Execute delete batch

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

    // Add a connection between two users
    @Override
    public boolean addConnection(int sourceID, int targetID, int weight)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement(); // Create statement to insert connection
            String sql = "INSERT INTO Connections (SourceID, TargetID, RelationshipStrength)"
            + " VALUES ('" + sourceID + "', '" + targetID + "', '" + weight + "');"; // Insert connection SQL

            stmt.executeUpdate(sql); // Execute insert

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

    // Delete a connection between two users
    @Override
    public boolean deleteConnection(int sourceID, int targetID)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement(); // Create statement to delete connection
            String sql = "DELETE FROM Connections WHERE (SourceID = '" + sourceID + "' AND TargetID = '" + targetID + "')"
                       + " OR (SourceID = '" + targetID + "' AND TargetID = '" + sourceID + "');"; // Delete both directions
            stmt.executeUpdate(sql); // Execute delete

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

    // Modify a user's password
    @Override
    public boolean modifyPassword(int id, String NewPassword)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement(); // Create statement to update password
            String sql = "UPDATE LoginInfo"
            + " SET Password = '" + NewPassword + "'"
            + " WHERE ID = '" + id + "';"; // Update password SQL
            stmt.executeUpdate(sql); // Execute update

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

    // Modify a user's email
    @Override
    public boolean modifyEmail(int id, String NewEmail)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement(); // Create statement to update email
            String sql = "UPDATE LoginInfo"
            + " SET Email = '" + NewEmail + "'"
            + " WHERE ID = '" + id + "';"; // Update email SQL
            stmt.executeUpdate(sql); // Execute update

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

    // Modify a user's name
    @Override
    public boolean modifyName(int id, String NewName)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement(); // Create statement to update user name
            String sql = "UPDATE User"
            + " SET Name = '" + NewName + "'"
            + " WHERE ID = '" + id + "';"; // Update name SQL
            stmt.executeUpdate(sql); // Execute update

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

    // Modify a user's company ID
    public boolean modifyCompanyID(int id, int companyID)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement(); // Create statement to update company ID
            String sql = "UPDATE User"
            + " SET CompanyID = '" + companyID + "'"
            + " WHERE ID = '" + id + "';"; // Update CompanyID SQL
            stmt.executeUpdate(sql); // Execute update

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

    // Add a new company to the database
    @Override
    public boolean addCompany(Company company)
    {
        boolean success = false;
        Statement stmt = null;

        try
        {
                stmt = conn.createStatement(); // Create statement to insert company
                String sql = "INSERT INTO Company (Name, IndustryID) "
                    + "VALUES ('" + company.Name() +"', '" + company.IndustryID() + "');"; // Insert company SQL
                stmt.executeUpdate(sql); // Execute insert

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

    // Get users by industry ID
    @Override
    public String[] getUsersByIndustry(int IndustryID)
    {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList <String> userList = new ArrayList<String>();

        try
        {
            stmt = conn.createStatement(); // Create statement to query users by industry
            rs = stmt.executeQuery("SELECT User.ID, User.Name"
            + " FROM User, Company"
            + " WHERE User.CompanyID = Company.ID"
            + " AND Company.IndustryID = '" + IndustryID + "';"); // Query users for given industry

            boolean found = false; // Track if any users found
            while(rs.next()) // Iterate over results
            {
                found = true; // Mark found
                userList.add(rs.getInt("ID") + " | " + rs.getString("Name")); // Add formatted entry
            }
            if (!found) { // If none found
                found = true; // Ensure flag set
                userList.add("No users found"); // Add placeholder
            }
            
            rs.close(); // Close result set
            stmt.close(); // Close statement
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return userList.toArray(new String[0]);
    }

    // Get users by company ID
    @Override
    public String[] getUsersByCompany(int CompanyID)
    {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList <String> userList = new ArrayList<String>();

        try
        {
            stmt = conn.createStatement(); // Create statement to query users by company
            rs = stmt.executeQuery("SELECT User.ID, User.Name"
            + " FROM User"
            + " WHERE User.CompanyID = '" + CompanyID + "';"); // Query users for given company

            boolean found = false; // Track if any users found
            while(rs.next()) // Iterate results
            {
                found = true; // Mark found
                userList.add(rs.getInt("ID") + " | " + rs.getString("Name")); // Add formatted entry
            }
            if (!found) { // If none found
                userList.add("No users found"); // Add placeholder
            }
            rs.close(); // Close result set
            stmt.close(); // Close statement
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return userList.toArray(new String[0]);
    }

    // Get list of companies
    @Override
    public String[] getCompanyList()
    {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<String> companyList = new ArrayList<>();

        try
        {
            stmt = conn.createStatement(); // Create statement to retrieve companies
            rs = stmt.executeQuery("SELECT * FROM Company;"); // Query all companies
            boolean found = false; // Track presence
            while (rs.next()) { // Iterate rows
                found = true; // Mark found
                companyList.add(rs.getInt("ID") + " | " + rs.getString("Name")); // Add entry
            }
            if (!found) { // If none found
                companyList.add("No companies found"); // Add placeholder
            }
            rs.close(); // Close result set
            stmt.close(); // Close statement
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return companyList.toArray(new String[0]);
    }

    // Get list of industries
    @Override
    public String[] getIndustryList()
    {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<String> industryList = new ArrayList<>();

        try
        {
            stmt = conn.createStatement(); // Create statement to retrieve industries
            rs = stmt.executeQuery("SELECT * FROM Industry;"); // Query all industries
            boolean found = false; // Track presence
            while (rs.next()) { // Iterate rows
                found = true; // Mark found
                industryList.add(rs.getInt("ID") + " | " + rs.getString("Industry")); // Add entry
            }
            if (!found) { // If none found
                industryList.add("No industries found"); // Add placeholder
            }
            rs.close(); // Close result set
            stmt.close(); // Close statement
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return industryList.toArray(new String[0]);
    }

    // Get connection list for a user
    @Override
    public String[] getConnectionList(User user)
    {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<String> connectionList = new ArrayList<>();
        int targetID;

        try
        {
            stmt = conn.createStatement(); // Create statement to query connections for user
            rs = stmt.executeQuery("SELECT * FROM Connections"
                                + " WHERE SourceID = '" + user.ID() + "'"
                                + " OR TargetID = '" + user.ID() + "';"); // Query both directions
            boolean found = false; // Track if any connections exist
            while (rs.next()) { // Iterate results
                found = true; // Mark found
                targetID = rs.getInt("TargetID"); // Get target ID
                connectionList.add(targetID + " | " + getUser(targetID).Name()); // Add formatted connection
            }
            if (!found) { // If none found
                connectionList.add("No connections found"); // Add placeholder
            }
            rs.close(); // Close result set
            stmt.close(); // Close statement
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return connectionList.toArray(new String[0]);
    }

    // Get the most recent ID from a specified table
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

    // Check if a given ID exists in a specified table
    @Override
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
                else if(colName.equalsIgnoreCase("TargetID")) // If there's no ID column, check for targetID (for connections table)
                {
                    idColumn = "TargetID";
                    break;
                }
            }
            rs.close();

            // Construct query based on available ID column
            String query;
            if (idColumn.equals("ID")) {
                query = "SELECT * FROM " + tableName + " WHERE ID = " + ID + ";";
            } else if (idColumn.equals("TargetID")) {
                query = "SELECT * FROM " + tableName + " WHERE TargetID = " + ID + ";";
            } else {
                query = "SELECT * FROM " + tableName + " WHERE rowid = " + ID + ";";
            }

            // Execute query to check existence
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

    // Check if a connection exists between two users
    @Override
    public boolean connectionExists(int sourceID, int targetID)
    {
        Statement stmt = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM Connections WHERE SourceID = " + sourceID + " AND TargetID = " + targetID + ";"; // Select all connections between source and target

            // Execute query to check existence
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                exists = true;
            }

            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return exists;
    }
}
