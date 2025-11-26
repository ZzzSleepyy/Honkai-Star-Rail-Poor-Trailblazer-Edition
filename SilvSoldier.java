import java.util.Random;

public class SilvSoldier extends Enemy{
    //Constructor
    public SilvSoldier(){
        super("Silvermane: Soldier", "Physical", 120, 20, 80, 3);
    }

    //Actions
    @Override
    public void skill (Character[] team, Enemy[] enemies){
        Random rand = new Random();
        Character target = team[rand.nextInt(team.length)];
        if (!target.isAlive()){
            return;
        }

        int dmg = getAttack() + 10;
        System.out.printf("%s uses Heavy Strike on %s!%n", getName(), target.getName());
        target.takeDamage(dmg);
        setSkillCooldown(2);
    }
}