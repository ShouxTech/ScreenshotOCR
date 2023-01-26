import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCR {
    public static Tesseract tesseractInstance = new Tesseract();

    {
        tesseractInstance.setDatapath("tessdata");
    }
    
    public static String doOCR(File input) throws TesseractException {
        String result = tesseractInstance.doOCR(input);
        return result;
    }
}
