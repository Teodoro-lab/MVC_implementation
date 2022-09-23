package abominableFramework;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ModelReflector {

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static Method getGetterMethod(Class cls, Field field) {
        Method getter = null;
        try {
            getter = cls.getDeclaredMethod("get" + capitalize(field.getName()));
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return getter;
    }

    private static String getModelInstanceAttribute(Method getter, Model order) {
        String result = null;
        try {
            result = String.valueOf(getter.invoke(order));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String[] getModelAttributes(Model model) {
        Class<?> cls = model.getClass();
        Field[] fields = cls.getDeclaredFields();
        String[] fieldNames = new String[fields.length];

        Field field;
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            field.setAccessible(true);
            fieldNames[i] = field.getName();
        }

        return fieldNames;
    }

    public static String[] getModelInstanceAttributes(Model model) {
        Class<?> cls = model.getClass();
        Field[] fields = cls.getDeclaredFields();
        String[] attrNames = new String[fields.length];

        Field field;
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            field.setAccessible(true);

            Method getter = getGetterMethod(cls, field);
            String attr = getModelInstanceAttribute(getter, model);

            attrNames[i] = attr;
        }

        return attrNames;
    }

    private static Class<Model> getModelTypeClass(String className) throws ClassNotFoundException {
        Class<Model> ModelExtendedClass;
        ModelExtendedClass = (Class<Model>) Class.forName(className);
        return ModelExtendedClass;
    }

    private static Constructor<Model> getModelTypeConstructor(Class<Model> objClass)
            throws NoSuchMethodException, SecurityException {
        Constructor<Model> constructor;
        constructor = objClass.getConstructor();
        return constructor;
    }

    private static Model getModelTypeInstance(Constructor<Model> constructor)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object object;
        object = constructor.newInstance();
        return (Model) object;
    }

    public static Model getModelInstance(String className)
            throws Exception {
        Model Model;

        try {
            Class<Model> objClass = getModelTypeClass(className);
            Constructor<Model> objConstructor = getModelTypeConstructor(objClass);
            Model = getModelTypeInstance(objConstructor);
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (NoSuchMethodException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

        return Model;
    }
}
