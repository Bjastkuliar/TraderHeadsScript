# TraderHeads Script
This is a little Java script intended to help with the addition of 
custom heads inside the [Wandering Trader datapack](https://vanillatweaks.net/picker/datapacks/) 
designed by Xisumavoid (and his team)
and used in the [Hermitcraft](https://hermitcraft.com/) minecraft series.

## Usage
Download the source, compile it and execute it. From then
onwards it should be pretty much straightforward what you have to do.

Usually commands or choices accept the extended format as well as an
abbreviated version of it (a single letter). For example if the command `quit`
is available, then typing `q` should do the trick
(actually any word starting with `q` should work).

## Data retrieval
Usually heads sites (like [minecraft-heads](https://minecraft-heads.com/)) give access
to the various skins in different formats. For the `texture` you are looking for
the `value` format listed under the "For Developers" section (at least on that site).

## Implementation of generated files
The script simply appends new lines to the `add_trade.mcfunction` file of
the plugin. When compiling and executing the source, you will need to specify
the **absolute path** at which the file currently resides 
(either the folder or the actual *.mcfunction* file)

Since the default plugin implements a fixed range of trade indexes from
which the game is going to get the additional trades, I have included
(along with a custom [`add_trade.mcfunction`](custom-files/add_trade.mcfunction))
an additional file that has to be replaced inside the original plugin, namely
[`provide_block_trades.mcfunction`](custom-files/provide_block_trades.mcfunction).

> **Note** replacing these two files will have an additional side effect
> which I originally had not intended to have, but that I then chose to keep: traders
> will no longer have a `fixed 8` trades, but instead they'll randomly get from `0 to 8`
> trades instead!

## Concerning intellectual property
The data contained in the [`custom-files`](custom-files) folder are modified files from [vanillatweaks.net](https://vanillatweaks.net/), I do not own any property on them. The data for the custom heads added to [`add_trade.mcfunction`](custom-heads/add_trade.mcfunction) comes from [minecraft-heads](https://minecraft-heads.com/).
