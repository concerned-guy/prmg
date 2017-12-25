import javafx.scene.paint.*;
import java.time.LocalDate;

public class Coloring {

    public static Color departmentColor(String department) {
	if (department.equals("PMAB"))
	    return Color.SEAGREEN;
	else if (department.equals("AMSN"))
	    return Color.DARKORCHID;
	else if (department.equals("EN"))
	    return Color.ROYALBLUE;
	else if (department.equals("SA"))
	    return Color.DARKORANGE;
	else if (department.equals("ICT"))
	    return Color.CRIMSON;
	else if (department.equals("WEO"))
	    return Color.LIGHTSEAGREEN;
	else if (department.equals("MST"))
	    return Color.TURQUOISE;
	else
	    return Color.HOTPINK;
    }

    public static Color moneyColor(int value) {
	if (value < 7500)
	    return Color.INDIGO;
	else if (value < 20000)
	    return Color.DODGERBLUE;
	else if (value < 30000)
	    return Color.DARKORANGE;
	else if (value < 40000)
	    return Color.ORANGERED;
	else
	    return Color.GOLD;
    }

    public static Color moneyColor(double value) {
	if (value < 7500)
	    return Color.INDIGO;
	else if (value < 20000)
	    return Color.DODGERBLUE;
	else if (value < 30000)
	    return Color.DARKORANGE;
	else if (value < 40000)
	    return Color.ORANGERED;
	else
	    return Color.GOLD;
    }

    public static Color binaryColor(int value) {
	if (value >= 0)
	    return Color.LIMEGREEN;
	else
	    return Color.RED;
    }

    public static Color binaryColor(double value) {
	if (value >= 0.0)
	    return Color.LIMEGREEN;
	else
	    return Color.RED;
    }

    public static Color finishedColor() {
	return Color.LIMEGREEN;
    }

    public static Color startDateColor(LocalDate startDate) {
	if (startDate.isAfter(LocalDate.now()))
	    return Color.DARKGREY;
	else
	    return Color.BLACK;
    }

    public static Color endDateColor(LocalDate endDate) {
	if (endDate != null)
	    return Color.LIMEGREEN;
	else
	    return Color.DARKGREY;
    }
}
