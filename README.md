Plugin now updated for Minecraft 1.21

I lost my other mc account so I'm not fully able to test everything here, so please lmk if you find bugs or have any feedback or suggestions :)

() indicate placeholder values, | is meant as 'or'

### Usage:
- To set a warp point, name an eye of ender "set (location name)" and then burn it at the desired location
- name a chorus fruit "warp (location name)" then, once eaten, it'll take you to that location
 
### Commands:

- removewarp (warp point name)
Remove the data of a warp point in the current dimension

- listwarps (dimension)
Sends the player a list of all warp locations in the specified dimension

- permission default|(player) list|(permission) [allow|disallow]

Note: [] only if 'list' wasn't used

Sets what functions or commands players are allowed to use with the plugin

Permissions:
 - set_warps - Needed to set warp points
 - remove_warps - Needed to remove existing warp points
 - list_warps - Needed to use /listwarps to see a list of all warp points
 - use_warps - Needed to teleport to warp points via chorus fruits

### Known issues:
- Chorus fruit available area check interferes with ability to go to warp points
- Permissions break if manual edits in the plugin data file leave !permission and permission together

### To Do: (by priority)

1. Add private warp points with "bound" eyes of ender and permission to use 
2. Add eye enchantment effect and particle effects when setting warp points
