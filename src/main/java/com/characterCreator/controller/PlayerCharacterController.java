package com.characterCreator.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.characterCreator.domain.Ability;
import com.characterCreator.domain.Equipment;
import com.characterCreator.domain.PlayerCharacter;
import com.characterCreator.service.PlayerCharacterService;



@Controller
public class PlayerCharacterController {
	
	private PlayerCharacterService pcService;
	
	@Autowired
	public PlayerCharacterController(PlayerCharacterService pcService) {
		this.pcService = pcService;
	}
	
	@GetMapping(value = {"/", "/index", "/playerCharacters"})
	public String index(Model model) {
		List<PlayerCharacter> pcList = pcService.findAllPlayerCharacters();
		
		model.addAttribute("pcList", pcList);
		return "index";
	}
	
	
	@GetMapping(value = "/playerCharacters/export/{fileFormat}")
	public void exportPlayerCharacters(@PathVariable String fileFormat, HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition","attachment;filename=pcexport." + fileFormat);
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		
		pcService.exportPlayerCharacters(fileFormat, writer);
		writer.close();	
	}
	
	@PostMapping(value = "/playerCharacters/import/{fileFormat}")
	public String importPlayerCharacters(@RequestParam MultipartFile file, @PathVariable String fileFormat, RedirectAttributes reAttributes) throws Exception {
		List<String> errorMessages = pcService.validateImport(file, fileFormat);
		
		if (!errorMessages.isEmpty()) {
			reAttributes.addFlashAttribute("errorMessages", errorMessages);
			return "redirect:/index";
		}
		
		List<PlayerCharacter> newPlayerCharacters = pcService.importPlayerCharacters(file, fileFormat);
		
		if (newPlayerCharacters == null) {
			errorMessages.add("Error Importing File! Perhaps an unknown ability or equipment?");
			reAttributes.addFlashAttribute("errorMessages", errorMessages);
			return "redirect:/index";
		}
		
		errorMessages = pcService.validatePlayerCharacterList(newPlayerCharacters);
		
		if (!errorMessages.isEmpty()) {
			reAttributes.addFlashAttribute("errorMessages", errorMessages);
			return "redirect:/index";
		}
		
		pcService.savePlayerCharacterList(newPlayerCharacters);
		return "redirect:/index";
		
		
	}
	
		
	@GetMapping(value = "/playerCharacters/{playerCharacterId}")
	public String viewCharacter(@PathVariable long playerCharacterId, Model model) {		
		PlayerCharacter pc = pcService.findPlayerCharacterById(playerCharacterId);
		
		if (pc == null) {
			return "doesNotExist";
		}
		
		model.addAttribute("pc", pc);
		return "characterProfile";
	}
	
	@GetMapping(value = "/playerCharacters/new")
	public String toCharacterCreation(Model model) {
		
		if (!model.containsAttribute("pc")) {
			model.addAttribute("pc", new PlayerCharacter());
		}
		
		this.addAttributesForCharacterCreateEdit(model);
		
		return "characterCreateEdit";
	}
	
	@PostMapping(value = "/playerCharacters/new/save")
	public String savePlayerCharacter(PlayerCharacter pc, BindingResult bindResult, Model model, RedirectAttributes reAttributes) {
		List<String> errorMessages = pcService.validatePlayerCharacter(pc);
		
		if (errorMessages.isEmpty()) {
			PlayerCharacter savedPC = pcService.savePlayerCharacter(pc);
			return "redirect:/playerCharacters/" + savedPC.getPlayerCharacterId();
			
		} else {
			reAttributes.addFlashAttribute("errorMessages", errorMessages);
			reAttributes.addFlashAttribute("pc", pc);
			return "redirect:/playerCharacters/new";
		}
		
	}
	
	@GetMapping(value = "/playerCharacters/{playerCharacterId}/edit")
	public String toCharacterEdit(@PathVariable long playerCharacterId, Model model) {
		PlayerCharacter pc = pcService.findPlayerCharacterById(playerCharacterId);
		
		if (pc == null) {
			return "doesNotExist";
		}
		
		if (!model.containsAttribute("pc")) {
			model.addAttribute("pc", pc);
		}
	
		this.addAttributesForCharacterCreateEdit(model);
		
		return "characterCreateEdit";
	}
	
	
	@PutMapping(value = "/playerCharacters/{playerCharacterId}/save")
	public String updatePlayCharacter(@PathVariable long playerCharacterId, PlayerCharacter pc, BindingResult bindResult, Model model, RedirectAttributes reAttributes) {		
		List<String> errorMessages = pcService.validatePlayerCharacter(pc);
		
		if (errorMessages.isEmpty()) {
			pc.setPlayerCharacterId(playerCharacterId);
			
			PlayerCharacter savedPC = pcService.savePlayerCharacter(pc);
			return "redirect:/playerCharacters/" + savedPC.getPlayerCharacterId();
			
		} else {
			reAttributes.addFlashAttribute("errorMessages", errorMessages);
			reAttributes.addFlashAttribute("pc", pc);
			
			String redirectPath = String.format("redirect:/playerCharacters/%d/edit", playerCharacterId);
			return redirectPath;
		}
		
	}
	
	private void addAttributesForCharacterCreateEdit(Model model) {
		model.addAttribute("headEquipmentList", Equipment.getAllEquipmentByType("Head"));
		model.addAttribute("bodyEquipmentList", Equipment.getAllEquipmentByType("Body"));
		model.addAttribute("weaponEquipmentList", Equipment.getAllEquipmentByType("Weapon"));
		model.addAttribute("abilityList", Ability.getAllAbilites());
	}

}
