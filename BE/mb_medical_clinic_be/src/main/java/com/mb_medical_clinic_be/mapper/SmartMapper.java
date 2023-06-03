package com.mb_medical_clinic_be.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class SmartMapper {

    private static final String flagSuffix = "SetFromInput";

    public static void transferData(Object source, Object target) {
        Field[] sourceFlagFields = Arrays.stream(source.getClass().getDeclaredFields())
                .filter(field -> field.getName().endsWith(flagSuffix)).toArray(size -> new Field[size]);
        for (Field flag : sourceFlagFields) {
            try {
                flag.setAccessible(true);
                if (flag.getBoolean(source)) {
                    String fieldName = getRefFieldName(flag.getName());
                    Field field = source.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();
                    String mutatorName = getMutatorName(fieldName);
                    Method mutator = target.getClass().getDeclaredMethod(mutatorName, fieldType);
                    mutator.invoke(target, field.get(source));
                }
            } catch (IllegalAccessException | NoSuchFieldException | InvocationTargetException e) {
                e.printStackTrace();

            } catch (NoSuchMethodException e) {
                /* */
            }
        }
    }

    private static String getRefFieldName(String flagFieldName) {
        return flagFieldName.substring(0, flagFieldName.length() - flagSuffix.length());
    }

    private static String getMutatorName(String refFieldName) {
        return "set" + capitalizeName(refFieldName);
    }

    private static String capitalizeName(String name) {
        String result;
        if (!Character.isUpperCase(name.charAt(1)) && !Character.isDigit(name.charAt(1))) {
            result = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        } else {
            result = name;
        }

        return result;
    }
}
