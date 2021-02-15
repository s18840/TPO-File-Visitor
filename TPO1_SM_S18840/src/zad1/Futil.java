package zad1;

import java.io.IOException;
import java.nio.file.*;

public class Futil extends SimpleFileVisitor {

    public static void processDir(String dirName, String resultFileName) {
        Path pathDirName = Paths.get(dirName);
        Path pathResultFileName = Paths.get(resultFileName);
        try {
            MyFileVisitor fileVisitor = new MyFileVisitor(pathResultFileName);
            Files.walkFileTree(pathDirName,fileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
