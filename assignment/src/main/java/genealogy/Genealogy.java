package genealogy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Output the class hierarchy or ancestry of a class.
 *
 * @author hom
 */
public class Genealogy {

    int c = new ArrayList<Class>().stream().mapToInt( p -> 1 ).sum();

    /**
     * @param args the command line arguments.
     */
    public static void main( String[] args ) {
        Genealogy gen = new Genealogy();
        gen.getAncestry(StringBuilder.class.getName());
    }

    /**
     * Get the ancestry of the class or interface with the given name.
     *
     * @param typeName to use
     * @return a string containing the type hierarchy of the type
     */
    public String getAncestry( String typeName ) {
        //TODO implement getAncestry
        Predicate<Field> nonStatic = ( Field f ) -> !Modifier.isStatic( f.getModifiers() );

        StringBuilder familyTree = new StringBuilder();
        StringBuilder formatting = new StringBuilder().append(" ");
        StringBuilder fields = new StringBuilder();

        Class<?> aClass;

        try {
            aClass = Class.forName(typeName);
        } catch (ClassNotFoundException exception) { throw new RuntimeException(exception); }

        while (!aClass.getSimpleName().equals("Object")) {
            familyTree.insert(0, " " + aClass.getName());
            aClass = aClass.getSuperclass();
        }

        familyTree.insert(0, aClass.getName());

        String[] parts = familyTree.toString().split("\\s");
        familyTree.delete(0, familyTree.length());
        familyTree.append("\nclass hierarchy of [").append(typeName).append("]\n");

        for (String part:
             parts) {
            Class<?> c;
            try {
                c = Class.forName(part);
            } catch (ClassNotFoundException exception) { throw new RuntimeException(exception); }

            String visibility = Modifier.toString( c.getModifiers() & ( 7+16 ));
            StringBuilder interfaces = new StringBuilder();
            if (c.getInterfaces().length > 0) {
                interfaces.append(" implements ");

                for (int i = 0; i < c.getInterfaces().length - 1; i++) {
                    interfaces.append(Modifier.toString(c.getInterfaces()[i].getModifiers() & (7 + 16)))
                            .append(" ").append(c.getInterfaces()[i].getName()).append(", ");
                }
                interfaces.append(Modifier.toString(c.getInterfaces()[c.getInterfaces().length - 1].getModifiers() & (7 + 16)))
                        .append(" ").append(c.getInterfaces()[c.getInterfaces().length - 1].getName());
            }

            familyTree.append(formatting).append(visibility).append(" ").append(c.getName())
                    .append(interfaces).append("\n");

            formatting.append("  ");
            Arrays.stream(c.getDeclaredFields()).filter(nonStatic)
                    .forEach(field -> fields.append("  ").append(field.getType()).append(" ").append(field.getName()).append("\n"));
        }

        if (!fields.toString().isBlank()) {
            familyTree.append("{\n").append(fields).append("}");
        }

        System.out.println(familyTree);
        return familyTree.toString();
    }
}
