package com.niceflow.component.spring.plus.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.niceflow.component.common.exception.ErrorCode;
import com.niceflow.component.spring.plus.enums.CommonErrorCodeEnum;
import com.niceflow.component.spring.plus.exception.AssertException;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 断言
 * <p>
 * 抛出 AssertException 异常
 *
 * @author duanjw
 * @date 2023/2/16
 */
@UtilityClass
public class Assert {

    /**
     * 参数
     *
     * @param expression
     */
    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new AssertException(CommonErrorCodeEnum.ARGS_VALUE_IS_TRUE);
        }
    }

    public static void isTrue(boolean expression, Supplier<ErrorCode> messageSupplier) {
        if (!expression) {
            throw new AssertException(nullSafeGet(messageSupplier));
        }
    }

    public static void isTrue(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new AssertException(errorCode);
        }
    }

    public static void isTrue(boolean expression, ErrorCode errorCode, String[] args) {
        if (!expression) {
            throw new AssertException(errorCode, args);
        }
    }

    /**
     * 参数
     *
     * @param expression
     */
    public static void isFalse(boolean expression) {
        if (expression) {
            throw new AssertException(CommonErrorCodeEnum.ARGS_VALUE_IS_FALSE);
        }
    }

    public static void isFalse(boolean expression, Supplier<ErrorCode> messageSupplier) {
        if (expression) {
            throw new AssertException(nullSafeGet(messageSupplier));
        }
    }

    public static void isFalse(boolean expression, ErrorCode errorCode) {
        if (expression) {
            throw new AssertException(errorCode);
        }
    }

    public static void isFalse(boolean expression, ErrorCode errorCode, String[] args) {
        if (expression) {
            throw new AssertException(errorCode, args);
        }
    }

    public static void isNull(@Nullable Object object, Supplier<ErrorCode> messageSupplier) {
        if (object != null) {
            throw new AssertException(nullSafeGet(messageSupplier));
        }
    }

    public static void isNull(@Nullable Object object, ErrorCode errorCode) {
        if (object != null) {
            throw new AssertException(errorCode);
        }
    }

    public static void isNull(@Nullable Object object, ErrorCode errorCode, String[] args) {
        if (object != null) {
            throw new AssertException(errorCode, args);
        }
    }

    public static void isNull(@Nullable Object object) {
        isNull(object, CommonErrorCodeEnum.ARGS_VALUE_IS_NULL);
    }

    public static void notNull(@Nullable Object object, Supplier<ErrorCode> messageSupplier) {
        if (object == null) {
            throw new AssertException(nullSafeGet(messageSupplier));
        }
    }

    public static void notNull(@Nullable Object object, ErrorCode errorCode) {
        if (object == null) {
            throw new AssertException(errorCode);
        }
    }

    public static void notNull(@Nullable Object object, ErrorCode errorCode, String[] args) {
        if (object == null) {
            throw new AssertException(errorCode, args);
        }
    }

    public static void notNull(@Nullable Object object) {
        notNull(object, CommonErrorCodeEnum.ARGS_VALUE_IS_NOT_NULL);
    }

    /**
     * 参数的值不为 null 且必须至少包含一个非空格的字符
     *
     * @param text
     */
    public static void hasLength(@Nullable String text) {
        if (!StringUtils.hasLength(text)) {
            throw new AssertException(CommonErrorCodeEnum.ARGS_VALUE_HAS_LENGTH);
        }
    }

    public static void hasLength(@Nullable String text, Supplier<ErrorCode> messageSupplier) {
        if (!StringUtils.hasLength(text)) {
            throw new AssertException(nullSafeGet(messageSupplier));
        }
    }

    public static void hasLength(@Nullable String text, ErrorCode errorCode) {
        if (!StringUtils.hasLength(text)) {
            throw new AssertException(errorCode);
        }
    }

    public static void hasLength(@Nullable String text, ErrorCode errorCode, String[] args) {
        if (!StringUtils.hasLength(text)) {
            throw new AssertException(errorCode, args);
        }
    }

    public static void hasText(@Nullable String text) {
        if (!StringUtils.hasText(text)) {
            throw new AssertException(CommonErrorCodeEnum.ARGS_VALUE_HAS_TEXT);
        }
    }

    public static void hasText(@Nullable String text, Supplier<ErrorCode> messageSupplier) {
        if (!StringUtils.hasText(text)) {
            throw new AssertException(nullSafeGet(messageSupplier));
        }
    }

    public static void hasText(@Nullable String text, ErrorCode errorCode) {
        if (!StringUtils.hasText(text)) {
            throw new AssertException(errorCode);
        }
    }

    public static void hasText(@Nullable String text, ErrorCode errorCode, String[] args) {
        if (!StringUtils.hasText(text)) {
            throw new AssertException(errorCode, args);
        }
    }

    public static void doesNotContain(@Nullable String textToSearch, String substring) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new AssertException(CommonErrorCodeEnum.ARGS_VALUE_DOES_NOT_CONTAIN);
        }
    }

    public static void doesNotContain(@Nullable String textToSearch, String substring, Supplier<ErrorCode> messageSupplier) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new AssertException(nullSafeGet(messageSupplier));
        }
    }

    public static void doesNotContain(@Nullable String textToSearch, String substring, ErrorCode errorCode) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new AssertException(errorCode);
        }
    }

    public static void doesNotContain(@Nullable String textToSearch, String substring, ErrorCode errorCode, String[] args) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new AssertException(errorCode, args);
        }
    }

    public static void notEmpty(@Nullable Object[] array) {
        if (ObjectUtils.isEmpty(array)) {
            throw new AssertException(CommonErrorCodeEnum.ARGS_VALUE_NOT_EMPTY);
        }
    }

    public static void notEmpty(@Nullable Object[] array, Supplier<ErrorCode> messageSupplier) {
        if (ObjectUtils.isEmpty(array)) {
            throw new AssertException(nullSafeGet(messageSupplier));
        }
    }

    public static void notEmpty(@Nullable Object[] array, ErrorCode errorCode) {
        if (ObjectUtils.isEmpty(array)) {
            throw new AssertException(errorCode);
        }
    }

    public static void notEmpty(@Nullable Object[] array, ErrorCode errorCode, String[] args) {
        if (ObjectUtils.isEmpty(array)) {
            throw new AssertException(errorCode, args);
        }
    }

    public static void noNullElements(@Nullable Object[] array) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new AssertException(CommonErrorCodeEnum.ARGS_VALUE_NO_NULL_ELEMENTS);
                }
            }
        }

    }

    public static void noNullElements(@Nullable Object[] array, Supplier<ErrorCode> messageSupplier) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new AssertException(nullSafeGet(messageSupplier));
                }
            }
        }

    }

    public static void noNullElements(@Nullable Object[] array, ErrorCode errorCode) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new AssertException(errorCode);
                }
            }
        }
    }

    public static void noNullElements(@Nullable Object[] array, ErrorCode errorCode, String[] args) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new AssertException(errorCode, args);
                }
            }
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new AssertException(CommonErrorCodeEnum.ARGS_VALUE_NOT_EMPTY);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, Supplier<ErrorCode> messageSupplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new AssertException(nullSafeGet(messageSupplier));
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, ErrorCode errorCode) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new AssertException(errorCode);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, ErrorCode errorCode, String[] args) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new AssertException(errorCode, args);
        }
    }

    public static void noNullElements(@Nullable Collection<?> collection) {
        if (collection != null) {
            Iterator var2 = collection.iterator();

            while (var2.hasNext()) {
                Object element = var2.next();
                if (element == null) {
                    throw new AssertException(CommonErrorCodeEnum.ARGS_VALUE_NO_NULL_ELEMENTS);
                }
            }
        }

    }

    public static void noNullElements(@Nullable Collection<?> collection, Supplier<ErrorCode> messageSupplier) {
        if (collection != null) {
            Iterator var2 = collection.iterator();

            while (var2.hasNext()) {
                Object element = var2.next();
                if (element == null) {
                    throw new AssertException(nullSafeGet(messageSupplier));
                }
            }
        }

    }

    public static void noNullElements(@Nullable Collection<?> collection, ErrorCode errorCode) {
        if (collection != null) {
            Iterator var2 = collection.iterator();

            while (var2.hasNext()) {
                Object element = var2.next();
                if (element == null) {
                    throw new AssertException(errorCode);
                }
            }
        }
    }

    public static void noNullElements(@Nullable Collection<?> collection, ErrorCode errorCode, String[] args) {
        if (collection != null) {
            Iterator var2 = collection.iterator();

            while (var2.hasNext()) {
                Object element = var2.next();
                if (element == null) {
                    throw new AssertException(errorCode, args);
                }
            }
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map) {
        if (CollectionUtils.isEmpty(map)) {
            throw new AssertException(CommonErrorCodeEnum.ARGS_VALUE_NOT_EMPTY);
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, Supplier<ErrorCode> messageSupplier) {
        if (CollectionUtils.isEmpty(map)) {
            throw new AssertException(nullSafeGet(messageSupplier));
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, ErrorCode errorCode) {
        if (CollectionUtils.isEmpty(map)) {
            throw new AssertException(errorCode);
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, ErrorCode errorCode, String[] args) {
        if (CollectionUtils.isEmpty(map)) {
            throw new AssertException(errorCode, args);
        }
    }

    //
    //    public static void isInstanceOf(Class<?> type, @Nullable Object obj, String message) {
    //        notNull(type, (String) "Type to check against must not be null");
    //        if (!type.isInstance(obj)) {
    //            instanceCheckFailed(type, obj, message);
    //        }
    //
    //    }
    //
    //    public static void isInstanceOf(Class<?> type, @Nullable Object obj, Supplier<ErrorCode> messageSupplier) {
    //        notNull(type, (String) "Type to check against must not be null");
    //        if (!type.isInstance(obj)) {
    //            instanceCheckFailed(type, obj, nullSafeGet(messageSupplier));
    //        }
    //
    //    }
    //
    //    public static void isInstanceOf(Class<?> type, @Nullable Object obj, ErrorCode errorCode) {
    //        notNull(type, (String) "Type to check against must not be null");
    //        if (!type.isInstance(obj)) {
    //            throw new AssertException(errorCode);
    //        }
    //    }
    //
    //    public static void isInstanceOf(Class<?> type, @Nullable Object obj, ErrorCode errorCode, String[] args) {
    //        notNull(type, (String) "Type to check against must not be null");
    //        if (!type.isInstance(obj)) {
    //            throw new AssertException(errorCode, args);
    //        }
    //    }
    //
    //    public static void isInstanceOf(Class<?> type, @Nullable Object obj) {
    //        isInstanceOf(type, obj, "");
    //    }
    //
    //    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, String message) {
    //        notNull(superType, (String) "Supertype to check against must not be null");
    //        if (subType == null || !superType.isAssignableFrom(subType)) {
    //            assignableCheckFailed(superType, subType, message);
    //        }
    //
    //    }
    //
    //    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, Supplier<ErrorCode> messageSupplier) {
    //        notNull(superType, (String) "Supertype to check against must not be null");
    //        if (subType == null || !superType.isAssignableFrom(subType)) {
    //            assignableCheckFailed(superType, subType, nullSafeGet(messageSupplier));
    //        }
    //
    //    }
    //
    //    // todo
    //
    //    public static void isAssignable(Class<?> superType, Class<?> subType) {
    //        isAssignable(superType, subType, "");
    //    }
    //
    //    private static void instanceCheckFailed(Class<?> type, @Nullable Object obj, @Nullable String msg) {
    //        String className = obj != null ? obj.getClass().getName() : "null";
    //        String result = "";
    //        boolean defaultMessage = true;
    //        if (StringUtils.hasLength(msg)) {
    //            if (endsWithSeparator(msg)) {
    //                result = msg + " ";
    //            } else {
    //                result = messageWithTypeName(msg, className);
    //                defaultMessage = false;
    //            }
    //        }
    //
    //        if (defaultMessage) {
    //            result = result + "Object of class [" + className + "] must be an instance of " + type;
    //        }
    //
    //        throw new IllegalArgumentException(result);
    //    }
    //
    //    private static void assignableCheckFailed(Class<?> superType, @Nullable Class<?> subType, @Nullable String msg) {
    //        String result = "";
    //        boolean defaultMessage = true;
    //        if (StringUtils.hasLength(msg)) {
    //            if (endsWithSeparator(msg)) {
    //                result = msg + " ";
    //            } else {
    //                result = messageWithTypeName(msg, subType);
    //                defaultMessage = false;
    //            }
    //        }
    //
    //        if (defaultMessage) {
    //            result = result + subType + " is not assignable to " + superType;
    //        }
    //
    //        throw new IllegalArgumentException(result);
    //    }
    //
    //    private static boolean endsWithSeparator(String msg) {
    //        return msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith(".");
    //    }
    //
    //    private static String messageWithTypeName(String msg, @Nullable Object typeName) {
    //        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    //    }

    @Nullable
    private static ErrorCode nullSafeGet(@Nullable Supplier<ErrorCode> messageSupplier) {
        return messageSupplier != null ? messageSupplier.get() : null;
    }


    /**
     * 断言两个对象是否不相等,如果两个对象相等 抛出IllegalArgumentException 异常
     * <pre class="code">
     *   Assert.notEquals(obj1,obj2);
     * </pre>
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @throws IllegalArgumentException obj1 must be not equals obj2
     */
    public static void notEquals(Object obj1, Object obj2) {
        notEquals(obj1, obj2, CommonErrorCodeEnum.ARGS_VALUE_MUST_NOT_EQUALS, obj1, obj2);
    }

    /**
     * 断言两个对象是否不相等,如果两个对象相等 抛出IllegalArgumentException 异常
     * <pre class="code">
     *   Assert.notEquals(obj1,obj2,"obj1 must be not equals obj2");
     * </pre>
     *
     * @param obj1             对象1
     * @param obj2             对象2
     * @param params           异常信息参数，用于替换"{}"占位符
     * @throws IllegalArgumentException obj1 must be not equals obj2
     */
    public static void notEquals(Object obj1, Object obj2, ErrorCode errorCode, Object... params) throws IllegalArgumentException {
        notEquals(obj1, obj2, () -> new AssertException(errorCode, params));
    }

    /**
     * 断言两个对象是否不相等,如果两个对象相等,抛出指定类型异常,并使用指定的函数获取错误信息返回
     *
     * @param obj1          对象1
     * @param obj2          对象2
     * @param errorSupplier 错误抛出异常附带的消息生产接口
     * @param <X>           异常类型
     * @throws X obj1 must be not equals obj2
     */
    public static <X extends Throwable> void notEquals(Object obj1, Object obj2, Supplier<X> errorSupplier) throws X {
        if (ObjectUtil.equals(obj1, obj2)) {
            throw errorSupplier.get();
        }
    }
    // ----------------------------------------------------------------------------------------------------------- Check not equals

    /**
     * 断言两个对象是否相等,如果两个对象不相等 抛出IllegalArgumentException 异常
     * <pre class="code">
     *   Assert.isEquals(obj1,obj2);
     * </pre>
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @throws IllegalArgumentException obj1 must be equals obj2
     */
    public static void equals(Object obj1, Object obj2) {
        equals(obj1, obj2, CommonErrorCodeEnum.ARGS_VALUE_MUST_EQUALS, obj1, obj2);
    }

    /**
     * 断言两个对象是否相等,如果两个对象不相等 抛出IllegalArgumentException 异常
     * <pre class="code">
     *   Assert.isEquals(obj1,obj2,"obj1 must be equals obj2");
     * </pre>
     *
     * @param obj1             对象1
     * @param obj2             对象2
     * @param params           异常信息参数，用于替换"{}"占位符
     * @throws IllegalArgumentException obj1 must be equals obj2
     */
    public static void equals(Object obj1, Object obj2, ErrorCode errorCode, Object... params) throws IllegalArgumentException {
        equals(obj1, obj2, () -> new AssertException(errorCode, params));
    }

    /**
     * 断言两个对象是否相等,如果两个对象不相等,抛出指定类型异常,并使用指定的函数获取错误信息返回
     *
     * @param obj1          对象1
     * @param obj2          对象2
     * @param errorSupplier 错误抛出异常附带的消息生产接口
     * @param <X>           异常类型
     * @throws X obj1 must be equals obj2
     */
    public static <X extends Throwable> void equals(Object obj1, Object obj2, Supplier<X> errorSupplier) throws X {
        if (ObjectUtil.notEqual(obj1, obj2)) {
            throw errorSupplier.get();
        }
    }
}
