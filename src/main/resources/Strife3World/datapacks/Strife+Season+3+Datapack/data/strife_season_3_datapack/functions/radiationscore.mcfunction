execute as @a[predicate=strife_season_3_datapack:radiation3,predicate=!strife_season_3_datapack:radiation2,predicate=!strife_season_3_datapack:radiation1] at @s run scoreboard players add @s radiation 1
execute as @a[predicate=strife_season_3_datapack:radiation2,predicate=!strife_season_3_datapack:radiation1] at @s run scoreboard players add @s radiation 2
execute as @a[predicate=strife_season_3_datapack:radiation1] at @s run scoreboard players add @s radiation 3
execute as @a[predicate=!strife_season_3_datapack:radiation3,predicate=!strife_season_3_datapack:radiation2,predicate=!strife_season_3_datapack:radiation1,scores={radiation=1..}] at @s run scoreboard players remove @s radiation 3
effect give @a[scores={radiation=40..}] minecraft:instant_damage 1 0 true
execute as @a[scores={radiation=..0}] at @s run scoreboard players set @s radiation 0