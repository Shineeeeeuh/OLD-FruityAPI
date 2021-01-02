package net.fruity.api.developper.stockage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQL {
	private Connection co;
	
	public void connect() {
		try {
			co = DriverManager.getConnection("jdbc:mysql://localhost/fruitygames", "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void executeSQL(String sql){
		try {
			PreparedStatement pr = co.prepareStatement(sql);
			pr.execute();
			pr.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void executeUpdate(String sql){
		try {
			PreparedStatement pr = co.prepareStatement(sql);
			pr.executeUpdate();
			pr.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getString(String sql, String t){
		String str = "";
		try {
			PreparedStatement pr = co.prepareStatement(sql);
			ResultSet rs = pr.executeQuery();
			while(rs.next()){
				str = rs.getString(t);
			}
			pr.close();
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public int getInt(String sql, String t){
		int i = 0;
		try {
			PreparedStatement pr = co.prepareStatement(sql);
			ResultSet rs = pr.executeQuery();
			while(rs.next()){
				i = rs.getInt(t);
			}
			pr.close();
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	
	public boolean getBoolean(String sql){
		boolean b = false;
		try {
			PreparedStatement pr = co.prepareStatement(sql);
			ResultSet rs = pr.executeQuery();
			b = rs.next();
			pr.close();
			return b;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
		
	public boolean isConnected(){
        return co != null;
    }
	
	public void disconnect(){
        if(isConnected()){
            try {
                co.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	public ArrayList<String> getList(String sql, String t){
		ArrayList<String> results = new ArrayList<>();
		PreparedStatement pr;
		try {
			pr = co.prepareStatement(sql);
			ResultSet rs = pr.executeQuery();
			while(rs.next()) {
				String n = rs.getString(t);
				results.add(n);
			}
			pr.close();
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public Connection getConnection() {
		return co;
	}
}
