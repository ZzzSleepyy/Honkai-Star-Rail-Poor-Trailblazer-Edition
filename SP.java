public class SP {
    //--Attribute--
    private int currentSP;
    private final int maxSP = 5;

    //--Constructor--
    public SP(int sp){
        this.currentSP = sp;
    }

    //--Functions--

    //Used for skills, consumes 1sp for 1 skill
    //Checks if currentSP is more than 0
    public boolean useSP(){
        if(currentSP > 0){
            currentSP--;
            return true;
        } else {
            return false;
        }
    }

    //Used for basic attack, gains 1sp for 1 basic attack
    //Stop gaining sp when it hits maxSP
    public void gainSP(){
        if(currentSP < maxSP){
            currentSP++;
        }
    }

    //--Getting Value--
    public int getSP(){
        return currentSP;
    }
}
