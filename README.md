# HardcoreSpeedChrono
Plugin du mode de jeu HardcoreSpeedChrono par Sirius7777

# Principe:
Le but du jeu est de tuer les 3 boss du jeu (Ender Dragon, Wither et les trois Elder Guardian) le plus rapidement possible.

Le plugin permet de chronométrer le temps de jeu et de suivre l'avancée dans la partie (quels boss sont en vie?).


# Autres plugins:
Le plugin fonctionne sans les plugins suivants, mais il est fortement recommandé de les installer pour une meilleure expérience de jeu:
- ChunkGenerator, fork (portage en 1.20) du plugin [ChunkPopulator](https://github.com/BlaisMathieu/OresPopulator) (disponible dans le dossier jars)
- [TreeAssist](https://www.spigotmc.org/resources/treeassist.67436/)
- [TrackingCompass](https://www.spigotmc.org/resources/tracking-compass.79777/)

# Commandes:
> Toutes ces commandes sont des commandes d'administration, elles ne sont pas accessibles aux joueurs.
- /hsc timer
  - start -> démarrer le chrono
  - resume -> reprendre le chrono
  - stop -> stopper le chrono
  - pause -> mettre le chrono en pause
  - reset -> reset le chrono
  - restart -> redémarrer le chrono
  - set -> définir le temps du chrono (**/!\\ Le temps  __DOIT__ être dans le format `HH:MM:SS`** eg: 12:34:56)
- /hsc game
  - start -> démarrer la partie
  - end -> reprendre la partie
  - stop -> arrêter la partie
  - reset -> réinitialiser la partie
- /hsc scoreboard
    - reload -> recharger le scoreboard
    - reset -> réinitialiser le scoreboard
- /hsc bosskill <elder|wither|ender>
- /hsc help

# A faire:
- Commenter le code :)
- Coeurs dans le tab
- Implémenter la récolte des données dans une base
- Ajouter la possibilité d'afficher/masquer le scoreboard pour tout le monde
- Nettoyer le code
- Fichiers de config etc
