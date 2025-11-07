package Main;

import DatabaseConnect.*;
import Dijkstra.*;
import Graph.*;

import java.util.Scanner;

/*
 * Main class to run the program.
 * Handles user interface and interaction.
 * Note: This class is a work in progress and some features are not yet implemented.
 * Sparsely commented as most of the code is self-explanatory or simple (e.g. cli menus).
 */

public class Main
{

    private static final Scanner in = new Scanner(System.in); // Single Scanner instance for the entire class, not closed as closing it closes whole input stream for program, and will be cleaned up on program exit anyway

    public static void main(String[] args)
    {
        User user = WelcomeScreen();
        HomeScreen(user);
    }

    private static User WelcomeScreen()
    {
        User user = null;
        System.out.println("--- Welcome to the Social Network Connection Finder! ---");

        int choice;
        do
        {
            System.out.println("Please choose one of the following options by entering it's corresponding number:");
            System.out.println("1 | Login");
            System.out.println("2 | Register");
            System.out.println("3 | Exit");
            choice = in.nextInt();
        } while(choice < 1 || choice > 3);

        switch (choice)
        {
            case 1:
                System.out.println("--- Login ---");
                user = loginScreen();
                break;
            case 2:
                System.out.println("--- Register ---");
                registerScreen();
                user = loginScreen(); // Asks user to login after registering
                break;
            case 3:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Error: Invalid choice. Exiting...");
                System.exit(1);
        }

        return user; //  Continues program after succesfull login
    }

    private static User loginScreen()
    {
        String email;
        String password;
        User currentUser = null;
        boolean success = false;
        while(success == false)
        {
            do
            {
                System.out.println("Please enter your email:");
                email = in.nextLine();
            } while(email.isEmpty());   // nextLine() returns "" if enter is pressed with no input, therefore check for empty string instead of null

            do
            {
                System.out.println("Please enter your password:");
                password = in.nextLine();
            } while(password.isEmpty());

            try
            {
                DatabaseConnect conn = new DatabaseConnect();
                currentUser = conn.getUser(email, password);
                if(conn.IDExists("User", currentUser.ID()))
                {
                    System.out.println("Login successful!");
                    success = true;
                } else {
                    System.out.println("Login failed: Email password combination not found.");
                }
                conn.close();
            }
            catch (Exception e)
            {
                System.out.println("Login failed: " + e.getMessage());
            }
        }
        
        return currentUser;
    }

    private static void registerScreen()
    {
        String name;
        String email;
        String password;
        boolean success = false;
        while(success == false)
        {
            do
            {
                System.out.println("Please enter your name:");
                name = in.nextLine();
            } while(name.isEmpty());

            do
            {
                System.out.println("Please enter your email:");
                email = in.nextLine();
            } while(email.isEmpty());

            do
            {
                System.out.println("Please enter your password:");
                password = in.nextLine();
                if(password.length() < 8)
                {
                    System.out.println("Password must be at least 8 characters long, please try again.");
                    password = "";
                }
                else if(!password.matches(".*[A-Za-z].*") || !password.matches(".*\\d.*"))
                {
                    System.out.println("Password must contain at least 1 letter and 1 number, please try again.");
                    password = "";
                }
                
            } while(password.isEmpty());

            register(name, email, password);
            success = true; // If registration fails, an exception is thrown and this line is not reached
        }
    }

    private static void register(String name, String email, String password)
    {
        User user = null;
        try
        {
            user = new User(name, email, password);
            DatabaseConnect conn = new DatabaseConnect();
            boolean added = conn.addUser(user);
            if (added) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed: User not added.");
            }
            conn.close();
        }
        catch (Exception e)
        {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
    
    private static User HomeScreen(User currentUser)
    {
        System.out.println("--- Home ---");

        int choice;
        do
        {
            System.out.println("Please choose one of the following options by entering it's corresponding number:");
            System.out.println("1 | Find Connections");
            System.out.println("2 | Manage Connections");
            System.out.println("3 | Manage Account");
            System.out.println("4 | Logout");
            System.out.println("5 | Exit");
            choice = in.nextInt();
            in.nextLine();
        } while(choice < 1 || choice > 5);

        switch (choice)
        {
            case 1:
                System.out.println("--- Find Connections ---");
                findConnectionsScreen(currentUser);
                break;
            case 2:
                System.out.println("--- Manage Connecions ---");
                manageConnectionsScreen(currentUser);
                break;
            case 3:
                System.out.println("--- Manage Account ---");
                manageAccountScreen(currentUser);
                break;
            case 4:
                System.out.println("Logging out user...");
                currentUser = WelcomeScreen();
                break;
            case 5:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Error: Invalid choice. Exiting...");
                System.exit(1);
        }

        HomeScreen(currentUser); // Loop back to home screen after completing an action
        return currentUser;
    }

    static void findConnectionsScreen(User currentUser)
    {
        int choice;
        do
        {
            System.out.println("Please choose one of the following options by entering it's corresponding number:");
            System.out.println("1 | Search Users by Industry");
            System.out.println("2 | Search Users by Company");
            System.out.println("3 | Return to Home Screen");
            choice = in.nextInt();
        } while(choice < 1 || choice > 3);

        DatabaseConnect conn = new DatabaseConnect();
        int targetID = -1;
        boolean success = true;

        switch (choice)
        {
            case 1:
                System.out.println("--- Search Users by Industry ---");

                int industryID;
                do
                {
                    success = true;
                    System.out.println("Please choose one of the following industries by entering it's corresponding number:");

                    String[] industryList = conn.getIndustryList();
                    for(String industry : industryList) 
                    {
                        System.out.println(industry);
                    }
                    industryID = in.nextInt();
                    in.nextLine(); // consume leftover newline
                    if(!conn.IDExists("Industry", industryID))
                    {
                        System.out.println("The Industry ID you entered does not exist, please try again.");
                        success = false;
                        continue;
                    }

                    String[] userList = conn.getUsersByIndustry(industryID);
                    if(userList[0].equals("No users found"))
                    {
                        System.out.println("No users found in the selected industry, please choose a different industry.");
                        success = false;
                    }

                } while(!success);

                do
                {
                    success = true;
                    System.out.println("Please choose one of the following Users by entering the User's corresponding Friend Code:");
                    System.out.println("Friend Code | Name");

                    String[] userList = conn.getUsersByIndustry(industryID);
                    for(String user : userList) // output all users in the selected industry
                    {
                        System.out.println(user);
                    }
                    targetID = in.nextInt(); // get user input for connection to find
                    in.nextLine(); // consume leftover newline

                    // Check if targetID is in userList
                    boolean foundInList = false;
                    for (String user : userList) 
                    {
                        String[] parts = user.split("\\|");
                        if (parts.length > 0) {
                            int friendCode = Integer.parseInt(parts[0].trim());
                            if (friendCode == targetID) 
                            {
                                foundInList = true;
                                break;
                            }
                        }
                    }

                    if (!foundInList)
                    {
                        System.out.println("The Friend Code you entered was not in the user list, please try again.");
                        success = false;
                        continue;
                    }

                    if(!conn.IDExists("User", targetID))
                    {
                        System.out.println("The Friend Code you entered does not exist, please try again.");
                        success = false;
                    }
                } while(!success);

                break;
            case 2:
                System.out.println("--- Search Users by Company ---");

                int companyID;
                success = true;
                do
                {
                    success = true;
                    System.out.println("Please choose one of the following companies by entering it's corresponding number:");

                    String[] companyList = conn.getCompanyList();
                    for(String company : companyList) 
                    {
                        System.out.println(company);
                    }
                    companyID = in.nextInt();
                    in.nextLine(); // consume leftover newline

                    if(!conn.IDExists("Company", companyID))
                    {
                        System.out.println("The Company ID you entered does not exist, please try again.");
                        success = false;
                        continue;
                    }

                    String[] userList = conn.getUsersByCompany(companyID);
                    if(userList[0].equals("No users found"))
                    {
                        System.out.println("No users found in the selected company, please choose a different company.");
                        success = false;
                    }

                } while(!success);

                do
                {
                    success = true;
                    System.out.println("Please choose one of the following Users by entering the User's corresponding Friend Code:");
                    System.out.println("Friend Code | Name");

                    String[] userList = conn.getUsersByCompany(companyID);

                    for(String user : userList) // output all users in the selected company
                    {
                        System.out.println(user);
                    }

                    targetID = in.nextInt(); // get user input for connection to find
                    in.nextLine(); // consume leftover newline

                    // Check if targetID is in userList
                    boolean foundInList = false;
                    for (String user : userList) 
                    {
                        String[] parts = user.split("\\|");
                        if (parts.length > 0) {
                            int friendCode = Integer.parseInt(parts[0].trim());
                            if (friendCode == targetID) 
                            {
                                foundInList = true;
                                break;
                            }
                        }
                    }

                    if (!foundInList)
                    {
                        System.out.println("The Friend Code you entered was not in the user list, please try again.");
                        success = false;
                        continue;
                    }

                    if(!conn.IDExists("User", targetID))
                    {
                        System.out.println("The Friend Code you entered does not exist, please try again.");
                        success = false;
                    }

                } while(!success);

                break;
            case 3:
                System.out.println("Returning to Home Screen...");
                break;
            default:
                System.out.println("Error: Invalid choice. Exiting...");
                System.exit(1);
        }
        
        Graph graph = new Graph();
        conn.loadGraph(graph);
        Dijkstra dijkstra = new Dijkstra(graph);
        System.out.println("Finding shortest path from " + currentUser.Name() + " to " + conn.getUser(targetID).Name() + "...");
        dijkstra.printPath(currentUser.Name(), conn.getUser(targetID).Name());
        System.out.println();
        conn.close();
    }

    static void manageConnectionsScreen(User currentUser)
    {
        int choice;
        do
        {
            System.out.println("Please choose one of the following options by entering it's corresponding number:");
            System.out.println("1 | Add new Connection");
            System.out.println("2 | Delete Connection");
            System.out.println("3 | View Connections");
            System.out.println("4 | Return to Home Screen");
            choice = in.nextInt();
        } while(choice < 1 || choice > 3);

        DatabaseConnect conn = new DatabaseConnect();
        int targetID;
        switch (choice)
        {
            case 1:
                int weight;
                System.out.println("--- Add new Connection ---");

                boolean success = true;
                do {
                    System.out.println("Please input the Friend Code of the person you want to connect to:");

                    targetID = in.nextInt();
                    in.nextLine(); // consume leftover newline

                    if (!conn.IDExists("User", targetID)) 
                    {
                        System.out.println("Error: User indicated by Friend Code doesn't exist, please try again.");
                    }
                    else if(targetID == currentUser.ID())
                    {
                        System.out.println("Error: You cannot add yourself as a connection. Please enter a different Friend Code.");
                    }
                    else if(conn.connectionExists(currentUser.ID(), targetID))
                    {
                        System.out.println("Error: You already have a connection with this user. Please enter a different Friend Code.");
                        targetID = -1; // force loop to continue
                    }
                    else 
                    {
                        //If we reach here, the input is valid. Now we verify the user is happy with it
                        System.out.println("You have selected the user:\n" + targetID + " | " + conn.getUser(targetID).Name());
                        System.out.println("Was this correct? (y/n)");
                        String confirmation = in.nextLine().trim().toLowerCase();
                        if (confirmation.equals("y")) 
                        {
                            success = true;
                        }
                        else
                        {
                            System.out.println("Let's try again.");
                            success = false;
                        }
                    }
                } while(!success);

                do {
                    System.out.println("Please rank the strength of your connection out of 5, 1 being strongest and 5 being weakest:");
                    weight = in.nextInt();
                    in.nextLine(); // consume leftover newline
                } while(weight < 1 || weight > 5);

                conn.addConnection(currentUser.ID(), targetID, weight);
                System.out.println("Connection added successfully!");
                break;
            case 2:
                System.out.println("--- Delete Connection ---");
                
                do
                {
                    System.out.println("Please choose one of the following connections by entering the User's corresponding Friend Code:");
                    System.out.println("Friend Code | Name");

                    String[] connectionList = conn.getConnectionList(currentUser);
                    for(String connection : connectionList) // output current connections
                    {
                        System.out.println(connection);
                    }
                    targetID = in.nextInt(); // get user input for connection to delete

                    in.nextLine(); // consume leftover newline
                } while(!conn.IDExists("Connections", targetID));

                conn.deleteConnection(currentUser.ID(), targetID);
                System.out.println("Connection deleted successfully!");
                break;
            case 3:
                System.out.println("--- View Connections ---");
                
                System.out.println("Here are your current connections:");
                System.out.println("Friend Code | Name");

                String[] connectionList = conn.getConnectionList(currentUser);
                for(String connection : connectionList) // output current connections
                {
                    System.out.println(connection);
                }
                System.out.println();
                break;
            case 4:
                System.out.println("Returning to Home Screen...");
                break;
            default:
                System.out.println("Error: Invalid choice. Exiting...");
                System.exit(1);
        }
        conn.close();
    }

    static void manageAccountScreen(User currentUser)
    {
        int choice;
        do
        {
            System.out.println("Please choose one of the following options by entering it's corresponding number:");
            System.out.println("1 | Update Email");
            System.out.println("2 | Update Password");
            System.out.println("3 | Update Name");
            System.out.println("4 | Update Company");
            System.out.println("5 | Return to Home Screen");
            choice = in.nextInt();
            in.nextLine(); // consume leftover newline
        } while(choice < 1 || choice > 5);

        DatabaseConnect conn = new DatabaseConnect();
        switch (choice)
        {
            case 1:
                System.out.println("--- Update Email ---");
                String email;
                do
                {
                    System.out.println("Please enter a new email:");
                    email = in.nextLine();
                } while(email.isEmpty());

                conn.modifyEmail(currentUser.ID(), email);

                currentUser.setEmail(email);
                break;
            case 2:
                System.out.println("--- Update Password ---");
                String password;
                do
                {
                    System.out.println("Please enter a new password:");
                    password = in.nextLine();
                } while(password.isEmpty());
                
                conn.modifyPassword(currentUser.ID(), password);

                currentUser.setPassword(password);
                break;
            case 3:
                System.out.println("--- Update Name ---");
                String name;
                do
                {
                    System.out.println("Please enter a new name:");
                    name = in.nextLine();
                } while(name.isEmpty());

                conn.modifyName(currentUser.ID(), name);

                currentUser.setName(name);
                break;
            case 4:
                System.out.println("--- Update Company ---");
                int companyID;
                do
                {
                    System.out.println("Please choose one of the following companies by entering it's corresponding number:");
                    System.out.println("0 | Register new Company");

                    String[] companyList = conn.getCompanyList();
                    for(String company : companyList) 
                    {
                        System.out.println(company);
                    }
                    companyID = in.nextInt();
                    in.nextLine(); // consume leftover newline
                } while(!conn.IDExists("Company", companyID) && companyID != 0); // 0 is valid input to register a new company

                if(companyID == 0)
                {
                    System.out.println("--- Register new Company ---");
                    String companyName;
                    int industryID;
                    do
                    {
                        System.out.println("Please enter the new company's name:");
                        companyName = in.nextLine();
                    } while(companyName.isEmpty());

                    do
                    {
                        System.out.println("Please choose one of the following industries by entering it's corresponding number:");
                        
                        String[] industryList = conn.getIndustryList();
                        for(String industry : industryList) 
                        {
                            System.out.println(industry);
                        }

                        industryID = in.nextInt();
                        in.nextLine(); // consume leftover newline

                    } while(!conn.IDExists("industry", industryID));

                    Company newCompany = null;
                    try
                    {
                        newCompany = new Company(companyName, industryID);
                        boolean added = conn.addCompany(newCompany);
                        if (added) {
                            companyID = conn.getMostRecentID("Company");
                            System.out.println("Company registration successful!");
                        } else {
                            System.out.println("Company registration failed: Company not added.");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("Company registration failed: " + e.getMessage());
                    }
                }

                // no else is needed as after updating companyID if a new company was created, it auto selects the newly created company, if no new company was created, the user selected an existing company
                conn.modifyCompanyID(currentUser.ID(), companyID);
                currentUser.setCompanyID(companyID);

                break;
            case 5:
                System.out.println("Returning to Home Screen...");
                break;
            default:
                System.out.println("Error: Invalid choice. Exiting...");
                System.exit(1);
        }

        conn.close();
    }
}