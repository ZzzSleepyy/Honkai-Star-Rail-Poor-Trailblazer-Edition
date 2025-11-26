import java.util.Random;

public class SilvLieutenant extends Enemy {
    //Constructor
    public SilvLieutenant() {
        super("Silvermane: Lieutenant", "Physical", 300, 50, 50, 5);
    }

    @Override
    public void skill(Character[] team, Enemy[] enemies) {
        Random rand = new Random();

        //Count how many enemies are alive
        int countAlive = 0;
        for (Enemy e : enemies) {
            if (e.isAlive()) countAlive++;
        }

        //If nobody alive to shield, skip
        if (countAlive == 0) {
            System.out.println(getName() + " found no allies to protect.");
            return;
        }

        //Pick a random alive ally
        Enemy ally;
        while (true) {
            ally = enemies[rand.nextInt(enemies.length)];
            if (ally.isAlive()) break;
        }

        //Apply shield
        ally.setShielded();
        System.out.println(getName() + " shields " + ally.getName() + "!");

        setSkillCooldown(2);
    }
}
