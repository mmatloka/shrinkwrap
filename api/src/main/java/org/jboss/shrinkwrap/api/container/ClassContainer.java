/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.shrinkwrap.api.container;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.Filter;

/**
 * Defines the contract for a component capable of storing Java Classes. <br/>
 * <br/>
 * The actual path to the {@link Class} resources within the {@link Archive} is up to the
 * implementations/specifications.
 *
 * @author <a href="mailto:aslak@conduct.no">Aslak Knutsen</a>
 * @version $Revision: $
 */
public interface ClassContainer<T extends Archive<T>> extends ResourceContainer<T> {
    // -------------------------------------------------------------------------------------||
    // Contracts --------------------------------------------------------------------------||
    // -------------------------------------------------------------------------------------||

    /**
     * Adds the {@link Class}, and all member (inner) {@link Class}es to the {@link Archive}.
     *
     * @param clazz The class to add to the Archive
     * @return This archive
     * @throws IllegalArgumentException
     *             If no class were specified
     */
    T addClass(Class<?> clazz) throws IllegalArgumentException;

    /**
     * Adds the {@link Class}, all member (inner) {@link Class}es and all used by it {@link Class}es to the
     * {@link Archive}.
     *
     * @param recursive
     *            should dependent classes be added
     * @param clazz
     *            The class to add to the Archive
     * @return This archive
     * @throws IllegalArgumentException
     *             If no class were specified
     */
    T addClass(boolean recursive, Class<?> clazz) throws IllegalArgumentException;

    /**
     * Adds the {@link Class}, all member (inner) {@link Class}es and all used by it {@link Class}es to the
     * {@link Archive}.
     *
     * @param recursive
     *            should dependent classes be added
     * @param depth
     *            classes will be retrieved on given depth
     * @param clazz
     *            The class to add to the Archive
     * @return This archive
     * @throws IllegalArgumentException
     *             If no class were specified
     */
    T addClass(boolean recursive, int depth, Class<?> clazz) throws IllegalArgumentException;

    /**
     * Adds the {@link Class}, and all member (inner) {@link Class}es, with the specified fully-qualified name, loaded
     * by the Thread Context {@link ClassLoader}, to the {@link Archive}.
     *
     * @param fullyQualifiedClassName
     *            The name of the {@link Class} to add
     * @return This archive
     * @throws IllegalArgumentException
     *             If no class name was specified
     * @throws IllegalArgumentException
     *             If the {@link Class} could not be loaded
     */
    T addClass(String fullyQualifiedClassName) throws IllegalArgumentException;

    /**
     * Adds the {@link Class}, and all member (inner) @link{Class}es, with the specified fully-qualified name, loaded by
     * the specified {@link ClassLoader}, to the {@link Archive}.
     *
     * @param fullyQualifiedClassName
     *            The name of the {@link Class} to add
     * @param cl
     *            The {@link ClassLoader} used to load the Class
     * @return This archive
     * @throws IllegalArgumentException
     *             If no class name was specified
     * @throws IllegalArgumentException
     *             If no {@link ClassLoader} was specified
     * @throws IllegalArgumentException
     *             If the {@link Class} could not be loaded by the target {@link ClassLoader}
     */
    T addClass(String fullyQualifiedClassName, ClassLoader cl) throws IllegalArgumentException;

    /**
     * Adds the {@link Class}es, and all member (inner) {@link Class}es to the {@link Archive}.
     *
     * @param classes
     *            The classes to add to the Archive
     * @return This archive
     * @throws IllegalArgumentException
     *             If no classes were specified
     */
    T addClasses(Class<?>... classes) throws IllegalArgumentException;

    /**
     * Adds all classes in the specified {@link Package} to the {@link Archive}. <br/>
     * SubPackages are excluded.
     *
     * @param pack
     *            The {@link Package} to add
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no package were specified
     * @see #addPackages(boolean, Package...)
     */
    T addPackage(Package pack) throws IllegalArgumentException;

    /**
     * Adds all classes in the default {@link Package} to the {@link Archive}. <br/>
     * SubPackages are excluded.
     *
     * @return This virtual archive
     */
    T addDefaultPackage();

    /**
     * Adds all classes in the specified {@link Package}s to the {@link Archive}.
     *
     * @param recursive
     *            Should the sub packages be added
     * @param packages
     *            All the packages to add
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no packages were specified
     * @see #addPackages(boolean, Filter, Package...)
     */
    T addPackages(boolean recursive, Package... packages) throws IllegalArgumentException;

    /**
     * Adds all classes accepted by the filter in the specified {@link Package}s to the {@link Archive}. <br/>
     *
     * The {@link ArchivePath} returned to the filter is the {@link ArchivePath} of the class, not the final location. <br/>
     * package.MyClass = /package/MyClass.class <br/>
     * <b>not:</b> package.MyClass = /WEB-INF/classes/package/MyClass.class <br/>
     *
     * @param recursive
     *            Should the sub packages be added
     * @param filter
     *            filter out specific classes
     * @param packages
     *            All the packages to add
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no packages were specified
     */
    T addPackages(boolean recursive, Filter<ArchivePath> filter, Package... packages) throws IllegalArgumentException;

    /**
     * Adds all classes in the specified {@link Package} to the {@link Archive}. <br/>
     * SubPackages are excluded.
     *
     * @param pack
     *            Package to add represented by a String ("my/package")
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no package were specified
     * @see #addPackages(boolean, Package...)
     */
    T addPackage(String pack) throws IllegalArgumentException;

    /**
     * Adds all classes in the specified {@link Package}s to the {@link Archive}.
     *
     * @param recursive
     *            Should the sub packages be added
     * @param packages
     *            All the packages to add represented by a String ("my/package")
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no packages were specified
     * @see #addPackages(boolean, Filter, Package...)
     */
    T addPackages(boolean recursive, String... packages) throws IllegalArgumentException;

    /**
     * Adds all classes accepted by the filter in the specified {@link Package}s to the {@link Archive}. <br/>
     *
     * The {@link ArchivePath} returned to the filter is the {@link ArchivePath} of the class, not the final location. <br/>
     * package.MyClass = /package/MyClass.class <br/>
     * <b>not:</b> package.MyClass = /WEB-INF/classes/package/MyClass.class <br/>
     *
     * @param recursive
     *            Should the sub packages be added
     * @param filter
     *            filter out specific classes
     * @param packages
     *            All the packages to add represented by a String ("my/package")
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no packages were specified
     */
    T addPackages(boolean recursive, Filter<ArchivePath> filter, String... packages) throws IllegalArgumentException;

    /**
     * Deletes the {@link Class}, and all member (inner) {@link Class}es from the {@link Archive}.
     *
     * @param clazz The class to be deleted from the Archive
     * @return This archive
     * @throws IllegalArgumentException If no class was specified
     */
    T deleteClass(Class<?> clazz) throws IllegalArgumentException;

    /**
     * Deletes the {@link Class}, and all member (inner) {@link Class}es, with the specified fully-qualified name, loaded by the
     * Thread Context {@link ClassLoader}, from the {@link Archive}.
     *
     * @param fullyQualifiedClassName The name of the {@link Class} to be deleted
     * @return This archive
     * @throws IllegalArgumentException If no class name was specified
     * @throws IllegalArgumentException If the {@link Class} could not be loaded
     */
    T deleteClass(String fullyQualifiedClassName) throws IllegalArgumentException;

    /**
     * Deletes the {@link Class}es, and all member (inner) {@link Class}es from the {@link Archive}.
     *
     * @param classes The classes to be removed from the {@link Archive}
     * @return This archive
     * @throws IllegalArgumentException If no classes were specified
     */
    T deleteClasses(Class<?>... classes) throws IllegalArgumentException;

    /**
     * Deletes all classes in the specified {@link Package} from the {@link Archive}. <br/>
     * SubPackages are excluded.
     *
     * @param pack The {@link Package} to be deleted
     * @return This archive
     * @throws IllegalArgumentException If no package was specified
     * @see #deletePackages(boolean, Package...)
     */
    T deletePackage(Package pack) throws IllegalArgumentException;

    /**
     * Deletes all classes in the specified {@link Package} from the {@link Archive}. <br/>
     * SubPackages are excluded.
     *
     * @param pack Package to be delete represented by a String ("my/package")
     * @return This archive
     * @throws IllegalArgumentException If no package was specified
     * @see #deletePackages(boolean, Package...)
     */
    T deletePackage(String pack) throws IllegalArgumentException;

    /**
     * Deletes all classes in the default {@link Package} from the {@link Archive}. <br/>
     * SubPackages are excluded.
     *
     * @return This archive
     */
    T deleteDefaultPackage();

    /**
     * Deletes all classes in the specified {@link Package}s from the {@link Archive}.
     *
     * @param recursive Should the sub packages be deleted?
     * @param packages All the packages to be deleted
     * @return This archive
     * @throws IllegalArgumentException If no packages were specified
     * @see #deletePackages(boolean, Filter, Package...)
     */
    T deletePackages(boolean recursive, Package... packages) throws IllegalArgumentException;

    /**
     * Delete all classes in the specified {@link Package}s from the {@link Archive}.
     *
     * @param recursive Should the sub packages be deleted?
     * @param packages All the packages to be deleted represented by a String ("my/package")
     * @return This archive
     * @throws IllegalArgumentException If no packages were specified
     * @see #deletePackages(boolean, Filter, Package...)
     */
    T deletePackages(boolean recursive, String... packages) throws IllegalArgumentException;

    /**
     * Deletes all classes accepted by the filter in the specified {@link Package}s from the {@link Archive}. <br/>
     *
     * The {@link ArchivePath} returned to the filter is the {@link ArchivePath} of the class, not the final location. <br/>
     * package.MyClass = /package/MyClass.class <br/>
     * <b>not:</b> package.MyClass = /WEB-INF/classes/package/MyClass.class <br/>
     *
     * @param recursive Should the sub packages be deleted?
     * @param filter filter out specific classes
     * @param packages All the packages to be deleted
     * @return This archive
     * @throws IllegalArgumentException If no packages were specified or if no filter was specified
     */
    T deletePackages(boolean recursive, Filter<ArchivePath> filter, Package... packages) throws IllegalArgumentException;

    /**
     * Delete all classes accepted by the filter in the specified {@link Package}s from the {@link Archive}. <br/>
     *
     * The {@link ArchivePath} returned to the filter is the {@link ArchivePath} of the class, not the final location. <br/>
     * package.MyClass = /package/MyClass.class <br/>
     * <b>not:</b> package.MyClass = /WEB-INF/classes/package/MyClass.class <br/>
     *
     * @param recursive Should the sub packages be deleted?
     * @param filter filter out specific classes
     * @param packages All the packages to be deleted represented by a String ("my/package")
     * @return This archive
     * @throws IllegalArgumentException If no packages were specified or if no filter was specified
     */
    T deletePackages(boolean recursive, Filter<ArchivePath> filter, String... packages) throws IllegalArgumentException;

}
