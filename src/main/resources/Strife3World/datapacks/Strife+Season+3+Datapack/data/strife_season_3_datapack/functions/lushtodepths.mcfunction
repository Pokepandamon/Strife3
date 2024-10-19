execute at @a[tag=inWater,scores={yPos=100},tag=!seenDepthWarning,tag=!deep] run title @p subtitle {"text": "Your mining speed has been decreased","color":"red"}
execute at @a[tag=inWater,scores={yPos=100},tag=!seenDepthWarning,tag=!deep] run title @p times 20 100 20
execute at @a[tag=inWater,scores={yPos=100},tag=!seenDepthWarning,tag=!deep] run title @p title {"text": "You are entering the Depths","color":"red"}
execute at @a[tag=inWater,scores={yPos=100},tag=!seenDepthWarning,tag=!deep] run tag @p add seenDepthWarning