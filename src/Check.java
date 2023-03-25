public class Check {
    public boolean areValuesOK(int diffLvl, int mineN, int timer, int supermine) { //Για το InvalidValueException
        boolean easyBroken = (diffLvl == 1 && (mineN < 9 || mineN > 11 || supermine != 0 || timer < 120 || timer > 180));
        boolean hardBroken = (diffLvl == 2 && (mineN < 35 || mineN > 45 || timer < 240 || timer > 360));
        if ((diffLvl != 1 && diffLvl != 2) || easyBroken || hardBroken) {
            return false;
        }
        return true;
    }
}
