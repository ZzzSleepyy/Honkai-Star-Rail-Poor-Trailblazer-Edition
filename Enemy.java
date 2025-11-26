public abstract class Enemy {
    // Attributes
    private final String name, weakness;
    private int maxhp, hp, atk, spd, maxtoughness, toughness;
    private boolean alive = true;
    private boolean broken = false;
    private boolean shielded = false;
    private int skillCooldown = 0;

    //Constructors
    public Enemy(String name, String weakness, int hp, int atk, int spd, int toughness) {
        this.name = name;
        this.weakness = weakness;
        this.maxhp = hp;
        this.hp = hp;
        this.atk = atk;
        this.spd = spd;
        this.maxtoughness = toughness;
        this.toughness = toughness;
    }

    //Enemy Actions
    public void basicAttack(Character target) {
        int dmg = atk;
        System.out.println(name + " attacks " + target.getName() + "!");
        target.takeDamage(dmg);
    }

    public abstract void skill(Character[] team, Enemy[] enemies);

    //Taking Damage
    public void takeDamage(int dmg, String element) {

        if(shielded){
            System.out.println(name + "'s shield blocked the attack!\n");
            shielded = false;
            return;
        }

        //Reduce toughness only if element matches and enemy not broken
        if (element.equalsIgnoreCase(weakness) && !broken) {
            toughness--;

            if (toughness <= 0) {
                toughness = 0;
                broken = true;
                System.out.println(name + " is BROKEN! Takes increased damage!\n");
                dmg = (int) (dmg * 1.5);
            }
        }

        //Apply HP damage
        hp -= dmg;
        System.out.println(name + " took " + dmg + " damage!\n");

        if (hp <= 0) {
            hp = 0;
            alive = false;
            System.out.println(name + " has been defeated!\n");
        }
    }

    //Recoveer Toughness
    public void recoverToughness(){
        broken = false;
        toughness = maxtoughness;
        System.out.println(name + " recovered toughness!");
    }

    //Cooldown Management
    public boolean canUseSkill(){
        return skillCooldown == 0;
    }

    public void setSkillCooldown(int turns){
        this.skillCooldown = turns;
    }

    public void reduceCooldown(){
        if (skillCooldown > 0) skillCooldown--;
    }

    // Status Checks
    public boolean isAlive(){ 
        return alive;
    }
    public boolean isBroken(){ 
        return broken;
    }
    public boolean isShielded(){
        return shielded;
    }

    public void showStatus(){
        System.out.println(name + " | HP: " + hp + "/" + maxhp
                + " | Weakness: " + weakness
                + " | Toughness: " + toughness + "/" + maxtoughness);

        if (shielded){
        System.out.print(" | SHIELDED");
        }
    }

    // Getters
    public String getName(){ 
        return name; 
    }
    public String getWeakness(){ 
        return weakness; 
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
    public int getToughness(){ 
        return toughness; 
    }

    //Setters
    public void setShielded(){
        this.shielded = true;
    }

}
