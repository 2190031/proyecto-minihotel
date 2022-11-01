/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import clases.*;

/**
 *
 * @author 18432
 */
public class c_proveedor {
       int id_proveedor;
       String nom_prov;
       String ape_prov;
       String tel_prov;
       String dir_prov;
    
       public c_proveedor() {
    }

    public c_proveedor(int id_cargo) {
        this.id_proveedor = id_cargo;
    }
    
    public c_proveedor(int id_proveedor, String nom_prov, String ape_prov, String tel_prov, String dir_prov) {
        this.id_proveedor = id_proveedor;
        this.nom_prov = nom_prov;
        this.ape_prov = ape_prov;
        this.tel_prov = tel_prov;
        this.dir_prov = dir_prov;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNom_prov() {
        return nom_prov;
    }

    public void setNom_prov(String nom_prov) {
        this.nom_prov = nom_prov;
    }

    public String getApe_prov() {
        return ape_prov;
    }

    public void setApe_prov(String ape_prov) {
        this.ape_prov = ape_prov;
    }

    public String getTel_prov() {
        return tel_prov;
    }

    public void setTel_prov(String tel_prov) {
        this.tel_prov = tel_prov;
    }

    public String getDir_prov() {
        return dir_prov;
    }

    public void setDir_prov(String dir_prov) {
        this.dir_prov = dir_prov;
    }

    
    
    @Override
    public String toString(){
    
    return nom_prov;
    }
    
       @Override
    public boolean equals(Object obj){
        
        return this.id_proveedor == ((c_proveedor) obj).id_proveedor;
    }

   
        
}
