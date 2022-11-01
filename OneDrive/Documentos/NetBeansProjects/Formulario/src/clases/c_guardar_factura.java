
package clases;


public class c_guardar_factura {
    
    int idCliente;
    int idEmp;
    String NCF;
    String DNI;
    float subtotal;
    float itbis;
    float total;
    String estado;

    public c_guardar_factura() {
    }
    
  public c_guardar_factura(int idFact, int idCliente, int idEmp, String NCF, String DNI, float subtotal, float itbis, float total, String estado) {
       
        this.idCliente = idCliente;
        this.idEmp = idEmp;
        this.NCF = NCF;
        this.DNI = DNI;
        this.subtotal = subtotal;
        this.itbis = itbis;
        this.total = total;
        this.estado = estado;
    }
   

  
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
    }

    public String getNCF() {
        return NCF;
    }

    public void setNCF(String NCF) {
        this.NCF = NCF;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getItbis() {
        return itbis;
    }

    public void setItbis(float itbis) {
        this.itbis = itbis;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

   
}
