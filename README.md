# README #

This project is a minigame code demonstration by Brian Schultz. This software is released under the [MIT License](https://github.com/TheBrianiac/OtisArena/blob/master/LICENSE).

## TESTING ##
I've already tested this extensively, but if you don't want to take my word for it, here's how you can do it yourself:

1. [Set up](https://www.spigotmc.org/wiki/spigot-installation/) a Spigot test server for Minecraft 1\.11\.
2. Create a lobby world and an arena world or two.
  1. If you need an arena, you can download this [free map](https://www.youtube.com/watch?v=K9rfzmMv5h0) by Phizzle and add the folder inside the .rar archive to your server's main directory.
3. Download the project and build it with maven (run 'mvn compile' from the project folder - details [here](https://spring.io/guides/gs/maven/)).
4. Install the compiled .jar file to your server's plugins directory. Run the server and then enter 'stop'.
5. Update plugins/OtisArena/config.yml with the details for your world(s).
  1. If you use Phizzle's map (see above), the default config will cover it.
6. Get a few friends (or cats than can walk over keyboards), log into your server, and play the game! The team with the most kills after 6 minutes wins.
7. If you find any bugs, [open an issue](https://github.com/TheBrianiac/OtisArena/issues/new) - I'll get an email.

## CONTRIBUTIONS ##
This project was not intended for production use, nor will it be maintained past Minecraft Release 1.11. If you would like to use it, feel free to do so according to the (fairly liberal) [license](https://github.com/TheBrianiac/OtisArena/blob/master/LICENSE).
