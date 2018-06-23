<!DOCTYPE html>
<html>
<head>
    <title>Character Profile - ${pc.name}</title>
    
    <meta http-equiv="Content-Type" content="no-cache; text/html; charset=UTF-8" />
    
    <link rel="stylesheet" href="/webjars/bootstrap/4.0.0-2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/styles.css"/>
    
    
    <script src="/javascript/scripts.js"></script>
    <script src="/webjars/jquery/3.3.1-1/jquery.min.js"></script>
</head>

<body class="text-center">
	<h1>${pc.name}</h1>
	
	<div class="mt-4">
		${pc.race}
	</div> 
	
	<div class="mt-4">
		Level ${pc.level} ${pc.characterClass}
	</div>
	
	<div class="mt-4">
	<span class="pr-2 d-inline-block">
		STR ${pc.strength}<br>
		DEX ${pc.dexterity}<br>
		CON ${pc.constitution}
	</span>
	
	<span class="pl-2 d-inline-block">
		INT ${pc.intelligence}<br> 
		WIS ${pc.wisdom}<br> 
		CHA ${pc.charisma}
	</span>
	</div>
	
	<div class="mt-4">
		Equipment: 
		<#list pc.equipment as key, value>
			<#if key?is_last>and</#if>
			${value}<#sep>,</#sep>
		</#list>
	</div>
	
	<div class="mt-4">
		Ability: ${pc.ability}
	</div>
	
	<div class="mt-4">
		<span>
			<a href="/playerCharacters/" class="btn btn-dark">Character Listing</a>
		</span>
		
		<span>
			<a href="/playerCharacters/${pc.playerCharacterId}/edit" class="btn btn-primary">Edit Character</a>
		</span>	
	</div>
			
</body>
</html>