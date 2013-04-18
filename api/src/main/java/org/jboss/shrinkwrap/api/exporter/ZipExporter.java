/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.shrinkwrap.api.exporter;

import org.jboss.shrinkwrap.api.Assignable;

/**
 * Exporter used to represent an {@link Assignable} in ZIP format.
 *
 * @see http://www.pkware.com/documents/casestudies/APPNOTE.TXT
 * @author <a href="mailto:baileyje@gmail.com">John Bailey</a>
 * @author <a href="mailto:aslak@conduct.no">Aslak Knutsen</a>
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @author <a href="mailto:mmatloka@gmail.com">Michal Matloka</a>
 * @version $Revision: $
 */
public interface ZipExporter extends StreamExporter {

    /**
     * @param enabled
     *            enable compression of content in ZIP format. True by default.
     * @return exporter with set given compression setting.
     */
    ZipExporter compressionEnabled(boolean enabled);

    /**
     * @return exporter of compressed content in ZIP format.
     */
    ZipExporter compressionEnabled();

    /**
     * @return exporter of uncompressed content in ZIP format.
     */
    ZipExporter compressionDisabled();

    /**
     * @return true if compression is enabled.
     */
    boolean isCompressionEnabled();
}
