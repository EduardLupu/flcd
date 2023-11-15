import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FiniteAutomata {

    private final String ELEM_SEPARATOR = ";";

    private boolean isDeterministic;

    private String initialState;

    private List<String> states;

    private List<String> alphabet;

    private List<String> finalStates;

    private final Map<Pair<String, String>, Set<String>> transitions;


    private void readFromFile(String filePath) {
        //We assume the file is valid :D
        try (Scanner scanner = new Scanner(new File(filePath))) {

            this.states = new ArrayList<>(List.of(scanner.nextLine().split(this.ELEM_SEPARATOR)));

            this.initialState = scanner.nextLine();

            this.alphabet = new ArrayList<>(List.of(scanner.nextLine().split(this.ELEM_SEPARATOR)));

            this.finalStates = new ArrayList<>(List.of(scanner.nextLine().split(this.ELEM_SEPARATOR)));

            while (scanner.hasNextLine()) {

                String transitionLine = scanner.nextLine();
                String[] transitionComponents = transitionLine.split(" ");


                if (states.contains(transitionComponents[0]) && states.contains(transitionComponents[2]) && alphabet.contains(transitionComponents[1])) {

                    Pair<String, String> transitionStates = new Pair<>(transitionComponents[0], transitionComponents[1]);

                    if (!transitions.containsKey(transitionStates)) {
                        Set<String> transitionStatesSet = new HashSet<>();
                        transitionStatesSet.add(transitionComponents[2]);
                        transitions.put(transitionStates, transitionStatesSet);
                    } else {
                        transitions.get(transitionStates).add(transitionComponents[2]);
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.isDeterministic = checkIfDeterministic();
    }

    public boolean checkIfDeterministic() {

        return this.transitions.values().stream().allMatch(list -> list.size() <= 1);

    }

    public FiniteAutomata(String filePath) {
        this.transitions = new HashMap<>();
        this.readFromFile(filePath);
    }

    public List<String> getStates() {
        return this.states;
    }

    public String getInitialState() {
        return this.initialState;
    }

    public List<String> getAlphabet() {
        return this.alphabet;
    }

    public List<String> getFinalStates() {
        return this.finalStates;
    }

    public Map<Pair<String, String>, Set<String>> getTransitions() {
        return this.transitions;
    }

    public String writeTransitions(){
        StringBuilder builder = new StringBuilder();
        builder.append("Transitions: \n");
        transitions.forEach((K, V) -> {
            builder.append("<").append(K.getFirst()).append(",").append(K.getSecond()).append("> -> ").append(V).append("\n");
        });

        return builder.toString();
    }

    public boolean checkAlphabet(String sequence) {
        for (int i = 0; i < sequence.length(); i++) {
            String symbol = String.valueOf(sequence.charAt(i));
            if (!alphabet.contains(symbol)) {
                return false;
            }
            if (sequence.charAt(sequence.length() - 1) != finalStates.get(0).toLowerCase().charAt(0)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSequence(String sequence){
        if(sequence.isEmpty())
            return finalStates.contains(initialState);

        if(checkAlphabet(sequence))
            return false;

        String state = initialState;
        for(int i=0;i<sequence.length();++i){
            Pair<String, String> key = new Pair<>(state, String.valueOf(sequence.charAt(i)));
            if(transitions.containsKey(key))
                state = transitions.get(key).iterator().next();
            else
                return true;
        }
        return finalStates.contains(state);
    }
}