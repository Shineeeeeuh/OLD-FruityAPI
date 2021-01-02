package net.fruity.api.developper.stockage;

import redis.clients.jedis.Jedis;

public class RedisManager {
	
	private static Jedis jedis;
	
	public static void initRedis() {
		jedis = new Jedis("localhost", 6379, 5000);
		jedis.auth("123456789");
	}
	
	public static void setData(String key, String data) {
		jedis.set(key, data);
	}
	
	public static String getData(String key) {
		return jedis.get(key);
	}
	
	public static void disconnect() {
		jedis.disconnect();
	}
	
	public static boolean isExist(String key) {
		return jedis.exists(key);
	}
	
	public static void removeKey(String key) {
		jedis.del(key);
	}
}
