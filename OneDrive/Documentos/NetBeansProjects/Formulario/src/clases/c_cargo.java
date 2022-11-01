/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author 18432
 */
public class c_cargo {
       int id_cargo;
       String descripcion;
    
       public c_cargo() {
    }

    public c_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }
    
    public c_cargo(int id_cargo, String descripcion) {
        this.id_cargo = id_cargo;
        this.descripcion = descripcion;
    }

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString(){
    
    return descripcion;
    }
    
       @Override
    public boolean equals(Object obj){
        
        return this.id_cargo == ((c_cargo) obj).id_cargo;
    }

   
        
}
