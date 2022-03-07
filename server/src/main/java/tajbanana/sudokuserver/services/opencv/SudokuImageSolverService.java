package tajbanana.sudokuserver.services.opencv;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class SudokuImageSolverService {

    public int[][] getSudokuMatrix(byte[] imageBytes) {
        Mat ImageMat = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_UNCHANGED);

        // Load trained network
        final MultiLayerNetwork trainedNetwork = EvalUtil.loadModel();

        // Load and process image
        final Mat processedImage = Utils.preProcessImage(ImageMat);
        // Load debugging image (same as one that is being processed)
        final Mat debuggingImage = ImageMat;

        // Mark outer rectangle and corners (we do this just for debugging, and since it is cool :P)
        Utils.markOuterRectangleAndCorners(processedImage, debuggingImage);

        // Remove all lines from processed image
        Utils.removeLines(processedImage);

        // Get sudoku matrix with estimated values
        final int[][] sudokuMatrix = Utils.getSudokuMatrix(processedImage, trainedNetwork);

        for (int[] row : sudokuMatrix) {
            System.out.println(Arrays.toString(row));
        }

        return sudokuMatrix;
    }

}
