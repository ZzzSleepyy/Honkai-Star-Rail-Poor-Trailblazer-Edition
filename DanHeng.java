public class DanHeng extends Character{
    //Constructor
    public DanHeng(){
        super("Dan Heng", "Wind", 110, 35, 100);
    }

    //Actions
    @Override
    public void skill(Enemy target, SP sp, Character[] team, Enemy[] enemies){
        if (!sp.useSP()) {
            System.out.println("Not enough SP!");
            return;
        }
        int dmg = getAttack() + 15;
        System.out.println(getName() + " used Cloudlancer Art: Torrent!");
        target.takeDamage(dmg, getElement());
        gainUltPoint();
    };

    @Override
    public void ultimate(Enemy target, SP sp, Character[] team, Enemy[] enemies){
        int dmg = getAttack() + 40;
        System.out.println(getName() + " unleashed Ethereal Dream!");
        target.takeDamage(dmg, getElement());
        resetUltPoint();
    };

    //Show skill info
    @Override
    public void skillInfo(){
        System.out.println("[1] Cloud Lancer Art: North Wind: Deals low wind damage to a single enemy.");
        System.out.println("[2] Cloud Lancer Art: Torrent: Deals mid wind damage to a single enemy.");
        System.out.println("[3] Ethereal Dream: Deals high wind damage to a single enemy.");
    }
}