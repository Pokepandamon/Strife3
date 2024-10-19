execute at @a[tag=inWater,scores={yPos=100},tag=!seenDepthWarning,tag=!semiDeep] run title @p subtitle {"text": "Your mining speed has been increased","color":"red"}
execute at @a[tag=inWater,scores={yPos=100},tag=!seenDepthWarning,tag=!semiDeep] run title @p times 20 100 20
execute at @a[tag=inWater,scores={yPos=100},tag=!seenDepthWarning,tag=!semiDeep] run title @p title {"text": "You are entering the Lush","color":"red"}
execute at @a[tag=inWater,scores={yPos=100},tag=!seenDepthWarning,tag=!semiDeep] run tag @p add seenDepthWarning