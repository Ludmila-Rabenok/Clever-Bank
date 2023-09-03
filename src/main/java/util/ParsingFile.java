package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class was created to save receipts to the check folder.
 */
public class ParsingFile {

    /**
     * This method writes to file
     *
     * @param list    of strings that will be saved in the file
     * @param idCheck receipt identifier, for naming a file with a receipt
     */
    public void writeAll(List<String> list, Integer idCheck) {
        try (FileWriter fw = new FileWriter(
                "c:\\Users\\User\\IdeaProjects\\Clever-Bank\\src\\check\\" + idCheck)) {
            for (String str : list) {
                fw.write(str);
                fw.flush();
            }
        } catch (IOException e) {
            System.err.println("Error during export data to file ");
            e.getStackTrace();
        }
    }

    /**
     * This method prepares strings. Moves each new line to another line.
     */
    public List<String> prepareForExport(List<String> list) {
        return list.stream().map(s -> s + "\n").collect(Collectors.toList());
    }
}
