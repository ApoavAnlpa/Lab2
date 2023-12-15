package Lab2;

import java.util.Arrays;
import java.util.Objects;

public final class Immutable implements MatrixClass {
    private final int rows;
    private final int cols;
    private final double[][] data;

    // Пуста матриця
    public Immutable() {
        this.rows = 0;
        this.cols = 0;
        this.data = new double[0][0];
    }

    // Матриця заданого розміру
    public Immutable(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    // Матриця з двовимірного масиву
    public Immutable(double[][] data) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Пустий масив.");
        }
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            if (data[i].length != cols) {
                throw new IllegalArgumentException("Стовпчики різних розмірів.");
            }
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }

    // Копія іншої матриці
    public Immutable(MatrixClass matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Матриця не може бути null.");
        }
        this.rows = matrix.getRows();
        this.cols = matrix.getCols();
        this.data = new double[rows][cols];
        double[][] matrixData = matrix.getData();
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrixData[i], 0, this.data[i], 0, cols);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    // Отримання матриці
    public double[][] getData() {
        double[][] copy = new double[rows][];
        for (int i = 0; i < rows; i++) {
            copy[i] = data[i].clone(); // повертаємо копію рядка, щоб не змінювати елементи матриці
        }
        return copy;
    }

    // Отримання елемента за індексами
    public double getElement(int rowIndex, int colIndex) {
        if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < cols) {
            return data[rowIndex][colIndex];
        } else {
            throw new IndexOutOfBoundsException("Індекс виходить за розмірність матриці.");
        }
    }

    // Отримання рядка за індексом
    public double[] getRow(int row) {
        if (row >= 0 && row < rows) {
            return data[row].clone();
        } else {
            throw new IndexOutOfBoundsException("Індекс виходить за розмірність матриці.");
        }
    }

    // Отримання стовпчика за індексом
    public double[] getCol(int col) {
        if (col >= 0 && col < cols) {
            double[] column = new double[rows];
            for (int i = 0; i < rows; i++) {
                column[i] = data[i][col];
            }
            return column;
        } else {
            throw new IndexOutOfBoundsException("Індекс виходить за розмірність матриці.");
        }
    }

    // Отримання розмірності матриці
    public String getDimensions() {
        return rows + "x" + cols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Immutable that = (Immutable) o;

        if (getRows() != that.getRows()) return false;
        if (getCols() != that.getCols()) return false;
        return Arrays.deepEquals(getData(), that.getData());
    }

    @Override
    public int hashCode() {
        int result = getRows();
        result = 31 * result + getCols();
        result = 31 * result + Arrays.deepHashCode(getData());
        return result;
    }

    // Додавання матриць
    @Override
    public Immutable add(MatrixClass toAdd) {
        if (this.rows != toAdd.getRows() || this.cols != toAdd.getCols()) {
            throw new IllegalArgumentException("Розміри матриць повинні співпадати.");
        }

        double[][] newData = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newData[i][j] = this.data[i][j] + toAdd.getElement(i, j);
            }
        }
        return new Immutable(newData);
    }

    // Множення на скаляр
    @Override
    public Immutable multiply(double multiplier) {
        double[][] newData = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newData[i][j] = this.data[i][j] * multiplier;
            }
        }
        return new Immutable(newData);
    }

    @Override
    public Immutable multiply(MatrixClass matrix) {
        if (this.cols != matrix.getRows()) {
            throw new IllegalArgumentException("Кількість стовпців першої матриці повинна дорівнювати " +
                    "кількості рядків другої матриці для множення.");
        }
        double[][] resultData = new double[this.rows][matrix.getCols()];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                for (int k = 0; k < this.cols; k++) {
                    resultData[i][j] += this.data[i][k] * matrix.getElement(k, j);
                }
            }
        }
        return new Immutable(resultData);
    }

    public Immutable transpose() {
        double[][] transposedData = new double[this.cols][this.rows];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                transposedData[j][i] = this.data[i][j];
            }
        }
        return new Immutable(transposedData);
    }

    public static Immutable makeDiagonalMatrix(double[] vector) {
        double[][] data = new double[vector.length][vector.length];
        for (int i = 0; i < vector.length; i++) {
            data[i][i] = vector[i];
        }
        return new Immutable(data);
    }

    public static Immutable makeIdentityMatrix(int size) {
        double[][] data = new double[size][size];
        for (int i = 0; i < size; i++) {
            data[i][i] = 1.0;
        }
        return new Immutable(data);
    }

    public static Immutable makeRandomRowMatrix(int size, double lowerLimit, double upperLimit) {
        return new Immutable(Matrix.makeRandomRowMatrix(size, lowerLimit, upperLimit));
    }

    public static Immutable makeRandomColumnMatrix(int size, double lowerLimit, double upperLimit) {
        return new Immutable(Matrix.makeRandomColumnMatrix(size, lowerLimit, upperLimit));
    }

    public Immutable inverse() {
        return new Immutable((new Matrix(this)).inverse());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : data) {
            sb.append("[");
            for (double v : row) {
                sb.append(String.format("%9.4f", v));
                sb.append(" ");
            }
            sb.append("  ]\n");
        }
        return sb.toString();
    }

}