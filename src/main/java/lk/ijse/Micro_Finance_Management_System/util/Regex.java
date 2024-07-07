package lk.ijse.Micro_Finance_Management_System.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFiledValid(TextField textField, String text){
        String field = "";

        switch (textField){
            case Id :
                field = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$";
                break;

            case Name:
                field = "^[a-zA-Z]+(?: [a-zA-Z]+)*$";
                break;

            case Address:
                field = "^([a-zA-Z0-9/\\\\''(),\\\\s-]{2,255})$";
                break;

            case Contact:
                field = "^(\\+?94)?(0)?(77|71|72|75|76|78|79|91|11|74)\\d{7}$";
                break;

            case Email:
                field = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;

            case Salary:
                field = "^(\\d+(\\.\\d{1,2})?)$";
                break;

            case Date:
                field = "^(0?[1-9]|1[0-2])/(0?[1-9]|[12][0-9]|3[01])/(?:[0-9]{2})?[0-9]{2}$";
                break;

            case Duration:
                field = "^[1-9][0-9]*$";
                break;

            case InterestRate:
                field = "^\\d+(\\.\\d)?$";
                break;


        }

        Pattern pattern = Pattern.compile(field);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextField location, javafx.scene.control.TextField field){
        if (Regex.isTextFiledValid(location,field.getText())){
            field.setStyle("-fx-border-color: green; -fx-border-width: 2 2 2 2;");
            return true;
        }else {
            field.setStyle("-fx-border-color: red; -fx-border-width: 2 2 2 2;");
            return false;
        }
    }
}
