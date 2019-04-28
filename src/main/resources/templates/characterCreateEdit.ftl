<!DOCTYPE html>
<html>
<head>
    <title>Character Editor</title>
    
    <meta http-equiv="Content-Type" content="no-cache; text/html; charset=UTF-8" />
    
    <link rel="stylesheet" href="/webjars/bootstrap/4.0.0-2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/styles.css"/>
    
    
    <script src="/javascript/scripts.js"></script>
    <script src="/webjars/jquery/3.3.1-1/jquery.min.js"></script>
</head>

<body class="text-center">
	<h1>Character Editor</h1>
	
	<#if errorMessages??>
		<div class="pt-3 pb-3">
			<#list errorMessages as error>
				<div class="alert-danger mb-2">${error}</div>
			</#list>
		</div>
	</#if>
	
	<#if pc.playerCharacterId lt 0>
		<#assign formAction="/playerCharacters/new/save"/>
	<#else>
		<#assign formAction="/playerCharacters/${pc.playerCharacterId}/save"/>
	</#if>
	
	
	<form action="${formAction}" method="post">
	
		<#if pc.playerCharacterId gte 0>
			<input type="hidden" name="_method" value="put" />
		</#if>
		
		<div class="pt-2 pb-4">
			Name <input type="text" name="name" value="${pc.name}" size=20>
			
		</div>
		
		<div class="pb-4">
			Race <input type="text" name="race" value="${pc.race}" size=20>				
		</div>
		
		<div class="pb-4">
			Level <input type="text" name="level" value="${pc.level}" size=2 maxlength=2>
			Class <input type="text" name="characterClass" value="${pc.characterClass}" size=20>					
		</div>	
		
		<div class="pb-4">
			<span class="pr-2 d-inline-block">
				STR <input type="text" name="strength" value="${pc.strength}" size=2 maxlength=2><br>
				DEX <input type="text" name="dexterity" value="${pc.dexterity}" size=2 maxlength=2><br>
				CON <input type="text" name="constitution" value="${pc.constitution}" size=2 maxlength=2>
			</span>
		
			<span class="pl-2 d-inline-block">
				INT <input type="text" name="intelligence" value="${pc.intelligence}" size=2 maxlength=2><br>
				WIS <input type="text" name="wisdom" value="${pc.wisdom}" size=2 maxlength=2><br>
				CHA <input type="text" name="charisma" value="${pc.charisma}" size=2 maxlength=2>
			</span>	
		</div>	
		
		<div class="pb-4">
			<span class="pr-4 d-inline-block">
				Head<br>
				<select name="equipment[Head]" class="bigWidth">
					<#list headEquipmentList as iterationItem>
						<#assign selected=""/>
						<#if iterationItem.name() = (pc.equipment.Head.name())!""><#assign selected="selected"/></#if>
						<option value="${iterationItem.name()}" ${selected}>${iterationItem.toString()}</option>
					</#list>
				</select>
			</span>
			
			<span class="pr-4 d-inline-block">
				Body<br>
				<select name="equipment[Body]" class="bigWidth">
					<#list bodyEquipmentList as iterationItem>
						<#assign selected=""/>
						<#if iterationItem.name() = (pc.equipment.Body.name())!""><#assign selected="selected"/></#if>
						<option value="${iterationItem.name()}" ${selected}>${iterationItem.toString()}</option>
					</#list>
				</select>
			</span>
			
			<span class="pr-4 d-inline-block">
				Weapon<br>
				<select name="equipment[Weapon]" class="bigWidth">
					<#list weaponEquipmentList as iterationItem>
						<#assign selected=""/>
						<#if iterationItem.name() = (pc.equipment.Weapon.name())!""><#assign selected="selected"/></#if>
						<option value="${iterationItem.name()}" ${selected}>${iterationItem.toString()}</option>
					</#list>
				</select>
			</span>						
		
		</div>	
		
		<div class="pb-4">
			Ability<br>
				<select name="ability">
					<#list abilityList as iterationItem>
						<#assign selected=""/>
						<#if iterationItem.name() = (pc.ability.name())!""><#assign selected="selected"/></#if>
						<option value="${iterationItem.name()}" ${selected}>${iterationItem.toString()}</option>
					</#list>
				</select>
		</div>		
		
		
		<div>
			<span><a href="/playerCharacters/" class="btn btn-dark">Character Listing</a></span>	
			<span><button class="btn btn-dark" type="submit">Submit</button></span>
		</div>		
	</form>
		
</body>
</html>