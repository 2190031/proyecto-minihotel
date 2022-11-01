
package clases;

public class c_detalle_factura {
    int idFact;
    int idProd;
    int cantidad_vendida;
    float importe;

    public c_detalle_factura() {
    }

    public c_detalle_factura(int idFact, int idProd, int cantidad_vendida, float importe) {
        this.idFact = idFact;
        this.idProd = idProd;
        this.cantidad_vendida = cantidad_vendida;
        this.importe = importe;
    }

    public int getIdFact() {
        return idFact;
    }

    public void setIdFact(int idFact) {
        this.idFact = idFact;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public int getCantidad_vendida() {
        return cantidad_vendida;
    }

    public void setCantidad_vendida(int cantidad_vendida) {
        this.cantidad_vendida = cantidad_vendida;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }
    
    
}
