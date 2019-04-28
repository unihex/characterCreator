package com.characterCreator.service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.StrReplace;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.characterCreator.domain.Ability;
import com.characterCreator.domain.PlayerCharacter;
import com.characterCreator.helper.ExportHelper;
import com.characterCreator.helper.ImportHelper;
import com.characterCreator.helper.ParseStringEquipmentMap;
import com.characterCreator.repository.PlayerCharacterRepository;


@Service
public class PlayerCharacterServiceImpl implements PlayerCharacterService {
	
	private PlayerCharacterRepository pcRepo;
	
	@Autowired
	public PlayerCharacterServiceImpl(PlayerCharacterRepository pcRepo) {
		this.pcRepo = pcRepo;
	}


	@Override
	public List<PlayerCharacter> findAllPlayerCharacters() {
		return pcRepo.findAllPlayerCharacters();
	}

	@Override
	public PlayerCharacter findPlayerCharacterById(long playerCharacterId) {
		return pcRepo.findPlayerCharacterById(playerCharacterId);
	}

	@Override
	public PlayerCharacter savePlayerCharacter(PlayerCharacter playerCharacter) {
		return pcRepo.savePlayerCharacter(playerCharacter);
	}
	
	
	@Override
	public List<PlayerCharacter> savePlayerCharacterList(List<PlayerCharacter> playerCharacterList) {
		return pcRepo.savePlayerCharacterList(playerCharacterList);
		
	}
	
	@Override
	public void exportPlayerCharacters(String exportType, PrintWriter writer) throws Exception {
		if (exportType.endsWith("json")) {
			ExportHelper.writeJson(this.findAllPlayerCharacters(), writer);
			
		} else if (exportType.equals("csv")) {
			String schema = "playerCharacterId,name,race,level,strength,dexterity,constitution,"
					+ "intelligence,wisdom,charisma,equipment,ability,characterClass";
			
			ExportHelper.writeCsv(schema, this.findAllPlayerCharacters(), writer);
		}
	}

	@Override
	public List<PlayerCharacter> importPlayerCharacters(MultipartFile file, String fileFormat) throws Exception {
		List<PlayerCharacter> newPlayerCharacters = new ArrayList<>();
		
		if (fileFormat.equals("json")) {
			
			newPlayerCharacters = ImportHelper.getListFromJson(file.getBytes(), PlayerCharacter.class);
			
		} else if (fileFormat.equals("csv")) {
			CellProcessor[] schema = new CellProcessor[] {
					new NotNull(new ParseLong()),
					new NotNull(),
					new NotNull(),
					new NotNull(new ParseLong()),
					new NotNull(new ParseLong()),
					new NotNull(new ParseLong()),
					new NotNull(new ParseLong()),
					new NotNull(new ParseLong()),
					new NotNull(new ParseLong()),
					new NotNull(new ParseLong()),
					new NotNull(new ParseStringEquipmentMap()),
					new NotNull(new StrReplace(" ", "_", new ParseEnum(Ability.class, true))),
					new NotNull(),
			};
			
			newPlayerCharacters = ImportHelper.getListFromCsv(file.getInputStream(), schema, PlayerCharacter.class);
			
		}

		return newPlayerCharacters;
	}
	
	@Override
	public List<String> validateImport(MultipartFile file, String fileFormat) {
		List<String> errorMessages = new ArrayList<>();
		
		long fileSize = file.getSize();
		String fileName = file.getOriginalFilename();
		int extensionIndex = fileName.lastIndexOf(".") + 1;
		String extension = fileName.substring(extensionIndex).toLowerCase();
		
		
		if (fileSize == 0) {
			errorMessages.add("No file found");
			
		} else if (fileSize > 500000) {
			errorMessages.add("File too big");
		
		} else if (!extension.equals(fileFormat)) {
			errorMessages.add("Invalid file format: Not " + fileFormat);
		}
		
		return errorMessages;
		
	}
	
	@Override 
	public List<String> validatePlayerCharacterList(List<PlayerCharacter> playerCharacterList) {
		List<String> errorMessages = new ArrayList<>();
		
		for (int i = 0; i < playerCharacterList.size(); i++) {
			errorMessages = validatePlayerCharacter(playerCharacterList.get(i));
			
			if (!errorMessages.isEmpty()) {
				errorMessages.add(0, String.format("Errors for importing Character #%s in the file:", i+1));
				return errorMessages;
			}
		}
		
		
		return errorMessages;
		
	}


	@Override
	public List<String> validatePlayerCharacter(PlayerCharacter playerCharacter) {
		List<String> errorMessages = new ArrayList<>();
		
		String name = playerCharacter.getName();
		String race = playerCharacter.getRace();
		String characterClass = playerCharacter.getCharacterClass();
		String message = "A character's %s can only contain letters numbers and one space";
		long[] stats = playerCharacter.getStats();
		
		if (!isValidString(name)) {
			errorMessages.add(String.format(message, "name"));
		}
		
		if (!isValidString(race)) {
			errorMessages.add(String.format(message, "race"));
		}
		
		if (!isValidString(characterClass)) {
			errorMessages.add(String.format(message, "class"));
		}
		
		if (stats[0] < 1 || stats[0] > 30) {
			errorMessages.add("A character's level must be between 1 and 30");
		}
		
		for (int i = 1; i < stats.length; i++) {
			if (stats[i] < 1 || stats[i] > 28) {
				errorMessages.add("All of a character's stats must be between 1 and 28");
				break;
			}
		}
		
		return errorMessages;
	}
	
	private boolean isValidString(String input) {
		if (StringUtils.isEmpty(input) || !input.trim().equals(input) || !StringUtils.isAlphanumericSpace(input) || StringUtils.split(input).length > 2) {
			return false;
			
		} else {
			return true;
		}
	}

}
