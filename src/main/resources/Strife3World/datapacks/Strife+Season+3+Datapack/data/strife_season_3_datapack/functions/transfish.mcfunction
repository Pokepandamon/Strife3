tag @e[nbt={Variant: 50726145}] remove Trans_Fish
data modify entity @e[tag=Trans_Fish,sort=nearest,limit=1] Variant set value 50726145
tag @e[type=minecraft:tropical_fish,name=Trans,nbt=!{Variant: 50726145}] add Trans_Fish