import java.util.*;

public class Game {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("==== Honkai Star Rail: Poor Trailblazer Edition ====");
            System.out.println("[1] Play");
            System.out.println("[2] Exit");
            System.out.print("Choose: ");
            String input = sc.nextLine();
            System.out.println();

            if (input.equals("1")) {
                startGame();
            } else if (input.equals("2")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice.\n");
            }
        }
    }

    private static void startGame() {

    //Player Team
    Character[] team = {
        new Trailblazer(),
        new DanHeng(),
        new March7th(),
        new Natasha()
    };

    SP sp = new SP(3);


    Enemy[][] waves = {

        // WAVE 1
        {
            new SilvSoldier(),
            new SilvSoldier()
        },

        // WAVE 2
        {
            new SilvSoldier(),
            new SilvCannoneer(),
            new SilvSoldier()

        },

        // WAVE 3
        {
            new SilvCannoneer(),
            new SilvLieutenant(),
            new SilvCannoneer(),
        }
    };
    Enemy finalBoss[] =  {new LordlyTrashcan()};

    //Run Normal Waves
    for (int wave = 0; wave < waves.length; wave++) {

        System.out.println("\n=== WAVE " + (wave + 1) + " ===\n");

        runBattle(team, waves[wave], sp);

        if (allCharactersDead(team)) {
            System.out.println("\nYour team has fallen...");
            return;
        }

        System.out.println("\nWave Cleared!\n");
    }


    //Run Boss Wave
    System.out.println("\n=== FINAL BOSS ===\n");

    runBattle(team, finalBoss, sp);

    if (allCharactersDead(team)) {
        System.out.println("\nYour team has fallen...");
        return;
    }

    System.out.println("\nYou beat all waves including the final boss!");
}

    private static void runBattle(Character[] team, Enemy[] enemies, SP sp) {
        Random rand = new Random();

        List<Object> turnOrder = new ArrayList<>();
        turnOrder.addAll(Arrays.asList(team));
        turnOrder.addAll(Arrays.asList(enemies));

        //Arranging Turn Order
        turnOrder.sort((a, b) -> {
            int sa = (a instanceof Character) ? ((Character) a).getSpeed() :
                     ((Enemy) a).getSpeed();
            int sb = (b instanceof Character) ? ((Character) b).getSpeed() :
                     ((Enemy) b).getSpeed();
            return sb - sa;
        });

        int index = 0;

        while (true) {

            //Skip dead units
            while (true) {
                Object u = turnOrder.get(index);
                if (u instanceof Character c && c.isAlive()) break;
                if (u instanceof Enemy e && e.isAlive()) break;

                index = (index + 1) % turnOrder.size();
            }

            showStatus(team, enemies, sp);
            showTurnPreview(turnOrder, index);

            Object unit = turnOrder.get(index);

            //Player Turn
            if (unit instanceof Character c) {
                playerTurn(c, team, enemies, sp);
            }

            //Enemy Turn
            else if (unit instanceof Enemy e) {

                if (e.isBroken()) {
                    System.out.println(e.getName() + " is broken and skips this turn!");
                    e.recoverToughness();
                    System.out.println();
                } else {

                    int attempts = 0;
                    while (attempts < 20) {
                        int tg = rand.nextInt(team.length);
                        if (team[tg].isAlive()) {
                            if(e.canUseSkill()){
                                e.skill(team, enemies);
                                break;
                            } else {
                                e.basicAttack(team[tg]);
                                e.reduceCooldown();
                                break;
                            }
                        }
                        attempts++;
                    }
                    System.out.println();
                }

                if (allCharactersDead(team)) return;
            }

            if (allEnemiesDead(enemies)) return;

            index = (index + 1) % turnOrder.size();
        }
    }

    private static void playerTurn(Character c, Character[] team, Enemy[] enemies, SP sp) {

    while (true) {
        System.out.println(c.getName() + "'s turn!");
        System.out.println("[1] Basic Attack");
        System.out.println("[2] Skill  (SP cost: 1)");
        System.out.println("[3] Ultimate  " + (c.isUltReady() ? "(READY)" : "(Not Ready)"));
        System.out.println("[4] Skill Info");
        System.out.print("Choose: ");

        String choice = sc.nextLine();
        System.out.println();

        //Basic Attack
        if (choice.equals("1")) {
            Enemy target = pickEnemy(enemies);
            if (target != null) c.basicAttack(target, sp);
            break;
        }

        //Skill
        else if (choice.equals("2")) {
            if (sp.getSP() <= 0) {
                System.out.println("Not enough SP! Choose another action.\n");
                continue;
            }

            //Skills doesn't need target
            if (c instanceof March7th || c instanceof Natasha) {
                c.skill(null, sp, team, enemies);
                break;
            }

            //Skills need target
            Enemy target = pickEnemy(enemies);
            if (target != null) c.skill(target, sp, team, enemies);
            break;
        }

        //Ultimate
        else if (choice.equals("3")) {
            if (!c.isUltReady()) {
                System.out.println("Ultimate not ready! Choose another action.\n");
                continue;
            }

            //Ultimate doesn't need target
            if (c instanceof March7th || c instanceof Natasha) {
                c.ultimate(null, sp, team, enemies);
                break;
            }

            //Ultimate needs target
            Enemy target = pickEnemy(enemies);
            if (target != null) c.ultimate(target, sp, team, enemies);
            break;
        }

        else if (choice.equals("4")) {
            c.skillInfo();
            System.out.println();
            continue; //Displays menu again
}

        else {
            System.out.println("Invalid choice.\n");
        }
    }
}


    private static Enemy pickEnemy(Enemy[] enemies) {
        while (true) {
            System.out.println("Choose enemy:");
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].isAlive()) {
                    System.out.printf("[%d] %s (HP: %d)\n",
                            i + 1, enemies[i].getName(), enemies[i].getHP());
                }
            }
            System.out.print("Enter: ");
            String in = sc.nextLine();
            System.out.println();

            try {
                int idx = Integer.parseInt(in) - 1;
                if (idx >= 0 && idx < enemies.length && enemies[idx].isAlive()) {
                    return enemies[idx];
                }
            } catch (Exception ignored) {}

            System.out.println("Invalid choice.\n");
        }
    }

    //Shows status for all
    private static void showStatus(Character[] team, Enemy[] enemies, SP sp) {
        System.out.println("--- ENEMIES ---");
        for (Enemy e : enemies) {
            if (e.isAlive()) e.showStatus();
        }
        System.out.println();

        System.out.println("--- ALLIES ---");
        for (Character c : team) {
            if (c.isAlive()) c.showStatus();
        }

        System.out.println("\nSP: " + sp.getSP());
        System.out.println();
    }

    //Display turn order
    private static void showTurnPreview(List<Object> order, int index) {
        System.out.print("Next to act: ");

        int shown = 0;
        int i = index;

        while (shown < 3) {
            Object u = order.get(i);

            if (u instanceof Character c && c.isAlive()) {
                System.out.print(c.getName());
                shown++;
            } else if (u instanceof Enemy e && e.isAlive()) {
                System.out.print(e.getName());
                shown++;
            }

            if (shown < 3) System.out.print(" -> ");

            i = (i + 1) % order.size();
        }

        System.out.println("\n");
    }

    //Check if every enemies is dead
    private static boolean allEnemiesDead(Enemy[] enemies) {
        for (Enemy e : enemies)
            if (e.isAlive()) return false;
        return true;
    }

    //Checks if every ally is dead
    private static boolean allCharactersDead(Character[] team) {
        for (Character c : team)
            if (c.isAlive()) return false;
        return true;
    }
}
