package hexlet.code;

/**
 * Структура данных с информацией о различиях.
 *
 * @param key ключ
 * @param oldValue старое значение
 * @param newValue новое значение
 * @param status статус
 */
public record DiffNode(Object key, Object oldValue, Object newValue, hexlet.code.DiffNode.Status status) {

    public enum Status {
        /**
         * Ключ имеется только во втором файле.
         */
        ADDED,
        /**
         * Ключ имеется только в первом файле.
         */
        REMOVED,
        /**
         * Значения разные.
         */
        CHANGED,
        /**
         * Значения одинаковые.
         */
        UNCHANGED
    }

}

