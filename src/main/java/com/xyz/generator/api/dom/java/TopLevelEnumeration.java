/**
 *    Copyright 2006-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.xyz.generator.api.dom.java;

import com.xyz.generator.api.dom.OutputUtilities;
import com.xyz.generator.internal.util.messages.Messages;

import java.util.*;

public class TopLevelEnumeration extends InnerEnum implements CompilationUnit {
    
    /** The imported types. */
    private Set<FullyQualifiedJavaType> importedTypes;

    /** The static imports. */
    private Set<String> staticImports;

    /** The file comment lines. */
    private List<String> fileCommentLines;

    /**
     * Instantiates a new top level enumeration.
     *
     * @param type
     *            the type
     */
    public TopLevelEnumeration(FullyQualifiedJavaType type) {
        super(type);
        importedTypes = new TreeSet<FullyQualifiedJavaType>();
        fileCommentLines = new ArrayList<String>();
        staticImports = new TreeSet<String>();
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#getFormattedContent()
     */
    public String getFormattedContent() {
        StringBuilder sb = new StringBuilder();

        for (String fileCommentLine : fileCommentLines) {
            sb.append(fileCommentLine);
            OutputUtilities.newLine(sb);
        }

        if (getType().getPackageName() != null
                && getType().getPackageName().length() > 0) {
            sb.append("package "); //$NON-NLS-1$
            sb.append(getType().getPackageName());
            sb.append(';');
            OutputUtilities.newLine(sb);
            OutputUtilities.newLine(sb);
        }

        for (String staticImport : staticImports) {
            sb.append("import static "); //$NON-NLS-1$
            sb.append(staticImport);
            sb.append(';');
            OutputUtilities.newLine(sb);
        }
        
        if (staticImports.size() > 0) {
            OutputUtilities.newLine(sb);
        }
        
        Set<String> importStrings = OutputUtilities.calculateImports(importedTypes);
        for (String importString : importStrings) {
            sb.append(importString);
            OutputUtilities.newLine(sb);
        }

        if (importStrings.size() > 0) {
            OutputUtilities.newLine(sb);
        }

        sb.append(super.getFormattedContent(0));

        return sb.toString();
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#getImportedTypes()
     */
    public Set<FullyQualifiedJavaType> getImportedTypes() {
        return Collections.unmodifiableSet(importedTypes);
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#getSuperClass()
     */
    public FullyQualifiedJavaType getSuperClass() {
        throw new UnsupportedOperationException(Messages.getString("RuntimeError.11")); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#isJavaInterface()
     */
    public boolean isJavaInterface() {
        return false;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#isJavaEnumeration()
     */
    public boolean isJavaEnumeration() {
        return true;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#addImportedType(com.xyz.generator.api.dom.java.FullyQualifiedJavaType)
     */
    public void addImportedType(FullyQualifiedJavaType importedType) {
        if (importedType.isExplicitlyImported()
                && !importedType.getPackageName().equals(
                        getType().getPackageName())) {
            importedTypes.add(importedType);
        }
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#addFileCommentLine(java.lang.String)
     */
    public void addFileCommentLine(String commentLine) {
        fileCommentLines.add(commentLine);
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#getFileCommentLines()
     */
    public List<String> getFileCommentLines() {
        return fileCommentLines;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#addImportedTypes(java.util.Set)
     */
    public void addImportedTypes(Set<FullyQualifiedJavaType> importedTypes) {
        this.importedTypes.addAll(importedTypes);
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#getStaticImports()
     */
    public Set<String> getStaticImports() {
        return staticImports;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#addStaticImport(java.lang.String)
     */
    public void addStaticImport(String staticImport) {
        staticImports.add(staticImport);
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#addStaticImports(java.util.Set)
     */
    public void addStaticImports(Set<String> staticImports) {
        this.staticImports.addAll(staticImports);
    }
}
