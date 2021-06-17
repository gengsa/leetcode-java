package leetcode;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class ValidNumber {

    public boolean isNumber(String s) {
        Automaton automaton = new Automaton();
        for (int i = 0; i < s.length(); i++) {
            automaton.get(s.charAt(i));
            if (automaton.currentState == State.INVALID) {
                return false;
            }
        }
        return automaton.currentState.accept;
    }
    
    enum State {
        INIT(false),
        INT_SIGN(false),
        INT(true),
        POINT_WITHOUT_INT(false),
        POINT(true),
        FRACTION(true),
        EXP(false),
        EXP_SIGN(false),
        EXP_VALUE(true),
        INVALID(false),
        ;
        
        final boolean accept;
        
        private State(boolean accept) {
            this.accept = accept;
        }
    }
    
    static final Set<State> VALID_FINALS_STATES = new HashSet<>() {{
        add(State.INT);
        add(State.POINT);
        add(State.FRACTION);
        add(State.EXP_VALUE);
    }};

    enum Alphabet {
        SIGN, NUMBER, POINT, EXP, OTHER
    }

    class Automaton {

        public State currentState = State.INIT;

        private Map<State, Map<Alphabet, State>> transitionTable = new HashMap<>() {{
//            SIGN, NUMBER, POINT, EXP, OTHER
            put(State.INIT, new HashMap<>() {{
                put(Alphabet.SIGN, State.INT_SIGN);
                put(Alphabet.NUMBER, State.INT);
                put(Alphabet.POINT, State.POINT_WITHOUT_INT);
            }});
            put(State.INT_SIGN, new HashMap<>() {{
                put(Alphabet.NUMBER, State.INT);
                put(Alphabet.POINT, State.POINT_WITHOUT_INT);
            }});
            put(State.INT, new HashMap<>() {{
                put(Alphabet.NUMBER, State.INT);
                put(Alphabet.POINT, State.POINT);
                put(Alphabet.EXP, State.EXP);
            }});
            put(State.POINT_WITHOUT_INT, new HashMap<>() {{
                put(Alphabet.NUMBER, State.FRACTION);
            }});
            put(State.POINT, new HashMap<>() {{
                put(Alphabet.NUMBER, State.FRACTION);
                put(Alphabet.EXP, State.EXP);
            }});
            put(State.FRACTION, new HashMap<>() {{
                put(Alphabet.NUMBER, State.FRACTION);
                put(Alphabet.EXP, State.EXP);
            }});
            put(State.EXP, new HashMap<>() {{
                put(Alphabet.SIGN, State.EXP_SIGN);
                put(Alphabet.NUMBER, State.EXP_VALUE);
            }});
            put(State.EXP_SIGN, new HashMap<>() {{
                put(Alphabet.NUMBER, State.EXP_VALUE);
            }});
            put(State.EXP_VALUE, new HashMap<>() {{
                put(Alphabet.NUMBER, State.EXP_VALUE);
            }});
        }};

        // get one char and transit
        public void get(char c) {
            Alphabet alphabet = toAlphabet(c);
            if (transitionTable.containsKey(currentState) && transitionTable.get(currentState).containsKey(alphabet)) {
                currentState = transitionTable.get(currentState).get(alphabet);
            } else {
                currentState = State.INVALID;
            }
        }

        public Alphabet toAlphabet(char c) {
            if (c == '+' || c == '-') {
                return Alphabet.SIGN;
            } else if (Character.isDigit(c)) {
                return Alphabet.NUMBER;
            } else if (c == '.') {
                return Alphabet.POINT;
            } else if (c == 'e' || c == 'E') {
                return Alphabet.EXP;
            } else {
                return Alphabet.OTHER;
            }
        }
    }

    public static void main(String[] args) {
        var target = new ValidNumber();
        assertEquals(true, target.isNumber("0"));
        assertEquals(false, target.isNumber("e"));
        assertEquals(false, target.isNumber("."));
        assertEquals(true, target.isNumber(".1"));
        assertEquals(true, target.isNumber("3e+7"));
        assertEquals(false, target.isNumber("99e2.5"));
        assertEquals(false, target.isNumber("--6"));
    }
}


