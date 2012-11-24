package org.jboss.shrinkwrap.impl.base.spec.donotchange2;

/**
 * This class is just here for the recursive ClassContainer.addPackage/addClass tests.
 * 
 * @author <a href="mailto:mmatloka@gmail.com">Michal Matloka</a>
 */
public class DummyDependentClassC {

    private DummyDependentClassB dummyDependentClassB;

    private static final class InnerClassC {

        private  DummyDependentClassD dummyDependentClassD;
    }
}
