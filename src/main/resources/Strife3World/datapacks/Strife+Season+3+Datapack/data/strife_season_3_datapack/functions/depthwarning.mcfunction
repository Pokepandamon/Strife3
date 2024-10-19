execute at @a[tag=inWater,scores={yPos=140},tag=seenDepthWarning] run tag @p remove seenDepthWarning
execute at @a[tag=inWater,scores={yPos=160},tag=seenDepthWarning] run tag @p remove seenDepthWarning
execute at @a[tag=inWater,scores={yPos=110},tag=seenDepthWarning] run tag @p remove seenDepthWarning
execute at @a[tag=inWater,scores={yPos=90},tag=seenDepthWarning] run tag @p remove seenDepthWarning
tag @a[tag=!inWater] remove seenDepthWarning