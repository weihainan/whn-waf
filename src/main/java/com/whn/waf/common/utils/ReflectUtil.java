package com.whn.waf.common.utils;

import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.*;
import java.util.Map;

/**
 * 反射相关帮助类
 */
public class ReflectUtil {

    private ReflectUtil() {
    }

    /**
     * 获取类的泛型参数
     *
     * @param clazz 目标类
     * @return 类的泛型参数
     */
    @SuppressWarnings("rawtypes")
    public static Type[] getGenericParameter(Class clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            return ((ParameterizedType) genericSuperclass)
                    .getActualTypeArguments();
        }
        return new Type[]{};
    }

    public static String getMethodSignature(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }

    public static String getMethodSignature(HandlerMethod method) {
        return method.getBeanType().getName() + "."
                + method.getMethod().getName();
    }

    /**
     * convert a map(fieldName,value) to a type T entity
     *
     * @param clazz the result bean type that we needs
     * @param map   the map contains field name and value
     * @return the T type entity
     */
    public static <T> T getBean(Class<T> clazz, Map<String, Object> map) {

        T entity = null;

        if (map.size() > 0) {

            try {
                entity = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                setFieldValue(entity, entry.getKey(), entry.getValue());
            }
        }
        return entity;
    }

    /**
     * make a field can be read directly
     *
     * @param field the field we we want read directly
     */
    public static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * set the value of a field in an object directly,<br/>
     * ignoring private or protected, not using method setXxx()
     *
     * @param object    the target object
     * @param fieldName the target field
     * @param value     the value of field we want to set
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException
                    ("Could not find field [" + fieldName + "] on target [" + object + "]");
        }

        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();// there never throw an exception
        }
    }

    /**
     * get the value of a field in an object directly,<br/>
     * ignoring private or protected, not using method getXxx()
     *
     * @param object    the target object
     * @param fieldName the target field
     * @return the value of field
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);

        Object result = null;

        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();// there never throw an exception
        }
        return result;
    }

    /**
     * get the  DeclaredField of an object
     * whatever the field defined in this class or supper class
     *
     * @param object    the target object
     * @param filedName the target field
     * @return the field
     */
    public static Field getDeclaredField(Object object, String filedName) {

        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(filedName);
            } catch (NoSuchFieldException e) {
                //Field does not defined in this level class, continue finding in the super class
            }
        }
        return null;
    }

    /**
     * get the DeclaredMethod of an object
     * whatever the method defined in this class or supper class
     *
     * @param object         the target object
     * @param methodName     the target method
     * @param parameterTypes the type array of parameters
     * @return
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {

        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                //method does not defined in this level class, continue finding in the super class
            }
        }

        return null;
    }

    /**
     * execute a method of an object directly ignoring private, protected
     *
     * @param object         the target object
     * @param methodName     the target method we want to ececute
     * @param parameterTypes the type array of parameters
     * @param parameters     the value array of parameters
     * @return the result after executing the method
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,
                                      Object[] parameters) throws InvocationTargetException {

        Method method = getDeclaredMethod(object, methodName, parameterTypes);

        if (method == null) {
            throw new IllegalArgumentException
                    ("Could not find method [" + methodName + "] on target [" + object + "]");
        }

        method.setAccessible(true);

        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();// there never throw an exception
        }
        return null;
    }

    /**
     * get the supper class's class type of T
     * like: public EmployeeDao extends BaseDao<Employee, String>
     *
     * @param clazz the class
     * @param index the index of type
     * @return the type class
     */
    @SuppressWarnings("rawtypes")
    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }

        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * get the supper class's class type of T
     * like: public EmployeeDao extends BaseDao<Employee, String>
     *
     * @param <T>
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Class<T> getSuperGenericType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }
}
