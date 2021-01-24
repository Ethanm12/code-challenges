import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * COSC326, SS, 2019. 
 * ETUDE#09: Lights On To Off (rev #1).
 *
 * @author David Carey <carda120@student.otago.ac.nz>
 * @author Ethan Morgan <moret434@student.otago.ac>
 */
public class Lights {
    /**
     * Matrix manipulation (RREF code taken from Rosetta Code & adapted) --
     *
     * @see: <https://rosettacode.org/wiki/Reduced_row_echelon_form>
     */
    
    /**
     * Puts supplied matrix <code>m</code> into reduced row echelon form via.
     * Gauss elimination.
     *
     * @param m 2D array (READ: Matrix) to reduce.
     * @return 2D matrix in reduced form (where possible).
     */
    static public int[][] reduce(int[][] matrix)
    {
	int lead = 0;
	int rowCount = matrix.length;
	int colCount = matrix[0].length;
	int i;
	boolean quit = false;

	for(int row = 0; row < rowCount && !quit; row++)
        {
	    if(colCount <= lead)
	    {
		quit = true;
		break;
	    }

	    i=row;

	    while(!quit && matrix[i][lead] == 0)
	    {
		i++;
		if(rowCount == i)
		{
		    i=row;
		    lead++;

		    if(colCount == lead)
		    {
			quit = true;
			break;
		    }
		}
	    }

	    if(!quit)
	    {
		swapRows(matrix, i, row);

		if(matrix[row][lead] != 0)
		    multiplyRow(matrix, row, 1.0f / matrix[row][lead]);

		for(i = 0; i < rowCount; i++)
		{
		    if(i != row)
			subtractRows(matrix, matrix[i][lead], row, i);
		}
	    }
	}

	return matrix;
    }

    /**
     * Transposes rows <code>row1</code> and <code>row2</code> of the given matrix.
     *
     * @param matrix Matrix to manipulate.
     * @param row1 First row to act upon.
     * @param row2 Second row to act upon.
     */
    static void swapRows(int[][] matrix, int row1, int row2)
    {
	int[] swap = new int[matrix[0].length];

	for(int col = 0; col < matrix[0].length; col++)
	    swap[col] = matrix[row1][col];

	for(int col = 0; col < matrix[0].length; col++)
        {
	    matrix[row1][col] = matrix[row2][col];
	    matrix[row2][col] = swap[col];
	}
    }

    /**
     * Multiply given <code>row</code> by the given <code>scalar</code>.
     *
     * @param m Matrix to manipulate.
     * @param row Row number to multiply.
     * @param scalar Scalar to multiply the row by.
     */
    static void multiplyRow(int[][] matrix, int row, double scalar)
    {
	for(int col = 0; col < matrix[0].length; col++)
	    matrix[row][col] *= scalar;
    }

    /**
     * Subtract a multiple of one row from another row.
     *
     * @param m Matrix to manipulate.
     * @param scalar Scalar factor.
     * @param subtract Row to subtract.
     * @param row Row to manipulate.
     */
    static void subtractRows(int[][] matrix, double scalar, int fromRow, int toRow)
    {
	for(int col = 0; col < matrix[0].length; col++)
	    matrix[toRow][col] -= scalar * matrix[fromRow][col];
    }
    
    /**
     * Received input from STDIN & solves the Lights On To Off puzzle.
     *
     * @param args CLI arguments.
     */
    public static void main(String[] args) {
	Scanner stdin = new Scanner(System.in);
	
	while (stdin.hasNextLine()) {
	    int edgeCount = 0;
	    int vertexCount = 0;
	    String vertices;
	    String edges;
	    
	    vertices = stdin.nextLine();
	    edges = stdin.nextLine();
	    StringTokenizer vertexTokens = new StringTokenizer(vertices);
	    StringTokenizer edgeTokens = new StringTokenizer(edges);
	    
	    while (vertexTokens.hasMoreTokens()) {
		vertexTokens.nextToken();
		vertexCount++;		
	    }	
	
	    int[][] adjacencyMatrix = new int[vertexCount][vertexCount+1];
	    
	    // Populate the adjacency matrix.
	    for (int row = 0; row < vertexCount; row++) {
		for (int column = 0; column < vertexCount; column++) {	
		    adjacencyMatrix[row][column] = 0;
		    // Set the implicit self-referential edge:
		    if (row == column) adjacencyMatrix[row][column] = 1;
		}
	    }

	    // Set the move vector:
	    for (int row = 0; row < vertexCount; row++) {
		adjacencyMatrix[row][vertexCount] = 1;
	    }

	    while (edgeTokens.hasMoreTokens()) {
		String edgeToken = edgeTokens.nextToken();
		int endVertex = (int)(edgeToken.charAt(0)-65);
		int startVertex = (int)(edgeToken.charAt(1)-65);
		edgeCount++;
		adjacencyMatrix[startVertex][endVertex] = 1;
	    }

	    adjacencyMatrix = reduce(adjacencyMatrix);

	    for (int i = 0; i < vertexCount; i++) {
		for (int j = 0; j <= vertexCount; j++) {
		    adjacencyMatrix[i][j] = Math.abs(adjacencyMatrix[i][j] % 2);
		}
	    }

	    boolean identityMatrix = true;
	    
	    for (int i = 0; i < vertexCount; i++) {
		if (adjacencyMatrix[i][i] != 1) identityMatrix = false;
	    }

	    System.err.println(vertices);
	    System.err.println(edges);
	    
	    if (identityMatrix) {
		System.out.println("No solution: All of the lights cannot be turned off.");		
	    } else {
		for (int row = 0; row < vertexCount; row++) {
		    char switchName = (char)(65+row);
		    boolean pressSwitch = (adjacencyMatrix[row][vertexCount] == 1);
		    
		    if (pressSwitch) {
			System.out.println("Press switch '" + switchName + "'");
		    }
		}

		System.out.println("Solution: All of the lights are turned off.");
	    }
	}
    }
}
