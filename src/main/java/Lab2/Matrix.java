package Lab2;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Matrix implements MatrixClass {
    private final int rows;
    private final int cols;
    private final double[][] data;

    // Пуста матриця
    public Matrix() {
        this.rows = 0;
        this.cols = 0;
        this.data = new double[0][0];
    }

    // Матриця заданого розміру
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    // Копія іншої матриці
    public Matrix(MatrixClass matrix) {
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

    // Матриця з двовимірного масиву
    public Matrix(double[][] data) {
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

    // Заповнення матриці випадковими числами від a до b
    public void Random(double a, double b) {
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = a + (b - a) * random.nextDouble();
            }
        }
    }

    // Встановлення значення елемента за індексом
    public void setElement(int rowIndex, int colIndex, double value) {
        if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < cols) {
            data[rowIndex][colIndex] = value;
        } else {
            throw new IndexOutOfBoundsException("Індекс виходить за розмірність матриці");
        }
    }

    // Встановлення значень елементів матриці двовимірним масивом
    public void setData(double[][] data) {
        if (data.length == rows && data[0].length == cols) {
            for (int i = 0; i < rows; i++) {
                if (data[i].length != cols) {
                    throw new IllegalArgumentException("Розміри вхідного масиву не відповідають розмірам матриці");
                }
                System.arraycopy(data[i], 0, this.data[i], 0, cols);
            }
        } else {
            throw new IllegalArgumentException("Розміри вхідного масиву не відповідають розмірам матриці");
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double[][] getData() {
        double[][] copy = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(data[i], 0, copy[i], 0, cols);
        }
        return copy;
    }

    // Отримання елемента за індексами
    public double getElement(int rowIndex, int colIndex) {
        if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < cols) {
            return data[rowIndex][colIndex];
        } else {
            throw new IndexOutOfBoundsException("Індекс виходить за розмірність матриці");
        }
    }

    // Отримання рядка за індексом
    public double[] getRow(int row) {
        if (row >= 0 && row < rows) {
            return data[row].clone(); // повертаємо копію рядка, щоб не змінювати елементи матриці
        } else {
            throw new IndexOutOfBoundsException("Індекс виходить за розмірність матриці");
        }
    }

    // Отримання стовпця за індексом
    public double[] getCol(int col) {
        if (col >= 0 && col < cols) {
            double[] column = new double[rows];
            for (int i = 0; i < rows; i++) {
                column[i] = data[i][col];
            }
            return column;
        } else {
            throw new IndexOutOfBoundsException("Індекс виходить за розмірність матриці");
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

        Matrix matrix = (Matrix) o;

        if (getRows() != matrix.getRows()) return false;
        if (getCols() != matrix.getCols()) return false;
        return Arrays.deepEquals(getData(), matrix.getData());
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
    public Matrix add(MatrixClass toAdd) {
        if (this.rows != toAdd.getRows() || this.cols != toAdd.getCols()) {
            throw new IllegalArgumentException("Розміри матриць повинні співпадати.");
        }

        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] + toAdd.getElement(i, j);
            }
        }
        return result;
    }

    // Множення на скаляр
    @Override
    public Matrix multiply(double multiplier) {
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] * multiplier;
            }
        }
        return result;
    }

    //Множення матриць між собою
    @Override
    public Matrix multiply(MatrixClass matrix) {
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
        return new Matrix(resultData);
    }

    //Транспонована матриця
    public Matrix transpose() {
        double[][] transposedData = new double[this.cols][this.rows];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                transposedData[j][i] = this.data[i][j];
            }
        }
        return new Matrix(transposedData);
    }

    public static Matrix makeDiagonalMatrix(double[] vector) {
        double[][] data = new double[vector.length][vector.length];
        for (int i = 0; i < vector.length; i++) {
            data[i][i] = vector[i];
        }
        return new Matrix(data);
    }

    public static Matrix makeIdentityMatrix(int size) {
        double[][] data = new double[size][size];
        for (int i = 0; i < size; i++) {
            data[i][i] = 1;
        }
        return new Matrix(data);
    }

    public static Matrix makeRandomRowMatrix(int size, double lowerLimit, double upperLimit) {
        Matrix randomRowMatrix = new Matrix(1, size);
        randomRowMatrix.Random(lowerLimit, upperLimit);
        return randomRowMatrix;
    }

    public static Matrix makeRandomColumnMatrix(int size, double lowerLimit, double upperLimit) {
        Matrix randomRowMatrix = new Matrix(size, 1);
        randomRowMatrix.Random(lowerLimit, upperLimit);
        return randomRowMatrix;
    }

    // Початкова перевірка перед підрахунком детермінанта
    public double determinant() {
        if (this.rows != this.cols) {
            throw new IllegalStateException("Детермінант може бути вирахуваним лише якщо матриця квадратна");
        }
        return determinant(this.data);
    }

    // Обчислення детермінанта матриці
    public static double determinant(double[][] matrixData) {
        double result = 0;
        if (matrixData.length == 1) {
            result = matrixData[0][0];
        } else if (matrixData.length == 2) {
            result = matrixData[0][0] * matrixData[1][1] - matrixData[0][1] * matrixData[1][0];
        } else {
            for (int i = 0; i < matrixData[0].length; i++) {
                double[][] minor = getMinor(matrixData, 0, i);
                result += matrixData[0][i] * Math.pow(-1, i) * determinant(minor);
            }
        }
        return result;
    }

    // Отримання мінору за рядком і стовпчиком
    private static double[][] getMinor(double[][] matrixData, int row, int column) {
        double[][] minor = new double[matrixData.length - 1][matrixData.length - 1];
        for (int i = 0; i < matrixData.length; i++) {
            for (int j = 0; i != row && j < matrixData[i].length; j++) {
                if (j != column) {
                    minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrixData[i][j];
                }
            }
        }
        return minor;
    }

    // Отримання оберненої матриці
    public Matrix inverse() {
        double det = determinant();
        if (Math.abs(det) < 1e-9) {
            throw new ArithmeticException("Матриця вироджена і не може бути обернена.");
        }

        // Заповнення приєднаної матриці
        double[][] adjugate = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                adjugate[j][i] = Math.pow(-1, i + j) * determinant(getMinor(this.data, i, j));
            }
        }

        Matrix inverseMatrix = new Matrix(adjugate);
        return inverseMatrix.multiply(1/det);
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