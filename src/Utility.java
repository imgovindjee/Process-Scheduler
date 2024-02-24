import java.util.ArrayList;
import java.util.List;

public class Utility {
    public static List<Row> deepCopy(List<Row> list){
        List<Row> newlist = new ArrayList();
        for(Row l:list){
            newlist.add(new Row(l.getProcessName(), l.getArrivalTime(), l.getBurstTime(), l.getPriorityLevel()));
        }
        return newlist;
    }
}
