execute at @e[type=armor_stand,nbt={CustomName: '{"text":"0,0"}'}] run item replace block ~ ~ ~ container.0 from entity @e[tag=aquaAffinityWearing,sort=nearest,limit=1] armor.head
execute at @e[type=armor_stand,nbt={CustomName: '{"text":"0,0"}'}] run data remove block ~ ~ ~ Items[0].tag.Enchantments[{id:"minecraft:aqua_affinity"}]
execute at @e[type=armor_stand,nbt={CustomName: '{"text":"0,0"}'}] run item replace entity @e[tag=aquaAffinityWearing,sort=nearest,limit=1] armor.head from block ~ ~ ~ container.0
tag @e[nbt={Inventory:[{Slot:103b,tag:{Enchantments:[{id:"minecraft:aqua_affinity"}]}}]}] add aquaAffinityWearing
tag @e[nbt=!{Inventory:[{Slot:103b,tag:{Enchantments:[{id:"minecraft:aqua_affinity"}]}}]}] remove aquaAffinityWearing