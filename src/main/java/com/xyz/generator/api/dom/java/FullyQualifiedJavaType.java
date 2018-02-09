package com.xyz.generator.api.dom.java;

import com.xyz.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.xyz.generator.internal.util.messages.Messages.getString;

/**
 * 类: FullyQualifiedJavaType <br>
 * 描述: 标准的Java类型<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年01月31日 16:51
 */
public class FullyQualifiedJavaType implements Comparable<FullyQualifiedJavaType>{

    private static final String JAVA_LANG = "java.lang";

    private static FullyQualifiedJavaType intInstance = null;

    private static FullyQualifiedJavaType stringInstance = null;

    private static FullyQualifiedJavaType booleanPrimitiveInstance = null;

    private static FullyQualifiedJavaType objectInstance = null;

    private static FullyQualifiedJavaType dateInstance = null;

    private static FullyQualifiedJavaType criteriaInstance = null;

    private static FullyQualifiedJavaType generatedCriteriaInstance = null;

    //名称
    private String baseShortName;

    //基本类名
    private String baseQualifiedName;

    //是否是显式的引入
    private boolean explicitlyImported;

    //包名称
    private String packageName;

    //是否是原始类型
    private boolean primitive;

    //是否是数组
    private boolean isArray;

    //原始类型封装类
    private PrimitiveTypeWrapper primitiveTypeWrapper;

    //参数类型列表
    private List<FullyQualifiedJavaType> typeArguments;

    //是否是通用类
    private boolean wildcardType;

    //有界通配符
    private boolean boundedWildcard;

    private boolean extendsBoundedWildcard;

    /**
     * 构造方法
     * @param fullTypeSpecification 完整的类名称（包含包名称）
     */
    public FullyQualifiedJavaType(String fullTypeSpecification) {
        super();
        typeArguments = new ArrayList<FullyQualifiedJavaType>();
        parse(fullTypeSpecification);

    }

    public boolean isExplicitlyImported(){
        return explicitlyImported;
    }

    /**
     * 获取Java类型的名称
     * @return
     */
    public String getFullyQualifiedName(){
        //StringBuilder 非线程安全
        StringBuilder sb = new StringBuilder();
        if (wildcardType){
            sb.append('?');
            if (boundedWildcard){
                if (extendsBoundedWildcard){
                    sb.append(" extends ");
                }else{
                    sb.append(" super ");
                }

                sb.append(baseQualifiedName);
            }
        }else {
            sb.append(baseQualifiedName);
        }

        if (typeArguments.size()>0){
            boolean first = true;
            sb.append('<');
            for (FullyQualifiedJavaType fqjt : typeArguments){
                if (first){
                    first = false;
                }else{
                    sb.append(", ");
                }
                sb.append(fqjt.getFullyQualifiedName());
            }
            sb.append('>');
        }
        return sb.toString();
    }

    public String getFullyQualifiedNameWithoutTypeParameters(){
        return baseQualifiedName;
    }

    /**
     * 获取引入import
     * @return
     */
    public List<String> getImportList(){
        List<String> answer = new ArrayList<String>();
        if (isExplicitlyImported()){
            int index = baseShortName.indexOf('.');
            if (index == -1){
                answer.add(baseQualifiedName);
            }else{
                StringBuilder sb = new StringBuilder();
                sb.append(packageName);
                sb.append('.');
                sb.append(baseShortName.substring(0,index));
                answer.add(sb.toString());
            }
        }

        for (FullyQualifiedJavaType fullyQualifiedJavaType:typeArguments){
            answer.addAll(fullyQualifiedJavaType.getImportList());
        }

        return answer;

    }

    /**
     * 获取包名
     * @return
     */
    public String getPackageName() {
        return packageName;
    }

    public String getShortName(){
        StringBuilder sb = new StringBuilder();
        if (wildcardType){
            sb.append('?');
            if (boundedWildcard){
                if (extendsBoundedWildcard){
                    sb.append(" extends ");
                }else{
                    sb.append(" super ");
                }

                sb.append(baseShortName);
            }
        }else{
            sb.append(baseShortName);
        }

        if (typeArguments.size()>0){
            boolean first = true;
            sb.append('<');
            for (FullyQualifiedJavaType fqjt:typeArguments){
                if (first){
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(fqjt.getShortName());
            }
            sb.append('>');
        }

        return sb.toString();
    }

    /**
     * 覆盖equals方法
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(!(obj instanceof FullyQualifiedJavaType)){
            return false;
        }

        FullyQualifiedJavaType other = (FullyQualifiedJavaType)obj;

        return getFullyQualifiedName().equals(other.getFullyQualifiedName());
    }

    /**
     * 覆盖hashCode方法
     * @return
     */
    @Override
    public int hashCode() {
        return getFullyQualifiedName().hashCode();
    }

    /**
     * 覆盖toString方法
     * @return
     */
    @Override
    public String toString() {
        return getFullyQualifiedName();
    }

    public boolean isPrimitive(){
        return primitive;
    }

    public PrimitiveTypeWrapper getPrimitiveTypeWrapper() {
        return primitiveTypeWrapper;
    }

    /**
     * int
     * @return
     */
    public static final FullyQualifiedJavaType getIntInstance(){
        if (intInstance == null){
            intInstance = new FullyQualifiedJavaType("int");
        }

        return intInstance;
    }

    /**
     * Map
     * @return
     */
    public static final FullyQualifiedJavaType getNewMapInstance(){
        return new FullyQualifiedJavaType("java.util.Map");
    }

    /**
     * List
     * @return
     */
    public static final FullyQualifiedJavaType getNewListInstance(){
        return new FullyQualifiedJavaType("java.util.List");
    }
    /**
     * HashMap
     * @return
     */
    public static final FullyQualifiedJavaType getNewHashMapInstance(){
        return new FullyQualifiedJavaType("java.util.HashMap");
    }

    /**
     * ArrayList
     * @return
     */
    public static final FullyQualifiedJavaType getNewArrayListInstance(){
        return new FullyQualifiedJavaType("java.util.ArrayList");
    }

    /**
     * Iterator
     * @return
     */
    public static final FullyQualifiedJavaType getNewIteratorInstance(){
        return new FullyQualifiedJavaType("java.util.Iterator");
    }

    /**
     * String
     * @return
     */
    public static final FullyQualifiedJavaType getStringInstance(){
        if (stringInstance == null){
            stringInstance = new FullyQualifiedJavaType("java.lang.String");
        }
        return stringInstance;
    }

    /**
     * boolean
     * @return
     */
    public static final FullyQualifiedJavaType getBooleanPrimitiveInstance(){
        if (booleanPrimitiveInstance == null){
            booleanPrimitiveInstance = new FullyQualifiedJavaType("boolean");
        }
        return booleanPrimitiveInstance;
    }

    /**
     * Object
     * @return
     */
    public static final FullyQualifiedJavaType getObjectInstance(){
        if (objectInstance == null){
            objectInstance = new FullyQualifiedJavaType("java.lang.Object");
        }
        return objectInstance;
    }

    /**
     * Date
     * @return
     */
    public static final FullyQualifiedJavaType getDateInstance(){
        if (dateInstance == null){
            dateInstance = new FullyQualifiedJavaType("java.util.Date");
        }
        return dateInstance;
    }

    /**
     * Criteria
     * @return
     */
    public static final FullyQualifiedJavaType getCriteriaInstance(){
        if (criteriaInstance == null){
            criteriaInstance = new FullyQualifiedJavaType("Criteria");
        }

        return criteriaInstance;
    }

    /**
     * GeneratedCriteria
     * @return
     */
    public static final FullyQualifiedJavaType getGeneratedCriteriaInstance(){
        if (generatedCriteriaInstance == null){
            generatedCriteriaInstance = new FullyQualifiedJavaType("GeneratedCriteria");
        }

        return generatedCriteriaInstance;
    }

    public int compareTo(FullyQualifiedJavaType other){
        return getFullyQualifiedName().compareTo(other.getFullyQualifiedName());
    }

    public void addTypeArgument(FullyQualifiedJavaType type){
        typeArguments.add(type);
    }

    /**
     * 转换类名
     * @param fullTypeSpecification
     */
    public void parse(String fullTypeSpecification){
        String spec = fullTypeSpecification.trim();

        if (spec.startsWith("?")){
            wildcardType = true;
            spec = spec.substring(1).trim();
            if (spec.startsWith("extends ")){
                boundedWildcard = true;
                extendsBoundedWildcard = true;
                spec = spec.substring(8);
            }else if (spec.startsWith("super ")){
                boundedWildcard = true;
                extendsBoundedWildcard = false;
                spec = spec.substring(6);
            }else{
                boundedWildcard = true;
            }
            parse(spec);
        }else{
            int index = fullTypeSpecification.indexOf('<');
            if (index == -1){
                simpleParse(fullTypeSpecification);
            }else{
                simpleParse(fullTypeSpecification.substring(0,index));
                int endIndex = fullTypeSpecification.lastIndexOf('>');
                if (endIndex == -1){
                    throw new RuntimeException(getString("RuntimeError.22",fullTypeSpecification));
                }
                genericParse(fullTypeSpecification.substring(index, endIndex + 1));
            }
            isArray = fullTypeSpecification.endsWith("]");
        }
    }

    private void simpleParse(String typeSpecification){
        baseQualifiedName = typeSpecification.trim();
        if (baseQualifiedName.contains(".")){
            packageName = getPackage(baseQualifiedName);
            baseShortName = baseQualifiedName.substring(packageName.length()+1);
            int index = baseShortName.lastIndexOf('.');
            if (index != -1){
                baseShortName = baseShortName.substring(index+1);
            }
            if (JAVA_LANG.equals(packageName)){
                explicitlyImported = false;
            }else{
                explicitlyImported = true;
            }
        }else{
            baseShortName = baseQualifiedName;
            explicitlyImported = false;
            packageName = "";

            primitive = false;
            primitiveTypeWrapper = null;

            if ("byte".equals(baseQualifiedName)){
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getByteInstance();
            }

            if ("short".equals(baseQualifiedName)){
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getShortInstance();
            }


            if ("int".equals(baseQualifiedName)){
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getIntegerInstance();
            }

            if ("long".equals(baseQualifiedName)){
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getLongInstance();
            }

            if ("char".equals(baseQualifiedName)){
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getCharacterInstance();
            }

            if ("float".equals(baseQualifiedName)){
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getFloadInstance();
            }

            if ("double".equals(baseQualifiedName)){
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getDoubleInstance();
            }

            if ("boolean".equals(baseQualifiedName)){
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getBooleanInstance();
            }

        }
    }

    private void genericParse(String genericSpecification){
        int lastIndex = genericSpecification.lastIndexOf('>');
        if (lastIndex == -1){
            throw new RuntimeException(getString("RuntimeError.22",genericSpecification));
        }

        String argumentString = genericSpecification.substring(1,lastIndex);

        //JDK 中提供的分裂字符串的工具类似于split，却不同于split
        StringTokenizer st = new StringTokenizer(argumentString,"<>",true);
        int openCount = 0;
        StringBuilder sb = new StringBuilder();
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            if ("<".equals(token)){
                sb.append(token);
                openCount ++;
            }else if(">".equals(token)){
                sb.append(token);
                openCount--;
            }else if (",".equals(token)){
                if (openCount == 0){
                    typeArguments.add(new FullyQualifiedJavaType(sb.toString()));
                    sb.setLength(0);
                }else{
                    sb.append(token);
                }
            }else {
                sb.append(token);
            }
        }

        if (openCount != 0){
            throw new RuntimeException(getString("RuntimeError.22",genericSpecification));
        }

        String finalType = sb.toString();

        if (StringUtility.stringHasValue(finalType)){
            typeArguments.add(new FullyQualifiedJavaType(finalType));
        }

    }

    /**
     * 获取包名称
     * @param baseQualifiedName
     * @return
     */
    private static String getPackage(String baseQualifiedName){
        int index = baseQualifiedName.lastIndexOf('.');
        return baseQualifiedName.substring(0,index);
    }

    public boolean isArray(){
        return isArray;
    }
}
