package com.xyz.generator.api.dom.java;

import com.xyz.generator.api.dom.OutputUtilities;
import com.xyz.generator.internal.util.StringUtility;

import java.util.*;

/**
 * 类: Interface <br>
 * 描述: 接口<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月02日 11:57
 */
public class Interface extends JavaElement implements CompilationUnit{

    private Set<FullyQualifiedJavaType> importedTypes;

    private Set<String> staticImports;

    private FullyQualifiedJavaType type;

    private Set<FullyQualifiedJavaType> superInterfaceTypes;

    private List<Method> methods;

    private List<String> fileCommentLines;

    public Interface(FullyQualifiedJavaType type) {
        super();
        this.type = type;
        superInterfaceTypes = new LinkedHashSet<FullyQualifiedJavaType>();
        methods = new ArrayList<Method>();
        importedTypes = new TreeSet<FullyQualifiedJavaType>();
        fileCommentLines = new ArrayList<String>();
        staticImports = new TreeSet<String>();
    }

    public Interface(String type) {
        this(new FullyQualifiedJavaType(type));
    }

    public Set<FullyQualifiedJavaType> getImportedTypes() {
        return Collections.unmodifiableSet(importedTypes);
    }


    public void addImportedType(FullyQualifiedJavaType importedType) {
        if (importedType.isExplicitlyImported()
                && !importedType.getPackageName().equals(type.getPackageName())) {
            importedTypes.add(importedType);
        }
    }

    public String getFormattedContent() {
        StringBuilder sb = new StringBuilder();

        for (String commentLine : fileCommentLines) {
            sb.append(commentLine);
            OutputUtilities.newLine(sb);
        }

        if (StringUtility.stringHasValue(getType().getPackageName())) {
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

        Set<String> importStrings = StringUtility.calculateImports(importedTypes);
        for (String importString : importStrings) {
            sb.append(importString);
            OutputUtilities.newLine(sb);
        }

        if (importStrings.size() > 0) {
            OutputUtilities.newLine(sb);
        }

        int indentLevel = 0;

        addFormattedJavadoc(sb, indentLevel);
        addFormattedAnnotations(sb, indentLevel);

        sb.append(getVisibility().getValue());

        if (isStatic()) {
            sb.append("static "); //$NON-NLS-1$
        }

        if (isFinal()) {
            sb.append("final "); //$NON-NLS-1$
        }

        sb.append("interface "); //$NON-NLS-1$
        sb.append(getType().getShortName());

        if (getSuperInterfaceTypes().size() > 0) {
            sb.append(" extends "); //$NON-NLS-1$

            boolean comma = false;
            for (FullyQualifiedJavaType fqjt : getSuperInterfaceTypes()) {
                if (comma) {
                    sb.append(", "); //$NON-NLS-1$
                } else {
                    comma = true;
                }

                sb.append(fqjt.getShortName());
            }
        }

        sb.append(" {"); //$NON-NLS-1$
        indentLevel++;

        Iterator<Method> mtdIter = getMethods().iterator();
        while (mtdIter.hasNext()) {
            OutputUtilities.newLine(sb);
            Method method = mtdIter.next();
            sb.append(method.getFormattedContent(indentLevel, true));
            if (mtdIter.hasNext()) {
                OutputUtilities.newLine(sb);
            }
        }

        indentLevel--;
        OutputUtilities.newLine(sb);
        OutputUtilities.javaIndent(sb, indentLevel);
        sb.append('}');

        return sb.toString();
    }

    public void addSuperInterface(FullyQualifiedJavaType superInterface) {
        superInterfaceTypes.add(superInterface);
    }
    public List<Method> getMethods() {
        return methods;
    }

    public void addMethod(Method method) {
        methods.add(method);
    }

    public FullyQualifiedJavaType getType() {
        return type;
    }

    public FullyQualifiedJavaType getSuperClass() {
        // interfaces do not have superclasses
        return null;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#getSuperInterfaceTypes()
     */
    public Set<FullyQualifiedJavaType> getSuperInterfaceTypes() {
        return superInterfaceTypes;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#isJavaInterface()
     */
    public boolean isJavaInterface() {
        return true;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#isJavaEnumeration()
     */
    public boolean isJavaEnumeration() {
        return false;
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

    public Set<String> getStaticImports() {
        return staticImports;
    }

    public void addStaticImport(String staticImport) {
        staticImports.add(staticImport);
    }

    public void addStaticImports(Set<String> staticImports) {
        this.staticImports.addAll(staticImports);
    }




}
