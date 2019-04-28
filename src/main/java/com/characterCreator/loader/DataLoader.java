package com.characterCreator.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.characterCreator.domain.Ability;
import com.characterCreator.domain.Equipment;
import com.characterCreator.domain.PlayerCharacter;
import com.characterCreator.repository.PlayerCharacterRepository;



@Component
public class DataLoader implements ApplicationRunner {
	
	private PlayerCharacterRepository pcRepo;
	
	@Autowired
	public DataLoader(PlayerCharacterRepository pcRepo) {
		this.pcRepo = pcRepo;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<PlayerCharacter> playerCharacterList = this.createPlayerCharacterList();
		pcRepo.savePlayerCharacterList(playerCharacterList);	
	}
	
	public List<PlayerCharacter> createPlayerCharacterList() {
		List<PlayerCharacter> playerCharacterList = new ArrayList<>();
		
		playerCharacterList.add(new PlayerCharacter("Ugoth Ugothal", "Orc", "Druid"));
		playerCharacterList.add(new PlayerCharacter("Christine Menpash", "Human", "Cleric"));
		playerCharacterList.add(new PlayerCharacter("Ben Gethros", "Halfling", "Paladin"));
		playerCharacterList.add(new PlayerCharacter("Tiana Juneplum", "Elf", "Alchemist"));
		playerCharacterList.add(new PlayerCharacter("Peter Loriad", "Human", "Bard"));
		
		long lowerBound = 1L;
		long upperBound = 25L;
		
		long level = RandomUtils.nextLong(1, 21);
		for (PlayerCharacter playerCharacter : playerCharacterList) {
			long strength =  RandomUtils.nextLong(lowerBound , upperBound);
			long dexterity =  RandomUtils.nextLong(lowerBound , upperBound);
			long constitution = RandomUtils.nextLong(lowerBound , upperBound);
			long intelligence =  RandomUtils.nextLong(lowerBound , upperBound);
			long wisdom =  RandomUtils.nextLong(lowerBound , upperBound);
			long charisma =  RandomUtils.nextLong(lowerBound , upperBound);
			
			playerCharacter.setStats(level, strength, dexterity, constitution, intelligence, wisdom, charisma);
			playerCharacter.setAbility(Ability.getRandomAbility());
			
			Map<String, Equipment> equipment = new HashMap<>();
			equipment.put("Head", Equipment.getRandomEquipmentByType("Head"));
			equipment.put("Body", Equipment.getRandomEquipmentByType("Body"));
			equipment.put("Weapon", Equipment.getRandomEquipmentByType("Weapon"));
			playerCharacter.setEquipment(equipment);
		}
		
		return playerCharacterList;
	}
}
