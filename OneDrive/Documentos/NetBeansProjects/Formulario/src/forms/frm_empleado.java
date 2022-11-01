package forms;

import Connection.myConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frm_empleado extends javax.swing.JFrame {
    DefaultTableModel Empleado;
    public frm_empleado() {
        initComponents();
        setLocationRelativeTo(null);
        this.Empleado = (DefaultTableModel) tbl_empleado.getModel();
        MostrarDatos("");
    }
    public void RefrescarTabla(){
        //Funcion encargada de Refrescar la tabla utilizando Revalidate
        try {
        Empleado.setColumnCount(0);
        Empleado.setRowCount(0);
        tbl_empleado.revalidate();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "error "+ex);
        }
    }
    public final void MostrarDatos(String valor){
            //Funcion para llenar la jtable de Usuarios desde la BD
            myConnection cc=new myConnection();
            Connection cn=myConnection.getConnection();
            RefrescarTabla();
            Empleado.addColumn("id_empleado");
            Empleado.addColumn("nombre_completo");
            Empleado.addColumn("dni");
            Empleado.addColumn("telefono");
            Empleado.addColumn("direccion_empleado");
            Empleado.addColumn("id_cargo");

            this.tbl_empleado.setModel(Empleado);
            String sql;
            if (valor.equals(""))
            {
            sql="SELECT Id_empleado, CONCAT(nombre_empleado,apellido_empleado),dni,telefono,direccion_empleado,id_cargo FROM empleado";
            }
           
            else{
            sql="SELECT Id_empleado, CONCAT(nombre_empleado, apellido_empleado),dni,telefono,direccion_empleado,id_cargo FROM empleado; WHERE nombre_empleado='"+valor+"'";
            } 

            String []datos=new String [6];
            try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
            datos[0]=rs.getString(1);
            datos[1]=rs.getString(2);
            datos[2]=rs.getString(3);
            datos[3]=rs.getString(4);
            datos[4]=rs.getString(5);
            datos[5]=rs.getString(6);
           

            Empleado.addRow(datos);
            }
            tbl_empleado.setModel(Empleado);
            }catch(SQLException ex){
            Logger.getLogger(frm_cliente.class.getName()).log(Level.SEVERE,null,ex);
            JOptionPane.showMessageDialog(null, "error "+ex);
            }
        }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        pnl_empleado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_empleado = new javax.swing.JTable();
        lbl_empleado1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btn_select_empleado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Seleccione empleado...");

        jPanel2.setBackground(new java.awt.Color(219, 35, 35));

        pnl_empleado.setBackground(new java.awt.Color(255, 255, 255));
        pnl_empleado.setForeground(new java.awt.Color(51, 51, 51));

        tbl_empleado.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        tbl_empleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_empleado.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        tbl_empleado.setGridColor(new java.awt.Color(204, 204, 204));
        tbl_empleado.setRowHeight(24);
        tbl_empleado.setSelectionBackground(new java.awt.Color(255, 51, 51));
        tbl_empleado.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_empleado);

        lbl_empleado1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_empleado1.setFont(new java.awt.Font("Corbel Light", 0, 48)); // NOI18N
        lbl_empleado1.setForeground(new java.awt.Color(170, 0, 0));
        lbl_empleado1.setText("Empleados");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/employee.png"))); // NOI18N

        btn_select_empleado.setBackground(new java.awt.Color(255, 255, 255));
        btn_select_empleado.setFont(new java.awt.Font("Corbel Light", 0, 24)); // NOI18N
        btn_select_empleado.setText("Seleccionar empleado");
        btn_select_empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_select_empleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_empleadoLayout = new javax.swing.GroupLayout(pnl_empleado);
        pnl_empleado.setLayout(pnl_empleadoLayout);
        pnl_empleadoLayout.setHorizontalGroup(
            pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_empleadoLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnl_empleadoLayout.createSequentialGroup()
                        .addComponent(lbl_empleado1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_select_empleado)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        pnl_empleadoLayout.setVerticalGroup(
            pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_empleadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(lbl_empleado1))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(btn_select_empleado)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(pnl_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(pnl_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_select_empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_select_empleadoActionPerformed
           if(tbl_empleado.getSelectedRow()>=0){
            
            try {
                DefaultTableModel Emp = (DefaultTableModel) tbl_empleado.getModel();
                String nombre = String.valueOf(Emp.getValueAt(tbl_empleado.getSelectedRow(),1));
                int id = Integer.parseInt((String) Emp.getValueAt(tbl_empleado.getSelectedRow(),0));
                frm_menu.txt_nom_emp_fact.setText(nombre);
                frm_menu.id_empleado = id;
                this.dispose();
            } catch(NumberFormatException ex) {
            Logger.getLogger(frm_cliente.class.getName()).log(Level.SEVERE,null,ex);
            JOptionPane.showMessageDialog(null, "error "+ex);
            }
            
        }
        
        else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            
        }
    }//GEN-LAST:event_btn_select_empleadoActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_select_empleado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_empleado1;
    private javax.swing.JPanel pnl_empleado;
    private javax.swing.JTable tbl_empleado;
    // End of variables declaration//GEN-END:variables
}
