import java.util.Random;

public class LordlyTrashcan extends Enemy {

    private int turnCount = 0;   // tracks how many times it acted

    //--Constructor--
    public LordlyTrashcan() {
        super("Lordly Trashcan", "Physical", 600, 80, 40, 7);
 
    }

    @Override
    public void skill(Character[] team, Enemy[] enemies) {
        Random rand = new Random();
        Character target = team[rand.nextInt(team.length)];

        if (!target.isAlive()) return;

        int dmg = getAttack() + 20;

        System.out.printf("%s hurls a pile of trash at %s!%n", getName(), target.getName());

        target.takeDamage(dmg);
        setSkillCooldown(5);
    }
}
