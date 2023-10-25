
public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable(13);

        String[] symbolsToBeAdded = {"a1", "1a", "b2", "c3", "d4", "a1"};

        System.out.println("Add symbols to the SymbolTable\n==============================");
        for (String s : symbolsToBeAdded) {
            System.out.println(symbolTable.addSymbol(s));
        }

        String[] symbolsToVerify = {"a1", "1a", "b2", "c3", "d4", "a1", "c7", "d12"};
        System.out.println("\n Check if the SymbolTable contains a symbol\n==============================");
        for (String symbol : symbolsToVerify) {
            System.out.println(symbolTable.containsSymbol(symbol));
        }

        String[] symbolsToGetPosition = new String[]{"a1", "1a", "b2", "c3", "d4", "a1", "b5"};
        System.out.println("\n Get the position of the symbol in the SymbolTable\n==============================");
        for (String symbol : symbolsToGetPosition) {
            System.out.println(symbolTable.getPosition(symbol));
        }

        System.out.println("\n Print the SymbolTable\n==============================");
        System.out.println(symbolTable);

        String[] symbolsToRemove = {"a1", "1a", "b2", "c3", "d4", "a1", "ed"};
        System.out.println("\nRemove symbols from the SymbolTable\n==============================");
        for (String symbol : symbolsToRemove) {
            System.out.println(symbolTable.removeSymbol(symbol));
        }
    }
}
