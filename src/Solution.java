

import java.util.*;

import util.Print;

public class Solution {
    private HashMap<String, ArrayList<Time>> map;

    private class Time{
        String key;
        String value;
        int timestamp;

        public Time(String k, String v, int t) {
            key = k;
            value = v;
            timestamp = t;
        }

        public int compareTo(Time t) {
            return this.timestamp - t.timestamp;
        }
    }

    /** Initialize your data structure here. */
    public Solution() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        ArrayList<Time> list;
        if (map.containsKey(key)) {
            list = map.get(key);
        } else {
            list = new ArrayList<>();
        }

        list.add(new Time(key, value, timestamp));
        map.put(key, list);
    }

    public String get(String key, int timestamp) {
        ArrayList<Time> list = map.get(key);
        Time t = new Time(key, "", timestamp);
        return binarySearch(list, 0, list.size() - 1, t);
    }

    private String binarySearch(ArrayList<Time> list, int low, int high, Time target) {
        if (low > high) return null;

        if (low == high && list.get(low).compareTo(target) <= 0){
            return list.get(low).value;
        } else if (low == high){
            return null;
        }

        int mid = (low + high) / 2;
        if (list.get(mid).compareTo(target) <= 0 && list.get(mid + 1).compareTo(target) > 0) {
            return list.get(mid).value;
        } else if (list.get(mid).compareTo(target) <= 0 && list.get(mid + 1).compareTo(target) <= 0){
            return binarySearch(list, mid + 1, high, target);
        } else{
            return binarySearch(list, low, mid - 1, target);
        }
    }
}
