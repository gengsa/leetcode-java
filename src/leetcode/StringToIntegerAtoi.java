package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class StringToIntegerAtoi {

    public int myAtoi(String s) {
        Automaton automaton = new Automaton();
        for (int i = 0; i < s.length(); i++) {
            automaton.get(s.charAt(i));
            if (automaton.currentState == State.END) {
                break;
            }
        }
        return (int)(automaton.sign * automaton.value);
    }
    
    enum State {
        START, SIGNED, IN_NUMBER, END,
    }

    enum Alphabet {
        SPACE, SIGN, NUMBER, OTHER,
    }

    class Automaton {
        public int sign = 1;
        public long value = 0;

        public State currentState = State.START;

        private Map<State, Map<Alphabet, State>> transitionTable = new HashMap<>() {{
            put(State.START, new HashMap<>() {{
                put(Alphabet.SPACE, State.START);
                put(Alphabet.SIGN, State.SIGNED);
                put(Alphabet.NUMBER, State.IN_NUMBER);
                put(Alphabet.OTHER, State.END);
            }});
            put(State.SIGNED, new HashMap<>() {{
                put(Alphabet.SPACE, State.END);
                put(Alphabet.SIGN, State.END);
                put(Alphabet.NUMBER, State.IN_NUMBER);
                put(Alphabet.OTHER, State.END);
            }});
            put(State.IN_NUMBER, new HashMap<>() {{
                put(Alphabet.SPACE, State.END);
                put(Alphabet.SIGN, State.END);
                put(Alphabet.NUMBER, State.IN_NUMBER);
                put(Alphabet.OTHER, State.END);
            }});
            put(State.END, new HashMap<>() {{
                put(Alphabet.SPACE, State.END);
                put(Alphabet.SIGN, State.END);
                put(Alphabet.NUMBER, State.END);
                put(Alphabet.OTHER, State.END);
            }});
        }};

        public void get(char c) {
            Alphabet alphabet = toAlphabet(c);
            currentState = transitionTable.get(currentState).get(alphabet);
            switch (currentState) {
                case IN_NUMBER: {
                    value = value * 10 + c - '0';
                    value = sign == 1 ? Math.min((long)Integer.MAX_VALUE, value) : Math.min(-(long)Integer.MIN_VALUE, value);
                    break;
                }
                case SIGNED: {
                    sign = c == '+' ? 1 : -1;
                    break;
                }
                default:
                    break;
            }
        }

        public Alphabet toAlphabet(char c) {
            if (c == ' ') {
                return Alphabet.SPACE;
            } else if (c == '+' || c == '-') {
                return Alphabet.SIGN;
            } else if (Character.isDigit(c)) {
                return Alphabet.NUMBER;
            } else {
                return Alphabet.OTHER;
            }
        }
    }

    public static void main(String[] args) {
        var target = new StringToIntegerAtoi();
        assertEquals(42, target.myAtoi("42"));
        assertEquals(-42, target.myAtoi("   -42"));
        assertEquals(4193, target.myAtoi("4193 with words"));
        assertEquals(0, target.myAtoi("words and 987"));
        assertEquals(Integer.MIN_VALUE, target.myAtoi("-91283472332"));
    }
}


