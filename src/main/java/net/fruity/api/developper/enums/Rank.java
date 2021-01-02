package net.fruity.api.developper.enums;

public enum Rank {

	JOUEUR(0, 0, "§7Joueur", false, false),
	VIP(1, 5, "§eVIP", false, false),
	YOUTUBEUR(2, 10, "§dYoutubeur", false, false),
	STAFF(3, 15, "§aStaff", false, false),
	MODO(4, 20, "§bModérateur", true, false),
	DEV(5, 25, "§dDéveloppeur", true, false),
	ADMIN(6, 30, "§cAdministrateur", true, true);
	
	private int id, power;
	private String prefix;
	private boolean modperm, adminperm;
	
	private Rank(int id, int power, String prefix, boolean modperm, boolean adminperm) {
		this.id = id;
		this.power = power;
		this.prefix = prefix;
		this.modperm = modperm;
		this.adminperm = adminperm;
	}
	
	public int getPower() {
		return power;
	}
	
	public int getID() {
		return id;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public boolean isModPermission() {
		return modperm;
	}
	
	public boolean isAdminPermission() {
		return adminperm;
	}
}