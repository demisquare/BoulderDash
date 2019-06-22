# BoulderDash
 **Boulder Dash** for University Project, written in Java by **Monster Group**.
 
 ## Requires:
 - Java 8 (Java Swing, JavaFX)
 - EmbASP Framework
 
## History:
#### 20-06-19:
- New AudioPlayer(solves OpenJDK problems)

#### 19-06-19:
- Jlabel position fixed in full screen
- Minor audio improvements
- Minor bugfix

#### 18-06-19:
- Fixed full screen mode
- Fixed main class
- Controller improved
- Fixed the enemy not dying after a Rock falls on him
- Fixed some synchronization issues between graphic and logic (tied to the above error)
- Fixed some typos in the Renderer class

#### 17-06-19:
- Now the Player moves 2x faster, it should feel more natural
- Added Hearts in the Score Panel
- Now music loops correctly

#### 15-06-19:
- Tentative implementation of a "delay" on some player actions (like moving, pushing rocks etc.)

#### 14-06-19:
- Multiplayer fixes

#### 13-06-19:
- Added network package
- Added Multiplayer panel.
- Added a new menu song and Credits song
- Added sfx on menu labels.
- Improved Music handler class
- Solved a bug concerning the sprite update
- Now Enemy and Player have the correct background sprites
- Swapped HashMap with ConcurrentHashMap in GameMap for consistency
- Started implementing the AI package (still work in progess)
- Added separate maps for different kinds of blocks (will be useful when implementing AI algorithms)
- Now Enemy object have their own basic AI (direction chosen randomly)

#### 12-06-19:
- Now music can be deactivated by options flag
- Now, if the player digs under two or more rocks, it dies
- Fixed a synchronization problem with GameObject.swap() and GameObject.destroy() methods
- Now the update process should be nearly serial

#### 11-06-19:
- Added Audio folder and Music handler classes
- Now, any Living implementors will die if a rock falls over them vertically
  while in motion (moving under a rock will not result in the Living object dying)

#### 10-06-19:
- Added Options panel and "turn_back" button in the Score panel
- Now rocks fall only if there's enough space
- Code improvements

#### 06-06-19:
- Added Menu and Score class
- Camera bugfix
- Block and Living merged into new class, GameObject
- block sprites management moved to graphics.Level
- New loading symbols: 'D' for Door, 'P' for Player, 'E' for Enemy
- Rocks and diamonds now slide on each other when falling
- Now player can push rocks (still testing)

#### 05-06-19:
- New assets dimensions (32px, 48px, 64px)

#### 28-05-19:
- New levelmap
- Added Door class
- Fixed levelmap reading verse
- Fixed a minor issue with Map.initialize()
- Used some instanceof operator instead of the getType mechanism
- Fixed a bug of various gravity() implementations

#### 27-05-19:
- Improved level map loading from text file
- Added _Algoritme van Dijkstra_ (Dijkstra's Algorithm) on enemies movement (not yet running)

#### 26-05-19:
- Changed the LivingSprite <-> Living relationship to separate more fully the logic package from the graphic one
- Changed some long switches in Level to a simple, static map

#### 25-05-19:
- Added a private constructor to Renderer class
- minor typo correction in a Living comment

#### 24-05-19:
- Camera and assets bugfix

#### 23-05-19:
- Improved Block spritesheet
- Added Ground, Wall and Door sprites
- Added digging skill on Player
- Fixed Enemy walking animation
- Added ls.movePose() and ls.standPose()
- Minor fixes on Level class

#### 22-05-19:
- Added a diamond counter to Map

#### 21-05-19:
- Added Diamond destruction upon collision with Player
- Part of the Enemy movement now works
- Added Gravity.gravity() implementation (doesn't work yet)
- Camera class improvement
- Lock of the camera on border improved and camera centered on the coordinates of the player

#### 17-05-19:
- Fixed Player animation and movement

#### 16-05-19:
- Added levelmap file
- Added a new border condition for Player and Enemy
- Living obj coordinates, x and y, now refers to their position in the logic matrix (Map)

#### 15-05-19:
- Added Renderer Class
- Fixed Map.initialize() to read map settings from file
- Fixed a NullPointerException due to an inexistent spritesheet
- Started implementing the neighboorhood handling for the enemies
- Implemented Enemy.move() - needs testing
- Fixed player issues

#### 14-05-19:
- Re-organized the logic package
- Added World class
- Level fixes

#### 12-05-19:
- Added Implemented Map.initialize() method
- Moved Main.java into start package

#### 07-05-19:
- Added error handling to destroy() methods
- Added LivingSprite class

#### 06-05-19:
- Added EmptyBlock class
- Added Block.update() abstract method
- Added Block.getType() abstract method
- Added Block.Blocks() enumerator
- Aleaned up interfaces
- Small enum cleanups
- Initialized Map.map
- Updated Destructible interface and its implementors

#### 05-05-19:
- Fixed Player Sprite direction

#### 03-05-19:
- Added Sprite, Frame and Animation Classes
- Added Living, Player and Enemy Classes
- Added Player, Enemy and Block Spritesheets
- Added sprites on Block-like Classes

#### 30-04-19:
- Added Gravity interface
- Added Destructible interface
- Added Map class

#### 09-04-19:
- First release.

## Authors:

* **demisquare** - (https://github.com/demisquare)
* **Dygwah98** - (https://github.com/Dygwah98)
* **DavidG33k** - (https://github.com/DavidG33k)
* **TizianaOliviero** - (https://github.com/TizianaOliviero)

