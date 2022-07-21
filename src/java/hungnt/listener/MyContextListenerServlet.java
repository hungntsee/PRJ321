/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnt.listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Admin
 */
public class MyContextListenerServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        String realPath = context.getRealPath("/");
        String roadMapPath = realPath + "WEB-INF/roadMap.txt";

        FileReader fr = null;
        BufferedReader br = null;
        Map<String,String> roadMap = new HashMap<>();
        try {
            br = new BufferedReader(new FileReader(roadMapPath));
            while(br.ready()){
                String map = br.readLine();
                //System.out.println("Listener: "+ map);
                String[] mapVal = map.split("=");
                roadMap.put(mapVal[0], mapVal[1]);
            }
           context.setAttribute("ROAD_MAP", roadMap);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if(fr != null){
                    fr.close();
                }
            } catch (IOException ex) {
               ex.printStackTrace();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
