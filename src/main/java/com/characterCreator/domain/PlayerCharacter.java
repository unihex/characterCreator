package com.characterCreator.domain;

import java.util.Map;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PlayerCharacter {	

	private long playerCharacterId;
	
	private String name;
	private String race; 
	private String characterClass;
	
	private long level;
	
	private long strength;
	private long dexterity;
	private long constitution;
	private long intelligence;
	private long wisdom;
	private long charisma;
	
	private Map<String, Equipment> equipment;
	private Ability ability;
	
	
	public PlayerCharacter() {
		this.playerCharacterId = -1;
		
		this.name = "";
		this.race = "";
		this.characterClass = "";
		
	}
	
	public PlayerCharacter(String name, String race, String characterClass) {
		this();
		
		this.name = name;
		this.race = race;
		this.characterClass = characterClass;
	}
	
	public void setStats(long level, long strength, long dexterity, long constitution, long intelligence, long wisdom, long charisma) {
		this.level = level;
		this.strength = strength;
		this.dexterity = dexterity;
		this.constitution = constitution;
		this.intelligence = intelligence;
		this.wisdom = wisdom;
		this.charisma = charisma;
	}
	
	@JsonIgnore
	public long[] getStats() {
		return new long[] {this.level, this.strength, this.dexterity, this.constitution, this.intelligence, this.wisdom, this.charisma};
	}
	
	
	public long getPlayerCharacterId() {
		return playerCharacterId;
	}
	
	public void setPlayerCharacterId(long playerCharacterId) {
		this.playerCharacterId = playerCharacterId;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	
	@JsonProperty("class")
	public String getCharacterClass() {
		return characterClass;
	}
	public void setCharacterClass(String characterClass) {
		this.characterClass = characterClass;
	}
	public long getLevel() {
		return level;
	}
	public void setLevel(long level) {
		this.level = level;
	}

	public long getStrength() {
		return strength;
	}
	public void setStrength(long strength) {
		this.strength = strength;
	}
	public long getDexterity() {
		return dexterity;
	}
	public void setDexterity(long dexterity) {
		this.dexterity = dexterity;
	}
	
	public long getConstitution() {
		return constitution;
	}

	public void setConstitution(long constitution) {
		this.constitution = constitution;
	}

	public long getIntelligence() {
		return intelligence;
	}
	public void setIntelligence(long intelligence) {
		this.intelligence = intelligence;
	}
	

	public long getWisdom() {
		return wisdom;
	}

	public void setWisdom(long wisdom) {
		this.wisdom = wisdom;
	}

	public long getCharisma() {
		return charisma;
	}

	public void setCharisma(long charisma) {
		this.charisma = charisma;
	}

	public Map<String, Equipment> getEquipment() {
		return equipment;
	}
	public void setEquipment(Map<String, Equipment> equipment) {
		this.equipment = equipment;
	}
	public Ability getAbility() {
		return ability;
	}
	public void setAbility(Ability ability) {
		this.ability = ability;
	}
	
	@Override
	public String toString() {
		return String.format("Player Character [Id=%s, Name=%s, Race=%s, Level=%s, Class=%s]", 
				this.playerCharacterId, this.name, this.race, this.level, this.characterClass);
		
	}
}
