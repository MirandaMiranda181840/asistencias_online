/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entidades.Unidad;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author miran
 */
public class prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here...
        
        Unidad unidad1 = new Unidad("Nombre1", 1, "des", 1);
        Unidad unidad2 = new Unidad("Nombre2", 2, "des", 1);
        Unidad unidad3 = new Unidad("Nombre3", 3, "des", 1);
        Unidad unidad4 = new Unidad("Nombre4", 4, "des", 1);
        
        List<Unidad> array = new ArrayList();
        array.add(unidad2);
        array.add(unidad1);
        array.add(unidad4);
        array.add(unidad3);
       
        Collections.sort(array);
        
        for(Unidad elemento:array){
            System.out.println(elemento);
        }
        System.out.println("----------");
        array.removeAll(array);
        System.out.println(array);
        
        
        
       
        
    }
    
}
