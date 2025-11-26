import java.util.Random;

public class SilvCannoneer extends Enemy {
    //Constructor
    public SilvCannoneer() {
        super("Silvermane: Cannoneer", "Wind", 120, 25, 70, 3);
    }

    //AOE Skill
    @Override
    public void skill(Character[] team, Enemy[] enemies) {

        Random rand = new Random();
        int index = rand.nextInt(team.length);
        Character target = team[index];

        // If selected target is dead, cancel skill
        if (!target.isAlive()) {
            return;
        }

        int dmg = getAttack() + 15;

        System.out.printf("%s hurls an Explosive Charge at %s!\n", getName(), target.getName());

        //Main Target
        target.takeDamage(dmg);

        // Put skill on cooldown
        setSkillCooldown(2);

        //AOE Damage = Half
        int aoeDmg = dmg / 2;

        // --- Hit left adjacent Character ---
        if (index - 1 >= 0 && team[index - 1].isAlive()) {
            System.out.println("Explosion hits " + team[index - 1].getName() + "!");
            team[index - 1].takeDamage(aoeDmg);
        }

        // --- Hit right adjacent Character ---
        if (index + 1 < team.length && team[index + 1].isAlive()) {
            System.out.println("Explosion hits " + team[index + 1].getName() + "!");
            team[index + 1].takeDamage(aoeDmg);
        }
    }
}
