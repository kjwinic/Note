import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;

public class Clock extends Thread{  
  
    public void run() {  
        while (true) {  
            GregorianCalendar time = new GregorianCalendar();  
            int hour = time.get(Calendar.HOUR_OF_DAY);  
            int min = time.get(Calendar.MINUTE);  
            int second = time.get(Calendar.SECOND);  
            Note.lblState.setText("    当前时间：" + hour + ":" + min + ":" + second);  
            try {  
                Thread.sleep(1000);  
            } catch (InterruptedException exception) {  
            }  
  
        }  
    }  
}  