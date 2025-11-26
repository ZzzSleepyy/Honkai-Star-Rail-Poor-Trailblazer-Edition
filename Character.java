public abstract class Character{
    //Attributes
    private final String name, element;
    private int maxhp, hp, atk, spd;
    private int ultPoints = 0;
    private boolean shielded = false;
    private boolean alive = true;

    //Constructor
    public Character(String name, String element, int hp, int atk, int spd){
        this.name = name;
        this.element = element;
        this.maxhp = hp;
        this.hp = hp;
        this.atk = atk;
        this.spd = spd;
    }

    //Player Actions
    public void basicAttack(Enemy target, SP sp){
        int dmg = atk;
        System.out.println(name + " used Basic Attack");
        target.takeDamage(dmg, element);
        sp.gainSP();
        gainUltPoint();
    };

    public abstract void skill(Enemy target, SP sp, Character[] team, Enemy[] enemies);
    public abstract void ultimate(Enemy target, SP sp, Character[] team, Enemy[] enemies);
    
    //Taking Damage
    public void takeDamage(int dmg){
        //If Character is Shielded
        if(shielded){
            System.out.println(name + "'s shield blocked the attack!\n");
            shielded = false;
            return;
        }
        
        //Damage Calculation
        hp -= dmg;
        System.out.println(name + " took " + dmg + " damage!\n");
        

        //If Character is knocked out
        if(hp <= 0){
            hp = 0;
            alive = false;
            System.out.println(name + " is knocked out!\n");
        }

        gainUltPoint(); //Gains ult point when taking damage
    }

    //Healing
    public void heal(int amount){
        hp = Math.min(maxhp, hp + amount); //Will take the minimum value
        System.out.println(name + " was healed " + amount + "HP!\n");
    }

    //Ultimate Mechanics
    public void gainUltPoint(){
        ultPoints = Math.min(10, ultPoints + 1); //Same as healing, max value = 10
    }
    
    public boolean isUltReady(){
        return ultPoints == 10; //Checks if ultPoints is equals to 10
    }

    public void resetUltPoint(){
        ultPoints = 0; //Resets the current ultPoints to 0
    }

    //Status Checks

    public abstract void skillInfo();

    public boolean isAlive(){
        return alive; //Checks if current character is alive
    }

    public boolean isShielded(){
        return shielded; //Checks if current character is shielded
    }

    //Prints Status
    public void showStatus() {
    System.out.print(name + " [" + element + "] HP: "
        + hp + "/" + maxhp
        + " | Ultimate: " + ultPoints + "/10"
        + " | Speed: " + spd);

    if (shielded) {
        System.out.print(" | SHIELDED");
    }

    System.out.println();
}

    //Getters
    public String getName(){ 
        return name;
    }
    public String getElement(){ 
        return element; 
    }
    public int getMaxHP(){ 
        return maxhp; 
    }
    public int getHP(){ 
        return hp; 
    }
    public int getAttack(){ 
        return atk; 
    }
    public int getSpeed(){ 
        return spd; 
    }
    public int getUltPoints(){ 
        return ultPoints; 
    }

    //Setters
    public void setShielded(){
        this.shielded = true;
    }

}