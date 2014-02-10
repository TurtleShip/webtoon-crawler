package theCrawler;

/**
 * This class hold a tuple of a label and a value
 */
public class LabelValueTuple<T> {

    public final String label;
    public final T value;

    public LabelValueTuple(String label, T value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return label;
    }
}
