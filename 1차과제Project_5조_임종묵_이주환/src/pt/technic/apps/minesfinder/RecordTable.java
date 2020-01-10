package pt.technic.apps.minesfinder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Massadas
 */
public class RecordTable implements Serializable {

    private transient final int MAX_CHAR = 10;
    private final int ranking = 10;
    
    private String name[] = new String[ranking];
    private long score[] = new long[ranking];

    private transient ArrayList<RecordTableListener> listeners;

    public RecordTable() {
//        name = "Anonymous";
//        score = 9999999;
       for(int i =0; i<ranking; i++) {
          name[i] = "Anonymous";
          score[i] = 9999999;
       }
        listeners = new ArrayList<>();
    }

    public String getName(int ranking) {
        return name[ranking].substring(0, Math.min(MAX_CHAR, name[ranking].length()));
    }

    public long getScore(int ranking) {
        return score[ranking];
    }

    public void setRecord(String name, long score) {
//        if (score < this.score) {
//            this.name = name;
//            this.score = score;
//            notifyRecordTableUpdated();
//        }
       long nextScore;
       String nextName;
       for(int i=0; i<ranking; i++) {
          if(score < this.score[i]) {
             nextScore = this.score[i];
             nextName = this.name[i];
             this.name[i] = name;
             this.score[i] = score;
             
             name = nextName;
             score = nextScore;
             
             notifyRecordTableUpdated();
          }
       }
    }

    public void addRecordTableListener(RecordTableListener list) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(list);
    }

    public void removeRecordTableListener(RecordTableListener list) {
        if (listeners != null) {
            listeners.remove(list);
        }
    }

    private void notifyRecordTableUpdated() {
        if (listeners != null) {
            for (RecordTableListener list : listeners) {
                list.recordUpdated(this);
            }
        }
    }
}