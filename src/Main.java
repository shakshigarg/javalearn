import models.GFG;
import models.Student;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        Student s1=new Student("sakshi",1);
        Class classObj=s1.getClass();

        System.out.println(classObj.getName());

        Method[] Methods = classObj.getMethods();
        for(Method method:Methods){
            System.out.println(method.toString());
        }

        Field[] fields=classObj.getDeclaredFields();
        System.out.println("----------------Fields--------------");
        for(Field field:fields){
            System.out.println(field.toString());
        }

        Student s2=new Student("Mahi",2);
        System.out.println(classObj+" "+s2.getClass());
        GFG gfg=new GFG();
        System.out.println("1");
        gfg=new GFG();
        System.out.println("2");
        gfg=new GFG();
        System.out.println("3");
       gfg=new GFG();
        System.out.println("4");
    }
}