package com.characterCreator.repository;

import java.util.List;

import com.characterCreator.domain.PlayerCharacter;



public interface PlayerCharacterRepository {
	
	
	PlayerCharacter savePlayerCharacter(PlayerCharacter playerCharacter);
	
	List<PlayerCharacter> findAllPlayerCharacters();
	
	PlayerCharacter findPlayCharacterById(long playerCharacterId);
	
	List<PlayerCharacter> savePlayerCharacterList(List<PlayerCharacter> playerCharacterList);
	
	
	
}