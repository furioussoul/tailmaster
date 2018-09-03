import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConsoleTest {


    @Test
    public void test() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        String begin = "2018-08-02 09:45";
        String end = "2018-08-02 18:00";

        double diff = (double)simpleDateFormat.parse(end).getTime()-simpleDateFormat.parse(begin).getTime();
        System.out.println(diff/1000/60/60);
    }
}
