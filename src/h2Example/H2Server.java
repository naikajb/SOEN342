package h2Example;

import java.sql.SQLException;

import org.h2.tools.Server;

public class H2Server {

	public static void main(String[] args) throws SQLException{
		Server server = Server.createTcpServer(args).start();
	}

}
