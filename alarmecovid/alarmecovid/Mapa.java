package alarmecovid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Mapa<K1,K2,V> {
    private Map<K1, Map<K2,V>> linhas;
    private Map<K2, Map<K1,V>> colunas;


    public Mapa(){
        this.linhas = new HashMap<>();
        this.colunas = new HashMap<>();
    }


    public Mapa(Map<K1, Map<K2, V>> linhas, Map<K2, Map<K1, V>> colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
    }


    public void addLinhaVazia(K1 key) {
        if (!linhas.containsKey(key)) {
            linhas.put(key, new HashMap<K2, V>());
        }
    }

    public void addColVazia(K2 key) {
        if (!colunas.containsKey(key)) {
            colunas.put(key, new HashMap<K1, V>());
        }
    }


    public void put(K1 rowKey, K2 colKey, V value) {
        Map<K2, V> row;
        Map<K1, V> col;

        if (linhas.containsKey(rowKey)) {
            row = linhas.get(rowKey);
        } else {
            row = new HashMap<K2, V>();
            linhas.put(rowKey, row);
        }

        if (colunas.containsKey(colKey)) {
            col = colunas.get(colKey);
        } else {
            col = new HashMap<K1, V>();
            colunas.put(colKey, col);
        }

        row.put(colKey, value);
        col.put(rowKey, value);
    }


    public V get(K1 rowKey, K2 colKey) {
        Map<K2, V> row;
        row = linhas.get(rowKey);
        if (row != null) {
            return row.get(colKey);
        }
        return null;
    }

    public Map<K2, V> getLinha(K1 rowKey) {
        if (linhas.containsKey(rowKey)) {
            return new HashMap<>(linhas.get(rowKey));
        }
        return null;
    }


    public Map<K1, V> getCol(K2 colKey) {
        if (colunas.containsKey(colKey)) {
            return new HashMap<>(colunas.get(colKey));
        }
        return null;
    }


    public Set<K1> getRowKeys() {
        return new HashSet(linhas.keySet());
    }

    public Set<K2> getColKeys() {
        return new HashSet(colunas.keySet());
    }
}
