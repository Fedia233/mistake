import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class RengeSecond<T extends Comparable<T>> implements Set<T> {

    private T start;
    private T end;
    private List<T> items;
    private Function<T, T> function;

    public RengeSecond(T start, T end, Function<T, T> function) {
        this.start = start;
        this.end = end;
        items = new ArrayList<>();
        for (T i = start; i.compareTo(end) <= 0; i = function.apply(i)) {
            items.add(i);
        }
    }

    public static RengeSecond of(Integer start, Integer end) {
        return new RengeSecond<>(start, end, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer + 1;
            }
        });
    }

    //
    public static RengeSecond of(Double start, Double end) {
        return new RengeSecond<>(start, end, new Function<Double, Double>() {
            @Override
            public Double apply(Double num) {
                return num + 0.1D;
            }
        });
    }

    public static RengeSecond of(Float start, Float end) {
        return new RengeSecond<>(start, end, new Function<Float, Float>() {
            @Override
            public Float apply(Float num) {
                return num + 0.1f;
            }
        });
    }

    public static RengeSecond of(Character start, Character end, Function<Character, Character> f) {
        return new RengeSecond<>(start, end, new Function<Character, Character>() {
            @Override
            public Character apply(Character character) {
                return (char) (character + 1);
            }
        });
    }


    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return items.contains(0);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            T cursor;
            private int nextIndex = 0;

            @Override
            public boolean hasNext() {
                return nextIndex < size();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                cursor = start;
                nextIndex++;
                return cursor;
            }
        };
    }

    @Override
    public Object[] toArray() {
        List<T> list = new ArrayList<>();
        Iterator<T> iter = iterator();
        while (iter.hasNext()) {
            list.add(iter.next());
        }
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        List<T> list = new ArrayList<>();
        Iterator<T> iter = iterator();
        while (iter.hasNext()) {
            list.add(iter.next());
        }
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        if (t.compareTo(start) > 0) {
            if (t.compareTo(end) < 0) {
                return false;
            }
            end = t;
            return true;
        } else {
            start = t;
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        T t = (T) o;
        if (t.compareTo(start) > 0) {
            if (t.compareTo(end) < 0) {
                end = t;
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T t : c) if (add(t)) modified = true;
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<T> e = iterator();
        while (e.hasNext()) {
            if (!c.contains(e.next())) {
                e.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;

        if (size() > c.size()) {
            for (Iterator<?> i = c.iterator(); i.hasNext(); )
                modified |= remove(i.next());
        } else {
            for (Iterator<?> i = iterator(); i.hasNext(); ) {
                if (c.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        end = start;
    }
}