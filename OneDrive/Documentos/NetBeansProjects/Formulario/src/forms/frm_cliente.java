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

public class frm_cliente extends javax.swing.JFrame {

    // creacion del modelo de la tabla
    DefaultTableModel Cliente;

    // metodo constructor que inicia el form y asigna el modelo de la tabla a la tabla

    
    public frm_cliente() {
        initComponents();
        setLocationRelativeTo(null);
        this.Cliente = (DefaultTableModel) tbl_cliente.getModel();
        MostrarDatos("");
    }

    public void RefrescarTabla() {
        //Funcion encargada de Refrescar la tabla utilizando Revalidate
        try {
            Cliente.setColumnCount(0);
            Cliente.setRowCount(0);
            tbl_cliente.revalidate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }

    public final void MostrarDatos(String valor) {
        //Funcion para llenar la jtable de Usuarios desde la BD
        myConnection cc = new myConnection();
        Connection cn = myConnection.getConnection();
        RefrescarTabla();
        Cliente.addColumn("id_cliente");
        Cliente.addColumn("nombre_cliente");
        Cliente.addColumn("apellido_cliente");
        Cliente.addColumn("telefono_cliente");
        Cliente.addColumn("correo_cliente");
        Cliente.addColumn("dni_cliente");
        Cliente.addColumn("direccion_cliente");
     
        this.tbl_cliente.setModel(Cliente);
        String sql;
        if (valor.equals("")) {
            sql = "SELECT * from cliente";
        } else {
            sql = "SELECT * from cliente WHERE id_cliente='" + valor + "'";
        }

        String[] datos = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);

                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
               

                Cliente.addRow(datos);
            }
            tbl_cliente.setModel(Cliente);
        } catch (SQLException ex) {
            Logger.getLogger(frm_cliente.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_usuario = new javax.swing.JLabel();
        img_producto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_cliente = new javax.swing.JTable();
        btn_select_user = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Seleccione usuario...");

        jPanel1.setBackground(new java.awt.Color(219, 35, 35));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        lbl_usuario.setFont(new java.awt.Font("Corbel Light", 0, 48)); // NOI18N
        lbl_usuario.setForeground(new java.awt.Color(170, 0, 0));
        lbl_usuario.setText("Seleccione un cliente");

        img_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user.png"))); // NOI18N

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tbl_cliente.setAutoCreateRowSorter(true);
        tbl_cliente.setFont(new java.awt.Font("Corbel Light", 0, 18)); // NOI18N
        tbl_cliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_cliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_cliente.setGridColor(new java.awt.Color(255, 153, 153));
        tbl_cliente.setRowHeight(24);
        tbl_cliente.setSelectionBackground(new java.awt.Color(255, 51, 51));
        tbl_cliente.getTableHeader().setResizingAllowed(false);
        tbl_cliente.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_cliente);

        btn_select_user.setBackground(new java.awt.Color(255, 255, 255));
        btn_select_user.setFont(new java.awt.Font("Corbel Light", 0, 24)); // NOI18N
        btn_select_user.setText("Seleccionar usuario");
        btn_select_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_select_userActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_select_user)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_usuario)
                        .addGap(392, 392, 392)
                        .addComponent(img_producto)))
                .addGap(54, 54, 54))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_usuario)
                    .addComponent(img_producto))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_select_user)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_select_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_select_userActionPerformed
        if(tbl_cliente.getSelectedRow()>=0 ){
            
            try {
               
                int id = Integer.parseInt((String) Cliente.getValueAt(tbl_cliente.getSelectedRow(), 0));   
                String dni = String.valueOf(Cliente.getValueAt(tbl_cliente.getSelectedRow(),5));
                 String nom_cliente = (String) Cliente.getValueAt(tbl_cliente.getSelectedRow(), 1);
                
                frm_menu.id_cliente = id;
                frm_menu.txt_dni_usu_fact.setText(dni);
                frm_menu.txt_nom_user_fact.setText(nom_cliente);
                
                
                this.dispose();
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error: "+ex);
            }
            
        }
        
        else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente, por favor");
            
        }
    }//GEN-LAST:event_btn_select_userActionPerformed

  
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_select_user;
    private javax.swing.JLabel img_producto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_usuario;
    private javax.swing.JTable tbl_cliente;
    // End of variables declaration//GEN-END:variables

}
