package com.characterCreator.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.characterCreator.domain.PlayerCharacter;
import com.characterCreator.helper.ErrorMessage;
import com.characterCreator.loader.DataLoader;
import com.characterCreator.repository.PlayerCharacterRepository;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayerCharacterServiceImplTest {
	
	private PlayerCharacterService pcService;
	private PlayerCharacterRepository pcRepo;
	private List<PlayerCharacter> pcList;
	
	@Before
	public void setupPCService() {
		pcRepo = Mockito.mock(PlayerCharacterRepository.class);
		pcService = new PlayerCharacterServiceImpl(pcRepo);
		
		DataLoader loader = new DataLoader(pcRepo);
		pcList = loader.createPlayerCharacterList();
	}
	
	
	@Test
	public void verifyFindAllPlayerCharactersReturnsCorrectList() throws Exception {
		List<PlayerCharacter> pcListCopy = this.deepCopyPlayCharacterList();
		 
		Mockito.when(pcRepo.findAllPlayerCharacters()).thenReturn(pcListCopy);
		
		Assert.assertEquals(pcService.findAllPlayerCharacters(), pcList);
		Mockito.verify(pcRepo, Mockito.times(1)).findAllPlayerCharacters();
	}
	
	@Test
	public void verifySavePlayerCharacterListReturnsList() throws Exception {
		List<PlayerCharacter> pcListCopy = this.deepCopyPlayCharacterList();
		 
		Mockito.when(pcRepo.savePlayerCharacterList(pcListCopy)).thenReturn(pcListCopy);
		
		Assert.assertEquals(pcService.savePlayerCharacterList(pcList), pcList);
		Mockito.verify(pcRepo, Mockito.times(1)).savePlayerCharacterList(pcList);
	}
	
	@Test
	public void verifyFindPlayerCharacterByIdReturnsCorrectPlayerCharacter() throws Exception {
		PlayerCharacter pc = pcList.get(0);
		PlayerCharacter pcCopy = this.deepCopyPlayerCharacter(pc);
		
		long pcId = pc.getPlayerCharacterId();
		long pcIdCopy = pcCopy.getPlayerCharacterId();
		
		Mockito.when(pcRepo.findPlayerCharacterById(pcIdCopy)).thenReturn(pcCopy);
		
		Assert.assertEquals(pcService.findPlayerCharacterById(pcId), pc);
		Mockito.verify(pcRepo, Mockito.times(1)).findPlayerCharacterById(pcId);
	}
	
	@Test
	public void verifySavePlayerCharacterReturnsCorrectPlayerCharacter() throws Exception {
		PlayerCharacter pc = pcList.get(0);
		PlayerCharacter pcCopy = this.deepCopyPlayerCharacter(pc);
		
		Mockito.when(pcRepo.savePlayerCharacter(pcCopy)).thenReturn(pcCopy);
		
		Assert.assertEquals(pcService.savePlayerCharacter(pc), pc);
		Mockito.verify(pcRepo, Mockito.times(1)).savePlayerCharacter(pc);
	}
	
	@Test
	public void verifyValidatePlayerCharacterReturnsErrorMessageIfCharacterHasAnInvalidName() {
		List<String> errorMessages = new ArrayList<>();
		String nameErrorMessage = String.format(ErrorMessage.getStringInputErrorMessage(), "name");
		
		//test null
		PlayerCharacter firstPC = pcList.get(0);
		firstPC.setName(null);
		errorMessages.addAll(pcService.validatePlayerCharacter(firstPC));
		
		//test trailing and leading white spaces
		PlayerCharacter secondPC = pcList.get(1);
		secondPC.setName(" Vivian ");
		errorMessages.addAll(pcService.validatePlayerCharacter(secondPC));
		
		//test non alphanumeric
		PlayerCharacter thirdPC = pcList.get(2);
		thirdPC.setName("Alpha-5");
		errorMessages.addAll(pcService.validatePlayerCharacter(thirdPC));
		
		//test more than one internal space
		PlayerCharacter fourthPC = pcList.get(3);
		fourthPC.setName("Victor von Doom");
		errorMessages.addAll(pcService.validatePlayerCharacter(fourthPC));
		
		Assert.assertEquals(4, errorMessages.size());
		errorMessages.forEach(s -> Assert.assertEquals(nameErrorMessage, s));
	}
	
	private List<PlayerCharacter> deepCopyPlayCharacterList() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JavaType listType = mapper.getTypeFactory().constructCollectionType(List.class, PlayerCharacter.class);
		List<PlayerCharacter> listCopy = mapper.readValue(mapper.writeValueAsBytes(pcList), listType);
		
		return listCopy;
	}
	
	private PlayerCharacter deepCopyPlayerCharacter(PlayerCharacter pc) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		PlayerCharacter pcCopy = mapper.readValue(mapper.writeValueAsBytes(pc), PlayerCharacter.class);
		return pcCopy;
	}
	
}