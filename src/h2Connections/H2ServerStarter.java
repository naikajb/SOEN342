package h2Connections;

import java.sql.SQLException;

import org.h2.tools.Server;

public class H2ServerStarter {

	public static void main(String[] args) throws SQLException{
		Server server = Server.createTcpServer(args).start();
	}

}
