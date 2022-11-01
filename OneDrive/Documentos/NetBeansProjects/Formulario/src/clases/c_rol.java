
package clases;

/**
 *
 * @author 18432
 */
public class c_rol {
    int id_rol;
    String descripcion_rol;

    public c_rol() {
    }

    public c_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public c_rol(int id_rol, String descripcion_rol) {
        this.id_rol = id_rol;
        this.descripcion_rol = descripcion_rol;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getDescripcion_rol() {
        return descripcion_rol;
    }

    public void setDescripcion_rol(String descripcion_rol) {
        this.descripcion_rol = descripcion_rol;
    }
    
  
    @Override
      public String toString(){
        
          return descripcion_rol;
      }
      
    @Override
    public boolean equals(Object obj){
        
        return this.id_rol == ((c_rol) obj).id_rol;
    }
}
