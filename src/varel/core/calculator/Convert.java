package varel.core.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Convert {

    private List<BigDecimal> Operand = new ArrayList<BigDecimal>();

    public Convert() {
        Operand.add(BigDecimal.valueOf(0.0));
    }

    public String add(BigDecimal newOperand) {
        Operand.add(newOperand);
        return V.STACK_TEMPLATE + (Operand.size() - 1);
    }

    public boolean set(int key, BigDecimal value) {
        if(key > -1 && key < Operand.size()) {
            Operand.set(key, value);
            return true;
        }
        return false;
    }

    public boolean set(String key, BigDecimal value) {
        int _key;
        try {
            _key = Integer.parseInt(key.substring(2, key.length()));
            if(_key > -1 && _key < Operand.size()) {
                Operand.set(Integer.parseInt(key), value);
                return true;
            }
        } catch (Error e) {
            return false;
        }
        return false;
    }

    public BigDecimal get(String key) {
        key = key.substring(V.STACK_TEMPLATE.length(), key.length());
        return Operand.get(Integer.parseInt(key));
    }
}
