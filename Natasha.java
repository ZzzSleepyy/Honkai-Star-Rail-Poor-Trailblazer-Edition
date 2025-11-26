import java.util.Scanner;

public class Natasha extends Character{
    //Constructor
    public Natasha(){
        super("Natasha", "Physical", 140, 15, 75);
    }

    //Actions
    @Override
    public void skill(Enemy target, SP sp, Character[] team, Enemy[] enemies){
        if (!sp.useSP()){
            System.out.println("Not enough SP!");
            return;
        }
        // Display allies
        Scanner scLocal = new Scanner(System.in);
        System.out.println("Choose an ally to heal:");
        for (int i=0; i<team.length; i++) {
            Character c = team[i];
            System.out.printf("[%d] %s (HP:%d/%d)%n",
                                i+1, c.getName(), c.getHP(), c.getMaxHP());
        }
        //Input ally to heal
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

        //Healing
        Character ally = team[choice];
        ally.heal(35);
        gainUltPoint();
    };

    //Show skill info
    @Override
    public void ultimate(Enemy target, SP sp, Character[] team, Enemy[] enemies){
        for (Character c : team)
            if (c.isAlive()){
                c.heal(30);
            }
        System.out.println(getName() + " restored everyone’s HP massively!");
        resetUltPoint();
    };

    @Override
    public void skillInfo(){
        System.out.println("[1] Behind the Kindness: Deals very low physical damage to a single enemy.");
        System.out.println("[2] Love, Heal, and Choose: Restores hp to a single ally.");
        System.out.println("[3] Gift of Rebirth: Heals all allies hp.");
    }
}
