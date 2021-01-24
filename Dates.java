import javax.sound.midi.SysexMessage;
import java.lang.*;
import java.util.*;
import java.util.regex.*;

public class Dates {


    /**
     *  Main method to get user input and decide the format of the date
     *  given by the user. Method will find the users format (/, -, <space>)
     *  and then split up day month year and put it into an array for use.
     *  The user's input will also be validated with
     *  methods checking each dd/mm/yy
     *
     * @param  args, which is not used at all.
     * */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter in a date: ");
        while(scan.hasNextLine()){
            try{
                boolean pass = true;
                String[] dateParts = new String[2];
                String format = "";
                String day = "";
                String month = "";
                String year = "";
                /* Could not find out why space could not be picked up with User input.
                 *  My friend Jaydin McMullan said scan.next() just needed to be scan.nextLine();.
                 *
                 * */
                String date = scan.nextLine();
                try{
                    if(date.contains("/")){
                        format = "/";
                        if(countFreq(date, format.charAt(0)) == 2){
                            dateParts = date.split("/");
                            day = dateParts[0];
                            month = dateParts[1];
                            year = dateParts[2];
                        }else{
                            System.err.println(date + " - INVALID: Enter in a date with only two separators.");
                        }
                    }else if(date.contains("-")){
                        format = "-";
                        if(countFreq(date, format.charAt(0)) == 2){
                            dateParts = date.split("-");
                            day = dateParts[0];
                            month = dateParts[1];
                            year = dateParts[2];
                        }else{
                            System.err.println(date + " - INVALID: Enter in a date with only two separators.");
                        }
                    }else if(date.contains(" ")){
                        format = " ";
                        if(countFreq(date, format.charAt(0)) == 2){
                            dateParts = date.split("\\s");
                            day = dateParts[0];
                            month = dateParts[1];
                            year = dateParts[2];
                        }else{
                            System.err.println(date + " - INVALID: Enter in a date with only two separators.");
                        }
                    }else{
                        System.err.println(date + " - INVALID: Enter in a valid date with one of the following separators; /, -, <space>. " );
                    }
                }catch (ArrayIndexOutOfBoundsException e){
                    System.err.println(date + " - INVALID: Enter in a date");
                }

                //Found this at http://www.javapractices.com/topic/TopicAction.do?Id=5
                if (!isDay(day, date)) {
                    System.err.println(date + " - INVALID: Validation for day has failed.");
                    pass = false;
                }
                if(!isMonth(day, month, year, date)){
                    System.err.println(date + " - INVALID: Validation for month has failed.");
                    pass = false;
                }
                if(!isYear(year, date)){
                    System.err.println(date + " - INVALID: Validation for year has failed.");
                    pass = false;
                }

                if(month.equals("JAN")){
                    month = "Jan";
                }else if(month.equals("FEB")){
                    month = "Feb";
                }else if(month.equals("MAR")){
                    month = "Mar";
                }else if(month.equals("APR")){
                    month = "Apr";
                }else if(month.equals("MAY")){
                    month = "May";
                }else if(month.equals("JUN")){
                    month = "Jun";
                }else if(month.equals("JUL")){
                    month = "Jul";
                }else if(month.equals("AUG")){
                    month = "Aug";
                }else if(month.equals("SEP")){
                    month = "Sep";
                }else if(month.equals("OCT")){
                    month = "Oct";
                }else if(month.equals("NOV")){
                    month = "Nov";
                }else if(month.equals("DEC")){
                    month = "Dec";
                }

                if(month.length() == 1 || month.length() == 2){
                    int monthInt = Integer.parseInt(month);
                    if(monthInt == 1){
                        month = "Jan";
                    }else if(monthInt == 2){
                        month = "Feb";
                    }else if(monthInt == 3){
                        month = "Mar";
                    }else if(monthInt == 4){
                        month = "Apr";
                    }else if(monthInt == 5){
                        month = "May";
                    }else if(monthInt == 6){
                        month = "Jun";
                    }else if(monthInt == 7){
                        month = "Jul";
                    }else if(monthInt == 8){
                        month = "Aug";
                    }else if(monthInt == 9){
                        month = "Sep";
                    }else if(monthInt == 10){
                        month = "Oct";
                    }else if(monthInt == 11){
                        month = "Nov";
                    }else if(monthInt == 12){
                        month = "Dec";
                    }
                }

                if(day.length() == 1){
                    day = "0"+day;
                }

                if(year.length() == 2){

                    int yearInt = Integer.parseInt(year);
                    if (yearInt >= 50){
                        yearInt += 1900;
                        year = String.valueOf(yearInt);
                    }else{
                        yearInt += 2000;
                        year = String.valueOf(yearInt);
                    }
                }



                if(pass){
                    System.out.println(day + " " + month + " " + year);
                }
            }catch(NumberFormatException e){
                e.getCause();
            }
        }

        scan.close();

    }

    private String convert(String day, String month, String Year){

        return "";
    }

    /**
     *  Count method returns how many times the format separator is
     *  in the User's input of date.
     *
     * @param  date, which is the String the user enters.
     * @param format, what format they entered in.
     * */
    private static int countFreq(String date, char format){
        int freq = 0;
        for(int i = 0; i < date.length(); i++){
            if(date.charAt(i) == format){
                freq++;
            }
        }
        return freq;
    }

    /**
     *  isDay Method validates if the users input for day is actually
     *  in the day range for the month. Also checks if the day is given
     *  with one or two digits.
     *
     * @param day is variable that needs validation
     * @return boolean for if day past checks
     *
     * */
    private static boolean isDay(String day, String date){
        int dayToInt = Integer.parseInt((day));
        if(day.length() == 2 || day.length() == 1){
            if(dayToInt > 0 && dayToInt <= 31){
                return true;
            }else{
                System.err.println(date + " - INVALID: Day needs to be between 1 - 31 for this month.");
            }

        }else{
            System.err.println(date + " - INVALID: Day needs to be either 1 or 2 digits.");
            return false;
        }
        return false;

    }

    /**
     *  isMonth method is used to validate User's input. First we check
     *  if the month is given in the right format through the use of regex.
     *  The second regex is to check if the month could have extra days if a leap year.
     *  The last block of code checks the month is a leap year and has extra day.
     *
     * @param day is variable that needs validation for month.
     * @param month is variable that is checked for format validation.
     * @param year is variable that is checked if leap year is true.
     * @return boolean for if day passes all checks.
     *
     * */
    private static boolean isMonth(String day, String month, String year, String date){
        // All Regex found from Derek Banas YouTube tutorial.
        int dayToInt = Integer.parseInt(day);
        String theRegex = "^((|JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC|Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec" +
                "|jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)|(0?[1-9]|1[0-2]))$";

        Pattern checkRegex = Pattern.compile(theRegex);
        Matcher regexMatcher = checkRegex.matcher(month);
        if(!regexMatcher.matches()){
            System.err.println(date + " - INVALID: Date is not in the correct format.");
            return false;
        }

        String theRegex2 = "^(APR|JUN|SEP|NOV|Apr|Jun|Sep|Nov|apr|jun|sep|nov" +
                "|4|6|9|11|04|06|09)$";

        Pattern checkRegex2 = Pattern.compile(theRegex2);
        Matcher regexMatcher2 = checkRegex2.matcher(month);
        if(regexMatcher2.matches() && dayToInt == 31 ){
            System.err.println(date + " - INVALID: Month has less that 31 days.");
            return false;
        }

        if(month.equals("FEB") || month.equals("feb") || month.equals("02") || month.equals("2")){
            if(!isLeapYear(year) && dayToInt > 29){
                System.err.println(date + " - INVALID: Pick a day between 0 - 29.");
                return false;
            }else if(!isLeapYear(year) && dayToInt > 28){
                System.err.println(date + " - INVALID: Pick a day between 0 - 28.");
                return false;
            }
        }
        return true;
    }

    /**
     *  IsLeapYear is a helper method for isMonth to find out
     *  if the month entered is within a leap year. If
     *  month is in a leap year this method will return true. If
     *  not then false.
     *
     * @param year to find out if year is a leap
     * @return boolean for if year passes checks
     *
     * */
    private static boolean isLeapYear(String year){
        int yearToInt = Integer.parseInt(year);
        //Got from TutorialsPoint forum.
        if (((yearToInt % 4 == 0) && (yearToInt % 100!= 0)) || (yearToInt % 400 == 0)){
            return true;
        }else{
            return false;
        }
    }

    /**
     *  IsYear method is used to validate the year from the User's input.
     *  If Year input is not two or four digits it will be fail. Year
     *  will also fail validation check if not in a year range.
     *
     * @param year to use for validation
     * @return boolean for if year passes checks
     *
     * */
    private static boolean isYear(String year, String date){
        int yearToInt = Integer.parseInt(year);
        try{
            if(year.length() != 2 && year.length() != 4){
                System.err.println(date + " - INVALID: Year needs to be either 2 or 4 digits long.");
                return false;
            }else if(year.length() == 2){
                if(yearToInt >= 50){
                    return true;
                }else{
                    return true;
                }
            }else if(yearToInt < 1753 || yearToInt > 3000){
                System.err.println(date + " - INVALID: Year out of range. Needs to be; 1753 and 3000 inclusive.");
                return false;
            }
        }catch (NumberFormatException e){
            System.err.println(date + " - INVALID: Enter in a date");
            return false;
        }
        return true;
    }

}
