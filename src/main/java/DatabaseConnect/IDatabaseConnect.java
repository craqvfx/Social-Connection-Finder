package DatabaseConnect;

import Graph.Graph;

public interface IDatabaseConnect 
{
    public void close();
    public boolean loadGraph(Graph graph);

    public User getUser(int id);
    public User getUser(String Email, String Password);
    
    public boolean addUser(User user);
    public boolean deleteUser(User user);

    public boolean addConnection(int from, int to, int weight);
    public boolean deleteConnection(int from, int to);

    public boolean modifyPassword(int id, String newPassword);
    public boolean modifyEmail(int id, String newEmail);
    public boolean modifyName(int id, String newName);
    public boolean modifyCompanyID(int id, int companyID);

    public boolean addCompany(Company company);

    public String[] getUsersByIndustry(int IndustryID);
    public String[] getUsersByCompany(int CompanyID);

    public String[] getCompanyList();
    public String[] getIndustryList();
    public String[] getConnectionList();

    public int getMostRecentID(String tableName);
    public boolean IDExists(String tableName, int ID);
    public boolean connectionExists(int from, int to);
}
