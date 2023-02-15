/**
 * Program Name: Weapon.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Aug. 24, 2021
 */
package weapons;

public abstract class Weapon {
	
	// Variables
	
	private String name;
	private String description;
	private int might; // The base amount of damage the weapon deals
	private int range; // The range of the weapon in tiles
	private boolean physical; // When false, the weapon is magical
	
	/* Weapon classes: (Use the first one!)
	 * - Sword/Dagger
	 * - Lance/Javelin
	 * - Axe/Hammer
	 * - Bow
	 * - Spell/Tome
	 * - Shield
	 */
	private String weaponClass;
	
	// Constructors
	
	public Weapon() {
		this.name = "";
		this.description = "";
		this.might = 0;
		this.range = 0;
		this.physical = true;
	}
	
	public Weapon(String name, String description, String weaponClass, int might, int range, boolean physical) {
		this.name = name;
		this.description = description;
		this.weaponClass = weaponClass;
		this.might = might;
		this.range = range;
		this.physical = physical;
	}
	
	// Getters
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getWeaponClass() {
		return this.weaponClass;
	}
	
	public int getMight() {
		return this.might;
	}
	
	public int getRange() {
		return this.range;
	}
	
	public boolean getPhysical() {
		return this.physical;
	}
	
	// Setters
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public void setWeaponClass(String weaponClass) {
		this.weaponClass = weaponClass;
	}
	
	public void setMight(int might) {
		this.might = might;
	}
	
	public void setRange(int range) {
		this.range = range;
	}
	
	public void setPhysical(boolean phys) {
		this.physical = phys;
	}
	
}
 // end class