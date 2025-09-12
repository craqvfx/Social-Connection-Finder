package DatabaseConnect;

import Graph.Graph;

public interface IDatabaseConnect 
{
    public void close();
    public boolean loadGraph(Graph graph);

    public User selectUser(int FriendCode);
    public User selectUser(String Email, String Password);
    
    public boolean addUser(User user);
    public boolean deleteUser(int FriendCode);

    public boolean addConnection(int from, int to, int weight);
    public boolean deleteConnection(int from, int to);

    public boolean modifyPassword(int FriendCode, String newPassword);
    public boolean modifyEmail(int FriendCode, String newEmail);
    public boolean modifyName(int FriendCode, String newName);

    public boolean addCompany(Company company);



}
