package sdemirci.packagechallenge;

import sdemirci.packagechallenge.data.Case;
import sdemirci.packagechallenge.data.CaseDeserializer;
import sdemirci.packagechallenge.exceptions.ApplicationArgumentException;
import sdemirci.packagechallenge.exceptions.ConstraintException;
import sdemirci.packagechallenge.exceptions.DeserializeException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    private final String filePath;
    private final CaseDeserializer caseDeserializer;
    private final PackingSolver packingSolver;

    public Application(String[] args, CaseDeserializer caseDeserializer, PackingSolver packingSolver) throws ApplicationArgumentException {
        this.caseDeserializer = caseDeserializer;
        this.packingSolver = packingSolver;

        filePath = validateAndExtractArgument(args);
    }

    public void run() throws DeserializeException {
        List<Case> cases = extractCasesFromFile(filePath, caseDeserializer);
        for (Case selectedCase : cases) {
            List<Integer> selectedItems = packingSolver.solve(selectedCase);
            if (selectedItems.isEmpty()) {
                System.out.println("-");
            } else {
                System.out.println(selectedItems.stream().map(String::valueOf).collect(Collectors.joining(",")));
            }
        }
    }

    private String validateAndExtractArgument(String[] args) throws ApplicationArgumentException {
        if (args == null || args.length != 1) {
            throw new ApplicationArgumentException("Given application arguments are not as expected. Only file path should be given as parameter");
        }

        validateFile(args[0]);

        return args[0];
    }

    private void validateFile(String filePath) throws ApplicationArgumentException {
        File file = new File(filePath);
        boolean fileExist = file.exists();
        if (!fileExist) {
            throw new ApplicationArgumentException("Given file path " + filePath + " is not valid");
        }
    }

    private List<Case> extractCasesFromFile(String filePath, CaseDeserializer caseDeserializer) throws DeserializeException {
        //Todo: This logic can be moved to another class
        //Todo: BufferReaderFactory can be implemented to avoid creating BufferReader instances at below code block. It increases testability of Application class
        List<Case> cases = new ArrayList<>();
        int lineCounter = 0;
        //It is assumed that file UTF-8 encoded
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            try {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    lineCounter++;
                    if (!line.trim().isEmpty()) {
                        cases.add(caseDeserializer.deserialize(line));
                    }
                }
            } catch (DeserializeException | ConstraintException deserializeException) {
                throw new DeserializeException("Error processing line " + lineCounter + ". Error: " + deserializeException.getMessage());
            }
        } catch (IOException ioException) {
            throw new DeserializeException("An error occurred while reading the file with error " + ioException.getMessage(), ioException);
        }

        return cases;
    }
}
