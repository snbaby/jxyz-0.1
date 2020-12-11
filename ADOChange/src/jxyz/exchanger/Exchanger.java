package jxyz.exchanger;

import java.sql.Connection;

public interface Exchanger {
	
 void process(Connection connection) throws Exception;

}
