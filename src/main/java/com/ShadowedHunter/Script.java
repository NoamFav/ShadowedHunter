package src.main;

import java.util.*;


public class Script {

    public static int health = 100;
    public static MainGameWindow gameWindow = MainGameWindow.getInstance();
    public static GameMap gameMap = new GameMap();
    private static int counter = 0;
    private static final String[] messages = {
            "Before we start the game I will give more information about the game.",
            "The game is a text-based, rogue-like game, this means that you won't finish it in one life.",
            "Hint: you can use a sheet of paper to write your way.",
            "The directions are simple: North/South/East/West.",
            "For simplicity, moving one tile only requires the initial of the direction (n/s/e/w).",
            "You can jump two tiles like this: 'jump <direction>' (ex: jump north).",
            "You open doors by typing: 'open <direction> -ern door' (ex: open northern door).",
            "When meet with a stairs, you can either go up or down them by typing: 'climb up/down the stairs'.",
            "The GUI you see gives you different information:",
            "Your health, your timer, the number of times you died (life), and your inventory.",
            "In your inventory, you can see that there are four items.",
            "There are two interactive items, the keys, and the health potions.",
            "Both can be found across the map, and beware, they aren't that easy to find.",
            "Keys can be used to open a locked door by typing: 'unlock <direction> -ern door' (ex: unlock the northern door).",
            "When unlocked, the door is unlocked for the entirety of the game.",
            "Keys aren't linked to a certain door, but they only can open one.",
            "Health potions restore your life to the max by typing: 'use potion'.",
            "Health potions are also single use, so be careful of how you use them.",
            "To pick up an item, when standing on them just time: 'pick up item'.",
            "Furthermore you can 'look around' to see what surrounds you.",
            "The place is full of traps, in your play through you will have to trigger some.",
            "When you trigger one, it can't be triggered again, even if you die, and go through the same tile.",
            "Also when you die, you won't lose your inventory.",
            "You can also stab yourself by typing: 'stab myself'.",
            "Or kill yourself, if you are stuck for example, by typing 'kill myself'.",
            "Finally to leave, type 'exit', and to see the cheatsheet, type 'help'.",
            "Have fun, and enjoy the game:",
            "Hello adventurer, are you ready for today's adventure?",
            "Here stand in front of you a giant dungeon, filled with mysteries.",
            "You've been hired by the king of this realm to kill the Lord ruling the lands you stepped in.",
            "The reason isn't clear to you, probably a question of power.",
            "But you don't really care, the reward is so large that you couldn't refuse in good conscience.",
            "In this dungeon, you'll meet: 4 floors, about 280 rooms to discover, dozens of monsters blocking your way,",
            "traps at every corner to prevent you from ascending the dungeon, and more.",
            "Your goal is simple, reach the top floor of the dungeon.",
            "By the instruction of the king, his throne is on the biggest circular room on the top floor",
            "Beware of your surroundings tho, because there is no safe place in this nightmare.",
            "You're standing in front of a huge door, you can here scream inside.",
            "Determined, you push the door and enter, the dungeon.",
            "The door slowly closes behind you, there is no turning back down.",
            "What do you do?"

    };

    private static final String[] finalStage = {
            "You open the door to the supposed location of the Lord.",
            "When entering you feel a cold breeze on your neck.",
            "Then a voice starts speaking: ",
            "'You finally arrived adventurer! I've been waiting for you. You took your sweet time.'",
            "'I know why you are here, the king finally succeeded in sending someone skilled enough to reach me.'",
            "You then say: 'So you know I have to kill you then ?!'",
            "He answered: 'Yes, it is the natural flow of time.'",
            "'I'm getting old now. it's many dozen winters now that I was in your shoes.'",
            "'Young and looking for wealth, no matter where.'",
            "You say surprised: 'What do you mean in my shoes?'",
            "'I was hired by the king to kill the Lord of this dungeon too.'",
            "'I didn't know then but when I arrived to this exact same room I knew'",
            "'There has to be someone on this throne, the balance of the Realm depends on it.'",
            "'You have to be the villain for the Realm to prosper.'",
            "You say: 'I get to take your place?'",
            "He replies: 'No, you HAVE to, when I'm ready you won't be able to resist.'",
            "'You will stab me, and take my place as Dark Lord of the Realm.'",
            "'And when you reach my age, someone else will come to kill you, and take your place.'",
            "I reply: 'But I don't want to do that, I'm not a bad guy.'",
            "He answers: 'Your free will ended the moment you stepped in this place'",
            "'This is your destiny",
            "He finished his sentence, then suddenly an uncontrollable urge flowed through me.",
            "I run to him and stab him in the chest. He smiles as his body drops to the floor.",
            "As you see his blood dripping on the floor, you feel something, something strange.",
            "His body start to shake, then suddenly, a black cloud leave and start twirling around you.",
            "After a moment the cloud enters your body, you feel different, powerful, more than ever",
            "You then sit on the immense throne in the middle of the room.",
            "This is your life now, until the end, you say:",
            "'I can work with that with a huge smile.",
            "The end."
    };
    public static void setGameWindow(MainGameWindow window) {
        gameWindow = window;
    }

    public static void updateHealth(int newHealth) {
        health = newHealth;
        gameWindow.repaint();
    }

    public static void kill() {
        updateHealth(100);
        MainGameWindow.setIconPosition(429,301);
        MainGameWindow.panel.incrementLifeNumber();
        gameMap.switchMap(0);
        Map<String, Integer> savedInventory = gameMap.getPlayer().inventory;
        gameMap.initializePlayer(1,17);
        gameMap.getPlayer().loadInventory(savedInventory);

    }

    public static void Action(String Action) {
        String action = Action.toLowerCase();

        Player player = gameMap.getPlayer();
        String[][] currentMap = gameMap.getCurrentMap();

        int x = player.getPosition().getX();
        int y = player.getPosition().getY();


        switch (action) {
            case "intro":
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (counter < messages.length) {
                            gameWindow.setOutputText(messages[counter]);
                            counter++;
                        } else {
                            timer.cancel();
                        }
                    }
                }, 0, 5000);
                break;
            case "help":
                gameWindow.setOutputText("Showing cheatSheet for 10s");
                break;
            case "n":
                gameWindow.setOutputText("Going north");
                gameMap.movePlayerUp();
                traps();
                loreRoom();
                break;

            case "e":
                gameWindow.setOutputText("Going east");
                gameMap.movePlayerRight();
                traps();
                loreRoom();
                break;

            case "w":
                gameWindow.setOutputText("Going west");
                gameMap.movePlayerLeft();
                traps();
                loreRoom();
                break;

            case "s":
                gameWindow.setOutputText("Going south");
                gameMap.movePlayerDown();
                traps();
                loreRoom();
                break;

            case "jump north":
                gameWindow.setOutputText("Jumping north");
                gameMap.movePlayerUp();
                gameMap.movePlayerUp();
                traps();
                loreRoom();
                break;

            case "jump east":
                gameWindow.setOutputText("Jumping east");
                gameMap.movePlayerRight();
                gameMap.movePlayerRight();
                traps();
                loreRoom();
                break;

            case "jump west":
                gameWindow.setOutputText("Jumping west");
                gameMap.movePlayerLeft();
                gameMap.movePlayerLeft();
                traps();
                loreRoom();
                break;

            case "jump south":
                gameWindow.setOutputText("Jumping south");
                gameMap.movePlayerDown();
                gameMap.movePlayerDown();
                traps();
                loreRoom();
                break;

            case "open northern door":
                switch (currentMap[y - 1][x]) {
                    case "d" -> {
                        gameWindow.setOutputText("Opening the door");
                        gameMap.openUp();
                        traps();
                    }
                    case "t" -> {
                        boolean survived = trapDoor();
                        currentMap[y - 1][x] = "d";
                        if (survived) {
                            gameMap.openUp();
                            traps();
                        }
                    }
                    case "s" -> {
                        secretDoor();
                        gameMap.openUp();
                    }
                    case "l" -> gameWindow.setOutputText("The door is locked");
                    default -> gameWindow.setOutputText("No door here");
                }
                break;

            case "open eastern door":
                switch (currentMap[y][x + 1]) {
                    case "d" -> {
                        gameWindow.setOutputText("Opening the door");
                        gameMap.openRight();
                        traps();
                    }
                    case "t" -> {
                        boolean survived = trapDoor();
                        currentMap[y][x + 1] = "d";
                        if (survived) {
                            gameMap.openRight();
                            traps();
                        }
                    }
                    case "s" -> {
                        secretDoor();
                        gameMap.openRight();
                    }
                    case "l" -> gameWindow.setOutputText("The door is locked");
                    default -> gameWindow.setOutputText("No door here");
                }
                break;

            case "open western door":
                if (x == 1 && y == 17){
                    gameWindow.setOutputText("You can't turn back, you have a mission to do.");
                    break;
                }
                switch (currentMap[y][x - 1]) {
                    case "d" -> {
                        gameWindow.setOutputText("Opening the door");
                        gameMap.openLeft();
                        traps();
                    }
                    case "t" -> {
                        boolean survived = trapDoor();
                        currentMap[y][x - 1] = "d";
                        if (survived) {
                            gameMap.openLeft();
                            traps();
                        }
                    }
                    case "s" -> {
                        secretDoor();
                        gameMap.openLeft();
                    }
                    case "l" -> gameWindow.setOutputText("The door is locked");
                    default -> gameWindow.setOutputText("No door here");
                }
                break;

            case "open southern door":
                switch (currentMap[y + 1][x]) {
                    case "d" -> {
                        gameWindow.setOutputText("Opening the door");
                        gameMap.openDown();
                        traps();
                    }
                    case "t" -> {
                        boolean survived = trapDoor();
                        currentMap[y + 1][x] = "d";
                        if (survived) {
                            gameMap.openDown();
                            traps();
                        }
                    }
                    case "s" -> {
                        secretDoor();
                        gameMap.openDown();
                    }
                    case "l" -> gameWindow.setOutputText("The door is locked");
                    default -> gameWindow.setOutputText("No door here");
                }
                break;

            case "unlock northern door":
                if (currentMap[y - 1][x].equals("l")) {
                    if (gameMap.getPlayer().getItemCount("k") >= 1){
                        gameWindow.setOutputText("the northern door is now unlocked");
                        gameMap.getPlayer().removeItem("k");
                        currentMap[y - 1][x] = "d";
                    }
                }
            case "unlock eastern door":
                if (currentMap[y][x + 1].equals("l")) {
                    if (gameMap.getPlayer().getItemCount("k") >= 1){
                        gameWindow.setOutputText("the eastern door is now unlocked");
                        gameMap.getPlayer().removeItem("k");
                        currentMap[y][x + 1] = "d";
                    }
                }
            case "unlock western door":
                if (currentMap[y][x - 1].equals("l")) {
                    if (gameMap.getPlayer().getItemCount("k") >= 1){
                        gameWindow.setOutputText("the western door is now unlocked");
                        gameMap.getPlayer().removeItem("k");
                        currentMap[y][x - 1] = "d";
                    }
                }
            case "unlock southern door":
                if (currentMap[y + 1][x].equals("l")) {
                    if (gameMap.getPlayer().getItemCount("k") >= 1){
                        gameWindow.setOutputText("the southern door is now unlocked");
                        gameMap.getPlayer().removeItem("k");
                        currentMap[y + 1][x] = "d";
                    }
                }
                break;

            case "jump":
                gameWindow.setOutputText("Youpi!!!");
                break;

            case "look around","l":
                String north = (y - 1 >= 0) ? currentMap[y - 1][x] : "Out of bounds";
                String south = (y + 1 < currentMap.length) ? currentMap[y + 1][x] : "Out of bounds";
                String east = (x + 1 < currentMap[y].length) ? currentMap[y][x + 1] : "Out of bounds";
                String west = (x - 1 >= 0) ? currentMap[y][x - 1] : "Out of bounds";
                String instance = currentMap[y][x];
                String[] data = {north, south, east, west, instance};

                for (int way = 0; way < data.length; way++) {
                    if (data[way].equals("x")) {
                        data[way] = "There is a wall";
                    } else if (data[way].matches("[ltd]")) {
                        data[way] = "There is a door";
                    } else if (data[way].equals("s")) {
                        data[way] = "There is a wall, a strange-looking wall";
                    } else if (data[way].equals("p")) {
                        data[way] = "There is a portcullis";
                    } else if (data[way].equals("a")) {
                        data[way] = "There is an archway";
                    } else if (data[way].equals("k")) {
                        data[way] = "There is a rusty key";
                    } else if (data[way].equals("he")) {
                        data[way] = "There is a health potion";
                    } else if (data[way].equals("stu")) {
                        data[way] = "There is a stair going up";
                    } else if (data[way].equals("std")) {
                        data[way] = "There is a stair going down";
                    } else {
                        data[way] = "There is nothing";
                    }
                }
                gameWindow.setOutputText(data[0] + " in the north.  " + data[1] + " in the south. " + data[2] + " in the east.  " + data[3] + " in the west.  " + data[4] + " where you are.");
                break;

            case "stab myself":
                gameWindow.setOutputText("you stabbed yourself: -10HP");
                updateHealth(health - 10);
                break;

            case "use potion":
                gameWindow.setOutputText("Using health potion");
                if (gameMap.getPlayer().getItemCount("he") >= 1 && health != 100) {
                    gameMap.getPlayer().removeItem("he");
                    updateHealth(100);
                } else if (gameMap.getPlayer().getItemCount("he") >= 1 && health == 100){
                    gameWindow.setOutputText("Your health is already maxed out.");
                } else {
                    gameWindow.setOutputText("You don't have any health potion");
                }
                break;

            case "kill myself":
                gameWindow.setOutputText("You committed suicide");
                kill();
                break;

            case "climb up the stairs":
                gameMap.stairsUp();
                break;

            case "climb down the stairs":
                gameMap.stairsDown();
                break;

            case "pick up item":
                pickItems();
                break;


            case "exit":
                gameWindow.setOutputText("Exiting\n");
                SaveLoadSystem.saveGame(GameMap.maps,gameMap.getPlayer(),"/src/main/resources/Save/Save.txt");
                System.exit(0);
                break;

            default:
                gameWindow.setOutputText("Invalid input \n");
                break;
        }

    }

    public static void pickItems() {
        Player player = gameMap.getPlayer();
        String[][] currentMap = gameMap.getCurrentMap();

        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        switch (currentMap[y][x]) {
            case "he":
                player.pickUpItem("he");
                gameWindow.setOutputText("You picked up a potion of Health, when use it will restore all your life, use wisely.");
                currentMap[y][x] = "";
                break;
            case "k":
                player.pickUpItem("k");
                gameWindow.setOutputText("You picked up an old rusty key, it can be used to open locked door.");
                currentMap[y][x] = "";
                break;
            default:
                gameWindow.setOutputText("Nothing to pick up");
                break;
        }
    }

    public static void secretDoor() {
        Random rand = new Random();
        int text = rand.nextInt(4);
        switch (text) {
            case 0 -> gameWindow.setOutputText("The wall in front of you feels weird, you try to touch it, the wall moves, it was a secret door!!");
            case 1 -> gameWindow.setOutputText("You see a door knob on the wall, you turn it, the wall move to reveal a secret pathway.");
            case 2 -> gameWindow.setOutputText("You see circular trace in front of this wall, you try to pull the wall in the direction of the trace, a secret door opens.");
            case 3 -> gameWindow.setOutputText("The wall start moving, opening a secret pathway, you hide behind it, a goblin walk out of it, you sneak past him into the new path.");
        }
    }

    public static boolean trapDoor() {
        Random rand = new Random();
        int effect = rand.nextInt(4);
        double chance = rand.nextDouble();

        if (chance <= 0.50 || health <= 50) {
            switch (effect) {
                case 0 -> gameWindow.setOutputText("When opening the door, a mechanism triggers, and the door blow up in your face. You died");
                case 1 -> gameWindow.setOutputText("The door knob seems to be stiff, you break the door open, when the door opens, a goblin stands behind it, jump on you and start eating your face. You died");
                case 2 -> gameWindow.setOutputText("You open the door, a string was connected to it, a blade come out of the wall and cut your head off. You died");
                case 3 -> gameWindow.setOutputText("You barely got time to turn the door knob that a giant sledge hammer hits you in the back of the head, bursting it open. You died");
            }
            kill();
            return false;
        } else {
            switch (effect) {
                case 0 -> gameWindow.setOutputText("When opening the door, a mechanism triggers, your reflex saves you, you jump back avoiding the blast. -50HP");
                case 1 -> gameWindow.setOutputText("The door knob seems to be stiff, you break the door open, when the door opens, a goblin stands behind it, you react sufficiently fast and stab him when he jump on you. -50HP");
                case 2 -> gameWindow.setOutputText("You open the door, a string was connected to it, a blade come out of the wall, you manage to avoid it. -50HP");
                case 3 -> gameWindow.setOutputText("You barely got time to turn the door knob that a giant sledge hammer swing down on you. You crouch to avoid it. -50HP");
            }
            updateHealth(health-50);
            return true;
        }
    }

    public static void traps() {
        Player player = gameMap.getPlayer();
        String[][] currentMap = gameMap.getCurrentMap();
        Random rand = new Random();
        double chance = rand.nextDouble();
        int text = rand.nextInt(4);

        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        switch (currentMap[y][x]) {
            case "pi":
                if (chance <= 0.25 || health <= 25) {
                    switch (text){
                        case 0 -> gameWindow.setOutputText("You hit a tripwire, poisoning insects fall on you, you try to get them off but one manages to bite you. You died");
                        case 1 -> gameWindow.setOutputText("You hit a pressure plate, a troll fall from it, you try to fight him, but he takes the upper hand and smash your head with his weapons. You died");
                        case 2 -> gameWindow.setOutputText("Out of the blue, a zombie pop out, bits you, and run away, you try to suck out the saliva, without success. You 'kinda' died");
                        case 3 -> gameWindow.setOutputText("A tarantula climbs silently onto your shoulder, sneaking her way to your neck, you don't feel a thing, she bits you. You died");
                    }
                    kill();
                } else {
                    switch (text){
                        case 0 -> gameWindow.setOutputText("You hit a tripwire, poisoning insects falls on you, you successfully get them off you. -25HP");
                        case 1 -> gameWindow.setOutputText("You hit a pressure plate, a troll fall from it, you engage into a fierce combats whit him, you take the upper hand and stick you sword in his skulls. -25HP");
                        case 2 -> gameWindow.setOutputText("Out of the blue, a zombie pop out, try to bits you. Your reflex save you, you swing your sword and cut his head clean off. -25HP");
                        case 3 -> gameWindow.setOutputText("A tarantula climb silently onto your shoulder, sneaking her way to your neck, you realise that, and throw her into the wall like a baseball. -25HP");
                    }
                    updateHealth(health - 25);
                }
                currentMap[y][x] = "";
                break;


            case "sp":
                if (chance <= 0.75 || health <= 75) {
                    switch (text){
                        case 0 -> gameWindow.setOutputText("You walk on a pressure plate, and the floor crumbles under your feet, revealing spikes, you end up impaled. You died");
                        case 1 -> gameWindow.setOutputText("The tile you just stepped in breaks under your weight, rotating blade where hidden underneath, and you get shredded to pieces. You died");
                        case 2 -> gameWindow.setOutputText("You step on a rug, a hole is hidden by it. you fall into a pit of spiders. You get scared and have a heart attack. You died");
                        case 3 -> gameWindow.setOutputText("You walk on a weighted tile, and spikes rise through the flour, cutting holes in you. You died");
                    }
                    kill();
                } else {
                    switch (text){
                        case 0 -> gameWindow.setOutputText("You walk on a pressure plate, and the floor crumbles under your feet, revealing spikes, you manage to grab the ledge. -75HP");
                        case 1 -> gameWindow.setOutputText("The tile you just stepped in break under your weight, the rotating blades where hidden underneath, and the blades where off. -75HP");
                        case 2 -> gameWindow.setOutputText("You step on a rug, a hole is hidden by it. you fall into a pit of spiders. You brandish your sword to drive them away and climb out of the hole. -75HP");
                        case 3 -> gameWindow.setOutputText("You walk on a weighted tile, and spikes rise through the flour, by pure luck, not a single one hits you. -75HP");
                    }
                    updateHealth(health - 75);
                }
                currentMap[y][x] = "";
                break;

            case "fp":
                if (chance <= 0.40 || health <= 40) {
                    switch (text){
                        case 0 -> gameWindow.setOutputText("The floor starts to shake violently, and a tile from the ceiling break and drop on you, it hits your head and shatter your skull. You died");
                        case 1 -> gameWindow.setOutputText("You see a vase on the side of the corridor, you pick it up, and a mechanism triggers. Spikes fall on you, and you try to avoid them but one falls right trough you. You died");
                        case 2 -> gameWindow.setOutputText("You hit a tripwire on the floor, nothing happens then suddenly, spikes fall on you, you try to roll away, to no avail, and one hit you mid-roll in the back. You died");
                        case 3 -> gameWindow.setOutputText("You see a lever on the wall, curious, you pull it, and gold falls on you, out of nowhere a giant brick falls on you and bursts your thorax. You died");
                    }
                    kill();
                } else {
                    switch (text) {
                        case 0 -> gameWindow.setOutputText("The floor starts to shake violently, and a tile from the ceiling breaks and drops on you, you manage to avoid it. -40HP");
                        case 1 -> gameWindow.setOutputText("You see a vase on the side of the corridor, you pick it up, and a mechanism triggers. Spikes fall on you, you successfully avoid all of them. -40HP");
                        case 2 -> gameWindow.setOutputText("You hit a tripwire on the floor, nothing happens then suddenly, spikes fall on you, you do the best somersault ever, and avoid them all. -40HP");
                        case 3 -> gameWindow.setOutputText("You see a lever on the wall, curious, you pull it, gold falls on you, then out of nowhere a giant brick falls on you. You jump on the side to avoid it. -40HP");
                    }
                    updateHealth(health - 40);
                }
                currentMap[y][x] = "";
                break;

            case "pa":
                if (chance <= 0.50 || health <= 50) {
                    switch (text) {
                        case 0 -> gameWindow.setOutputText("You walk on a pressure plate, arrows come out of holes in the wall, to make other holes in your body. You died");
                        case 1 -> gameWindow.setOutputText("You trigger a tripwire, you hear gears in the wall, suddenly spikes come out of the wall, turning you into Swiss cheese. You died");
                        case 2 -> gameWindow.setOutputText("An arrow falls on you, when you pick it up you realize it is poisoned, a few second later you convulse on the floor. You died");
                        case 3 -> gameWindow.setOutputText("A skeleton dropped from the ceiling scaring you to death. You died");
                    }
                    kill();
                } else {
                    switch (text) {
                        case 0 -> gameWindow.setOutputText("You walk on a pressure plate, arrows come out of holes in the wall, and your dance class helps you avoid them. -25HP");
                        case 1 -> gameWindow.setOutputText("You trigger a tripwire, you hear gears in the wall, suddenly spikes come out of the wall, luckily they stopped right in front of your nose. -25HP");
                        case 2 -> gameWindow.setOutputText("An arrow falls on you, when you pick it up you realize it is poisoned, you quickly suck the poison out. -25HP");
                        case 3 -> gameWindow.setOutputText("A skeleton dropped from the ceiling, scared, you punch the skeleton with everything you've got. -25HP");
                    }
                    updateHealth(health - 25);
                }
                currentMap[y][x] = "";
                break;

            case "sw":
                if (chance <= 0.90 || health <= 90) {
                    switch (text) {
                        case 0 -> gameWindow.setOutputText("Your foot gets stuck between two tiles, and suddenly the walls around you start to shrink into you, you try to escape but you slowly get squeezed to death. You died");
                        case 1 -> gameWindow.setOutputText("You curiously pull the lever that's sticking to the wall, you don't even have time to react the walls shrink in an instant, and you with it. You died");
                        case 2 -> gameWindow.setOutputText("A werewolf appears from the corridor. You attempt to escape, but he grabs you and throws you into a wall, splattering it with your blood. You died");
                        case 3 -> gameWindow.setOutputText("You walked a pressure plate, the tile you were in springs you out into the ceiling crushes you like a soda can. You died");
                    }
                    kill();
                } else {
                    switch (text) {
                        case 0 -> gameWindow.setOutputText("Your foot gets stuck between two tiles, and suddenly the walls around you start to shrink into you, you manage to free your feet and escape the wall. -90HP");
                        case 1 -> gameWindow.setOutputText("You curiously pull the lever that's sticking to the wall, by fear the moment you pull the lever, you jump away, and the wall shrinks in an instant behind you. -90HP");
                        case 2 -> gameWindow.setOutputText("A werewolf appears from the corridor. You successfully avoid his first attack and stab him in the back. -90HP");
                        case 3 -> gameWindow.setOutputText("You walked a pressure plate, the tile you stepped in spring out, but luckily you managed to avoid it. -90HP");
                    }
                    updateHealth(health - 90);
                }
                currentMap[y][x] = "";
                break;

            case "wb":
                switch (text) {
                    case 0 -> gameWindow.setOutputText("You hit a tripwire, and four axes pop out the walls, cutting you into five equal pieces. You died");
                    case 1 -> gameWindow.setOutputText("You step on a pressure plate, and two giant sledgehammers swing down from the ceiling bashing your skulls to shred. You died");
                    case 2 -> gameWindow.setOutputText("A blade swings down from the ceiling making a perfect hole in your head. You died");
                    case 3 -> gameWindow.setOutputText("A black knight appears in the corridor. Despite your attempts to defend yourself, it’s to no avail. With one swift swing of his long sword, he cuts your head right off. You died.");
                }
                kill();
                currentMap[y][x] = "";
                break;

            case "h":
                if (health>20) {
                    switch (text) {
                        case 0 -> gameWindow.setOutputText("You step on a rug in the middle of the corridor, it makes you fall into the previous floor. -20HP");
                        case 1 -> gameWindow.setOutputText("You whistle into the long corridor of the dungeon, you don't pay attention to the hole in the floor, you fall into the previous floor. -20HP");
                        case 2 -> gameWindow.setOutputText("You step on a plank in the middle of the corridor, it snaps under your feet, and you fall into the previous floor. -20HP");
                        case 3 -> gameWindow.setOutputText("You trip on a tile and fall into a hole in the middle of the corridor, you fall into the previous floor. -20HP");
                    }
                    updateHealth(health - 20);

                } else {
                    switch (text) {
                        case 0 -> gameWindow.setOutputText("You step on a rug in the middle of the corridor, it makes you fall into the previous floor, your legs go into your body. You died");
                        case 1 -> gameWindow.setOutputText("You whistle into the long corridor of the dungeon, you don't pay attention to the hole in the floor, you fall and break your legs, an ogre passing by finish the job. You died");
                        case 2 -> gameWindow.setOutputText("You step on a plank in the middle of the corridor, it snaps under your feet when hitting the floor, the plank hits your head. You died");
                        case 3 -> gameWindow.setOutputText("You trip on a tile and fall in a hole in the middle of the corridor, you fall into the hole head first and break your neck. You died");
                    }
                    kill();
                }

                gameMap.switchMap(GameMap.currentMapIndex-1);
                traps();
                break;

            case "p":
                switch (text) {
                    case 0 -> gameWindow.setOutputText("You pass under a portcullis, you feel anxious passing under those spikes.");
                    case 1 -> gameWindow.setOutputText("When passing under the portcullis, you hear a click, you quickly jump forward and avoid the spikes of the gate. The gate comes back up a moment later ");
                    case 2 -> gameWindow.setOutputText("The portcullis in front of you feel scary, by fear, you run to pass it, the gate didn't budge tho.");
                    case 3 -> gameWindow.setOutputText("Scared by the spikes of the portcullis you throw something underneath, the gate quickly closes on it, and when it goes back up, you safely roll underneath.");
                }

        }
    }

    public static void loreRoom() {
        Player player = gameMap.getPlayer();
        String[][] currentMap = gameMap.getCurrentMap();

        int x = player.getPosition().getX();
        int y = player.getPosition().getY();



        switch (currentMap[y][x]) {
            case "1":
                gameWindow.setOutputText("An old broken table lies in the middle of the room, and a pile of rotten leather lies in the north-west corner of the room.");
                deleteLore("1");
                break;
            case "2":
                deleteLore("2");
                break;
            case "3":
                gameWindow.setOutputText("Someone has scrawled a fell symbol on the north wall, and patches of mushrooms grow in the south-west corner of the room.");
                deleteLore("3");
                break;
            case "4":
                gameWindow.setOutputText("A set of demonic war masks hangs on the north wall, and someone has scrawled 'This is not a secret door' on the south wall.");
                deleteLore("4");
                break;
            case "5":
                deleteLore("5");
                break;
            case "6":
                gameWindow.setOutputText("Someone has scrawled 'two, two, two, seven' in draconic script on the north wall, and the sound of horns fills the room. There is a key in the room.");
                deleteLore("6");
                break;
            case "7":
                gameWindow.setOutputText("Several square holes are cut into the ceiling and floor, and a rusted amulet lies in the north-west corner of the room.");
                deleteLore("7");
                break;
            case "8":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("8");
                break;
            case "9":
                deleteLore("9");
                break;
            case "10":
                gameWindow.setOutputText("Someone has scrawled 'Abandon all hope' in goblin runes on the north wall, and several pieces of rotten leather are scattered throughout the room. There is a key in the room.");
                deleteLore("10");
                break;
            case "11":
                gameWindow.setOutputText("Several iron cages are scattered throughout the room, and several pieces of rotting wood are scattered throughout the room.");
                deleteLore("11");
                break;
            case "12":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("12");
                break;
            case "13":
                gameWindow.setOutputText("There is a key and a health potion in the room.");
                deleteLore("13");
                break;
            case "14":
                deleteLore("14");
                break;
            case "15":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("15");
                break;
            case "16":
                deleteLore("16");
                break;
            case "17":
                deleteLore("17");
                break;
            case "18":
                gameWindow.setOutputText("A mural of an undead goddess covers the ceiling, and someone has scrawled 'There is no way out' in orcish runes on the north wall. There is a health potion in the room.");
            case "19":
                deleteLore("19");
                break;
            case "20":
                gameWindow.setOutputText("Someone has scrawled 'We've run out of rope' on the south wall, and a sulphurous odor fills the north- west corner of the room. There is a key in the room.");
            case "21":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("21");
                break;
            case "22":
                gameWindow.setOutputText("The room has a high domed ceiling, and a faded and torn tapestry hangs from the west wall. There is a key in the room.");
                deleteLore("22");
                break;
            case "23":
                gameWindow.setOutputText("A chute descends from the room into the next dungeon level down, and a fountain and statue of a Draconic God sits in the east side of the room.");
                deleteLore("23");
                break;
            case "24":
                gameWindow.setOutputText("Part of the north wall has collapsed into the room, and a forge and anvil sit in the center of the room. There is a key in the room.");
                deleteLore("24");
                break;
            case "25":
                gameWindow.setOutputText("A tile labyrinth covers the floor, and someone has scrawled nine, eight, three in draconic script on the north wall.");
                deleteLore("25");
                break;
            case "26":
                deleteLore("26");
                break;
            case "27":
                gameWindow.setOutputText("A balcony hangs from the south wall, and the floor is covered with broken glass.");
                deleteLore("27");
                break;
            case "28":
                gameWindow.setOutputText("A shallow pit lies in the north side of the room, and a putrid odor fills the south-west corner of the room.");
                deleteLore("28");
                break;
            case "29":
                gameWindow.setOutputText("A stair ascends to a wooden platform in the south- west corner of the room, and a carved stone statue stands in the south-west corner of the room.");
                deleteLore("29");
                break;
            case "30":
                gameWindow.setOutputText("A mural of ancient mythology covers the ceiling, and several adventurer corpses are scattered throughout the room. There is a key in the room.");
                deleteLore("30");
                break;
            case "31":
                deleteLore("31");
                break;
            case "32":
                deleteLore("32");
                break;
            case "33":
                gameWindow.setOutputText("A stair ascends to a balcony hanging from the north wall, and the sound of footsteps can be faintly heard near the west wall.");
                deleteLore("33");
                break;
            case "34":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("34");
                break;
            case "35":
                gameWindow.setOutputText("A tile mosaic of legendary monsters covers the floor, and a simple fireplace sits against the north wall.");
                deleteLore("35");
                break;
            case "36":
                deleteLore("36");
                break;
            case "37":
                gameWindow.setOutputText("A crater has been blasted into the floor in the south- east corner of the room, and a rusted amulet lies in the north-east corner of the room.");
                deleteLore("37");
                break;
            case "38":
                deleteLore("38");
                break;
            case "39":
                gameWindow.setOutputText("Someone has scrawled 'The walls listen' on the east wall, and several barrel staves are scattered throughout the room. There is a key in the room.");
                deleteLore("39");
                break;
            case "40":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("40");
                break;
            case "41":
                deleteLore("41");
                break;
            case "42":
                gameWindow.setOutputText("A group of monstrous faces have been carved into the north wall, and a circle of tall stones stands in the south-east corner of the room. There is a key in the room.");
                deleteLore("42");
                break;
            case "43":
                gameWindow.setOutputText("A set of demonic war masks hangs on the west wall, and someone has scrawled 'I've forgotten my name' on the south wall. There is a health potion in the room.");
                deleteLore("43");
                break;
            case "44":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("44");
                break;
            case "45":
                deleteLore("45");
                break;
            case "46":
                gameWindow.setOutputText("An iron sarcophagus sits in the south-east corner of the room, and the floor is covered with mud.");
                deleteLore("46");
                break;
            case "47":
                gameWindow.setOutputText("Someone has scrawled 'Twist the cog to reset the trap' on the west wall, and laughter can be faintly heard near the south wall.");
                deleteLore("47");
                break;
            case "48":
                gameWindow.setOutputText("A magical mosaic on the east wall depicts the betrayal of whomever views it, and a faded and torn tapestry hangs from the south wall.");
                deleteLore("48");
                break;
            case "49":
                gameWindow.setOutputText("there is a key in the room.");
                deleteLore("49");
                break;
            case "50":
                gameWindow.setOutputText("A forge and anvil sit in the north side of the room, and a clanking sound can be faintly heard near the west wall. There is a key in the room.");
                deleteLore("50");
                break;
            case "51":
                deleteLore("51");
                break;
            case "52":
                gameWindow.setOutputText("A forge and anvil sit in the north side of the room, and a stream of oil flows through the room.");
                deleteLore("52");
                break;
            case "53":
                gameWindow.setOutputText("A tapestry of ancient mythology hangs from the west wall, and the sound of footsteps can be heard in the east side of the room. There is a health potion in the room.");
                deleteLore("53");
                break;
            case "54":
                gameWindow.setOutputText("The floor is covered in perfect hexagonal tiles, and a pile of shattered weapons lies in the west side of the room.");
                deleteLore("54");
                break;
            case "55":
                gameWindow.setOutputText("A stone dais sits in the east side of the room, and someone has scrawled a large X on the south wall.");
                deleteLore("55");
                break;
            case "56":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("56");
                break;
            case "57":
                gameWindow.setOutputText("The walls have been engraved with arcane runes, and a pile of iron spikes lies in the west side of the room.");
                deleteLore("57");
                break;
            case "58":
                gameWindow.setOutputText("A well lies in the north-west corner of the room, and spirals of yellow stones cover the floor. There is a health potion in the room.");
                deleteLore("58");
                break;
            case "59":
                deleteLore("59");
                break;
            case "60":
                deleteLore("60");
                break;
            case "61":
                gameWindow.setOutputText("A wooden platform hangs over a deep pit in the north-west corner of the room, and an acrid odor fills the south-west corner of the room.");
                deleteLore("61");
                break;
            case "62":
                deleteLore("62");
                break;
            case "63":
                gameWindow.setOutputText("A tile labyrinth covers the floor, and someone has scrawled 'The gold dragon is not a dragon' on the east wall.");
                deleteLore("63");
                break;
            case "64":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("64");
                break;
            case "65":
                gameWindow.setOutputText("A tile labyrinth covers the floor, and someone has scrawled The Legion of the Veiled Dagger looted this place on the west wall.");
                deleteLore("65");
                break;
            case "66":
                deleteLore("66");
                break;
            case "67":
                gameWindow.setOutputText("Several square holes are cut into the north and west walls, and an altar of evil sits in the south side of the room.");
                deleteLore("67");
                break;
            case "68":
                gameWindow.setOutputText("A mural of legendary monsters covers the ceiling, and someone has scrawled 'The last wards have fallen' on the south wall.");
                deleteLore("68");
                break;
            case "69":
                gameWindow.setOutputText("A stone ramp ascends towards the south wall, and someone has scrawled an arrow pointing down on the west wall.");
                deleteLore("69");
                break;
            case "70":
                gameWindow.setOutputText("Part of the west wall has collapsed into the room, and a pile of torn paper lies in the south-west corner of the room.");
                deleteLore("70");
                break;
            case "71":
                gameWindow.setOutputText("A stone stair ascends towards the east wall, and a rotting odor fills the south-east corner of the room.");
                deleteLore("71");
                break;
            case "72":
                deleteLore("72");
                break;
            case "73":
                gameWindow.setOutputText("Someone has scrawled 'In the Dominion of Tomes, when the Black Ferret is chained and light becomes shadow, the Adventurers of the Silver Wolf shall fall' on the north wall.");
                deleteLore("73");
                break;
            case "74":
                gameWindow.setOutputText("A magical statue in the south-east corner of the room speaks riddles and cryptic prophecies, and a metallic odor fills the south side of the room.");
                deleteLore("74");
                break;
            case "75":
                gameWindow.setOutputText("The south and east walls are covered with scorch marks, and a pile of bent copper coins lies in the center of the room.");
                deleteLore("75");
                break;
            case "76":
                gameWindow.setOutputText("A cube of solid stone stands in the east side of the room, and someone has scrawled  Mind the gap on the south wall. There is a health potion in the room.");
                deleteLore("76");
                break;
            case "77":
                deleteLore("77");
                break;
            case "78":
                gameWindow.setOutputText("Someone has scrawled 'The curse can never be broken' on the west wall, and the walls are covered with scorch marks.");
                deleteLore("78");
                break;
            case "79":
                deleteLore("79");
                break;
            case "80":
                gameWindow.setOutputText("A magical mosaic on the south wall depicts the betrayal of whomever views it, and sporadic knocking fills the room.");
                deleteLore("80");
                break;
            case "81":
                gameWindow.setOutputText("A rope ascends to a catwalk hanging between the north and south walls, and a faded and torn tapestry hangs from the north wall.");
                deleteLore("81");
                break;
            case "82":
                gameWindow.setOutputText("Numerous pillars line the west wall, and an iron sarcophagus sits in the south side of the room.");
                deleteLore("82");
                break;
            case "83":
                deleteLore("83");
                break;
            case "84":
                deleteLore("84");
                break;
            case "85":
                gameWindow.setOutputText("Someone has scrawled 'The Heroes of Bacot killed nine goblins here' on the east wall, and a sundered shield lies in the west side of the room.");
                deleteLore("85");
                break;
            case "86":
                gameWindow.setOutputText("A set of demonic war masks hangs on the south wall, and a crushed helm lies in the south-east corner of the room.");
                deleteLore("86");
                break;
            case "87":
                deleteLore("87");
                break;
            case "88":
                gameWindow.setOutputText("Someone has scrawled 'Don't lose your head' in blood on the south wall, and several pieces of rotting wood are scattered throughout the room.");
                deleteLore("88");
                break;
            case "101":
                deleteLore("101");
                break;
            case "102":
                gameWindow.setOutputText("The floor is covered in square tiles, alternating white and black, and a pile of spoiled meat lies in the south side of the room. There is a health potion in the room.");
                deleteLore("102");
                break;
            case "103":
                gameWindow.setOutputText("An iron chandelier hangs from the ceiling in the center of the room. A pentagram is carved in the floor. There is a key in the room.");
                deleteLore("103");
                break;
            case "104":
                deleteLore("104");
                break;
            case "105":
                gameWindow.setOutputText("The floor is covered in perfect hexagonal tiles, and several pieces of rotten bread are scattered throughout the room. There is a key in the room.");
                deleteLore("105");
                break;
            case "106":
                gameWindow.setOutputText("Someone has scrawled 'The Sapphire Foxes looted this place' on the west wall, and the ceiling is covered with cobwebs.");
                deleteLore("106");
                break;
            case "107":
                deleteLore("107");
                break;
            case "108":
                gameWindow.setOutputText("Someone has scrawled 'Death comes on silent wings' on the north wall, and numerous monstrous skulls lie within niches in the north and south walls.");
                deleteLore("108");
                break;
            case "109":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("109");
                break;
            case "110":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("110");
                break;
            case "111":
                deleteLore("111");
                break;
            case "112":
                deleteLore("112");
                break;
            case "113":
                deleteLore("113");
                break;
            case "114":
                deleteLore("114");
                break;
            case "115":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("115");
                break;
            case "116":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("116");
                break;
            case "117":
                deleteLore("117");
                break;
            case "118":
                gameWindow.setOutputText("The floor is covered in perfect hexagonal tiles, and iron chains hang from the ceiling in the south side of the room.");
                deleteLore("118");
                break;
            case "119":
                deleteLore("119");
                break;
            case "120":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("120");
                break;
            case "121":
                deleteLore("121");
                break;
            case "122":
                gameWindow.setOutputText("A set of demonic war masks hangs on the north wall, and lit candles are scattered across the floor.");
                deleteLore("122");
                break;
            case "123":
                gameWindow.setOutputText("A fountain decorated with five water-breathing dragon heads sits in the north-east corner of the room, and several corroded iron spikes are scattered throughout the room.");
                deleteLore("123");
                break;
            case "124":
                gameWindow.setOutputText("A wooden ladder rests against the north wall, and a pile of barrel staves lies in the west side of the room.");
                deleteLore("124");
                break;
            case "125":
                gameWindow.setOutputText("A ruined siege weapon sits in the west side of the room, and someone has scrawled 'Trespassers will be flayed alive' in blood on the north wall.");
                deleteLore("125");
                break;
            case "126":
                deleteLore("126");
                break;
            case "127":
                deleteLore("127");
                break;
            case "128":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("128");
                break;
            case "129":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("129");
                break;
            case "130":
                deleteLore("130");
                break;
            case "131":
                deleteLore("131");
                break;
            case "132":
                deleteLore("132");
                break;
            case "133":
                deleteLore("133");
                break;
            case "134":
                gameWindow.setOutputText("Spirals of red stones cover the floor, and a pile of bent copper coins lies in the south-east corner of the room.");
                deleteLore("134");
                break;
            case "135":
                gameWindow.setOutputText("The floor is covered in perfect hexagonal tiles, and mysterious levers and mechanisms cover the east and west walls.");
                deleteLore("135");
                break;
            case "136":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("136");
                break;
            case "137":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("137");
                break;
            case "138":
                gameWindow.setOutputText("A wooden platform hangs over a deep pit in the north-east corner of the room, and a sour odor fills the room.");
                deleteLore("138");
                break;
            case "139":
                gameWindow.setOutputText("A simple fireplace sits against the east wall, and the ceiling is covered with pale stalactites. There is a key in the room.");
                deleteLore("139");
                break;
            case "140":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("140");
                break;
            case "141":
                gameWindow.setOutputText("Someone has scrawled 'Upon the fourth day of the Autumn of Blood, when sea becomes sky, the Tempest of Lust shall be freed' on the north wall. There is a key in the room.");
                deleteLore("141");
                break;
            case "142":
                gameWindow.setOutputText("Ghostly wailing can be faintly heard near the east wall, and a shattered sword lies in the north-east corner of the room.");
                deleteLore("142");
                break;
            case "143":
                gameWindow.setOutputText("A wooden platform hangs over a deep pit in the west side of the room, and someone has scrawled a demonic face on the south wall.");
                deleteLore("143");
                break;
            case "144":
                gameWindow.setOutputText("A stack of crates filled with rocks stands against the south wall, and the south and west walls are covered with slime.");
                deleteLore("144");
                break;
            case "145":
                gameWindow.setOutputText("Lit candles are scattered across the floor, and the ceiling is covered with scorch marks.");
                deleteLore("145");
                break;
            case "146":
                gameWindow.setOutputText("The room has a high domed ceiling, and a pile of rotten apples lies in the south-east corner of the room.");
                deleteLore("146");
                break;
            case "147":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("147");
                break;
            case "148":
                gameWindow.setOutputText("A wooden ladder rests against the east wall, and a pile of torches lies in the north-east corner of the room. There is a key in the room.");
                deleteLore("148");
                break;
            case "149":
                gameWindow.setOutputText("A tile labyrinth covers the floor, and a pile of broken arrows lies in the north side of the room. There is a health potion in the room.");
                deleteLore("149");
                break;
            case "150":
                gameWindow.setOutputText("Someone has scrawled 'The axe is cursed' in dwarvish runes on the east wall, and a charred wooden chest lies in the south-east corner of the room. There is a key in the room.");
                deleteLore("150");
                break;
            case "151":
                deleteLore("151");
                break;
            case "152":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("152");
                break;
            case "153":
                deleteLore("153");
                break;
            case "154":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("154");
                break;
            case "155":
                deleteLore("155");
                break;
            case "156":
                deleteLore("156");
                break;
            case "157":
                deleteLore("157");
                break;
            case "158":
                deleteLore("158");
                break;
            case "159":
                deleteLore("159");
                gameWindow.setOutputText("The south and east walls have been engraved with geometric patterns, and a simple fireplace sits against the west wall. There is a key in the room.");
                break;
            case "160":
                gameWindow.setOutputText("An acrid odor fills the west side of the room, and a shattered sword lies in the west side of the room.");
                deleteLore("160");
                break;
            case "161":
                deleteLore("161");
                break;
            case "162":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("162");
                break;
            case "163":
                gameWindow.setOutputText("The room has a high domed ceiling, and a group of draconic faces have been carved into the south wall.");
                deleteLore("163");
                break;
            case "164":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("164");
                break;
            case "165":
                deleteLore("165");
                break;
            case "166":
                deleteLore("166");
                break;
            case "167":
                gameWindow.setOutputText("The north and west walls have been engraved with alien symbols, and several pieces of rotting wood are scattered throughout the room.");
                deleteLore("167");
                break;
            case "168":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("168");
                break;
            case "169":
                gameWindow.setOutputText("A faded and torn tapestry hangs from the south wall, and several pieces of blood-soaked clothing are scattered throughout the room.");
                deleteLore("169");
                break;
            case "170":
                gameWindow.setOutputText("A group of monstrous faces have been carved into the north wall, and a sundered amulet lies in the south side of the room.");
                deleteLore("170");
                break;
            case "171":
                gameWindow.setOutputText("A tile mosaic of ghoulish carnage covers the floor, and numerous humanoid skulls are scattered throughout the room. There is a health potion in the room.");
                deleteLore("171");
                break;
            case "172":
                gameWindow.setOutputText("A group of monstrous faces have been carved into the south wall, and several iron cages are scattered throughout the room.");
                deleteLore("172");
                break;
            case "173":
                deleteLore("173");
                break;
            case "174":
                gameWindow.setOutputText("The walls have been engraved with geometric patterns, and rusting iron spikes line the south wall. There is a key in the room.");
                deleteLore("174");
                break;
            case "175":
                deleteLore("175");
                break;
            case "176":
                deleteLore("176");
                break;
            case "177":
                deleteLore("177");
                break;
            case "201":
                gameWindow.setOutputText("The scent of smoke fills the room, and a charred wooden shield lies in the center of the room. There is a key in the room.");
                deleteLore("201");
                break;
            case "202":
                gameWindow.setOutputText("The south and east walls have been engraved with strange symbols, and several bent copper coins are scattered throughout the room.");
                deleteLore("202");
                break;
            case "203":
                deleteLore("203");
                break;
            case "204":
                gameWindow.setOutputText("A set of demonic war masks hangs on the south wall.");
                deleteLore("204");
                break;
            case "205":
                deleteLore("205");
                break;
            case "206":
                gameWindow.setOutputText("Someone has scrawled 'There is nothing we can do' in goblin runes on the west wall, and mournful weeping can be heard in the north side of the room.");
                deleteLore("206");
                break;
            case "207":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("207");
                break;
            case "208":
                deleteLore("208");
                break;
            case "209":
                gameWindow.setOutputText("Several alcoves are cut into the north wall, and a ruined siege weapon sits in the south-east corner of the room.");
                deleteLore("209");
                break;
            case "210":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("210");
                break;
            case "211":
                gameWindow.setOutputText("Someone has scrawled There is no way out on the north wall, and a pile of spoiled meat lies in the west side of the room. There is a health potion in the room.");
                deleteLore("211");
                break;
            case "212":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("212");
                break;
            case "213":
                gameWindow.setOutputText("A mural of a god of trickery covers the ceiling, and someone has scrawled 'Death is the only exit' on the south wall. There is a health potion in the room.");
                deleteLore("213");
                break;
            case "214":
                gameWindow.setOutputText("A well lies in the east side of the room, and a simple fireplace sits against the north wall. There is a key in the room.");
                deleteLore("214");
                break;
            case "215":
                deleteLore("215");
                break;
            case "216":
                gameWindow.setOutputText("A shallow pit lies in the south-east corner of the room, and the walls are covered with sword cuts.");
                deleteLore("216");
                break;
            case "217":
                deleteLore("217");
                break;
            case "218":
                gameWindow.setOutputText("Clouds of flying insects fill the south-west corner of the room, and a pile of rotting wood lies in the east side of the room.");
                deleteLore("218");
                break;
            case "219":
                deleteLore("219");
                break;
            case "220":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("220");
                break;
            case "221":
                deleteLore("221");
                break;
            case "222":
                deleteLore("222");
                gameWindow.setOutputText("The floor is covered in perfect hexagonal tiles, and a scratching sound can be faintly heard near the west wall.");
                break;
            case "223":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("223");
                break;
            case "224":
                deleteLore("224");
                break;
            case "225":
                gameWindow.setOutputText("A set of demonic war masks hangs on the east wall, and someone has scrawled 'Sigoaro died here' in blood on the east wall. There is a key in the room.");
                deleteLore("225");
                break;
            case "226":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("226");
                break;
            case "227":
                deleteLore("227");
                break;
            case "228":
                gameWindow.setOutputText("A magical shrine of a god of dwarves in the east side of the room heals all wounds of whomever sacrifices a magical item upon it (but only once). There is a key in the room.");
                deleteLore("228");
                break;
            case "229":
                gameWindow.setOutputText("A stone stair ascends towards the north wall, and several pieces of rotten rope are scattered throughout the room. There is a health potion in the room.");
                deleteLore("229");
                break;
            case "230":
                deleteLore("230");
                break;
            case "231":
                gameWindow.setOutputText("A sundered helm lies in the north-west corner of the room.");
                deleteLore("231");
                break;
            case "232":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("232");
                break;
            case "233":
                deleteLore("233");
                break;
            case "234":
                deleteLore("234");
                break;
            case "235":
                deleteLore("235");
                break;
            case "236":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("236");
                break;
            case "237":
                gameWindow.setOutputText("A forge and anvil sit in the south-west corner of the room, and a corroded key lies in the south side of the room.");
                deleteLore("237");
                break;
            case "238":
                gameWindow.setOutputText("A wooden platform hangs over a deep pit in the north-east corner of the room, and someone has scrawled 'Twist the cog to reset the trap' on the west wall.");
                deleteLore("238");
                break;
            case "239":
                deleteLore("239");
                break;
            case "240":
                gameWindow.setOutputText("Someone has scrawled 'We've run out of swords' on the west wall, and several shattered weapons are scattered throughout the room.");
                deleteLore("240");
                break;
            case "241":
                gameWindow.setOutputText("Someone has scrawled a basic map of the dungeon on the south wall, and a rusted gauntlet lies in the south-west corner of the room. There is a key in the room.");
                deleteLore("241");
                break;
            case "242":
                gameWindow.setOutputText("Someone has scrawled 'Ran out of arrows' in blood on the east wall, and a rusted gauntlet lies in the south-east corner of the room. There is a key in the room.");
                deleteLore("242");
                break;
            case "243":
                gameWindow.setOutputText("A set of demonic war masks hangs on the east wall, and the sound of drums can be heard in the west side of the room. There is a key in the room.");
                deleteLore("243");
                break;
            case "244":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("244");
                break;
            case "245":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("245");
                break;
            case "246":
                gameWindow.setOutputText("Someone has scrawled Who took my dwarf skull in goblin runes on the north wall, and a hole has been blasted into the east wall. There is a key in the room.");
                deleteLore("246");
                break;
            case "247":
                gameWindow.setOutputText("Someone has scrawled a large X on the south wall, and a hissing noise can be heard in the north-west corner of the room. There is a health potion.");
                deleteLore("247");
                break;
            case "248":
                deleteLore("248");
                break;
            case "249":
                gameWindow.setOutputText("A ladder ascends to a balcony hanging from the south wall, and a shallow pool of water lies in the north-west corner of the room.");
                deleteLore("249");
                break;
            case "250":
                deleteLore("250");
                break;
            case "251":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("251");
                break;
            case "252":
                deleteLore("252");
                break;
            case "253":
                deleteLore("253");
                break;
            case "254":
                gameWindow.setOutputText("Spirals of green stones cover the floor, and the south and west walls are covered with veins of metal.");
                deleteLore("254");
                break;
            case "255":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("255");
                break;
            case "256":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("256");
                break;
            case "257":
                deleteLore("257");
                break;
            case "258":
                deleteLore("258");
                break;
            case "259":
                gameWindow.setOutputText("Someone has scrawled The Cohort of Zazer looted this place on the north wall, and an iron chain hangs from the ceiling in the north-west corner of the room. There is a key in the room.");
                deleteLore("259");
                break;
            case "260":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("260");
                break;
            case "261":
                deleteLore("261");
                break;
            case "262":
                deleteLore("262");
                break;
            case "263":
                gameWindow.setOutputText("Various torture devices are scattered throughout the room, and someone has scrawled 'Willes Grysor fell here, his luck ran out before his arrows' on the east wall.");
                deleteLore("263");
                break;
            case "264":
                gameWindow.setOutputText("Someone has scrawled 'Don't lose your head' on the south wall, and several wax blobs are scattered throughout the room.");
                deleteLore("264");
                break;
            case "265":
                deleteLore("265");
                break;
            case "266":
                deleteLore("266");
                break;
            case "267":
                deleteLore("267");
                break;
            case "268":
                deleteLore("268");
                break;
            case "269":
                gameWindow.setOutputText("Skeletons hang from chains and manacles against the east and west walls, and rusting iron spikes line the north wall.");
                deleteLore("269");
                break;
            case "270":
                deleteLore("270");
                break;
            case "271":
                gameWindow.setOutputText("A cube of solid stone stands in the south-east corner of the room, and someone has scrawled 'Beware the basilisk' on the south wall.");
                deleteLore("271");
                break;
            case "272":
                deleteLore("272");
                break;
            case "273":
                gameWindow.setOutputText("A chute falls into the room from above, and someone has scrawled 'left, straight, door, right, straight' on the east wall. There is a health potion in the room.");
                deleteLore("273");
                break;
            case "274":
                gameWindow.setOutputText("A stone sarcophagus sits in the north-west corner of the room, and a pile of rotten rope lies in the south- east corner of the room.");
                deleteLore("274");
                break;
            case "275":
                gameWindow.setOutputText("A wooden platform hangs over a deep pit in the center of the room, and a tapestry of a legendary battle hangs from the west wall.");
                deleteLore("275");
                break;
            case "276":
                gameWindow.setOutputText("A tile mosaic of a legendary battle covers the floor, and someone has scrawled a monstrous face on the west wall.");
                deleteLore("276");
                break;
            case "277":
                gameWindow.setOutputText("A faded and torn tapestry hangs from the east wall, and burning torches in iron sconces line the north and east walls.");
                deleteLore("277");
                break;
            case "278":
                deleteLore("278");
                break;
            case "279":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("279");
                break;
            case "280":
                deleteLore("280");
                break;
            case "281":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("281");
                break;
            case "282":
                gameWindow.setOutputText("The floor is covered in square tiles, alternating white and black, and someone has scrawled a drawing of a castle on the east wall.");
                deleteLore("282");
                break;
            case "283":
                gameWindow.setOutputText("Spirals of blue stones cover the floor, and a ruined siege weapon sits in the south side of the room.");
                deleteLore("283");
                break;
            case "284":
                deleteLore("284");
                break;
            case "301":
                deleteLore("301");
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (counter < finalStage.length) {
                            gameWindow.setOutputText(finalStage[counter]);
                            counter++;
                        } else {
                            timer.cancel();
                        }
                    }
                }, 0, 5000);
                break;
            case "302":
                gameWindow.setOutputText("A tile mosaic of a god of illusions covers the floor, and a broken spear lies in the west side of the room.");
                deleteLore("302");
                break;
            case "303":
                deleteLore("303");
                break;
            case "304":
                gameWindow.setOutputText("A foul odor fills the north side of the room, and a sundered helm lies in the east side of the room.");
                deleteLore("304");
                break;
            case "305":
                gameWindow.setOutputText("Several alcoves are cut into the south and east walls, and a pile of rotten leather lies in the north- east corner of the room.");
                deleteLore("305");
                break;
            case "306":
                deleteLore("306");
                break;
            case "307":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("307");
                break;
            case "308":
                gameWindow.setOutputText("A mural of ghoulish carnage covers the ceiling, and the south and west walls are covered with veins of metal.");
                deleteLore("308");
                break;
            case "309":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("309");
                break;
            case "310":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("310");
                break;
            case "311":
                gameWindow.setOutputText("Someone has scrawled 'The walls listen' on the south wall, and a whistling noise can be heard in the center of the room. There is a key in the room.");
                deleteLore("311");
                break;
            case "312":
                gameWindow.setOutputText("A tapestry of arcane patterns hangs from the south wall, and burning torches in iron sconces line the south and west walls.");
                deleteLore("312");
                break;
            case "313":
                deleteLore("313");
                break;
            case "314":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("314");
                break;
            case "315":
                gameWindow.setOutputText("Someone has scrawled 'You cannot kill it with wizardry' in draconic script on the west wall, and several adventurer corpses are scattered throughout the room. There is a health potion in the room.");
                deleteLore("315");
                break;
            case "316":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("316");
                break;
            case "317":
                deleteLore("317");
                break;
            case "318":
                gameWindow.setOutputText("A mural of a goddess of illusions covers the ceiling, and the floor is covered with mud.");
                deleteLore("318");
                break;
            case "319":
                deleteLore("319");
                break;
            case "320":
                gameWindow.setOutputText("The floor is covered with dead insects, and a pile of rotten apples lies in the north-east corner of the room. There is a key in the room.");
                deleteLore("320");
                break;
            case "321":
                deleteLore("321");
                break;
            case "322":
                deleteLore("322");
                break;
            case "323":
                gameWindow.setOutputText("Several square holes are cut into the east and west walls, and someone has scrawled 'Good spot for trap' in goblin runes on the west wall. There is a key in the room.");
                deleteLore("323");
                break;
            case "324":
                gameWindow.setOutputText("A set of demonic war masks hangs on the west wall, and someone has scrawled 'The gold dragon is not a dragon' in blood on the north wall.");
                deleteLore("324");
                break;
            case "325":
                gameWindow.setOutputText("There is a key in the room.");
                deleteLore("325");
                break;
            case "326":
                deleteLore("326");
                break;
            case "327":
                gameWindow.setOutputText("Several alcoves are cut into the west wall, and a putrid odor fills the room. There is a key in the room.");
                deleteLore("327");
                break;
            case "328":
                gameWindow.setOutputText("Several square holes are cut into the ceiling and floor, and chanting can be faintly heard near the north wall. There is a key in the room.");
                deleteLore("328");
                break;
            case "329":
                gameWindow.setOutputText("A tile mosaic of a legendary battle covers the floor, and a putrid odor fills the center of the room.");
                deleteLore("329");
                break;
            case "330":
                deleteLore("330");
                break;
            case "331":
                gameWindow.setOutputText("A carved stone statue stands in the south-west corner of the room, and someone has scrawled 'You cannot kill it with swords' in draconic script on the east wall.");
                deleteLore("331");
                break;
            case "332":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("332");
                break;
            case "333":
                gameWindow.setOutputText("Someone has scrawled 'The gold is cursed' on the north wall, and a ruined gauntlet lies in the west side of the room. There is a health potion in the room.");
                deleteLore("333");
                break;
            case "334":
                gameWindow.setOutputText("There is a health potion in the room.");
                deleteLore("334");
                break;

        }

    }

    public static void deleteLore(String room){
        String[][] currentMap = gameMap.getCurrentMap();
        for (int i  = 1; i < currentMap.length; i++) {
            for (int j = 1; j < currentMap[i].length; j++) {
                if (currentMap[i][j].equals(room)) {
                    currentMap[i][j] = "";
                }
            }
        }
    }

    public static void action(String action) {
        Action(action);
    }
}