package com.xyz.generator.api.dom.java;

/**
 * 类: PrimitiveTypeWrapper <br>
 * 描述: 基本类型的封装类<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年01月31日 17:03
 */
public class PrimitiveTypeWrapper extends FullyQualifiedJavaType {

    private static PrimitiveTypeWrapper booleanInstance;
    private static PrimitiveTypeWrapper byteInstance;
    private static PrimitiveTypeWrapper characterInstance;
    private static PrimitiveTypeWrapper doubleInstance;
    private static PrimitiveTypeWrapper floadInstance;
    private static PrimitiveTypeWrapper integerInstance;
    private static PrimitiveTypeWrapper longInstance;
    private static PrimitiveTypeWrapper shortInstance;

    private String toPrimitiveMethod;

    /**
     * 构造方法
     * @param fullyQualifiedName 类全名
     * @param toPrimitiveMethod  方法名称
     */
    public PrimitiveTypeWrapper(String fullyQualifiedName,String toPrimitiveMethod) {
        super(fullyQualifiedName);
        this.toPrimitiveMethod = toPrimitiveMethod;
    }

    public String getToPrimitiveMethod() {
        return toPrimitiveMethod;
    }

    /**
     * boolean
     * @return
     */
    public static PrimitiveTypeWrapper getBooleanInstance(){
        if (booleanInstance == null){
            booleanInstance = new PrimitiveTypeWrapper("java.lang.Boolean","booleanValue()");
        }
        return booleanInstance;
    }

    /**
     * byte
     * @return
     */
    public static PrimitiveTypeWrapper getByteInstance(){
        if (byteInstance == null){
            byteInstance = new PrimitiveTypeWrapper("java.lang.Byte","byteValue()");
        }
        return byteInstance;
    }


    /**
     * Character
     * @return
     */
    public static PrimitiveTypeWrapper getCharacterInstance(){
        if (characterInstance == null){
            characterInstance = new PrimitiveTypeWrapper("java.lang.Character","charValue()");
        }
        return characterInstance;
    }

    /**
     * double
     * @return
     */
    public static PrimitiveTypeWrapper getDoubleInstance() {
        if (doubleInstance == null){
            doubleInstance = new PrimitiveTypeWrapper("java.lang.Double","doubleValue()");
        }
        return doubleInstance;
    }

    /**
     * float
     * @return
     */
    public static PrimitiveTypeWrapper getFloadInstance() {
        if (floadInstance == null){
            floadInstance = new PrimitiveTypeWrapper("java.lang.Float","floatValue()");
        }
        return floadInstance;
    }

    /**
     * integer
     * @return
     */
    public static PrimitiveTypeWrapper getIntegerInstance() {
        if (integerInstance == null){
            integerInstance = new PrimitiveTypeWrapper("java.lang.Integer","intValue()");
        }
        return integerInstance;
    }

    /**
     * long
     * @return
     */
    public static PrimitiveTypeWrapper getLongInstance() {
        if (longInstance == null){
            longInstance = new PrimitiveTypeWrapper("java.lang.Long","longValue()");
        }
        return longInstance;
    }

    /**
     * short
     * @return
     */
    public static PrimitiveTypeWrapper getShortInstance() {
        if (shortInstance == null){
            shortInstance = new PrimitiveTypeWrapper("java.lang.Short","shortValue()");
        }
        return shortInstance;
    }
}
