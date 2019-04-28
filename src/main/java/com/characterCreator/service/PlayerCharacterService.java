package com.characterCreator.service;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.characterCreator.domain.PlayerCharacter;



public interface PlayerCharacterService {
	
	List<PlayerCharacter> findAllPlayerCharacters();
	PlayerCharacter findPlayerCharacterById(long playerCharacterId);
	
	PlayerCharacter savePlayerCharacter(PlayerCharacter playerCharacter);
	List<PlayerCharacter> savePlayerCharacterList(List<PlayerCharacter> playerCharacterList);
	
	List<String> validatePlayerCharacter(PlayerCharacter playerCharacter);
	List<String> validatePlayerCharacterList(List<PlayerCharacter> playerCharacterList);
	
	List<String> validateImport(MultipartFile file, String fileFormat);
	
	List<PlayerCharacter> importPlayerCharacters(MultipartFile file, String fileFormat) throws Exception;
	void exportPlayerCharacters(String exportType, PrintWriter writer) throws Exception;

}
