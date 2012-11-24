package org.jboss.shrinkwrap.impl.base;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.commons.EmptyVisitor;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingClassAdapter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class for extraction of classes used by given class.
 *
 * @author <a href="mailto:mmatloka@gmail.com">Michal Matloka</a>
 */
public final class DependentClassExtractor {

    /**
     * Collects all class types from processed class.
     *
     * @see org.objectweb.asm.commons.Remapper
     */
    private static final class ClassCollector extends Remapper {

        /**
         * Retrieved classes
         */
        private final Set<Class<?>> classes = new HashSet<Class<?>>();

        /**
         * History of visited class types, we story class names in order to limit number of class resolutions
         */
        private final Set<String> visited = new HashSet<String>();

        private ClassCollector() {
            /* empty */
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String[] mapTypes(final String[] types) {
            for (final String type : types) {
                this.addType(type);
            }
            return super.mapTypes(types);
        }

        @Override
        public String map(final String type) {
            this.addType(type);
            return type;
        }

        @Override
        public String mapType(final String type) {
            this.addType(type);
            return type;
        }

        /**
         * @return collected classes
         */
        public Set<Class<?>> getClasses() {
            return classes;
        }

        private void addType(final String type) {
            if (type == null || visited.contains(type)) {
                return;
            }
            visited.add(type);

            if (!type.startsWith("java") && !type.startsWith("[L")) {
                try {
                    final String className = type.replace('/', '.');
                    this.classes.add(Class.forName(className));
                } catch (final ClassNotFoundException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    public static final int DEFAULT_DEPTH = 1;

    /**
     * Visit history, map <Class name, depth>. Created in order to avoid cycles, and check, if we have already visited
     * class on same depth, or different lower and we should reanalyze class
     */
    private final Map<String, Integer> visited = new HashMap<String, Integer>();

    /**
     * Retrieve classes used by given class.
     *
     * @param name
     *            class name
     * @param depth
     *            search depth
     * @return dependent classes
     * @throws IOException
     */
    public Set<Class<?>> getUsedClasses(final String name, final int depth) throws IOException {
        Validate.notNullOrEmpty(name, "Class name cannot be null or empty");

        if (depth < 1) {
            throw new IllegalArgumentException("Invalid depth");
        }

        final Integer oldDepth = visited.get(name);
        if (oldDepth != null && oldDepth >= depth) {
            return Collections.emptySet();
        }
        visited.put(name, depth);

        final Set<Class<?>> classes = getUsedClasses(name);

        final Set<Class<?>> result = new HashSet<Class<?>>(classes);

        final int newDepth = depth - 1;
        if (newDepth > 0) {
            for (final Class<?> clazz : classes) {
                final Set<Class<?>> usedClasses = getUsedClasses(clazz.getName(), newDepth);
                result.addAll(usedClasses);
            }
        }
        return result;
    }

    /**
     * Retrieved classes used directly by given class
     *
     * @param name
     *            class name
     * @return used classes
     * @throws IOException
     */
    private static Set<Class<?>> getUsedClasses(final String name) throws IOException {
        final ClassReader reader = new ClassReader(name);
        final ClassCollector classCollector = new ClassCollector();
        final ClassVisitor inner = new EmptyVisitor();

        final RemappingClassAdapter visitor = new RemappingClassAdapter(inner, classCollector);
        reader.accept(visitor, ClassReader.EXPAND_FRAMES);

        return classCollector.getClasses();
    }
}
