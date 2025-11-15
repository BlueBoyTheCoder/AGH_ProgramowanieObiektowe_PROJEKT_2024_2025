package project.evolution.Statistics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CsvWriter {
    private String filePath;
    private List<String> columnNames;

    public CsvWriter(String filePath, List<String> columnNames) throws IOException {
        this.filePath = filePath;
        this.columnNames = new ArrayList<>(columnNames);

        if (!fileExists()) {
            try (FileOutputStream fos = new FileOutputStream(filePath);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                 PrintWriter writer = new PrintWriter(osw)) {

                writer.write("\uFEFF");
                writer.println(String.join(";", columnNames));
            }
        }
    }

    public void addRow(Map<String, Object> rowData) throws IOException {
        for (String column : columnNames) {
            if (!rowData.containsKey(column)) {
                throw new IllegalArgumentException("No value in column: " + column);
            }
        }

        List<String> values = new ArrayList<>();
        for (String column : columnNames) {
            values.add(Objects.toString(rowData.get(column), ""));
        }

        try (FileOutputStream fos = new FileOutputStream(filePath, true);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             PrintWriter writer = new PrintWriter(osw)) {

            String line = values.stream()
                    .map(this::escapeCsvField)
                    .collect(java.util.stream.Collectors.joining(";"));

            writer.println(line);
        }
    }

    private boolean fileExists() {
        try {
            java.io.File file = new java.io.File(filePath);
            return file.exists() && file.length() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private String escapeCsvField(String field) {
        if (field == null) {
            return "";
        }
        if (field.contains(";") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }

    public synchronized void saveDateToFile(ArrayList<String> statistics){
        try {
            Map<String, Object> row = new HashMap<>();
            row.put("Animals amount", statistics.get(0));
            row.put("Plants amount", statistics.get(1));
            row.put("Free tiles amount", statistics.get(2));
            row.put("Average energy", statistics.get(4));
            row.put("Dead animals average age", statistics.get(5));
            row.put("Average children amount", statistics.get(6));
            row.put("Most popular genotypes", statistics.get(3));
            this.addRow(row);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}