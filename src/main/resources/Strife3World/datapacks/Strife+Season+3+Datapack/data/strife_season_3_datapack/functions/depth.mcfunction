execute as @a at @s run scoreboard players operation @s depth = @s yPos
execute as @a at @s run scoreboard players operation @s depth -= 255 255
execute as @a at @s run scoreboard players operation @s depth *= -1 -1
execute at @a[tag=inWater] run title @p actionbar [{"text":"Your Depth: "},{"score":{"objective":"depth","name":"*"}}]