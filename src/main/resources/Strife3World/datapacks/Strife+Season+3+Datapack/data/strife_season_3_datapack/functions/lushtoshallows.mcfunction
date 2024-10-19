execute at @a[tag=inWater,scores={yPos=150},tag=!seenDepthWarning,tag=!shallow] run title @p subtitle {"text": "Your mining speed has been increased","color":"red"}
execute at @a[tag=inWater,scores={yPos=150},tag=!seenDepthWarning,tag=!shallow] run title @p times 20 100 20
execute at @a[tag=inWater,scores={yPos=150},tag=!seenDepthWarning,tag=!shallow] run title @p title {"text": "You are entering the Shallows","color":"red"}
execute at @a[tag=inWater,scores={yPos=150},tag=!seenDepthWarning,tag=!shallow] run tag @p add seenDepthWarning