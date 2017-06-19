package osmparser.tools;

public interface Consumer<T> {
    void accept(T value);
}