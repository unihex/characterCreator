package com.characterCreator.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Equipment {
	
	HELM_OF_HEALING("Helm of Healing", "Head", "When casting healing spells reroll all 1s and 2s"),
	CAP_OF_INVISIBILITY("Cap of Invisibility", "Head", "Once per day: Invisibility"),
	HAT_OF_DISGUISE("Hat of Disguise", "Head", "At will: Alter self"),
	MEDUSAS_MASK("Medusa's Mask", "Head", "Once per day: Flesh to stone"),
	CIRCLET_OF_TELEPATHY("Circlet of Telepathy", "Head", "At will: Telepathy"),
	CROWN_OF_SWORDS("Crown of Swords", "Head", "Reaction: Spiritual weapon"),
	
	ROBE_OF_BONES("Robe of Bones", "Body", "One per day: Summon Undead"),
	KIMONO_OF_SKILL("Kimono of Skill", "Body", "Once per day: Gain +10 bonus to a skill check "),
	GUNMANS_DUSTER("Gunman's Duster", "Body", "+10 DR against arrows, bolts, and bullets"),
	CHAOS_WAR_ARMOR("Chaos War Amor", "Body", "Advantage to saving throws against abilites with the elemental descriptor"),
	HOLY_VESTMENTS("Holy Vestments", "Body", "One per day: Holy"),
	
	PELORS_SWORD("Pelor's Sword", "Weapon", "Advantage on attack rolls versus undead"),
	BOW_OF_JUPITER("Bow of Jupiter", "Weapon", "One per encounter: Lighting Barrage");

	
	
	private final String name;
	private final String type;
	@SuppressWarnings("unused")
	private final String description;
	
	private static class Holder {
		static Map<String, List<Equipment>> equipmentMap = new HashMap<>();
	}
	
	Equipment(String name, String type, String description) {
		this.name = name;
		this.type = type;
		this.description = description;
		
		List<Equipment> equipmentList = Holder.equipmentMap.get(this.type);
		
		if (equipmentList == null) {
			equipmentList = new ArrayList<>();
		}
		
		equipmentList.add(this);
		Holder.equipmentMap.put(this.type, equipmentList);		
	}
	
	public static Equipment getRandomEquipmentByType(String type) {
		List<Equipment> equipmentList = Holder.equipmentMap.get(type);
		
		return equipmentList.get(RandomUtils.nextInt(0, equipmentList.size()));
	}
	
	public static List<Equipment> getAllEquipmentByType(String type) {
		return Holder.equipmentMap.get(type);
	}
	
	@Override @JsonValue
	public String toString() {
		return this.name;
	}
}
