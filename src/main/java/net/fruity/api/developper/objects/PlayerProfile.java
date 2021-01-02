package net.fruity.api.developper.objects;

import net.fruity.api.developper.enums.Rank;
import net.fruity.api.developper.stockage.MySQL;
import net.fruity.api.developper.stockage.RedisManager;

public class PlayerProfile {
	
	private String uuid;
	private Rank r;
	private int c;
	
	public PlayerProfile(String uuid, Rank r, int c) {
		this.uuid = uuid;
		this.r = r;
		this.c = c;
	}
	
	public int getCoins() {
		return c;
	}
	
	public void setCoins(int c) {
		this.c = c;
	}
	
	public void setRank(Rank r) {
		this.r = r;
	}
	
	public String getUUID() {
		return uuid;
	}
	
	public Rank getRank() {
		return r;
	}
	
	public void saveMySQL() {
		MySQL sql = new MySQL();
		sql.connect();
		sql.executeUpdate("UPDATE players SET rank = "+r.getID()+", coins = "+c+" WHERE uuid = \""+uuid+"\"");
		sql.disconnect();
	}
	
	public void saveRedis() {
		RedisManager.initRedis();
		RedisManager.setData("player."+uuid, r.getID()+","+c);
		RedisManager.disconnect();
	}

}
