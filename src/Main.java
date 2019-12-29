import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter full filename with words");
        String FILE_NAME = reader.readLine();//"C:\\wordsssss.txt";
        System.out.println("Enter full output filename");
        String FILE_OUTPUT = reader.readLine();//"C:\\output.txt";
        try{
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME), StandardCharsets.UTF_8);
            List<String> sortlist = sortStrArrayList(lines, 0);
            writeToFile(FILE_OUTPUT, sortStrArrayList(getListLongestConcatenateWords(sortlist),1));
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            reader.close();
        }

    }

    /*
     *
     *   Get array with concatenated words
     *
     */
    public static List<String> getListLongestConcatenateWords(List<String> arraylist){
        if (arraylist.isEmpty())
            return null;
        List<String> result = new ArrayList<>();
        Set<String> shortWords = new HashSet<>();
        for (int i = 0; i < arraylist.size(); i++) {
            String word = arraylist.get(i);
            if (checkOccurences(shortWords, word) && (!word.equals(""))) {
                result.add(word);
            }
            shortWords.add(word);
        }
        return result;
    }
    /*
     *
     *   CheckOccurences
     *
     */
    private static boolean checkOccurences(Set<String> shortWords, String word){
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
    private static List<String> sortStrArrayList(List<String> array, int descasc){
        //sort arrays by length before returning
        Comparator<String> comprator = null;
        if (descasc==1)
            comprator = (o1, o2) -> o2.length() - o1.length();
        else
            comprator = (o1, o2) -> o1.length() - o2.length();
        Collections.sort(array, comprator);
        return array;
    }

    /*
     * output
     */
    private static void writeToFile(String fileName, List<String> output) throws IOException {
        if (output.isEmpty())
            return ;
        FileWriter writer = null;
        List<String> longestword = new ArrayList<>();
        List<String> secondlongestword = new ArrayList<>();
        try {
            writer = new FileWriter(fileName);
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
            String word = "";
            if (longestword.size()>1)
                word = "The longest concatenated word are " + System.getProperty("line.separator");
            else
                word = "The longest concatenated word is ";
            for (int i = 0; i < longestword.size(); i++) {
                word = word+ longestword.get(i)+"; Lenght - " + longestword.get(i).length()+ System.getProperty("line.separator");
            }
            writer.write(word);
            if (secondlongestword.size()>1)
                word = "The 2nd longest concatenated word are " + System.getProperty("line.separator");
            else
                word = "The 2nd longest concatenated word is ";
            for (int i = 0; i < secondlongestword.size(); i++) {
                word = word+ secondlongestword.get(i)+"; Lenght - " + secondlongestword.get(i).length()+ System.getProperty("line.separator");
            }
            writer.write(word);
            word = "The total number of concatenated words is " + output.size();
            writer.write(word  + System.getProperty("line.separator"));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }


    }
}
