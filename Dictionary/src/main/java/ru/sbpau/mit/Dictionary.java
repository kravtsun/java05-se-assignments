package ru.sbpau.mit;

/**
 * хеш-таблица, использующая список
 * ключами и значениями выступают строки
 * стандартный способ получить хеш объекта -- вызвать у него метод hashCode()
 */
public interface Dictionary {
    /**
     * кол-во ключей в таблице
     */
    int size();

    /**
     * @param key
     * @return true, если такой ключ @p key содержится в таблице
     */
    boolean contains(String key);

    /**
     *
     * @param key
     * @return возвращает значение, хранимое по ключу key
     * если такого нет, возвращает null
     */
    String get(String key);

    /**
     * положить по ключу key значение value
     * @param key
     * @param value
     * @return вернуть ранее хранимое значение, либо null
     */
    String put(String key, String value);

    /**
     * забыть про пару key-value для переданного @p key
     * @param key
     * @returnи вернуть забытое value, либо null, если такой пары не было
     */
    String remove(String key);

    /**
     * забыть про все пары key-value
     */
    void clear();
}