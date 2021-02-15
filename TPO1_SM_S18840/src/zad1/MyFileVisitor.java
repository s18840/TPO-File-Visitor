package zad1;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;


public class MyFileVisitor extends SimpleFileVisitor<Path> {
    Charset charsetIn = Charset.forName("Cp1250");
    Charset charsetOut = Charset.forName("UTF-8");
    ByteBuffer byteBuffer;
    FileChannel fileChannel;

    public MyFileVisitor(Path fileChannelPath) throws IOException {
        this.fileChannel = FileChannel.open(fileChannelPath, StandardOpenOption.WRITE,StandardOpenOption.CREATE);
    }

    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        long atributesExtent = attrs.size();
        System.out.println("File "+path+" is "+atributesExtent+" bytes.");
        this.toUTF(FileChannel.open(path), atributesExtent);

        return FileVisitResult.CONTINUE;
    }

    public void toUTF (FileChannel inFileChannel,long bufferExtent) throws IOException {
        CharBuffer charBuffer ;
        ByteBuffer byteBufferEncoder;
        int toAllocate = (int)bufferExtent+1;

        byteBuffer=ByteBuffer.allocate(toAllocate);
        byteBuffer.clear();
        //byteBuffer.flip();
        inFileChannel.read(byteBuffer);
        byteBuffer.flip();
        charBuffer= charsetIn.decode(byteBuffer);
        byteBufferEncoder= charsetOut.encode(charBuffer);
        while (byteBufferEncoder.hasRemaining()){
            this.fileChannel.write(byteBufferEncoder);
        }
    }
}
