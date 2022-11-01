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
public class c_categoria {
    int id_categoria;
    String descripcion_categoria;

    public c_categoria() {
    }

    public c_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public c_categoria(int id_categoria, String descripcion_categoria) {
        this.id_categoria = id_categoria;
        this.descripcion_categoria = descripcion_categoria;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getDescripcion_categoria() {
        return descripcion_categoria;
    }

    public void setDescripcion_categoria(String descripcion_categoria) {
        this.descripcion_categoria = descripcion_categoria;
    }
    
    @Override
    public String toString(){
    
    return descripcion_categoria;
    }
    
    @Override
    public boolean equals(Object obj){
        
        return this.id_categoria == ((c_categoria) obj).id_categoria;
    }
    
}
