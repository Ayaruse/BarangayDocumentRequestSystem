package Counter;
import Data.*;
import java.util.*;

public class CounterMain {
    static ArrayList<CounterDetails> counterUser = new ArrayList<>();
    
    public static void main(String[] args) {
        UserDataSaver.loadCounters(counterUser);
        new CounterWelcoming();
        
    }
}
