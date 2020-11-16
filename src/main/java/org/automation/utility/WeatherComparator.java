package org.automation.utility;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.automation.customexceptions.OutOfRangeException;

import java.util.List;

public class WeatherComparator  {

    public static Logger log = Logger.getLogger(WeatherComparator.class);

   static public String  compareTo(List apiTempList, List uiTempList) throws OutOfRangeException{

   for(int i = 0;i<apiTempList.size();i++)

        {
        int apiTemp=(int)apiTempList.get(i);
        int uiTemp=(int)uiTempList.get(i);
        if(apiTemp-uiTemp<Integer.parseInt(LocalConfig.TEMP_VAR)){
        return "success";
        }else{
         throw new OutOfRangeException("Difference is out of Range");
        }
        }
        return "fail";
  }

}






//           BasicConfigurator.configure();
//           log.info("Comparing temperature of UI and API");
//            for (int i=0;i<apilist.size();i++){
//            int diff=apilist.get(i) - UIlist.get(i);
//            int tempVariance=Integer.parseInt(LocalConfig.TEMP_VAR.toString());
//           // log.info(apilist.get(i)-UIlist.get(i));
//            if(diff < tempVariance){
//                log.info("success");
//            }else {
//                throw new OutOfRangeException("out of weather range exception");
//            }
//        }
    //}

