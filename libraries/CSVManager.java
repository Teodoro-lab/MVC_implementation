package libraries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import libraries.abominableFramework.Model;
import libraries.abominableFramework.ModelReflector;
import libraries.abominableFramework.PersistenceManager;
import models.Candidato;

public class CSVManager implements PersistenceManager {
    private String root;

    private boolean fileAlredyCreated(String fileName) {
        File f = new File(fileName);
        return (f.exists() && !f.isDirectory());
    }

    private void createFileForEntity(String className) throws IOException {
        String fileName = root + className;
        File entityFile = new File(fileName);
        entityFile.createNewFile();
    }

    private void createFileIfNotCreated(String fileName) {
        String filePath = root + fileName;
        if (!fileAlredyCreated(filePath)) {
            try {
                createFileForEntity(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Model model) {
    }

    @Override
    public Model get(String id) {
        return null;
    }

    // TODO Make real use of reflection and not depend on the Candidato class
    @Override
    public Model[] getAll(Model model) {
        ArrayList<Candidato> info = new ArrayList<>();
        Reader in;
        Iterable<CSVRecord> records;

        try {
            in = new FileReader(root + model.getClass().getName());
            records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {
                String name = record.get(0);
                String partidoPolitico = record.get(1);
                Long numeroVotos = Long.valueOf(record.get(2));
                Candidato candidato = new Candidato(name, partidoPolitico);
                candidato.setNumero_de_votos(numeroVotos);

                info.add(candidato);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Candidato[] candidatosArr = new Candidato[info.size()];
        candidatosArr = info.toArray(candidatosArr);

        return candidatosArr;
    }

    @Override
    public void parseModel() {

    }

    @Override
    public void store(Model model) {
        createFileIfNotCreated(model.getClass().getName());
        String filePath = root + model.getClass().getName();

        String[] info = ModelReflector.getModelInstanceAttributes(model);

        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(filePath),
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE);
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(info);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String[] toArray(CSVRecord rec) {
        String[] arr = new String[rec.size()];
        int i = 0;
        for (String str : rec) {
            arr[i++] = str;
        }
        return arr;
    }

    private static void print(CSVPrinter printer, String[] s) throws Exception {
        for (String val : s) {
            printer.print(val != null ? String.valueOf(val) : "");
        }
        printer.println();

    }

    // TODO
    @Override
    public void update(Model model) {
        File f = new File(root + model.getClass().getName());
        Class cls = model.getClass();

        try (CSVParser parser = new CSVParser(new FileReader(f), CSVFormat.DEFAULT)) {
            List<CSVRecord> list = new ArrayList();
            try {
                list = parser.getRecords();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            String edited = f.getAbsolutePath();

            String[] attributes = ModelReflector.getModelInstanceAttributes(model);

            f.delete();
            try (CSVPrinter printer = new CSVPrinter(new FileWriter(edited),
                    CSVFormat.DEFAULT.withRecordSeparator(System.getProperty("line.separator")))) {
                for (CSVRecord record : list) {
                    String[] s = toArray(record);

                    if (s[0].equalsIgnoreCase(attributes[0])) {
                        for (int i = 0; i < s.length; i++) {
                            s[i] = attributes[i];
                        }
                    }

                    print(printer, s);
                }
                parser.close();
                printer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("CSV file was updated successfully !!!");
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

}
