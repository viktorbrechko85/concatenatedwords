import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        InputOutputData inputOutputData = new InputOutputData("C:\\words.txt", "C:\\output.txt");
        List<String> inputListWords = inputOutputData.inputFromFile();//input from File
        Concatenatedwords concatenatedwords = new Concatenatedwords(inputListWords);
        List<String> outputListWords = concatenatedwords.getResultWordsList();
        inputOutputData.outputToFile(outputListWords);
        inputOutputData.outputToConsole(outputListWords);
    }
}


class Concatenatedwords{
    private List<String> inputList;
    private List<String> resultList;

    public Concatenatedwords(List<String> inputList) {
        this.inputList = inputList;
        this.resultList = new ArrayList<>();
    }
    /*
     *
     *   Get array with concatenated words
     *
     */
    public List<String> getResultWordsList(){
        List<String> listsOfWords = sortStrArrayList(inputList, 0);
        resultList = sortStrArrayList(getListLongestConcatenateWords(listsOfWords), 1);
        return resultList;
    }

    private  List<String> getListLongestConcatenateWords(List<String> listsOfWords){
        if (listsOfWords.isEmpty())
            return null;
        List<String> result = new ArrayList<>();
        Set<String> shortWords = new HashSet<>();
        for (int i = 0; i < listsOfWords.size(); i++) {
            String word = listsOfWords.get(i);
            if (checkOccurences(shortWords, word) && (!word.equals(""))) {
                resultList.add(word);
            }
            shortWords.add(word);
        }
        return resultList;
    }
    /*
     *
     *   CheckOccurences
     *
     */
    private boolean checkOccurences(Set<String> shortWords, String word){
        if (shortWords.isEmpty())
            return false;
        boolean[] concatCheckOccurences = new boolean[word.length() + 1];
        concatCheckOccurences[0] = true;
        for (int i = 1; i <= word.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (!concatCheckOccurences[j])
                    continue;
                if (shortWords.contains(word.substring(j, i))) {
                    concatCheckOccurences[i] = true;
                    break;
                }
            }
        }
        return concatCheckOccurences[word.length()];
    }
    /*
     *
     *   Sorting of array
     *
     */
    private List<String> sortStrArrayList(List<String> arr, int descasc){
        //sort arrays by length before returning
        Comparator<String> comprator = null;
        if (descasc==1)
            comprator = (o1, o2) -> o2.length() - o1.length();
        else
            comprator = (o1, o2) -> o1.length() - o2.length();
        Collections.sort(arr, comprator);
        return arr;
    }
}

class InputOutputData{
    private String fileNameInput = null;
    private String fileNameOutput = null;

    public InputOutputData() {
    }

    public InputOutputData(String fileNameInput, String fileNameOutput) {
        this.fileNameInput = fileNameInput;
        this.fileNameOutput = fileNameOutput;
    }
    /*
     * input
     */
    public List<String> inputFromFile() throws IOException {
        List<String> lines = null;
        if (fileNameInput==null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter full output filename");
            fileNameInput = reader.readLine();
            reader.close();
        }
        try{
            lines = Files.readAllLines(Paths.get(fileNameInput), StandardCharsets.UTF_8);
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    /*
     * output
     */
    public void outputToConsole(List<String> output) throws IOException {
        if (output.isEmpty())
            return ;
        System.out.println(getOutputResult(output));
    }
    public void outputToFile(List<String> output) throws IOException {
        if (output.isEmpty())
            return ;
        if (fileNameOutput==null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter full filename with words");
            fileNameOutput = reader.readLine();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileNameOutput);
            String result = getOutputResult(output);
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }
    }

    private String getOutputResult(List<String> output){
        if (output.isEmpty())
            return "No data";
        List<String> longestword = new ArrayList<>();
        List<String> secondlongestword = new ArrayList<>();
        int length = output.get(0).length();
        int lensec = 0;
        longestword.add(output.get(0));
        for (int i = 1; i < output.size(); i++) {
            if (output.get(i).length()==length){
                longestword.add(output.get(i));
            }else if(output.get(i).length()>=lensec){
                lensec = output.get(i).length();
                secondlongestword.add(output.get(i));
            }
        }
        StringBuilder word = new StringBuilder();
        if (longestword.size()>1)
            word.append("The longest concatenated word are " + System.getProperty("line.separator"));
        else
            word.append("The longest concatenated word is ");
        for (int i = 0; i < longestword.size(); i++) {
            word.append(longestword.get(i)+"; Lenght - " + longestword.get(i).length()+ System.getProperty("line.separator"));
        }
        //writer.write(word);
        if (secondlongestword.size()>1)
            word.append("The 2nd longest concatenated word are " + System.getProperty("line.separator"));
        else
            word.append("The 2nd longest concatenated word is ");
        for (int i = 0; i < secondlongestword.size(); i++) {
            word.append(secondlongestword.get(i)+"; Lenght - " + secondlongestword.get(i).length()+ System.getProperty("line.separator"));
        }
        word.append("The total number of concatenated words is " + output.size());
        word.append(System.getProperty("line.separator"));
        return word.toString();
    }

}


