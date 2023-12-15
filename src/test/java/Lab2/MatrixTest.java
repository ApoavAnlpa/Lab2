package Lab2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void testFillRandom() {
        Matrix matrix = new Matrix(6, 6);
        matrix.Random(-100.0, 100.0);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertTrue(matrix.getElement(i, j) >= -100.0 && matrix.getElement(i, j) <= 100.0);
            }
        }
    }

    @Test
    void testSetElement() {
        Matrix matrix = new Matrix(6, 6);
        matrix.setElement(2, 5, 32.456);
        assertEquals(32.456, matrix.getElement(2, 5));
    }

    @Test
    void testSetData() {
        Matrix matrix = new Matrix(3, 3);
        double[][] data = {{123.4, 234.5, 345.6}, {456.7, 567.8, 678.9}, {789.0, 890.1, 901.2}};
        matrix.setData(data);
        assertArrayEquals(data, matrix.getData());
    }
    @Test
    void testGetElement() {
        Matrix matrix = new Matrix(3, 3);
        double[][] data = {{123.4, 234.5, 345.6}, {456.7, 567.8, 678.9}, {789.0, 890.1, 901.2}};
        matrix.setData(data);
        assertEquals(567.8, matrix.getElement(1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.getElement(3, 3));
    }

    @Test
    void testGetRow() {
        Matrix matrix = new Matrix(new double[][] {{123.4, 234.5, 345.6}, {456.7, 567.8, 678.9}, {789.0, 890.1, 901.2}});
        assertArrayEquals(new double[]{456.7, 567.8, 678.9}, matrix.getRow(1));
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.getRow(3));
    }

    @Test
    void testGetCol() {
        Matrix matrix = new Matrix(new double[][] {{123.4, 234.5, 345.6}, {456.7, 567.8, 678.9}, {789.0, 890.1, 901.2}});
        assertArrayEquals(new double[]{234.5, 567.8, 890.1}, matrix.getCol(1));
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.getCol(3));
    }
    @Test
    void testGetSize() {
        Matrix matrix = new Matrix(6, 7);
        assertEquals("6x7", matrix.getDimensions());
    }

    @Test
    void testEqualsAndHashCode() {
        Matrix matrix1 = new Matrix(2, 3);
        Matrix matrix2 = new Matrix(2, 3);
        Matrix matrix3 = new Matrix(6, 6);

        assertTrue(matrix1.equals(matrix2) && matrix2.equals(matrix1));
        assertFalse(matrix1.equals(matrix3) || matrix3.equals(matrix1));
        assertEquals(matrix1.hashCode(), matrix2.hashCode());
        assertNotEquals(matrix1.hashCode(), matrix3.hashCode());

    }

    @Test
    void testImmutableMatrixFromData() {
        double[][] testData = {{123.4, 234.5, 345.6}, {456.7, 567.8, 678.9}};
        Immutable immutableMatrix = new Immutable(testData);

        testData[0][0] = 789.0;

        assertEquals(123.4, immutableMatrix.getElement(0, 0));
    }

    @Test
    void testImmutableMatrixFromMatrix() {
        double[][] testData = {{123.4, 234.5, 345.6}, {456.7, 567.8, 678.9}};

        Matrix matrix = new Matrix(2, 3);
        matrix.setData(testData);

        Immutable immutableMatrix = new Immutable(matrix);

        matrix.setElement(0, 0, 789.0);

        assertEquals(123.4, immutableMatrix.getElement(0, 0));


    }
    @Test
    void testAdd() {
        Matrix matrix1 = new Matrix(new double[][]{{123.4, 234.5}, {345.6, 456.7}});
        Matrix matrix2 = new Matrix(new double[][]{{567.8, 678.9}, {789.0, 890.1}});
        Matrix expected = new Matrix(new double[][]{{691.2, 913.4}, {1134.6, 1346.8}});
        Matrix result = matrix1.add(matrix2);
        double delta = 0.0001;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(expected.getElement(i, j), result.getElement(i, j), delta);
            }
        }
    }

    @Test
    void testMultiply() {
        Matrix matrix = new Matrix(new double[][]{{123.4, 234.5}, {345.6, 456.7}});
        double multiplier = 2.0;
        Matrix expected = new Matrix(new double[][]{{246.8, 469.0}, {691.2, 913.4}});
        Matrix result = matrix.multiply(multiplier);
        assertArrayEquals(expected.getData(), result.getData());
    }

    @Test
    void testAddWithIncompatibleSizes() {
        Matrix matrix1 = new Matrix(2, 2);
        Matrix matrix2 = new Matrix(3, 3);
        assertThrows(IllegalArgumentException.class, () -> matrix1.add(matrix2));
    }

    @Test
    void testImmutableAdd() {
        Immutable imMatrix1 = new Immutable(new double[][]{{123.4, 234.5}, {345.6, 456.7}});
        Immutable imMatrix2 = new Immutable(new double[][]{{567.8, 678.9}, {789.0, 890.1}});
        Immutable expected = new Immutable(new double[][]{{691.2, 913.4}, {1134.6, 1346.8}});
        Immutable result = imMatrix1.add(imMatrix2);

        double delta = 0.0001;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(expected.getElement(i, j), result.getElement(i, j), delta);
            }
        }
    }

    @Test
    void testImmutableMultiply() {
        Immutable imMatrix = new Immutable(new double[][]{{123.4, 234.5}, {345.6, 456.7}});
        double multiplier = 2.0;
        Immutable expected = new Immutable(new double[][]{{246.8, 469.0}, {691.2, 913.4}});
        Immutable result = imMatrix.multiply(multiplier);
        assertArrayEquals(expected.getData(), result.getData());
    }

    @Test
    void testImmutableAddWithIncompatibleSizes() {
        Immutable imMatrix1 = new Immutable(2, 2);
        Immutable imMatrix2 = new Immutable(3, 3);
        assertThrows(IllegalArgumentException.class, () -> imMatrix1.add(imMatrix2));
    }

    @Test
    void testMultiplyMatrices() {
        Matrix matrix1 = new Matrix(new double[][]{
                {1, 5},
                {2, 3},
                {1, 7}
        });

        Matrix matrix2 = new Matrix(new double[][]{
                {1, 2, 3, 7},
                {5, 2, 8, 1}
        });

        Matrix expected = new Matrix(new double[][]{
                {26, 12, 43, 12},
                {17, 10, 30, 17},
                {36, 16, 59, 14}
        });

        assertEquals(expected, matrix1.multiply(matrix2));
    }

    @Test
    void testMultiplyMatricesInvalidSize() {
        Matrix matrix1 = new Matrix(new double[][]{
                {26, 12},
                {17, 10}
        });

        Matrix matrix2 = new Matrix(new double[][]{
                {12},
                {17},
                {14}
        });

        assertThrows(IllegalArgumentException.class, () -> matrix1.multiply(matrix2));
    }

    @Test
    void testImmutableMultiplyMatrices() {
        Immutable matrix1 = new Immutable(new double[][]{
                {1, 5},
                {2, 3},
                {1, 7}
        });

        Immutable matrix2 = new Immutable(new double[][]{
                {1, 2, 3, 7},
                {5, 2, 8, 1}
        });

        Immutable expected = new Immutable(new double[][]{
                {26, 12, 43, 12},
                {17, 10, 30, 17},
                {36, 16, 59, 14}
        });

        assertEquals(expected, matrix1.multiply(matrix2));
    }

    @Test
    void testImmutableMultiplyMatricesInvalidSize() {
        Immutable matrix1 = new Immutable(new double[][]{
                {26, 12},
                {17, 10}
        });

        Immutable matrix2 = new Immutable(new double[][]{
                {12},
                {17},
                {14}
        });

        assertThrows(IllegalArgumentException.class, () -> matrix1.multiply(matrix2));
    }

    @Test
    void testTranspose() {
        Matrix original = new Matrix(new double[][]{
                {1, 2, 3, 7},
                {5, 2, 8, 1}

        });

        Matrix transposed = original.transpose();
        Matrix expected = new Matrix(new double[][]{
                {1, 5},
                {2, 2},
                {3, 8},
                {7, 1}
        });

        assertEquals(expected, transposed);
    }

    @Test
    void testImmutableTranspose() {
        Immutable original = new Immutable(new double[][]{
                {1, 2, 3, 7},
                {5, 2, 8, 1}

        });

        Immutable transposed = original.transpose();
        Immutable expected = new Immutable(new double[][]{
                {1, 5},
                {2, 2},
                {3, 8},
                {7, 1}
        });

        assertEquals(expected, transposed);
    }

    @Test
    void testMakeDiagonalMatrix() {
        double[] vector = {5, 2, 8};
        Matrix diagonalMatrix = Matrix.makeDiagonalMatrix(vector);

        for (int i = 0; i < vector.length; i++) {
            assertEquals(vector[i], diagonalMatrix.getElement(i, i));
        }

        for (int i = 0; i < diagonalMatrix.getRows(); i++) {
            for (int j = 0; j < diagonalMatrix.getCols(); j++) {
                if (i != j) {
                    assertEquals(0.0, diagonalMatrix.getElement(i, j));
                }
            }
        }
    }

    @Test
    void testImmutableMakeDiagonalMatrix() {
        double[] vector = {5, 2, 8};
        Immutable diagonalMatrix = Immutable.makeDiagonalMatrix(vector);

        for (int i = 0; i < vector.length; i++) {
            assertEquals(vector[i], diagonalMatrix.getElement(i, i));
        }

        for (int i = 0; i < diagonalMatrix.getRows(); i++) {
            for (int j = 0; j < diagonalMatrix.getCols(); j++) {
                if (i != j) {
                    assertEquals(0.0, diagonalMatrix.getElement(i, j));
                }
            }
        }
    }

    @Test
    void testMakeIdentityMatrix() {
        int size = 5;
        Matrix identityMatrix = Matrix.makeIdentityMatrix(size);

        for (int i = 0; i < size; i++) {
            assertEquals(1.0, identityMatrix.getElement(i, i));
        }

        for (int i = 0; i < identityMatrix.getRows(); i++) {
            for (int j = 0; j < identityMatrix.getCols(); j++) {
                if (i != j) {
                    assertEquals(0.0, identityMatrix.getElement(i, j));
                }
            }
        }
    }

    @Test
    void testImmutableMakeIdentityMatrix() {
        int size = 5;
        Immutable identityMatrix = Immutable.makeIdentityMatrix(size);

        for (int i = 0; i < size; i++) {
            assertEquals(1.0, identityMatrix.getElement(i, i));
        }

        for (int i = 0; i < identityMatrix.getRows(); i++) {
            for (int j = 0; j < identityMatrix.getCols(); j++) {
                if (i != j) {
                    assertEquals(0.0, identityMatrix.getElement(i, j));
                }
            }
        }
    }
    @Test
    void testMakeRandomRowMatrix() {
        Matrix randomRowMatrix = Matrix.makeRandomRowMatrix(5, -10.0, 10.0);

        assertEquals(5, randomRowMatrix.getCols());
        assertEquals(1, randomRowMatrix.getRows());

        for (int j = 0; j < 5; j++) {
            assertTrue(randomRowMatrix.getElement(0, j) >= -10.0 && randomRowMatrix.getElement(0, j) <= 10.0);
        }

    }

    @Test
    void testImmutableMakeRandomRowMatrix() {
        Immutable randomRowMatrix = Immutable.makeRandomRowMatrix(5, -10.0, 10.0);

        assertEquals(5, randomRowMatrix.getCols());
        assertEquals(1, randomRowMatrix.getRows());

        for (int j = 0; j < 5; j++) {
            assertTrue(randomRowMatrix.getElement(0, j) >= -10.0 && randomRowMatrix.getElement(0, j) <= 10.0);
        }

    }

    @Test
    void testMakeRandomColumnMatrix() {
        Matrix randomColumnMatrix = Matrix.makeRandomColumnMatrix(5, -10, 10);

        assertEquals(1, randomColumnMatrix.getCols());
        assertEquals(5, randomColumnMatrix.getRows());

        for (int i = 0; i < 5; i++) {
            assertTrue(randomColumnMatrix.getElement(i, 0) >= -10 && randomColumnMatrix.getElement(i, 0) <= 10);
        }

    }

    @Test
    void testImmutableMakeRandomColumnMatrix() {
        Immutable randomColumnMatrix = Immutable.makeRandomColumnMatrix(5, -10.0, 10.0);

        assertEquals(1, randomColumnMatrix.getCols());
        assertEquals(5, randomColumnMatrix.getRows());

        for (int i = 0; i < 5; i++) {
            assertTrue(randomColumnMatrix.getElement(i, 0) >= -10.0 && randomColumnMatrix.getElement(i, 0) <= 10.0);
        }

    }

    @Test
    void testInverse() {
        double[][] data = {
                {1, 1, 2},
                {1, 2, 1},
                {2, 1, 1}
        };
        Matrix matrix = new Matrix(data);
        Matrix inversedMatrix = matrix.inverse();

        double[][] expectedData = {
                {-0.25, -0.25, 0.75},
                {-0.25, 0.75, -0.25},
                {0.75, -0.25, -0.25}
        };
        Matrix expectedMatrix = new Matrix(expectedData);

        for (int i = 0; i < inversedMatrix.getRows(); i++) {
            for (int j = 0; j < inversedMatrix.getCols(); j++) {

                assertEquals(inversedMatrix.getElement(i, j), expectedMatrix.getElement(i, j), 1e-9);

            }
        }
    }


}