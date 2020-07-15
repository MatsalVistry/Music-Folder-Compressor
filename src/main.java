import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class main
{
    public static void main(String [] args) throws IOException
    {
        File f = new File("C:\\Vatsal\\Leaks Backup Git"+"\\");
        ArrayList<File> files = new ArrayList<>(Arrays.asList(f.listFiles()));
        ArrayList<Fileind> incomplete = new ArrayList<>();
        ArrayList<Fileind> currentComplete = new ArrayList<>();

        for(File fs : files)
            incomplete.add(new Fileind(fs,(double)fs.length()));

        int bundles = 0;
        int counter = 0;
        new File("C:\\Vatsal\\Leaks Backup Git"+"\\"+"Folder"+bundles+"\\").mkdir();
        while(!incomplete.isEmpty())
        {
            if(counter==5)
            {
                pack("C:\\Vatsal\\Leaks Backup Git"+"\\"+"Folder"+bundles+"\\","C:\\Vatsal\\Leaks Backup Git"+"\\"+"Folder"+bundles+".zip");
                if(new File("C:\\Vatsal\\Leaks Backup Git"+"\\"+"Folder"+bundles+".zip\\").length()>75000000 )
                {
                    bundles++;
                    new File("C:\\Vatsal\\Leaks Backup Git"+"\\"+"Folder"+bundles+"\\").mkdir();
                    counter = 0;
                }
                else
                {
                    File file = new File("C:\\Vatsal\\Leaks Backup Git"+"\\"+"Folder"+bundles+".zip\\");
                    file.delete();
                    counter = 0;
                }

            }
            else
            {
                Fileind current = incomplete.remove(0);

                String dir = current.f+"";
                dir = dir.replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("\\\\"));
                String[] arr = dir.split("\\\\");
                String newDir = "";
                for (int y = 0; y < arr.length - 1; y++)
                {
                    newDir += arr[y] + "\\";
                }
                newDir += "Folder"+bundles+"\\\\" + arr[arr.length - 1];


                Files.move(Paths.get(current.f+""),Paths.get(newDir));
                System.out.println(current.f);
            }
            counter++;
        }
        pack("C:\\Vatsal\\Leaks Backup Git"+"\\"+"Folder"+bundles+"\\","C:\\Vatsal\\Leaks Backup Git"+"\\"+"Folder"+bundles+".zip");

    }
    public static void pack(String sourceDirPath, String zipFilePath) throws IOException
    {
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p)))
        {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp).filter(path -> !Files.isDirectory(path)).forEach(path ->
            {
                ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                try
                {
                    zs.putNextEntry(zipEntry);
                    Files.copy(path, zs);
                    zs.closeEntry();
                }
                catch (IOException e)
                {
                    System.err.println(e);
                }
            });
        }
    }
    static class Fileind implements Comparable
    {
        File f;
        double size;

        public Fileind(File f, double size)
        {
            this.f=f;
            this.size=size;
        }

        @Override
        public int compareTo(Object o)
        {
            Fileind fi = (Fileind)o;

            if(fi.size<size)
                return 1;
            else
                return -1;
        }
    }

}