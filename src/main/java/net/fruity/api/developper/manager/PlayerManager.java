package net.fruity.api.developper.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.fruity.api.developper.enums.Rank;
import net.fruity.api.developper.objects.PlayerProfile;
import net.fruity.api.developper.stockage.MemoryStockage;
import net.fruity.api.developper.stockage.MySQL;
import net.fruity.api.developper.stockage.RedisManager;

public class PlayerManager {
	public static PlayerProfile getPlayer(String uuid) {
		if(MemoryStockage.players.containsKey(uuid)) {
			return MemoryStockage.players.get(uuid);
		}else {
			RedisManager.initRedis();
			if(RedisManager.isExist("player."+uuid)) {
				String[] playerdata = RedisManager.getData("player."+uuid).split(",");
				Rank rank = Rank.JOUEUR;
				for(Rank r : Rank.values()) {
					if(r.getID() == Integer.parseInt(playerdata[0])) {
						rank = r;
						break;
					}
					continue;
				}
				RedisManager.disconnect();
				return new PlayerProfile(uuid, rank, Integer.parseInt(playerdata[1]));
			}else {
				RedisManager.disconnect();
				MySQL mysql = new MySQL();
				mysql.connect();
				if(mysql.getBoolean("SELECT rank FROM players WHERE uuid = \""+uuid+"\"")) {
					Connection co = mysql.getConnection();
					int rankid = 0;
					int coins = 0;
					Rank rank = Rank.JOUEUR;
					try {
						Statement s = co.createStatement();
						ResultSet rs = s.executeQuery("SELECT * FROM players WHERE uuid = \""+uuid.toString()+"\"");
						while(rs.next()) {
							coins = rs.getInt("coins");
							rankid = rs.getInt("rank");
						}
						s.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					mysql.disconnect();
					for(Rank r : Rank.values()) {
						if(r.getID() == rankid) {
							rank = r;
							break;
						}
						continue;
					}
					return new PlayerProfile(uuid, rank, coins);
				}else {
					return new PlayerProfile(uuid, Rank.JOUEUR, 100);
				}
			}
		}
	}
	
	public static boolean isPlayerExist(String uuid) {
		if(MemoryStockage.players.containsKey(uuid)) {
			return true;
		}else {
			RedisManager.initRedis();
			if(RedisManager.isExist("player."+uuid)) {
				RedisManager.disconnect();
				return true;
			}else {
				RedisManager.disconnect();
				MySQL mysql = new MySQL();
				mysql.connect();
				if(mysql.getBoolean("SELECT rank FROM players WHERE uuid = \""+uuid+"\"")) {
					mysql.disconnect();
					return true;
				}else {
					mysql.disconnect();
					return false;
				}
			}
			
		}
	}
}
