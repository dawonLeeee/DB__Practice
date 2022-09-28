package puppyWalk.dog.model.dao;

import static puppyWalk.common.JDBCTemplete.*;

import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DogDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;
	
	public DogDAO() {
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("dog-query.xml"));
			// main-query.xml 파일의 내용을 읽어와 Properties 객체에 저장

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
