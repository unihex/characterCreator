package com.characterCreator.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.characterCreator.domain.PlayerCharacter;



@Repository
public class PlayerCharacterRepositoryListImplementation implements PlayerCharacterRepository {
	
	List<PlayerCharacter> pcList = new ArrayList<>();

	@Override
	public PlayerCharacter savePlayerCharacter(PlayerCharacter playerCharacter) {
		int size = pcList.size();
		long playerCharacterId = playerCharacter.getPlayerCharacterId();
		
		if (playerCharacterId < 0 || playerCharacterId >= pcList.size()) {
			playerCharacter.setPlayerCharacterId(size);
			pcList.add(playerCharacter);
			
			System.out.println("Saving:" + playerCharacter.toString());
			return playerCharacter;
			
		} else {
			
			pcList.set((int) playerCharacterId, playerCharacter);
			System.out.println(String.format("Updating Player Character %d to :%s", playerCharacterId, playerCharacter.toString()));
			return playerCharacter;
		}
	}
	
	@Override
	public List<PlayerCharacter> savePlayerCharacterList(List<PlayerCharacter> playerCharacterList) {
		for (PlayerCharacter pc : playerCharacterList) {
			this.savePlayerCharacter(pc);
		}
		
		return pcList;	
	}

	@Override
	public List<PlayerCharacter> findAllPlayerCharacters() {
		return pcList;
	}

	@Override
	public PlayerCharacter findPlayCharacterById(long playerCharacterId) {
		if (playerCharacterId < 0 || playerCharacterId >= pcList.size()) {
			return null;
		}
		
		return pcList.get((int) playerCharacterId);
	}
}
