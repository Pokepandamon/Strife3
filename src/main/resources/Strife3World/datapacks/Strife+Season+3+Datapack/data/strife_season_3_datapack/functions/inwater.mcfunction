execute as @a[predicate=strife_season_3_datapack:inwater1,predicate=!strife_season_3_datapack:swimming] at @s run tag @s add inWater
execute as @a[predicate=strife_season_3_datapack:inwater,predicate=strife_season_3_datapack:swimming] at @s run tag @s add inWater
execute as @a[predicate=!strife_season_3_datapack:inwater1,predicate=!strife_season_3_datapack:swimming] at @s run tag @s remove inWater
execute as @a[predicate=!strife_season_3_datapack:inwater,predicate=strife_season_3_datapack:swimming] at @s run tag @s remove inWater
