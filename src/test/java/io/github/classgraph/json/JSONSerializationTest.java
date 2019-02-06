package io.github.classgraph.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import nonapi.io.github.classgraph.json.JSONDeserializer;
import nonapi.io.github.classgraph.json.JSONSerializer;

/**
 * The Class JSONSerializationTest.
 */
@SuppressWarnings("unused")
public class JSONSerializationTest {

    /**
     * The Class A.
     *
     * @param <X>
     *            the generic type
     * @param <Y>
     *            the generic type
     */
    private static class A<X, Y> {

        /** The x. */
        X x;

        /** The y. */
        Y y;

        /**
         * Instantiates a new a.
         */
        public A() {
        }

        /**
         * Instantiates a new a.
         *
         * @param x
         *            the x
         * @param y
         *            the y
         */
        public A(final X x, final Y y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * The Class B.
     *
     * @param <V>
     *            the value type
     */
    private static class B<V> {

        /** The b. */
        V b;

        /**
         * Instantiates a new b.
         */
        public B() {
        }

        /**
         * Instantiates a new b.
         *
         * @param q
         *            the q
         */
        public B(final V q) {
            this.b = q;
        }
    }

    /**
     * The Class C.
     *
     * @param <T>
     *            the generic type
     */
    private static class C<T> extends B<T> {

        /** The a. */
        A<List<T>, String> a;

        /** The arr. */
        T[] arr;

        /**
         * Instantiates a new c.
         */
        public C() {
        }

        /**
         * Instantiates a new c.
         *
         * @param t
         *            the t
         */
        public C(final T t) {
            super(t);
            final List<T> tList = new ArrayList<>();
            tList.add(t);
            a = new A<>(tList, "x");
            final List<T> ts = new ArrayList<>();
            ts.add(t);
            ts.add(t);
            ts.add(t);
            @SuppressWarnings("unchecked")
            final T[] a = (T[]) ts.toArray();
            arr = a;
        }
    }

    /**
     * The Class D.
     *
     * @param <T>
     *            the generic type
     */
    private static class D<T> {

        /** The z. */
        T z;

        /** The q. */
        C<T> q;

        /** The map. */
        Map<T, T> map = new HashMap<>();

        /** The list. */
        List<T> list;

        /**
         * Instantiates a new d.
         */
        public D() {
        }

        /**
         * Instantiates a new d.
         *
         * @param obj
         *            the obj
         */
        public D(final T obj) {
            z = obj;
            q = new C<>(obj);
            map.put(obj, obj);
            list = new ArrayList<>();
            list.add(obj);
            list.add(obj);
            list.add(obj);
        }
    }

    /**
     * The Class E.
     */
    private static class E extends D<Short> {

        /** The c. */
        C<Integer> c = new C<>(5);

        /** The z. */
        int z = 42;

        /**
         * Instantiates a new e.
         */
        public E() {
        }

        /**
         * Instantiates a new e.
         *
         * @param obj
         *            the obj
         */
        public E(final Short obj) {
            super(obj);
        }
    }

    /**
     * The Class F.
     */
    private static class F extends D<Float> {

        /** The wxy. */
        int wxy = 123;

        /**
         * Instantiates a new f.
         */
        public F() {
        }

        /**
         * Instantiates a new f.
         *
         * @param f
         *            the f
         */
        public F(final Float f) {
            super(f);
        }
    }

    /**
     * The Class G.
     */
    private static class G {

        /** The e. */
        E e = new E((short) 3);

        /** The f. */
        F f = new F(1.5f);
    }

    /**
     * The Class H.
     */
    private static class H {

        /** The g. */
        G g;
    }

    /**
     * Test JSON.
     */
    @Test
    public void testJSON() {
        final H h = new H();
        h.g = new G();

        final String json0 = JSONSerializer.serializeFromField(h, "g", 0, false);

        final String expected = //
                "{\"e\":{\"q\":{\"b\":3,\"a\":{\"x\":[3],\"y\":\"x\"},\"arr\":[3,3,3]},\"map\":{\"3\":3},"
                        + "\"list\":[3,3,3],\"c\":{\"b\":5,\"a\":{\"x\":[5],\"y\":\"x\"},\"arr\":[5,5,5]},"
                        + "\"z\":42},\"f\":{\"z\":1.5,\"q\":{\"b\":1.5,\"a\":{\"x\":[1.5],\"y\":\"x\"},"
                        + "\"arr\":[1.5,1.5,1.5]},\"map\":{\"1.5\":1.5},\"list\":[1.5,1.5,1.5],\"wxy\":123}}";

        assertThat(json0).isEqualTo(expected);

        final G obj = JSONDeserializer.deserializeObject(G.class, json0);

        final String json1 = JSONSerializer.serializeObject(obj, 0, false);

        assertThat(json0).isEqualTo(json1);
    }

    /**
     * Test serialize then deserialize scan result.
     */
    @Test
    public void testSerializeThenDeserializeScanResult() {
        try (ScanResult scanResult = new ClassGraph()
                .whitelistPackagesNonRecursive(JSONSerializationTest.class.getPackage().getName())
                .ignoreClassVisibility().scan()) {
            final int indent = 2;
            final String scanResultJSON = scanResult.toJSON(indent);
            final ScanResult scanResultDeserialized = ScanResult.fromJSON(scanResultJSON);
            final String scanResultReserializedJSON = scanResultDeserialized.toJSON(indent);
            assertThat(scanResultReserializedJSON).isEqualTo(scanResultJSON);
        }
    }
}
