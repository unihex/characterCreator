package com.characterCreator.domain;

import org.apache.commons.lang3.RandomUtils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Ability {
	
	BESERKER_BARRAGE("Beserker Barrage", "Skill", "Make a DC 5 Constitution check. If sucessful, you can make a melee weapon attack. "
			+ "You may repeat this check until failure. The DC increases by 5 for each subsequent check."),
	
	RAIN_OF_ARROWS("Rain of Arrows", "Skill", "You may make (Dex modifier) ranged weapon attacks"),
	
	NO("No", "Skill", "You automatically succeed on an effect that requires a Wisdom saving throw"),
	
	BREAK("Break", "Skill", "Make a melee weapon attack. On a hit, the target must make a Constitution saving throw. "
			+ "If it fails, it is reduced to zero hit points"),
	
	DISARMING_SHOT("Disarming Shot", "Skill", "Make a ranged weapon attack with disadvantage. "
			+ "On a hit, the target drops what ever they are holding"),
	
	RAY_OF_FROST("Ray of Frost", "Spell", "1d6 cold damage per level to a single target"),
	
	FIREBALL("Fireball", "Spell", "10d8 fire damage in an area"),
	
	LIGHTNING_BOLT("Lightning Bolt", "Spell", "10d8 lightning damage in a line"),
	
	MAGIC_MISSLE("Magic Missle", "Spell", "3 bolts with 1 addtional per 2 levels. Each bolts do 1d4+1 force damage"),
	
	HOLY("Holy", "Spell", "12d12 radiant damage to a single target"),
	
	FLARE("Flare", "Spell", "12d12 fire damage to a single target"),
	
	ULTIMA("Ultima", "Spell", "12d12 raw damage in an area");
	
	private final String name;
	@SuppressWarnings("unused")
	private final String type;
	@SuppressWarnings("unused")
	private final String description;
	
	private static final Ability[] VALUES = values();
	private static final int SIZE = VALUES.length;

	Ability(String name, String type, String description) {
		this.name = name;
		this.type = type;
		this.description = description;	
	}
	
	public static Ability getRandomAbility() {
		return VALUES[RandomUtils.nextInt(0, SIZE)];			
	}
	
	public static Ability[] getAllAbilites() {
		return VALUES;
	}
	
	@Override @JsonValue
	public String toString() {
		return this.name;
	}
}
