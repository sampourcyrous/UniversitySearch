package universitysearch;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by zubairbaig on 2/19/16.
 * Edited by  jacobsteele on 3/03/16.
 */
public class DBManager {
    private static SessionFactory factory; 
    public static synchronized SessionFactory getSessionFactory() {
    	if (factory == null) {
    		Configuration cfg = new Configuration();
    		cfg.setProperty("hibernate.connection.url", "jdbc:mysql://" + System.getenv().get("OPENSHIFT_MYSQL_DB_HOST") + ":" +
                    System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/universitysearch");
    		cfg.setProperty("hibernate.connection.username", System.getenv("OPENSHIFT_MYSQL_DB_USERNAME"));
    		cfg.setProperty("hibernate.connection.password", System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD"));
    		factory = cfg.configure().buildSessionFactory();
    }
    	return factory;
    	/*
    	try{
    		Configuration cfg = new Configuration();
    		cfg.setProperty("hibernate.connection.url", "jdbc:mysql://" + System.getenv().get("OPENSHIFT_MYSQL_DB_HOST") + ":" +
                    System.getenv("OPENSHIFT_MYSQL_DB_PORT"));
    		cfg.setProperty("hibernate.connection.username", System.getenv("OPENSHIFT_MYSQL_DB_USERNAME"));
    		cfg.setProperty("hibernate.connection.password", System.getenv("OPENSHIFT_MYSQL_DB_USERNAME"));
    		factory = cfg.configure().buildSessionFactory();
    	}catch (Throwable ex) { 
    		System.err.println("Failed to create sessionFactory object." + ex);
    		throw new ExceptionInInitializerError(ex); 
        }
    	UserManager UM = new UserManager();
    	UM.setFactory(factory);
    	Integer userID = UM.addUser(input, "test", "test", "test", 0, 0, 0);
    	return userID;*/
    }
}
