import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*; 
public class snapVisualizer
{
    public static void main (String[] args) throws Exception
    {
       JFrame window = new JFrame();
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       int length = 900;
       int width = 750;
       window.setBounds(0, 0, length, width);
       //info is an array of each snap user
       ArrayList<snapInfo> info = new ArrayList<snapInfo>();
       //creates an array of the snap data and reads in text file
       ArrayList<String> arr = new ArrayList<String>();
       try (BufferedReader br = new BufferedReader(new FileReader("snapData.txt")))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                arr.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
       //Creates the first user
       String dS = arr.get(2).substring(0, 4);
       int date = Integer.parseInt(dS);
       info.add(new snapInfo(arr.get(0), date, 255, 0, 0));
       //repeatName is used to avoid double counting users
       boolean repeatName = false; 
       //creates user specific colors at random
       Random rand = new Random();
       int r = rand.nextInt(255) + 25;
       int g = rand.nextInt(255) + 25;
       int b = rand.nextInt(255) + 25;
       //for loop finds and adds each user object to snapInfo class
       for (int x = 0; x < arr.size(); x += 3)
       {    
              for (int y = 0; y < info.size(); y++)
              {
                  if (arr.get(x).equals(info.get(y).getName()))
                        repeatName = true;
              }
              if (!repeatName)
              {
                  dS = arr.get(x+2).substring(0, 4);
                  date = Integer.parseInt(dS);
                  r = rand.nextInt(215) + 25;
                  g = rand.nextInt(215) + 25;
                  b = rand.nextInt(215) + 25;
                  info.add(new snapInfo(arr.get(x), date, r, g, b));
                }
              else
                  repeatName = false;  
        }
       JPanel p = new JPanel ()
        {   
           protected void paintComponent(Graphics a){ 
            //gets first and last date
            int startDate = info.get(0).getDate();
            int endDate = (info.get(info.size()-1).getDate())+1;
            int iterator = startDate;
            int date = Integer.parseInt(arr.get(2).substring(0, 4));
            int daySpace = length/21;
            //extra styling
            a.setColor(new Color(11, 170, 250));
            a.fillRect(0,0, length, 25);
            a.setColor(new Color(41, 44, 47));
            a.fillRect(0, width/2+53, length, width);
            a.setColor(new Color(150, 150, 150));
            a.fillRect(0, width/2+52, length, 2);
            while (iterator != endDate)
             {
              for (int x = 0; x < info.size(); x++)
                 {
                     for (int y = 2; y < arr.size(); y+=3)
                     {
                         //if user recieved snap on current date it is counted towards the sender
                         date = Integer.parseInt(arr.get(y).substring(0, 4));
                         if (date == iterator && arr.get(y-2).equals(info.get(x).getName()))
                         {
                             info.get(x).snapCount();
                            }
                        }
                    }
              int snapStreak = 0;
              for (int z = info.size()-1; z >= 0 ; z--)
              {
                 //skips ahead to next month
                  if ((iterator%100) == 31)
                    iterator += 69;
                  else 
                   {
                    double multiplyer = (info.get(z).getSnapCount()*1.0);
                    int newY = (int)multiplyer;
                    if (newY != 0)
                    {
                        //draws data point and keeps track of highest snap score
                        if (newY > snapStreak)
                            snapStreak = newY;
                        a.setColor(new Color(info.get(z).getR(), info.get(z).getG(), info.get(z).getB()));
                        a.fillRect(daySpace, ((length/2)-30)-newY, 5, 4);
                        a.setColor(new Color(info.get(z).getR(), info.get(z).getG(), info.get(z).getB(), 90));
                        a.fillRect(daySpace, ((length/2)-30)-newY, 5, newY-5);
                        }
                 }
                }
              //resets snap counts for current day
              for (int i = 0; i < info.size(); i++)
                    {
                        info.get(i).resetCount();
                    }
              //extra styling
              a.setColor(new Color(0, 0, 0));
              int space = 0;
              if (snapStreak > 99)
                    space = 8;
              else if (snapStreak > 9)
                    space = 5;
              else 
                    space = 1;
              if (snapStreak != 0)
              {
                  a.drawString(Integer.toString(snapStreak), daySpace-space, (length/2-32)-snapStreak);
              }
              else
                  a.drawString("|", daySpace, (length/2-30)-snapStreak);
              daySpace += (length)/30;
              snapStreak = 0; 
              iterator ++;
            }
            //Gets each users name by printing out info
            daySpace = (length)/30;
            int dayYSpace = (width/2)+85; 
            for (int x = 0; x < info.size(); x++)
            {
                if (x%5 == 0)
                {
                    dayYSpace += 35;
                    daySpace = 25;
                }
                a.setColor(new Color(info.get(x).getR(), info.get(x).getG(), info.get(x).getB()));
                a.drawString(info.get(x).getName(), daySpace, dayYSpace);
                daySpace += 185;
            }
            //extra styling
            a.setColor(new Color(0, 0, 0));
            a.fillRect(0, 25, length, 1);
            a.fillRect(0, (width/2)+51, length, 3);
            a.setColor(new Color(230, 230, 230));
            Font stringFont = new Font( "SansSerif", Font.BOLD, 12 ); 
            a.setFont(stringFont); 
            a.drawString((Integer.toString(info.get(0).getDate()).substring(0, 1))+"-"+(Integer.toString(info.get(0).getDate()).substring(1)), 30, (width/2)+70);
            a.drawString((Integer.toString(info.get(info.size()-1).getDate()).substring(0, 1))+"-"+(Integer.toString(info.get(info.size()-1).getDate()).substring(1)), length-65, (width/2)+70);
            a.drawString("<- Start Date", 70, width/2+70);
            a.drawString("End Date ->", length-150, width/2+70);
            a.drawString("~ Usernames ~", length/2-70, width/2+70);
            stringFont = new Font( "SansSerif", Font.BOLD, 10 ); 
            a.setFont(stringFont); 
            a.drawString("Made by Austin Franzen. Java 2020.", 25, 17);
         }
       };
       window.add(p);
       window.setVisible(true); 
    }
}