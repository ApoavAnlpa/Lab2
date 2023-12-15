package Lab2;

public interface MatrixClass {
    int getRows();
    int getCols();
    double[][] getData();
    double getElement(int row, int col);
    double[] getRow(int row);
    double[] getCol(int col);
    String getDimensions();
    MatrixClass add(MatrixClass toAdd);
    MatrixClass multiply(double multiplier);
    MatrixClass multiply(MatrixClass matrix);
    MatrixClass transpose();
    MatrixClass inverse();
}