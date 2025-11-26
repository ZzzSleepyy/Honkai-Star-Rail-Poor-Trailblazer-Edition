import java.util.Scanner;

public class March7th extends Character{
    //Constructor
    public March7th(){
        super("March 7th", "Ice", 130, 20, 90);
    }

    //Actions
    @Override
    public void skill(Enemy target, SP sp, Character[] team, Enemy[] enemies){
        if (!sp.useSP()){
            System.out.println("Not enough SP!");
            return;
        }

        //Display allies
        Scanner scLocal = new Scanner(System.in);
        System.out.println("Choose an ally to shield:");
        for (int i=0; i<team.length; i++) {
            Character c = team[i];
            System.out.printf("[%d] %s (HP:%d/%d)%n",
                                i+1, c.getName(), c.getHP(), c.getMaxHP());
        }

        //Input ally to shield
        int choice;
        while (true){
            System.out.print("Enter ally number: ");
            try {
                choice = Integer.parseInt(scLocal.nextLine()) - 1;
                if (choice >= 0 && choice < team.length && team[choice].isAlive()){
                    break;
                }
            } catch (NumberFormatException e){}
            System.out.println("Invalid input. Please try again.");
        }

        //Setting shield
        Character ally = team[choice];
        ally.setShielded();
        System.out.println(getName() + " shielded " + ally.getName() + " from the next hit!");
        gainUltPoint();
    };

    @Override
    public void ultimate(Enemy target, SP sp, Character[] team, Enemy[] enemies){
        int dmg = getAttack() + 25;
        System.out.println(getName() + " fired Glacial Cascade!");
        for (Enemy e : enemies){
            if (e.isAlive()){
                e.takeDamage(dmg, getElement());
            }
        }
        resetUltPoint();
    };

    //Show skill info
    @Override
    public void skillInfo(){
        System.out.println("[1] Frigid Cold Arrow: Deals low ice damage to a single enemy.");
        System.out.println("[2] The Power of Cuteness: Provides a shield to an ally that can absorb incoming damage.");
        System.out.println("[3] Glacial Cascade: Deals aoe ice damage to all enemies.");
    }
}
