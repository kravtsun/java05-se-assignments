package ru.sbpau.mit;

public class DictionaryImpl implements Dictionary {

    // how many elements at most can be stored in a bucket with one hash
    final private static int MAX_BUCKET_FILL_SIZE = 4;

    private class Node {
        private int hash;
        String[] values;
    }

    Node[] nodes;
    /**
     * кол-во ключей в таблице
     */
    public int size() {
        return 0;
    }

    /**
     * @param key
     * @return true, если такой ключ @p key содержится в таблице
     */
    public boolean contains(String key) {
        return false;
    }

    /**
     * @param key
     * @return возвращает значение, хранимое по ключу key
     * если такого нет, возвращает null
     */
    public String get(String key) {
        return null;
    }

    /**
     * положить по ключу key значение value
     *
     * @param key
     * @param value
     * @return вернуть ранее хранимое значение, либо null
     */
    public String put(String key, String value) {
        return null;
    }

    /**
     * забыть про пару key-value для переданного @p key
     *
     * @param key
     * @returnи вернуть забытое value, либо null, если такой пары не было
     */
    public String remove(String key) {
        return null;
    }

    /**
     * забыть про все пары key-value
     */
    public void clear() {

    }
}
