package com.heaven7.java.game.base.state;


import java.util.Arrays;

public final class Objects {
    private Objects() {
    }

    public static boolean equal( Object a, Object b) {
        return a == b || a != null && a.equals(b);
    }

    public static int hashCode( Object... objects) {
        return Arrays.hashCode(objects);
    }

    public static Objects.ToStringHelper toStringHelper(Object self) {
        return new Objects.ToStringHelper(simpleName(self.getClass()));
    }

    public static Objects.ToStringHelper toStringHelper(Class<?> clazz) {
        return new Objects.ToStringHelper(simpleName(clazz));
    }

    public static Objects.ToStringHelper toStringHelper(String className) {
        return new Objects.ToStringHelper(className);
    }

    private static String simpleName(Class<?> clazz) {
        String name = clazz.getName();
        name = name.replaceAll("\\$[0-9]+", "\\$");
        int start = name.lastIndexOf(36);
        if(start == -1) {
            start = name.lastIndexOf(46);
        }

        return name.substring(start + 1);
    }

    public static <T> T firstNonNull( T first,  T second) {
        if(first != null){
            return first;
        }
        if(second == null){
            throw new NullPointerException();
        }
        return second;
    }

    public static final class ToStringHelper {
        private final String className;
        private Objects.ToStringHelper.ValueHolder holderHead;
        private Objects.ToStringHelper.ValueHolder holderTail;
        private boolean omitNullValues;

        private ToStringHelper(String className) {
            if(className == null){
                throw new NullPointerException();
            }
            this.holderHead = new Objects.ToStringHelper.ValueHolder();
            this.holderTail = this.holderHead;
            this.omitNullValues = false;
            this.className = className;
        }

        public Objects.ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }

        public Objects.ToStringHelper add(String name, /*@Nullable*/ Object value) {
            return this.addHolder(name, value);
        }

        public Objects.ToStringHelper add(String name, boolean value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public Objects.ToStringHelper add(String name, char value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public Objects.ToStringHelper add(String name, double value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public Objects.ToStringHelper add(String name, float value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public Objects.ToStringHelper add(String name, int value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public Objects.ToStringHelper add(String name, long value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public Objects.ToStringHelper addValue(/*@Nullable*/ Object value) {
            return this.addHolder(value);
        }

        public Objects.ToStringHelper addValue(boolean value) {
            return this.addHolder(String.valueOf(value));
        }

        public Objects.ToStringHelper addValue(char value) {
            return this.addHolder(String.valueOf(value));
        }

        public Objects.ToStringHelper addValue(double value) {
            return this.addHolder(String.valueOf(value));
        }

        public Objects.ToStringHelper addValue(float value) {
            return this.addHolder(String.valueOf(value));
        }

        public Objects.ToStringHelper addValue(int value) {
            return this.addHolder(String.valueOf(value));
        }

        public Objects.ToStringHelper addValue(long value) {
            return this.addHolder(String.valueOf(value));
        }

        public String toString() {
            boolean omitNullValuesSnapshot = this.omitNullValues;
            String nextSeparator = "";
            StringBuilder builder = (new StringBuilder(32)).append(this.className).append('{');

            for(Objects.ToStringHelper.ValueHolder valueHolder = this.holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
                if(!omitNullValuesSnapshot || valueHolder.value != null) {
                    builder.append(nextSeparator);
                    nextSeparator = ", ";
                    if(valueHolder.name != null) {
                        builder.append(valueHolder.name).append('=');
                    }

                    builder.append(valueHolder.value);
                }
            }

            return builder.append('}').toString();
        }

        private Objects.ToStringHelper.ValueHolder addHolder() {
            Objects.ToStringHelper.ValueHolder valueHolder = new Objects.ToStringHelper.ValueHolder();
            this.holderTail = this.holderTail.next = valueHolder;
            return valueHolder;
        }

        private Objects.ToStringHelper addHolder(/*@Nullable*/ Object value) {
            Objects.ToStringHelper.ValueHolder valueHolder = this.addHolder();
            valueHolder.value = value;
            return this;
        }

        private Objects.ToStringHelper addHolder(String name, /*@Nullable*/ Object value) {
            if(name == null){
                throw new NullPointerException();
            }
            Objects.ToStringHelper.ValueHolder valueHolder = this.addHolder();
            valueHolder.value = value;
            valueHolder.name = name;
            return this;
        }

        private static final class ValueHolder {
            String name;
            Object value;
            Objects.ToStringHelper.ValueHolder next;

            private ValueHolder() {
            }
        }
    }
}
