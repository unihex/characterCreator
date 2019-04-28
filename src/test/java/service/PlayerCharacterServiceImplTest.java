package service;

import org.junit.Test;
import org.mockito.Mockito;

import com.characterCreator.domain.PlayerCharacter;
import com.characterCreator.loader.DataLoader;
import com.characterCreator.repository.PlayerCharacterRepository;
import com.characterCreator.service.PlayerCharacterService;
import com.characterCreator.service.PlayerCharacterServiceImpl;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;

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
		
		assertEquals(pcService.findAllPlayerCharacters(), pcList);
		Mockito.verify(pcRepo, Mockito.times(1)).findAllPlayerCharacters();
	}
	
	@Test
	public void verifySavePlayerCharacterListReturnsList() throws Exception {
		List<PlayerCharacter> pcListCopy = this.deepCopyPlayCharacterList();
		 
		Mockito.when(pcRepo.savePlayerCharacterList(pcListCopy)).thenReturn(pcListCopy);
		
		assertEquals(pcService.savePlayerCharacterList(pcList), pcList);
		Mockito.verify(pcRepo, Mockito.times(1)).savePlayerCharacterList(pcList);
	}
	
	@Test
	public void verifyFindPlayerCharacterByIdReturnsCorrectPlayerCharacter() throws Exception {
		PlayerCharacter pc = pcList.get(0);
		PlayerCharacter pcCopy = this.deepCopyPlayerCharacter(pc);
		
		long pcId = pc.getPlayerCharacterId();
		long pcIdCopy = pcCopy.getPlayerCharacterId();
		
		Mockito.when(pcRepo.findPlayerCharacterById(pcIdCopy)).thenReturn(pcCopy);
		
		assertEquals(pcService.findPlayerCharacterById(pcId), pc);
		Mockito.verify(pcRepo, Mockito.times(1)).findPlayerCharacterById(pcId);
	}
	
	@Test
	public void verifySavePlayerCharacterReturnsCorrectPlayerCharacter() throws Exception {
		PlayerCharacter pc = pcList.get(0);
		PlayerCharacter pcCopy = this.deepCopyPlayerCharacter(pc);
		
		Mockito.when(pcRepo.savePlayerCharacter(pcCopy)).thenReturn(pcCopy);
		
		assertEquals(pcService.savePlayerCharacter(pc), pc);
		Mockito.verify(pcRepo, Mockito.times(1)).savePlayerCharacter(pc);
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