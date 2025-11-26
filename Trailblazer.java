import java.util.Scanner;

public class Trailblazer extends Character{
    //Constructor
    public Trailblazer(){
        super("Trailblazer", "Physical", 150, 25, 80);
    }

    //Actions
    @Override
    public void skill(Enemy target, SP sp, Character[] team, Enemy[] enemies){
        if (!sp.useSP()) {
            System.out.println("Not enough SP!");
            return;
        }

        int dmg = getAttack() + 15;
        
        //Finding the index of the main target
        int index = -1;
        for (int i=0; i<enemies.length; i++){
            if (enemies[i] == target){
                index = i;
                break;
            }
        }

        System.out.println(getName() + " used RIP Home Run!");
        
        //Dealing damage to main target
        target.takeDamage(dmg, getElement());

        //Aoe dmg is weaker
        int aoeDmg = dmg/2;

        //For left enemy, Checks if valid index and isAlive
        if (index - 1 >= 0 && enemies[index - 1].isAlive()){
            System.out.println("AOE hit " + enemies[index - 1].getName());
            enemies[index - 1].takeDamage(aoeDmg, getElement());
        }
        
        //For right enemy, Checks if valid index and isAlive
        if (index + 1 < enemies.length && enemies[index + 1].isAlive()){
            System.out.println("AOE hit " + enemies[index + 1].getName());
            enemies[index + 1].takeDamage(aoeDmg, getElement());
        }

        gainUltPoint();
    };

    @Override
    public void ultimate(Enemy target, SP sp, Character[] team, Enemy[] enemies){
        
        Scanner sc = new Scanner(System.in);

        //2 forms of Ultimate
        System.out.println("\nChoose Ultimate Mode:");
        System.out.println("[1] Blowout: Farewell Hit (Single Target)");
        System.out.println("[2] Blowout: RIP Home Run (AOE)");
        System.out.print("Choose: ");

        int choice;

        //Checks input and if input is invalid and print the following statement | Prevents the program from crashing
        while(true){
            try{
                choice = Integer.parseInt(sc.nextLine());
                if (choice > 0 && choice < 3){
                    break;
                }
            } catch (NumberFormatException e){}
            System.out.println("Invalid input. Please try again.");
        }
        
        if (choice == 1){ //Single Target
            int dmg = getAttack() + 40;
            System.out.println(getName() + " uses Blowout: Farewell Hit!");
            target.takeDamage(dmg, getElement());
        } else { //AOE
            int dmg = getAttack() + 20;
            System.out.println(getName() + " uses Blowout: RIP Home Run!");
            for (Enemy e : enemies){
                if (e.isAlive()){
                    e.takeDamage(dmg, getElement());
                }
            }
        }
        resetUltPoint();
    };


    //Show sKill info
    @Override
    public void skillInfo(){
        System.out.println("[1] Farewell Hit: Deals low physical damage to a single enemy.");
        System.out.println("[2] RIP Home Run: Deals physical aoe damage to adjacent enemies.");
        System.out.println("[3] Star Dustace: Enhances the damage of the next basic attack or skill.");
    }
}