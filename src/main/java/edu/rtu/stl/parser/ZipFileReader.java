package edu.rtu.stl.parser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

public class ZipFileReader {

    public List<String> readLines(Path path) {
        try {
            ArrayList<String> result = new ArrayList<>();
            ZipFile zipFile = new ZipFile(path.toFile());
            for (Object fileHeader : zipFile.getFileHeaders()) {
                System.out.println("reading file header: " + fileHeader);
                try (Scanner scanner = new Scanner(zipFile.getInputStream((FileHeader) fileHeader))) {
                    while (scanner.hasNext()) {
                        result.add(scanner.nextLine());
                    }
                }
            }
            return result;
        } catch (ZipException e) {
            return Collections.emptyList();
        }
    }
}
