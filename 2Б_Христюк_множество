public class Helper {
    public static void main(String[] args) {
        SetNum set5 = new SetNum("N",5, 88);
        System.out.println(set5);
        if (set5.isElemInSet(91)) {
            System.out.println(91 + " принадлежит множеству " + set5.getName());
        }
        SetNum set0 = new SetNum();
        System.out.println(set0);
    }
}
 
class SetNum {
    private String name;
    private int powerSet;
    private int [] set;
 
    SetNum(String name, int powerSet, int beginElem) {
        this.name = name.toUpperCase();
        this.powerSet = powerSet;
        this.set = new int[powerSet];
        for (int i = 0; i < powerSet; i++) {
            set[i] = beginElem++;
        }
    }
 
    SetNum() {
        this("O",0,0);
    }
 
    boolean isElemInSet(int elem){
        boolean ret = false;
        for (int tmp : set) {
            if (tmp == elem) {
                ret = true;
                break;
            }
        }
        return ret;
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (powerSet > 0) {
            sb.append('|').append(name).append('|').append(" = {");
            for (int i = 0; i < powerSet - 1; i++) {
                sb.append(set[i]).append(';');
            }
            sb.append(set[powerSet - 1]).append('}').append('\n');
        } else {
            sb.append('|').append(name).append('|');
        }
 
        return sb.toString();
    }
 
    public String getName() {
        return name;
    }
}
