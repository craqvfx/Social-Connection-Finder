package DatabaseConnect;

import java.util.ArrayList;

import Graph.Graph;

public interface IDatabaseConnect 
{
    public void close();
    public boolean loadGraph(Graph graph);

    public User getUser(int FriendCode);
    public User getUser(String Email, String Password);
    
    public boolean addUser(User user);
    public boolean deleteUser(User user);

    public boolean addConnection(int from, int to, int weight);
    public boolean deleteConnection(int from, int to);

    public boolean modifyPassword(int FriendCode, String newPassword);
    public boolean modifyEmail(int FriendCode, String newEmail);
    public boolean modifyName(int FriendCode, String newName);

    public boolean addCompany(Company company);

    public ArrayList<User> getUsersByIndustry(int IndustryID);
    public ArrayList<User> getUsersByCompany(int CompanyID);
}
