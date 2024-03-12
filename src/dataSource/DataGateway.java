package src.dataSource;


import java.sql.SQLException;

public class DataGateway {
    AccountGetaway accounts;


    public DataGateway()  {
        try{
            accounts = new AccountGetaway("identifier.sqlite");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) {
        new DataGateway();
    }


}
