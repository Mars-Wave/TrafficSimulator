package simulator.model;

public abstract class Event implements Comparable<Event> {

    private int _time;

    public Event(int time) {
        if (time < 1)
            throw new IllegalArgumentException("Time must be positive (" + time + ")");
        else
            _time = time;
    }

    public int getTime() {
        return _time;
    }

    @Override
    public int compareTo(Event o) {
        return Integer.compare(this._time, o._time);
    }

    abstract void execute(RoadMap map);
}
